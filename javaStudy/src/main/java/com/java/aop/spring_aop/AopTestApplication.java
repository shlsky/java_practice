package com.java.aop.spring_aop;

import com.java.model.School;
import com.java.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
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

	private static SayHelloImplOne sayHello = new SayHelloImplOne();

	/**
	 * java -Xms256m  -Xmx512m  -javaagent:/Users/songhongling/dada/growth-activity/docker/files/aspectjweaver
	 * -1.8.11.jar  -jar  target/growth-activity-1.0.0.jar  --spring.profiles.active=local
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(AopTestApplication.class, args);
		sayHello.testSpel(new Student().setName("shl").setSchool(new School().setName("光山一高")));
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AopTestApplication.class);
	}
}
