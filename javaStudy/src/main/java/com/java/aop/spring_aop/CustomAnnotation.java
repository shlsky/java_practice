package com.java.aop.spring_aop;

import java.lang.annotation.*;

/**
 * Created by hongling.shl on 2018/11/16.
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CustomAnnotation {
	
	String value() default "";
}
