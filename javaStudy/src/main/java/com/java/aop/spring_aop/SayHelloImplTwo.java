package com.java.aop.spring_aop;

import com.java.model.Student;
import org.springframework.stereotype.Service;

/**
 * @author Song Hongling
 * @date 2019/7/31
 */
@Service
public class SayHelloImplTwo implements SayHello<Student,Long> {


	/**
	 * 接口上的注解无法生效
	 *
	 * @param word
	 */
	@Override
	public void say(String word) {
	
	}
	
	@Override
	public void testSpel(Student student) {
	
	}
	
	public void testWithinAopTwo(){
		System.out.println("testWithinAopTwo");
	}
}
