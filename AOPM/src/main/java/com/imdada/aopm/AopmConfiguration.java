package com.imdada.aopm;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.dada.infra.metrics.client.api.MetricUtil;
import com.imdada.aopm.common.MonitorKeys;
import com.imdada.aopm.common.MonitorWindow;
import com.imdada.aopm.common.ResultStatusEnum;
import com.imdada.aopm.rpc.ServiceIpHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * dada-client configuration 在spring 启动后执行
 *
 * @author Song Hongling
 * @date 2019/7/16
 */
@Configuration
@Slf4j
public class AopmConfiguration {

    private final static String ERROR_SUFFIX = "_error.log";

    /**
     * Controller切面，统计接口的失败率
     */
    @Aspect
    @Component
    public class ControllerMonitorAspect implements InitializingBean {

        @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
        public void monitor() {
        }

        @Around("monitor()")
        public Object controllerMonitor(ProceedingJoinPoint pjp) throws Throwable {
            Object result = null;
            Throwable e = null;
            try {
                result = pjp.proceed(pjp.getArgs());
                return result;
            } catch (Throwable throwable) {
                e = throwable;
                throw throwable;
            } finally {
                statisticResponse(result, e);
            }
        }

        private void statisticResponse(Object result, Throwable thl) {

            try {
                HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
                    .getRequest();
                String uri = request.getServletPath();

                ResultStatusEnum statusEnum = MonitorWindow.judgeResultIsOk(result, thl);
                String[] tags = new String[] {MonitorKeys.TAG_URI,
                    uri, MonitorKeys.TAG_SOURCE_APP,
                    ServiceIpHolder.getServiceName(request.getRemoteAddr()), MonitorKeys.TAG_SOURCE_IP,
                    request.getRemoteAddr()};

                MetricUtil.increment(MonitorKeys.METRIC_REQUEST_ALL_COUNT, tags);

                if (ResultStatusEnum.FAIL.equals(statusEnum)) {
                    MetricUtil.increment(MonitorKeys.METRIC_REQUEST_FAIL_COUNT, tags);
                }
                if (ResultStatusEnum.EXCEPTION.equals(statusEnum)) {
                    MetricUtil.increment(MonitorKeys.METRIC_REQUEST_EXCEPTION_COUNT, tags);
                }

                MonitorWindow.fetchRateCounter(MonitorKeys.METRIC_REQUEST_FAIL_RATE, tags).calcFailRate(
                    ResultStatusEnum.OK.equals(statusEnum));
            } catch (Exception e) {
                log.warn("ControllerMonitorAspect执行异常", e);
            }
        }

        @Override
        public void afterPropertiesSet() throws Exception {
            log.info("ControllerMonitorAspect 注入成功");
        }
    }

    /**
     * 注入spring容器完成时间listener
     *
     * @return
     */
    @Bean
    public ApplicationListener<ApplicationReadyEvent> readyEventListenerMonitor() {

        return new ApplicationListener<ApplicationReadyEvent>() {
            /**
             * Handle an application event.
             *
             * @param applicationReadyEvent the event to respond to
             */
            @Override
            public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

                log.info("aopm注入spring容器完成事件listener");
                LoggerContext context = (LoggerContext)LoggerFactory.getILoggerFactory();

                List<Logger> loggers = context.getLoggerList();

                for (Logger logger : loggers) {
                    Iterator<Appender<ILoggingEvent>> appenderIterator = logger.iteratorForAppenders();
                    while (appenderIterator.hasNext()) {
                        Appender<ILoggingEvent> appender = appenderIterator.next();
                        addCatErrorFilter(appender);
                    }
                }
            }
        };
    }

    /**
     * 日志文件结尾为_error.log 的appender增加filter
     *
     * @param appender error log appender
     */
    private void addCatErrorFilter(Appender<ILoggingEvent> appender) {

        log.info("日志文件{}的appender增加filter成功", appender.getName());
        String fileName = (appender != null) && (appender instanceof FileAppender) ?
            ((FileAppender<ILoggingEvent>)appender).getFile() : "";

        //日志文件结尾为_error.log 的appender增加filter
        if (fileName != null && fileName.length() != 0 && fileName.endsWith(ERROR_SUFFIX)) {
            appender.addFilter(new Filter<ILoggingEvent>() {
                @Override
                public FilterReply decide(ILoggingEvent event) {
                    try {
                        if (event.getLevel() != null && event.getLevel().isGreaterOrEqual(Level.ERROR)) {
                            MetricUtil.increment(MonitorKeys.METRIC_LOG_ERROR_COUNT, MonitorKeys.TAG_LOG_LEVEL,
                                "error");
                        } else if (event.getLevel() != null && event.getLevel().isGreaterOrEqual(Level.WARN)) {
                            MetricUtil.increment(MonitorKeys.METRIC_LOG_ERROR_COUNT, MonitorKeys.TAG_LOG_LEVEL, "warn");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return FilterReply.NEUTRAL;
                }
            });
        }
    }
}
