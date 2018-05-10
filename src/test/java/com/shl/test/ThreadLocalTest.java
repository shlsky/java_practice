/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2018 All Rights Reserved.
 */
package com.shl.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import java.util.concurrent.TimeUnit;

import com.shl.practice.ThreadLocalVariable;
import org.junit.Test;

/**
 *
 * @author songhongling
 * @version $Id: ThreadLocalTest.java, v 0.1 2018年05月02日 下午5:36 songhongling Exp $
 */
public class ThreadLocalTest {

    private static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 10, 1000,
        TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10), new CallerRunsPolicy());

    @Test
    public void test(){
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.submit(new Runnable() {
                public void run() {
                    System.out.println(ThreadLocalVariable.getThreadLocal().get());
                }
            });
        }
    }
}