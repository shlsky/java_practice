/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2017 All Rights Reserved.
 */
package com.shl.practice;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.util.Assert;

/**
 * @author fufeng
 * @version $Id: CustomClassLoader.java, v 0.1 2017-11-10 下午5:53 fufeng Exp $$
 */
public class CustomClassLoader {

    public static void main(String[] args) throws Exception{
        ClassLoader myClassLoader = new ClassLoader() {
            /**
             * Loads the class with the specified <a href="#name">binary name</a>.
             * This method searches for classes in the same manner as the {@link
             * #loadClass(String, boolean)} method.  It is invoked by the Java virtual
             * machine to resolve class references.  Invoking this method is equivalent
             * to invoking {@link #loadClass(String, boolean) <tt>loadClass(name,
             * false)</tt>}.  </p>
             *
             * @param name The <a href="#name">binary name</a> of the class
             * @return The resulting <tt>Class</tt> object
             * @throws ClassNotFoundException If the class was not found
             */
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {

                /*这段逻辑验证了执行的loadClass()不同，就表示使用了不同的ClassLoader
                if (name != null){
                    return super.loadClass(name);
                }*/
                String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                InputStream is = getClass().getResourceAsStream(fileName);
                try {
                    if (is == null){
                        return super.loadClass(name);
                    } else {
                        byte[] b = new byte[is.available()];
                        is.read(b);
                        return defineClass(name,b,0,b.length);
                    }
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = myClassLoader.loadClass("com.shl.practice.CustomClassLoader").newInstance();
        Assert.isTrue(obj instanceof com.shl.practice.CustomClassLoader);
        System.out.println(obj.getClass().getClassLoader());
        System.out.println(com.shl.practice.CustomClassLoader.class.getClassLoader());
        System.out.println(obj instanceof com.shl.practice.CustomClassLoader);
    }
}