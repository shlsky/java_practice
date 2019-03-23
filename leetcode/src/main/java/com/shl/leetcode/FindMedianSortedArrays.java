package com.shl.leetcode;

/**
 * @author hongling.shl
 * @date 2019/3/13
 */
public class FindMedianSortedArrays {
	
	public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
		
		int[] shortArr = nums1.length > nums2.length ? nums2 : nums1;
		int[] longArr = nums1.length > nums2.length ? nums1 : nums2;
		int left1 = 0, left2 = 0;
		int right1 = shortArr.length - 1, right2 = longArr.length - 1;
		
		
		//表示在较长数组中的位置
		int half = (shortArr.length + longArr.length + 1) / 2 - 1;
		if (nums1.length == 0 || nums2.length == 0) {
			return (longArr[(left2 + right2) / 2] + longArr[(left2 + right2 + 1) / 2]) / 2.0;
		}
		
		double medianNums1 = 0;
		double medianNums2 = 0;
		
		//短数组只有一个元素时，直接在长数组中找到中位数的位置
		do {
			
			medianNums1 = (shortArr[(left1 + right1) / 2] + shortArr[(left1 + right1 + 1) / 2]) / 2.0;
			medianNums2 = (longArr[(left2 + right2) / 2] + longArr[(left2 + right2 + 1) / 2]) / 2.0;
			
			//留小不留大
			if (medianNums1 < medianNums2) {
				left1 = (left1 + right1) / 2;
				right2 = (left2 + right2 + 1) / 2 - 1;
			} else if (medianNums1 > medianNums2) {
				right1 = (left1 + right1 + 1) / 2 - 1;
				left2 = (left2 + right2) / 2;
			} else {
				return medianNums1;
			}
		} while (right1 - left1 > 0 && right2 - left2 > 0);
		
		if ((shortArr.length + longArr.length) % 2 == 0) {
			
			//取较大的两个元素
			if (right1 - left1 < 0 || shortArr[left1] < longArr[left2]) {
				return (longArr[left2] + longArr[left2 + 1]) / 2.0;
			} else if (shortArr[left1] > longArr[left2 + 1]) {
				return (shortArr[right1] + longArr[left2 + 1]) / 2.0;
			} else {
				return (shortArr[left1] + longArr[left2]) / 2.0;
			}
			
		} else {
			//取中间值
			if (shortArr[right1] >= longArr[left2] && shortArr[right1] <= longArr[left2 + 1]) {
				return shortArr[right1];
			} else if (shortArr[right1] < longArr[left2]) {
				return longArr[left2];
				
			} else {
				return longArr[left2 + 1];
			}
		}
	}
	
	
	public static void main(String[] args) {
		int[] nums1 = new int[]{1,2};
		int[] nums2 = new int[]{-1, 3};
		
		System.out.println(findMedianSortedArrays(nums1, nums2));
	}
	
	
}
