package com.raushan;

import java.util.HashSet;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        int[] nums = {1,1,0,0,1,1,1,0,1};
        System.out.println(numberOfSpecialChars("aaAbcBC"));
    }

    public static int numberOfSpecialChars(String word) {
        int n = word.length();
        Set<Character> st = new HashSet<>();
        char[] chars = word.toCharArray();
        for (char c : chars) {
            if (isUpperCase(c))
                st.add(c);
        }
        int count = 0;
        for (char c : chars) {
            if (!isUpperCase(c)) {
                char upperCase = (char) (c - 32);
                if (st.contains(upperCase)) {
                    count++;
                    st.remove(upperCase);
                }
            }
        }

        return count;
    }

    private static boolean isUpperCase(char c) {
        return c >= 'A' && c <= 'Z';
    }

}
