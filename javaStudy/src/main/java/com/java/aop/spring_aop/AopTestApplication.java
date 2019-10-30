package com.java.aop.spring_aop;

import java.lang.management.ManagementFactory;

import com.java.model.School;
import com.java.model.Student;
import com.sun.tools.attach.VirtualMachine;
import javassist.ClassPool;
import javassist.CtClass;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by hongling.shl on 2018/11/16.
 */
@SpringBootApplication
@Configuration
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class AopTestApplication extends SpringBootServletInitializer {

    //private static SayHelloImplOne sayHello = new SayHelloImplOne();

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {

            //方法一
            VirtualMachine vm = VirtualMachine.attach(ManagementFactory.getRuntimeMXBean().getName().split("@")[0]);
            vm.loadAgent(
                "/Users/songhongling/.m2/repository/org/aspectj/aspectjweaver/1.8.11/aspectjweaver-1.8.11.jar");
            vm.detach();
            new SayHelloImplOne().testSpel(new Student().setName("shl").setSchool(new School().setName("光山一高")));

            //方法二
            //Agent.getInstrumentation().addTransformer(
            //    (loader, className, classBeingRedefined, protectionDomain, classfileBuffer) -> {
            //        try {
            //            if (!SayHelloImplOne.class.getName().equals(className)) {
            //                return null;
            //            }
            //            ClassPool pool = new ClassPool();
            //            pool.appendSystemPath();
            //            CtClass cc = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
            //            final CtMethod method = cc.getMethod("testSpel", "()V");
            //            method.insertBefore("System.out.println(\"before testSpel\");");
            //            CtClass etype = ClassPool.getDefault().get("java.io.IOException");
            //            method.addCatch("{ System.out.println($e); throw $e; }", etype);
            //            return cc.toBytecode();
            //        } catch (Exception e) {
            //            e.printStackTrace();
            //        }
            //        return null;
            //    }, true);
            //
            //Agent.getInstrumentation().redefineClasses(new ClassDefinition(SayHelloImplOne.class, getByteArray()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        new SayHelloImplOne().testSpel(new Student().setName("shl").setSchool(new School().setName("光山一高")));
        SpringApplication.run(AopTestApplication.class, args);

    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AopTestApplication.class);
    }

    public static byte[] getByteArray() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        CtClass cc = pool.get(SayHelloImplOne.class.getName());
        return cc.toBytecode();
    }
}
