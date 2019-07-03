package com.java.aop.extend;

import com.java.aop.spring_aop.CustomAnnotation;
import com.java.aop.spring_aop.SayHello;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * Created by hongling.shl on 2018/11/16.
 */
public class SayHelloInstance extends SayHello implements InitializingBean,BeanPostProcessor {
	
	public static SayHelloInstance SINGLE;
	@CustomAnnotation(value = "")
	public void say1(){
		System.out.println("SayHelloInstance say1");
	}
	
	
	public static void main(String[] args) {
		SayHello instance = new SayHelloInstance();
		instance.say("hello");
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
	
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		SINGLE = (SayHelloInstance) bean;
		return bean;
	}
}
