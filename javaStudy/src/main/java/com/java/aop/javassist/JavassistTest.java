package com.java.aop.javassist;

import com.java.aop.spring_aop.SayHelloImplTwo;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

/**
 * @author Song Hongling
 * @date 2019/10/31
 */
public class JavassistTest {

    public static void main(String[] args) {

        try {
            // 定义类
            CtClass ctClass = ClassPool.getDefault().get("com.java.aop.spring_aop.SayHelloImplTwo");

            // 需要修改的方法名称
            String mname = "testWithinAopTwo";
            CtMethod mold = ctClass.getDeclaredMethod(mname);
            // 修改原有的方法名称
            String nname = mname + "$impl";
            mold.setName(nname);

            //使用原始方法名loop，定义一个新方法，在这个方法内部调用loop$impl
            CtMethod newMethod = CtNewMethod.make("public void "+mname+"(){" +
                    "long start=System.currentTimeMillis();" +
                    "System.out.println(\"耗时:\"+(System.currentTimeMillis()-start)+\"ms\");" +
                    ""+nname+"();" +//调用loop$impl
                    "System.out.println(\"耗时:\"+(System.currentTimeMillis()-start)+\"ms\");" +
                    "}"
                , ctClass);
            ctClass.addMethod(newMethod);
            ctClass.toClass();

            SayHelloImplTwo to1 = new SayHelloImplTwo();
            to1.testWithinAopTwo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
