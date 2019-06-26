package com.java.aop.extend;

import com.java.aop.spring_aop.CustomAnnotation;
import com.java.aop.spring_aop.SayHello;

/**
 * Created by hongling.shl on 2018/11/16.
 */
public class SayHelloInstance extends SayHello {
	
	
	@CustomAnnotation(value = "")
	public void say1(){
		System.out.println("SayHelloInstance say1");
	}
	
	
	public static void main(String[] args) {
		SayHello instance = new SayHelloInstance();
		instance.say("hello");
	}
}
