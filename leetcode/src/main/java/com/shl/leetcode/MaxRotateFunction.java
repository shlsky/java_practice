package com.shl.leetcode;

/**
 * @author hongling.shl
 * @date 2019/3/23
 */
public class MaxRotateFunction {
	
	public static int maxRotateFunction(int[] a) {
		if (a.length == 0) {
			return 0;
		}
		int max = 0x80000000;
		for (int i = 0; i < a.length; i++) {
			int ret = 0, swap = a[a.length - 1];
			for (int j = a.length - 1; j > 0; j--) {
				
				ret += j * a[j];
				a[j] = a[j - 1];
			}
			a[0] = swap;
			max = Math.max(max, ret);
		}
		return max;
	}
	
	public static int maxRotateFunctionV2(int[] a) {
		if (a.length == 0) {
			return 0;
		}
		int indexLong = a.length - 1;
		int max = 0x80000000, sum = a[0], ret = 0;
		for (int i = 1; i < a.length; i++) {
			sum += a[i];
			ret += i * a[i];
		}
		sum -= a[indexLong];
		
		for (int j = 1; j < a.length; j++) {
			max = Math.max(ret, max);
			ret = ret - a[(a.length - j) % a.length] * indexLong + sum;
			sum += a[(a.length - j) % a.length] - a[(indexLong - j) % a.length];
		}
		return Math.max(ret, max);
	}
	
	public static void main(String[] args) {
		int[] a = new int[]{1, 2, 3, 4};
		System.out.println(maxRotateFunctionV2(a));
	}
}
