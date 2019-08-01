package com.java.aop.spring_aop;

import com.java.model.Student;

/**
 * @author Song Hongling
 * @date 2019/7/31
 */
public interface SayHello<T, PK> {
	
	/**
	 * 接口上的注解无法生效
	 *
	 * @param word
	 */
	@CustomAnnotation(value = "'say-'+#word")
	void say(String word);
	
	@CustomAnnotation(value = "'ajfafjaf'")
	void testSpel(Student student);
}
