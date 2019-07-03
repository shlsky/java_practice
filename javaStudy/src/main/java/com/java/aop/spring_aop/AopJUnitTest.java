package com.java.aop.spring_aop;

import com.java.model.School;
import com.java.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by hongling.shl on 2018/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootTest(classes = AopTestApplication.class)
@Slf4j
public class AopJUnitTest {
	
	@Autowired
	private SayHello sayHello;
	
	/**
	 * 结论：spring aop代理是代理到方法级别，当同一个类的多个方法有advice时，会生成多个代理类，也就是说内部方法间调用
	 * 不会触发切面逻辑。(大错特错!!)
	 *
	 * 结论：spring cglib确实是subclass base proxy，但是这个subclass base proxy并没有织入advice，advice是运行时织入的
	 *
	 * note:methodProxy.invokeSuper(proxy,AopProxyUtils.adaptArgumentsIfNecessary(method, args))这个在执行sayHello.say();
	 * 时第二个接口被代理(是因为动态分派,然后走织入逻辑，第一次没走的原因是已经进intercept)
	 *
	 * methodProxy.invoke(proxy,AopProxyUtils.adaptArgumentsIfNecessary(method, args))表示执行代理类的方法
	 *
	 *
	 * CglibAopProxy 738 return this.methodProxy.invoke(this.target, this.arguments);这个决定了不能self-invocation
	 *
	 * DefaultAdvisorChainFactory ,CglibAopProxy,DynamicAdvisedInterceptor多看这三个类
	 *
	 * AbstractAutowireCapableBeanFactory.resolveBeforeInstantiation 先执行解析advisor的逻辑，
	 * 然后doCreateBean的过程中，执行beanPostProcesser.after逻辑，将bean转换为一个proxy。
	 */
	@Test
	public void test(){
//		sayHello.say("hello linfeng");
		sayHello.testSpel(new Student().setName("shl").setSchool(new School().setName("光山一高")));
	}
}
