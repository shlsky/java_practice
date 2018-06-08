package com.shl.hystrix.test;

import com.shl.hystrix.command.SayHelloExtendsCommand;
import org.junit.Test;

/**
 * Created by hongling.shl on 2018/6/7.
 */
public class CommandTest {
	
	@Test
	public void test(){
		SayHelloExtendsCommand command = new SayHelloExtendsCommand("groupKey","commandKey");
		command.execute();
	}
}
