package com.java.aop.jdkproxy;

import sun.misc.ProxyGenerator;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author shl.sky
 * @date 2017/11/3
 */
public class JdkDynamicProxyTest {
	
	public static void main(String[] args) {
		IDrive drive = new DriveImpl();
		InvocationHandler handler = new DynamicProxy(drive);
		
		IDrive proxyInstance = (IDrive) Proxy.newProxyInstance(handler.getClass().getClassLoader(), drive.getClass().getInterfaces(), handler);
		
		byte[] classFile = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{IDrive.class});
		
		//打印代理类
		try (FileOutputStream fos = new FileOutputStream("./IDriveProxy.class")) {
			fos.write(classFile);
			fos.flush();
			System.out.println("代理类class文件写入成功");
		} catch (Exception e) {
			System.out.println("写文件错误");
		}
		proxyInstance.doIt();
		
		
	}
	
	/**
	 * 接口
	 */
	public interface IDrive {
		
		/**
		 * 开车
		 */
		void doIt();
	}
	
	/**
	 * 被代理类
	 */
	public static class DriveImpl implements IDrive {
		@Override
		public void doIt() {
			System.out.println("drive car~");
		}
	}
	
	/**
	 * 代理handler
	 */
	public static class DynamicProxy implements InvocationHandler {
		/**
		 * 这个就是我们要代理的真实对象
		 */
		private Object real;
		
		/**
		 * 构造方法，给我们要代理的真实对象赋初值
		 */
		public DynamicProxy(Object real) {
			this.real = real;
		}
		
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			//在代理真实对象前我们可以添加一些自己的操作
			System.out.println("before rent house");
			
			System.out.println("Method:" + method);
			
			//当代理对象调用真实对象的方法时，其会自动的跳转到代理对象关联的handler对象的invoke方法来进行调用
			Object res = method.invoke(real, args);
			
			//在代理真实对象后我们也可以添加一些自己的操作
			System.out.println("after rent house");
			
			return res;
		}
	}
}
