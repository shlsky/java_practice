package com.java;

import com.java.exception.core.SystemException;
import org.springframework.util.Assert;

/**
 * Created by hongling.shl on 2019/1/18.
 */
public class ExceptionTest {
	
	public static void main(String[] args) {
		System.out.println(fo(6));
	}
	
	
	public static int fo(int  n) {
		int a1=0,a2=1,result=0;
		if (n<2){
			return n;
		}
		for(int i = 2;i<=n;i++){
			result = a1+a2;
			a1=a2;
			a2=result;
		}
		return result;
	}
}
