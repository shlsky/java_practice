package com.shl.leetcode;

/**
 * @author hongling.shl
 * @date 2019/3/23
 */
public class SubtreeWithAllDeepest {
	
	public class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;
		
		TreeNode(int x) {
			val = x;
		}
	}
	
	
	/**
	 * Definition for a binary tree node.
	 */
	class Solution {
		
		private TreeNode result = null;
		private int max = 0;
		
		public TreeNode subtreeWithAllDeepest(TreeNode root) {
			result = root;
			recursion(root, 0);
			return result;
		}
		
		/**
		 * 返回遍历到哪一层
		 * @param root
		 * @param depth
		 * @return
		 */
		public int recursion(TreeNode root, int depth) {
			
			if (root == null) {
				return depth;
			}
			
			int left = recursion(root.left, depth + 1);
			int right = recursion(root.right, depth + 1);
			
			//如果
			if (left == right && left >= max) {
				result = root;
				max = left;
			}
			return Math.max(left, right);
		}
	}
	
}
