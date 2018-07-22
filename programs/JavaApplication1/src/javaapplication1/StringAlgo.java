/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.lang.*;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;

/**
 *
 * @author sambit
 */
public class StringAlgo {

    public static class CommonAlgo {

        private static boolean isBalanced(String brackets) {

            if ((brackets.length() % 2) != 0) { //odd number of brackets cannot make an equillibrium
                return false;
            }

            HashMap<String, String> map = new HashMap();
            map.put(")", "(");
            map.put("]", "[");
            map.put("}", "{");

            LinkedList<String> stack = new LinkedList();
            for (int i = 0; i < brackets.length(); i++) {
                String bracket = "" + brackets.charAt(i);
                String peek = stack.peekFirst();
                String possibleOpenedBracket = map.get(bracket);
                if (possibleOpenedBracket != null && peek != null && peek.equals(possibleOpenedBracket)) {
                    stack.pop();
                } else {
                    stack.push(bracket);
                }
            }
            return stack.isEmpty();
        }

        public static void testIsBalanced(String[] brackets) {
            for (String bracket : brackets) {
                boolean balanced = isBalanced(bracket);
                String prettyPrintBool = balanced ? "YES" : "NO";
                System.out.println(prettyPrintBool);
            }
        }
    }

    public static class PatternMatch {

        public static class WildCard {

            public static boolean match(final String str, final String pattern) { //not finished - not as complex as I thought it is. will do it later
                /*  The wildcard pattern can include the characters ‘?’ and ‘*’
                    ‘?’ – matches any single character
                    ‘*’ – Matches any sequence of characters (including the empty sequence) 
                 */
                if (str == null || pattern == null) {
                    return false;
                }

                if (pattern.equals("*")) {
                    return true;
                }

                boolean match = true;
                char nextCharLookingFor = str.charAt(0); //default

                for (int originalIterator = 0, patternIterator = 0; originalIterator < str.length();) {
                    char originalChar = str.charAt(originalIterator);

                    if (nextCharLookingFor != originalChar || nextCharLookingFor == '#') {
                        originalIterator++;
                        if (nextCharLookingFor == '#') {
                            //reset 
                            nextCharLookingFor = '%'; //something unknown
                        }
                        continue;
                    }

                    match = match & true;
                    char patternChar = pattern.charAt(patternIterator);
                    if (originalChar == patternChar) {
                        match = match & true;
                        nextCharLookingFor = pattern.charAt(patternIterator + 1);
                    } else if (patternChar == '*') {
                        nextCharLookingFor = pattern.charAt(patternIterator + 1);
                    } else if (patternChar == '?') {
                        nextCharLookingFor = '#'; //any char
                    } else {
                        nextCharLookingFor = patternChar;
                        match = match & false;
                    }

                    originalIterator++;
                    patternIterator++;
                }

                return match;
            }
        }

        public static class KMP {

            private static int[] buildTempArray(final String pattern) {
                int[] arr = new int[pattern.length()];
                arr[0] = 0;
                for (int i = 1, j = 0; i < pattern.length();) {

                    char charAtJ = pattern.charAt(j);
                    char charAtI = pattern.charAt(i);
                    if (charAtI != charAtJ) {
                        //j becomes value of previous character of j
                        int prevIndexOfJ = j - 1;
                        if (prevIndexOfJ < 0) {
                            prevIndexOfJ = 0;
                        }
                        j = arr[prevIndexOfJ];
                        if (j == 0) {
                            arr[i] = 0;
                            i++;
                        }
                    } else {
                        arr[i] = j + 1;
                        j++;
                        i++;
                    }
                }
                return arr;
            }

            /*
            Points to remember in KMP:
            1. We shall not go back to previous starting point of original string if a mismatched character occurrs.
            2. Pattern also will not start from beginning after a mismatch.
            3. We shall check if previous string of the mismatch point of pattern has a suffix that is also a prefix.
             */
            public static int exec(final String str, final String pattern) {
                int[] arr = buildTempArray(pattern);

                int matchStart = -1;

                for (int i = 0, j = 0; i < str.length(); i++) {
                    char originalChar = str.charAt(i);
                    char patternChar = pattern.charAt(j);
                    if (originalChar == patternChar) {
                        if (matchStart == -1) {
                            matchStart = i - j;
                        }
                        j++;
                    } else {
                        matchStart = -1;
                        int prev = j - 1 < 0 ? 0 : j - 1;
                        j = arr[prev];
                        if (j != 0) {
                            i--;
                        }
                    }

                    if (j >= pattern.length()) {
                        break;
                    }

                    if (i + 1 >= str.length() && j + 1 >= pattern.length()) {
                        matchStart = -1;
                    }
                }
                return matchStart;
            }
        }

        public static class BruteForce {

            public static boolean exec(final String str, final String pattern) {
                boolean found = false;
                for (int i = 0; i < str.length(); i++) {
                    boolean oneCharMatch = false;
                    for (int j = 0, k = i; j < pattern.length(); j++, k++) {
                        char patternChar = pattern.charAt(j);
                        char originalChar = str.charAt(k);
                        if (patternChar == originalChar) {
                            oneCharMatch = true;
                        } else {
                            oneCharMatch = false;
                            break;
                        }
                    }
                    found = oneCharMatch == true;

                    if (found) {
                        break;
                    }
                }
                return found;
            }

            //I will not go back in original string but go back in pattern - DOES NOT WORK
            public static boolean exec2(final String str, final String pattern) {
                boolean match = false;
                for (int i = 0, j = 0; j < pattern.length() || i < str.length();) {
                    char originalChar = str.charAt(i);
                    char patternChar = pattern.charAt(j);
                    if (originalChar == patternChar) {
                        //keep on iterating over pattern as well as original string and see if reaches end of the pattern 
                        i++;
                        j++;
                        match = true;
                    } else {
                        //do not reset original str iterator.
                        //reset pattern iterator
                        if (j == 0) {
                            //if mismatch at beginning of the pattern - proceed.
                            //else hold on to the orginal position and start from there
                            i++;
                        }
                        j = 0; // so that it starts from beginning at the pattern again.
                        match = false;
                    }

                    if (i >= str.length() || j >= pattern.length()) { //break if out of bounds of original string or pattern string
                        break;
                    }
                }
                return match;
            }
        }
    }
}
