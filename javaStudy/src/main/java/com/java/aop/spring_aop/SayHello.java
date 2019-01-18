package com.java.aop.spring_aop;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

/**
 * Created by jackson on 16/2/19.
 */
@Service
@EnableAspectJAutoProxy(proxyTargetClass = true)
//@CustomAnnotation(value = "")
public class SayHello {
	
	@CustomAnnotation(value = "")
    public void say(){
		System.out.println("say" + this.getClass());
        say1();
    }
	@CustomAnnotation(value = "")
    public void say1(){
		System.out.println("say1" + this.getClass());
    }
}
