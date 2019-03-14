package com.shl.leetcode;

/**
 * @author hongling.shl
 * @date 2019/3/13
 */
public class FindMedianSortedArrays {
	
	public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
		int left1 = 0, left2 = 0;
		int right1 = nums1.length - 1, right2 = nums2.length - 1;
		double median1 = median(nums1, 0, nums1.length - 1);
		double median2 = median(nums2, 0, nums2.length - 1);
		
		double medianNums1 = 0;
		double medianNums2 = 0;
		
		while (right1 - left1 > 0 && right2 - left2 > 0) {
			medianNums1 = median(nums1, left1, right1);
			medianNums2 = median(nums2, left2, right2);
			
			if (medianNums1 <= medianNums2) {
				left1 = right1 > left1 ? (left1 + right1) / 2 + 1 : (left1 + right1) / 2;
				right2 = (left2 + right2) / 2;
			} else {
				right1 = (left1 + right1) / 2;
				left2 = right2 > left2 ? (left2 + right2) / 2 + 1 : (left2 + right2) / 2;
			}
		}
		
		//todo 处理只剩一个元素的情况，只剩一个元素需要特殊处理
		
		if ((nums1.length + nums2.length) % 2 == 0) {
			return (nums1[(left1+right1)/2] + nums2[(left2+right2)/2]) / 2.0;
		} else {
			return (nums1[left1] > nums2[left2]) ? nums1[left1] : nums2[left2];
		}
	}
	
	public static double median(int[] nums1, int left, int right) {
		if (right - left < 0) {
			return 0;
		} else {
			return (nums1[(left + right) / 2] + nums1[(left + right + 1) / 2]) / 2.0;
		}
		
	}
	
	public static void main(String[] args) {
		int[] nums1 = new int[]{1, 3, 5};
		int[] nums2 = new int[]{7,8};
		
		System.out.println(findMedianSortedArrays(nums1, nums2));
	}
	
	
}
