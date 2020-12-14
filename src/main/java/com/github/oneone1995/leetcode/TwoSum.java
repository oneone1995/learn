package com.github.oneone1995.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * leetcode-1 两数之和
 * @see <a href="https://leetcode-cn.com/problems/two-sum/" />
 */
public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        //map保存的是 key-当前元素,value-当前元素的索引
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) return new int[]{map.get(target - nums[i]), i};
            map.put(nums[i], i);
        }
        return new int[]{};
    }
}
