/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shl.practice;

/**
 *
 * @author songhongling
 * @version $Id: ThreadLocalVariable.java, v 0.1 2018年05月02日 下午5:44 songhongling Exp $
 */
public class ThreadLocalVariable {

    public final ThreadLocal<Long> threadLocal = new ThreadLocal<Long>(){
        @Override
        protected Long initialValue() {
            System.out.println("I am initial by thread "+Thread.currentThread().getName());
            return Thread.currentThread().getId();
        }
    };

    /**
     * Getter method for property threadLocal.
     *
     * @return property value of threadLocal
     */
    public ThreadLocal<Long> getThreadLocal() {
        return threadLocal;
    }
}