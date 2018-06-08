package com.shl.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * Created by hongling.shl on 2018/6/7.
 */
public class SayHelloExtendsCommand extends HystrixCommand<String> {
	private Throwable throwable;
	
	public SayHelloExtendsCommand(String groupKey, String commandKey) {
		super(
				HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
						.andCommandKey(HystrixCommandKey.Factory.asKey(commandKey))
						.andCommandPropertiesDefaults(
								HystrixCommandProperties.Setter()
										.withExecutionTimeoutInMilliseconds(1000)
										.withCircuitBreakerRequestVolumeThreshold(5)
										.withCircuitBreakerErrorThresholdPercentage(10)));
	}
	
	@Override
	protected String run() throws Exception {
		String res = null;
		try {
			System.out.println("hello");
		} catch (Exception e) {
			this.throwable = e;
			throw e;
		}
		
		return res;
	}
	
	@Override
	protected String getFallback() {
		System.out.println("invoke fallback ");
		if (this.throwable != null) {
			System.out.println(throwable.getLocalizedMessage());
		}
		System.out.println("--------");
		return "fallback";
	}
}
