package com.github.oneone1995.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 剑指 Offer 54. 二叉搜索树的第k大节点
 * @see
 * <a href="https://leetcode-cn.com/problems/er-cha-sou-suo-shu-de-di-kda-jie-dian-lcof/ />
 */
public class KthLargest {
    public int kthLargest(TreeNode root, int k) {
        List<Integer> result = new ArrayList<>(k);
        dfs(root, k, result);
        return result.get(k - 1);
    }

    private void dfs(TreeNode root, int k, List<Integer> result) {
        if (root == null) return;

        dfs(root.right, k, result);
        result.add(root.val);
        if (result.size() == k) return;
        dfs(root.left, k, result);
    }
}
