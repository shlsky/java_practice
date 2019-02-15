package com.shl.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hongling.shl
 * @date 2019/2/11
 */
public class DistinctString {
	
	public static void main(String[] args) {
		
		System.out.println(numDistinct("babgbag", "bag"));
		
	}
	
	public static int numDistinct(String s, String t) {
		
		List<Integer> ss = new ArrayList<>();
		
		int[][] dis = new int[2][s.length()];
		
		
		for (int i = 0; i < t.length(); i++) {
			if (i == 0) {
				for (int n = 0; n < s.length(); n++) {
					if (s.charAt(n) == t.charAt(i)) {
						dis[i][n] = 1;
					}
				}
				continue;
			}
			
			//清理数据
			for (int n = 0; n < s.length(); n++) {
				dis[i % 2][n] = 0;
			}
//			for (int m = 0; m < s.length(); m++) {
//				for (int k = m + 1; k < s.length(); k++) {
//					if (dis[(i - 1)%2][m] > 0 && s.charAt(k) == t.charAt(i)) {
//						dis[i%2][k] += dis[(i - 1)%2][m];
//					}
//				}
//			}
			ss.clear();
			for (int m = 0; m < s.length(); m++) {
				
				if (dis[(i - 1) % 2][m] > 0) {
					ss.add(m);
				}
				
				if (s.charAt(m) == t.charAt(i)) {
					for (Integer index : ss) {
						if (m>index){
							dis[i % 2][m] += dis[(i - 1) % 2][index];
						}
					}
				}
			}
		}
		int num = 0;
		for (int n = 0; n < s.length(); n++) {
			num += dis[(t.length() - 1) % 2][n];
		}
		
		return num;
	}
	
	
}
