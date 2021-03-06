package io.github.packagewjx.algorithm.leetcode;

import io.github.packagewjx.algorithm.LinkedList;
import io.github.packagewjx.algorithm.leetcode.TreeNode;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;

/**
 * Created by 吴俊贤 on 2017/4/3.
 */
public class TreeTraverse {
	public static void testConstructTreeQianxuZhongxu(Integer[] nodes) {
		TreeNode root = constructTree(nodes, 0);
		ArrayList<Integer> result = new ArrayList<>();
		forwardTraverse(root, result);
		Integer[] qianxu = new Integer[result.size()];
		result.toArray(qianxu);

		result.clear();
		inorderTraverse(root, result);
		Integer[] zhongxu = new Integer[result.size()];
		result.toArray(zhongxu);


		TreeNode node = constructTree(qianxu, 0, qianxu.length, zhongxu, 0, zhongxu.length);
		printTreeTraverse(node);
	}

	public static void testConstructTreeInteger(Integer[] nums) {
		TreeNode root = constructTree(nums, 0);
		printTreeTraverse(root);
	}

	public static void printTreeTraverse(TreeNode root) {
		ArrayList<Integer> result = new ArrayList<>();
		forwardTraverse(root, result);
		System.out.print("Forward:");
		for (Integer integer : result) {
			System.out.print(integer);
		}
		System.out.println();

		result.clear();
		System.out.print("Inorder:");
		backwardTraverse(root, result);
		for (Integer integer : result) {
			System.out.print(integer);
		}
		System.out.println();

		result.clear();
		System.out.print("Backward:");
		inorderTraverse(root, result);
		for (Integer integer : result) {
			System.out.print(integer);
		}
	}

	/**
	 * 递归建树，根据数组构建一棵树，null的位置则为没有子女
	 *
	 * @param treeVals 树上各节点数值的数组。若该位置为null，则代表为空节点
	 * @return
	 */
	public static TreeNode constructTree(Integer[] treeVals, int cur) {
		if (treeVals == null || cur >= treeVals.length || treeVals[cur] == null) {
			return null;
		}

		TreeNode root = new TreeNode(treeVals[cur]);
		root.left = constructTree(treeVals, cur * 2 + 1);
		root.right = constructTree(treeVals, cur * 2 + 2);

		return root;
	}


	/**
	 * 利用队列建树，当前节点紧跟后面的两个数字为子节点，null后面没有紧跟的节点了，比上一个节省空间
	 *
	 * @param tree
	 * @return
	 */
	public static TreeNode constructTreeInLeetCode(Integer[] tree) {
		if (tree == null || tree.length == 0)
			return null;

		TreeNode root = new TreeNode(tree[0]);

		TreeNode cur = root;

		LinkedList<TreeNode> treeNodes = new LinkedList<>();
		for (int i = 1; i < tree.length; i++) {
			if (tree[i] != null) {
				TreeNode left = new TreeNode(tree[i]);
				cur.left = left;
				treeNodes.offer(left);
			}
			i++;
			if (i < tree.length && tree[i] != null) {
				TreeNode right = new TreeNode(tree[i]);
				cur.right = right;
				treeNodes.offer(right);
			}
			if (treeNodes.size() == 0)
				break;
			cur = treeNodes.poll();
		}

		return root;
	}

	/**
	 * 前序遍历和中序遍历构造树
	 *
	 * @param qianxu  前序遍历数组
	 * @param qStart  前序遍历数组的开始
	 * @param qEnd    前序遍历数组的结束
	 * @param zhongxu 中序遍历数组
	 * @param zStart  中序遍历数组的开始
	 * @param zEnd    中序遍历数组的结束
	 * @return 根节点
	 */
	public static TreeNode constructTree(Integer[] qianxu, int qStart, int qEnd, Integer[] zhongxu, int zStart, int zEnd) {
		//首先检查数组
		if (qianxu == null || zhongxu == null) {
			return null;
		}
		if (qEnd - qStart != zEnd - zStart) {
			//两个数组大小不等，数组有问题，返回空
			return null;
		}
		if (qStart == qEnd) {
			//若是数组大小为0，返回空
			return null;
		}

		TreeNode root = new TreeNode(qianxu[qStart]);

		if (qStart + 1 == qEnd) {
			//只有一个元素时，返回本元素
			return root;
		}

		int i = zStart;
		/*
			第一步，在中序遍历数组中搜索这个根节点
		*/
		for (; i < zEnd; i++) {
			if (zhongxu[i] == root.val) {
				break;
			}
		}
		if (i == zEnd) {
			//在数组中没有搜到这个元素，因此是错误的，返回null
			return null;
		}

		root.left = constructTree(qianxu, qStart + 1, qStart + 1 + i - zStart, zhongxu, zStart, i);
		root.right = constructTree(qianxu, qStart + 1 + i - zStart, qEnd, zhongxu, i + 1, zEnd);

		return root;
	}


	public static void forwardTraverse(TreeNode node, ArrayList<Integer> result) {
		if (node == null) {
			return;
		}
		result.add(node.val);
		forwardTraverse(node.left, result);
		forwardTraverse(node.right, result);
	}

	public static void backwardTraverse(TreeNode node, ArrayList<Integer> result) {
		if (node == null) {
			return;
		}
		backwardTraverse(node.left, result);
		backwardTraverse(node.right, result);
		result.add(node.val);
	}

	public static void inorderTraverse(TreeNode node, ArrayList<Integer> result) {
		if (node == null) {
			return;
		}
		inorderTraverse(node.left, result);
		result.add(node.val);
		inorderTraverse(node.right, result);
	}
}
