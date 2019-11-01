package com.imdada.aopm.rpc;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import com.dada.base.registry.DiscoveryContext;
import com.dada.base.registry.consumer.ServiceInstance;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: yh
 * @Date: 2019/8/15 3:48 PM
 */
@Slf4j
public class ServiceIpHolder {

    private static Map<String, String> IP_SERVICE_MAP = new HashMap<>();

    private static ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
    ;

    static {
        executorService.scheduleAtFixedRate(() -> {
            try {
                refresh();
            } catch (Exception e) {
                log.error("ServiceIpHolder update exception", e);
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

    public static String getServiceName(String ip) {
        if (IP_SERVICE_MAP.containsKey(ip)) {
            return IP_SERVICE_MAP.get(ip);
        }
        return ip;
    }

    public static void refresh() {
        ServiceInstance discoveryNodes = DiscoveryContext.getRegisteredServiceInstance();

        String propertyName = "dependentServiceInstances";
        Object propertyValue = null;
        Class cls = discoveryNodes.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            try {
                //得到属性
                Field field = fields[i];
                field.setAccessible(true);
                //获取属性
                String name = field.getName();
                if (propertyName.equals(name)) {
                    propertyValue = field.get(discoveryNodes);
                    break;
                }
            } catch (IllegalAccessException e) {
                log.warn("ServiceIpHolder exception", e);
            }
        }
        Map<String, List<ServiceInstance>> serviceMap = null;
        if (propertyValue != null) {
            serviceMap = JSONObject.parseObject(JSONObject.toJSONString(propertyValue),
                new TypeReference<Map<String, List<ServiceInstance>>>() {});
        }
        if (serviceMap != null && serviceMap.size() > 0) {
            for (Entry<String, List<ServiceInstance>> entry : serviceMap.entrySet()) {
                for (ServiceInstance instance : entry.getValue()) {
                    IP_SERVICE_MAP.put(instance.getHost(), instance.getServiceName());
                }
            }
        }
    }
}
