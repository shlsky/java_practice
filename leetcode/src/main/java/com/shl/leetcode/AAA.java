package com.shl.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author hongling.shl
 * @date 2019/3/15
 */
public class AAA {
	
	public static int lengthOfLongestSubstring(String s) {
		
		if (s == null || s.length() < 1){
			return 0;
		}
		
		int[] charIndex = new int[52];
		for (int i = 0; i < charIndex.length; i++) {
			charIndex[i] = -1;
		}
		
		int start = 0, max = 0;
		for (int i = 0; i < s.length(); i++) {
			
			int arrIndex = s.charAt(i) - 'a';
			if (charIndex[arrIndex] == -1) {
				charIndex[arrIndex] = i;
			} else {
				start = charIndex[arrIndex] + 1;
				charIndex[arrIndex] = i;
			}
			
			max = max < (i - start + 1) ? (i - start + 1) : max;
			
		}
		return max;
	}
	
	public static void main(String[] args) {
		
		System.out.println(lengthOfLongestSubstring(" "));
	}
	
}
