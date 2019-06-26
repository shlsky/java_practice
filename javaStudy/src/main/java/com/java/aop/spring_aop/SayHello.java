package com.java.aop.spring_aop;

import com.java.model.Student;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

/**
 * Created by jackson on 16/2/19.
 */
@Service
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SayHello {
	
	@CustomAnnotation(value = "'say-'+#word")
    public void say(String word){
		System.out.println("say " + word);
    }
	
	@CustomAnnotation(value = "'ajfafjaf'")
    public void testSpel(Student student){
		
	}
}
