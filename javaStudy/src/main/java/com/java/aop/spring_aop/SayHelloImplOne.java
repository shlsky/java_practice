package com.java.aop.spring_aop;

import com.java.model.Student;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

/**
 *
 * @author jackson
 * @date 16/2/19
 */
@Service
public class SayHelloImplOne implements SayHello<Long,Object>,InitializingBean {
	
	public  static SayHelloImplOne self;
	
	@Override
	@CustomAnnotation(value = "'say-'+#word")
    public void say(String word){
		System.out.println("say " + word);
    }
	
	@Override
	@CustomAnnotation(value = "'ajfafjaf'")
    public void testSpel(Student student){
		
	}
	
	public void testWithinAopOne(){
		System.out.println("testWithinAopOne");
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		SayHelloImplOne.self = this;
	}
}
