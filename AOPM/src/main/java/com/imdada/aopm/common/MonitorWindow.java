package com.imdada.aopm.common;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSON;

import com.dada.infra.metrics.client.api.MetricUtil;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;

/**
 * 接口的计数器
 *
 * @author songhongling
 */
@Slf4j
public class MonitorWindow {

    private final static RateCounter DEFAULT_COUNTER = new RateCounter("", "") {
        @Override
        public void calcFailRate(boolean isOk) {}

        @Override
        public void monitorAndReset() {}
    };

    private final static ConcurrentHashMap<String, RateCounter> URI_COUNTER_MAP = new ConcurrentHashMap<>();

    private final static ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(1);

    static {
        threadPoolExecutor.scheduleAtFixedRate(() -> {
            try {
                log.info("MonitorWindow.scheduleAtFixedRate");
                URI_COUNTER_MAP.forEach((s, uriCounter) -> uriCounter.monitorAndReset());
            } catch (Exception e) {
                log.error("MonitorWindow upload monitor exception", e);
            }
        }, 10, 50, TimeUnit.SECONDS);
    }

    /**
     * 获取接口的计数器
     *
     * @return
     */
    public static RateCounter fetchRateCounter(String metric, String... tags) {

        String key = Joiner.on(":").skipNulls().join(tags).concat(":").concat(metric);

        if (!URI_COUNTER_MAP.containsKey(key)) {
            if (URI_COUNTER_MAP.size() > 100) {
                log.warn("URI_COUNTER_MAP is full");
                return DEFAULT_COUNTER;
            }
            URI_COUNTER_MAP.putIfAbsent(key, new RateCounter(metric, tags));
        }
        return URI_COUNTER_MAP.get(key);
    }

    /**
     * 判断结果是否成功
     *
     * @param response
     * @return
     */
    public static ResultStatusEnum judgeResultIsOk(Object response, Throwable e) {
        if (e != null) {
            return ResultStatusEnum.EXCEPTION;
        }

        if (Objects.isNull(response)) {
            return ResultStatusEnum.FAIL;
        } else {
            String status;
            if (response instanceof String) {
                status = JSON.parseObject(response.toString()).getString("status");
            } else {
                status = JSON.parseObject(JSON.toJSONString(response)).getString("status");
            }
            return ("ok".equalsIgnoreCase(status) || "success".equalsIgnoreCase(status)) ? ResultStatusEnum.OK
                : ResultStatusEnum.FAIL;
        }
    }

    /**
     * 接口失败率计数器
     */
    public static class RateCounter {

        private String metric;

        private String[] tags;

        /**
         * 接口窗口内请求量
         */
        private AtomicInteger allCount = new AtomicInteger(0);

        /**
         * 接口窗口内非成功量
         */
        private AtomicInteger failCount = new AtomicInteger(0);

        /**
         * 窗口起始时间戳
         */
        private LocalDateTime localDateTime = LocalDateTime.now();

        /**
         * 更新锁
         */
        private AtomicBoolean updateLock = new AtomicBoolean(true);

        /**
         * 构造器
         *
         * @param metric 指标
         * @param tags   tag
         */
        private RateCounter(String metric, String... tags) {
            this.metric = metric;
            this.tags = tags;
        }

        /**
         * 统计接口的请求量和失败量
         *
         * @param isOk 返回结果是否是成功
         */
        public void calcFailRate(boolean isOk) {

            if (updateLock.compareAndSet(true, false)) {
                allCount.incrementAndGet();
                if (!isOk) {
                    failCount.incrementAndGet();
                }
                updateLock.compareAndSet(false, true);
            }
        }

        /**
         * 上传接口失败率到monitor，并重置窗口计数器
         */
        public void monitorAndReset() {

            LocalDateTime now = LocalDateTime.now();

            if (now.isAfter(localDateTime.plusMinutes(1L)) && updateLock.compareAndSet(true, false)) {
                if (allCount.get() > 0) {
                    MetricUtil.gauge(this.metric, ((double)failCount.getAndSet(0)) / allCount.getAndSet(0), this.tags);
                } else {
                    MetricUtil.gauge(this.metric, 0, this.tags);
                }
                this.localDateTime = now;
                updateLock.compareAndSet(false, true);
            }
        }
    }
}
