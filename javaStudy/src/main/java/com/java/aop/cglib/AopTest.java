package com.java.aop.cglib;

import com.java.aop.spring_aop.SayHello;

/**
 * Created by jackson on 16/2/19.
 */
public class AopTest {

    public static void main(String[] args) {
        CglibProxy proxy = new CglibProxy();
        // 通过生成子类的方式创建代理类，子类织入逻辑后调用super.method，
		// 如果super.method中调用了原类中其他方法，则通过JVM动态分派机制派给子类
        SayHello proxyImp = (SayHello)proxy.getProxy(SayHello.class);
        
        proxyImp.say();
        
        //只输出一次
//        proxyImp.say1();
    }
}
