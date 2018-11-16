package com.java.aop.spring_aop;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by hongling.shl on 2018/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringBootTest(classes = AopTestApplication.class)
@Slf4j
public class AopJUnitTest {
	
	@Autowired
	private SayHello sayHello;
	
	/**
	 * 结论：spring aop代理是代理到方法级别，当同一个类的多个方法有advice时，会生成多个代理类，也就是说内部方法间调用
	 * 不会触发切面逻辑。
	 */
	@Test
	public void test(){
		sayHello.say1();
	}
}
