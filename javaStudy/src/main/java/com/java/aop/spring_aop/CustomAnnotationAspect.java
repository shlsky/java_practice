package com.java.aop.spring_aop;

import com.java.util.SpelUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by alan on 2018/5/28.
 */
@Aspect
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CustomAnnotationAspect {
	
    @Pointcut("@annotation(com.java.aop.spring_aop.CustomAnnotation)")
	public void logPointcut() {
    }

    @Around("logPointcut()")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
	
		System.out.println(pjp.getSignature().toString()+" 前置代理");
		System.out.println(getRediskey(pjp));
		Object res=  pjp.proceed();
		System.out.println(pjp.getSignature().toString()+" 后置代理");
		return res;
    }
	
	private String getRediskey(ProceedingJoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		MethodSignature methodSignature = (MethodSignature) signature;
		Method targetMethod = methodSignature.getMethod();
		Object target = joinPoint.getTarget();
		Object[] arguments = joinPoint.getArgs();
		CustomAnnotation annotation = getReadAnno(joinPoint);
		String spel=null;
		if(annotation != null){
			spel = annotation.value();
		}
		return SpelUtil.parse(target,spel, targetMethod, arguments);
	}
	
	private CustomAnnotation getReadAnno(ProceedingJoinPoint pjp) {
		CustomAnnotation readAnno = ((MethodSignature)pjp.getSignature()).getMethod().getAnnotation(CustomAnnotation.class);
		if (readAnno == null) {
			readAnno = pjp.getTarget().getClass().getAnnotation(CustomAnnotation.class);
		}
		
		return readAnno;
	}
}
