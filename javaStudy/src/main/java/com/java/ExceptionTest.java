package com.java;

import com.java.exception.core.SystemException;
import org.springframework.util.Assert;

/**
 * Created by hongling.shl on 2019/1/18.
 */
public class ExceptionTest {
	
	public static void main(String[] args) {
		try {
			Assert.notNull(null,"不能为空");
		} catch (Exception e){
			
			throw new SystemException("发生异常",e);
		}
	}
}
