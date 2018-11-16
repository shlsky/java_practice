package com.java.aop.spring_aop;

import org.springframework.stereotype.Service;

/**
 * Created by jackson on 16/2/19.
 */
@Service
@CustomAnnotation(value = "")
public class SayHello {
	
	
    public void say(){
        System.out.println("say");
        say1();
    }
    
    public void say1(){
        System.out.println("say1");
    }
}
