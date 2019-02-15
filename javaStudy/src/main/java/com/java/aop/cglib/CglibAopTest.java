package com.java.aop.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author jackson
 * @date 16/2/19
 */
public class CglibAopTest {
	
	public static void main(String[] args) {
		CglibProxy proxy = new CglibProxy();
		// 通过生成子类的方式创建代理类，子类织入逻辑后调用super.method，
		// 如果super.method中调用了原类中其他方法，则通过JVM动态分派机制派给子类
		SayHello proxyImp = (SayHello) proxy.getProxy(SayHello.class);
		
		proxyImp.say();
	}
	
	public static class CglibProxy {
		
		private Enhancer enhancer = new Enhancer();
		
		public Object getProxy(Class clazz) {
			//设置需要创建子类的类
			enhancer.setSuperclass(clazz);
			enhancer.setCallback(new ProxyCallback());
			//通过字节码技术动态创建子类实例
			return enhancer.create();
		}
	}
	
	public static class ProxyCallback implements MethodInterceptor {
		
		@Override
		public Object intercept(Object cglibProxyObject, Method originMethod, Object[] args, MethodProxy methodProxy) throws Throwable {
			System.out.println("前置代理" + methodProxy.getSignature().toString());
			
			//通过代理类调用父类中的方法
			//此处不能用proxy.invoke的原因是MethodProxy.FastClassInfo.f1 对自己的递归调用,所以要使用invokeSuper调用父类的方法
			Object result = methodProxy.invokeSuper(cglibProxyObject, args);
			System.out.println("后置代理");
			return result;
		}
	}
	
	public static class SayHello {
		
		public void say() {
			System.out.println("say" + this.getClass());
			say1();
		}
		
		public void say1() {
			System.out.println("say1" + this.getClass());
		}
	}
}
