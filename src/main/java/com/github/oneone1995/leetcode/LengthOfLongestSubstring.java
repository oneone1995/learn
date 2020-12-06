package com.github.oneone1995.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * leetcode-3 无重复字符的最长子串
 * @see
 * <a href="https://leetcode-cn.com/problems/longest-substring-without-repeating-characters/ />
 */
public class LengthOfLongestSubstring {
    public int lengthOfLongestSubstring(String s) {
        int pos = 0;
        int max = 0;

        Map<Character, Integer> map = new HashMap<>();
        for(int i = 0; i < s.length(); i++){
            Integer put = map.put(s.charAt(i), i);
            if (put != null && pos <= put) {
                //这里注意判断条件，只有pos比第一次出现重复位置小的时候才需要移动pos的位置
                //说明出现重复
                pos = put + 1;
            }

            max = Math.max(max, i - pos + 1);
        }
        return max;
    }
}
