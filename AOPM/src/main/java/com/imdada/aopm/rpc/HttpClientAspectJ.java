package com.imdada.aopm.rpc;

import java.net.URI;
import java.net.URISyntaxException;

import com.dada.infra.metrics.client.api.MetricUtil;
import com.imdada.aopm.common.MonitorKeys;
import com.imdada.aopm.common.MonitorWindow;
import com.imdada.aopm.common.ResultStatusEnum;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpUriRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: yh
 * @Date: 2019/8/27 4:24 PM
 */
@Aspect
public class HttpClientAspectJ {

    private static Logger logger = LoggerFactory.getLogger(HttpClientAspectJ.class);

    //@Pointcut("execution(* org.apache.http.impl.client.CloseableHttpClient+.
    // execute(org.apache.http.client.methods.HttpUriRequest,org.apache.http.client.ResponseHandler))")
    //注释掉
    public void wrapRpc() {
    }

    //@Around("wrapRpc()")
    //注释掉
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // monitor 打点
        URI uri = fetchRpcURI(joinPoint);
        Object result = null;
        Throwable thl = null;
        try {
            result = joinPoint.proceed();
            return result;
        } catch (Throwable e) {
            thl = e;
            throw e;
        } finally {
            statisticResponse(uri, result, thl);
        }
    }

    public static void statisticResponse(HttpUriRequest request, Object result, Throwable thl) {
        try {
            statisticResponse(new URI(request.getRequestLine().getUri()), result, thl);
        } catch (Exception e) {
            logger.warn("ControllerMonitorAspect执行异常", e);
        }
    }

    public static void statisticResponse(URI uri, Object result, Throwable thl) {
        try {
            if (uri != null) {

                ResultStatusEnum statusEnum = MonitorWindow.judgeResultIsOk(result, thl);
                String[] tags = new String[] {MonitorKeys.TAG_CALL_APP,
                    ServiceIpHolder.getServiceName(uri.getHost()), MonitorKeys.TAG_URI, uri.getPath()};

                MetricUtil.increment(MonitorKeys.METRIC_CALL_ALL_COUNT, tags);

                if (ResultStatusEnum.FAIL.equals(statusEnum)) {
                    MetricUtil.increment(MonitorKeys.METRIC_CALL_FAIL_COUNT, tags);
                }
                if (ResultStatusEnum.EXCEPTION.equals(statusEnum)) {
                    MetricUtil.increment(MonitorKeys.METRIC_CALL_EXCEPTION_COUNT, tags);
                }

                MonitorWindow.fetchRateCounter(MonitorKeys.METRIC_CALL_FAIL_RATE, tags).calcFailRate(
                    ResultStatusEnum.OK.equals(statusEnum));
            }
        } catch (Exception e) {
            logger.warn("ControllerMonitorAspect执行异常", e);
        }
    }

    public static URI fetchRpcURI(ProceedingJoinPoint joinPoint) {

        try {
            HttpRequest request = null;
            for (Object param : joinPoint.getArgs()) {
                if (param instanceof HttpRequest) {
                    request = (HttpUriRequest)param;
                }
            }
            return new URI(request.getRequestLine().getUri());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
