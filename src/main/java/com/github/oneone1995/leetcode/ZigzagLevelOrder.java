package com.github.oneone1995.leetcode;

import java.util.*;

/**
 * leetcode-103 二叉树的锯齿形层次遍历
 * @see
 * <a href="https://leetcode-cn.com/problems/binary-tree-zigzag-level-order-traversal/ />
 */
public class ZigzagLevelOrder {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        List<List<Integer>> result = new ArrayList<>();

        if (root == null) return result;

        /*
        思路和层次遍历一样，区别在于偶数行需要对当前行遍历结果反转，当然也可以直接用双端队列解决
         */
        int k = 0;//行数
        while (!queue.isEmpty()) {
            LinkedList<Integer> row = new LinkedList<>();
            int rowSize = queue.size();
            for (int i = 0; i < rowSize; i++) {
                TreeNode node = queue.poll();
                if (k % 2 != 0) {
                    row.addFirst(node.val);
                } else {
                    row.addLast(node.val);
                }
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }
            result.add(row);
            k++;
        }
        return result;
    }
}
