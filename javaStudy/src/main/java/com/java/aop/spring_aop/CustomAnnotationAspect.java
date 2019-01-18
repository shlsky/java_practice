package com.java.aop.spring_aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

/**
 * Created by alan on 2018/5/28.
 */
@Aspect
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CustomAnnotationAspect {
	
    @Pointcut("@annotation(com.java.aop.spring_aop.CustomAnnotation)")
//	@Pointcut("execution(* com.java.aop.spring_aop.SayHello.*(..))")
	public void logPointcut() {
    }

    @Around("logPointcut()")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {
	
		System.out.println(pjp.getSignature().toString()+" 前置代理");
		CustomAnnotation readAnno = getReadAnno(pjp);
		Object res=  pjp.proceed();
		System.out.println(pjp.getSignature().toString()+" 后置代理");
		return res;
		
//		if (readAnno == null){
//			System.out.println("我没有注解!!!!");
//			return pjp.proceed();
//		} else {
//			System.out.println("我有注解!!!!");
//			return pjp.proceed();
//		}
    }
    
	
	private CustomAnnotation getReadAnno(ProceedingJoinPoint pjp) {
		CustomAnnotation readAnno = ((MethodSignature)pjp.getSignature()).getMethod().getAnnotation(CustomAnnotation.class);
		if (readAnno == null) {
			readAnno = pjp.getTarget().getClass().getAnnotation(CustomAnnotation.class);
		}
		
		return readAnno;
	}
}
