package com.java.aop.spring_aop;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.Table;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

/**
 * java -Xms256m  -Xmx512m  -javaagent:/Users/songhongling/.m2/repository/org/aspectj/aspectjweaver/1.8.11/aspectjweaver-1.8.11.jar -jar  target/javaStudy-1.0-SNAPSHOT.jar
 * @author Song Hongling
 * @date 2019/7/31
 */

@Aspect
@Component
public class TargetAop {

    public static final String SQL = "Sql";
    public static final String SCHEMA_TABLE = "Schema.Table";
    public static final String WHERE_CRITERIA = "whereCriteria";

    @Pointcut("within(com.java.aop.spring_aop.SayHello+) && execution(* testSpel(..))")
    public void logPointcut() {
    }

    @Around("logPointcut()")
    public Object log(ProceedingJoinPoint pjp) throws Throwable {

        System.out.println(
            "----TargetAop----" + ((MethodSignature)pjp.getSignature()).getMethod().getName() + " 前置代理");
        Type[] infList = pjp.getSignature().getDeclaringType().getGenericInterfaces();
        String sql;
        Query queryAnnotation = ((MethodSignature)pjp.getSignature()).getMethod().getAnnotation(Query.class);
        if (queryAnnotation != null) {
            sql = queryAnnotation.value();
        } else {
            sql = String.format("%s.%s", pjp.getSignature().getDeclaringType().getSimpleName(),
                ((MethodSignature)pjp.getSignature()).getMethod().getName());
        }

        String schemaTable;

        Table table = null;
        for (Type inf : infList) {
            if (inf instanceof ParameterizedTypeImpl
                &&com.java.aop.spring_aop.SayHello.class.isAssignableFrom(((ParameterizedTypeImpl)inf).getRawType())) {
                System.out.println("implements SayHello.class");
                Type pkType = ((ParameterizedType)inf).getActualTypeArguments()[0];
                table = (Table)((Class)pkType).getAnnotation(Table.class);
                System.out.println("annotations");
            }
        }

        if (table != null){
            schemaTable = String.format("%s.%s", table.schema(),
                table.name());
        }


        String whereCriteria = null;
        whereCriteria = pjp.getArgs().toString();

        Object res = pjp.proceed();

        System.out.println("----TargetAop----" + pjp.getSignature().toString() + " 后置代理");

        return res;
    }
}
