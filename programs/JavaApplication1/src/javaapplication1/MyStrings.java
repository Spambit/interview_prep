package javaapplication1;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class MyStrings {

    private static class StringRange {
        int left;
        int right;

        public static StringRange create(int left, int right) {
            StringRange range = new StringRange();
            range.left = left;
            range.right = right;
            return range;
        }
    }

    private static ArrayList<StringRange> wordRanges(String sentence) {
        int i = 0, j = 0;
        ArrayList<StringRange> wordRanges = new ArrayList();
        for (; j < sentence.length(); j++) {
            char c = sentence.charAt(j);
            if (c == ' ') {
                // found a word
                StringRange wordRange = StringRange.create(i, j);
                j++; // whitespace
                i = j;
                wordRanges.add(wordRange);
            }
        }
        StringRange wordRange = StringRange.create(i, j);
        wordRanges.add(wordRange); // last word . //assumes does not have any trailing space in sentence
        return wordRanges;
    }

    private static String substringByRange(StringRange range, String str) {
        String s = "";
        for (int i = range.left; i < range.right; i++) {
            char c = str.charAt(i);
            s += c;
        }
        return s;
    }

    public static String reverseWords(String sentence) {
        ArrayList<StringRange> wordRanges = wordRanges(sentence);
        Collections.reverse(wordRanges);
        String newSentence = "";
        for (StringRange wordRange : wordRanges) {
            newSentence += " " + substringByRange(wordRange, sentence); // bug - first whitespace in new string
        }
        return newSentence;
    }

    public static String reverse(String s) {
        char[] arr = s.toCharArray();
        for (int i = 0, j = arr.length - 1; i < j; i++, j--) {
            char t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
        }
        return String.valueOf(arr);
    }

    public static String reverseInRange(String s, int startIndex, int endIndex) {
        char[] arr = s.toCharArray();
        String newStr = "";
        for (int j = endIndex; j >= startIndex; j--) {
            char t = arr[j];
            newStr += t;
        }
        return newStr;
    }

    public static String reverseAllWords(String sentence) {
        sentence += " ";
        String newSentence = "";
        for (int i = 0, j = 0; j < sentence.length(); j++) {
            char c = sentence.charAt(j);
            if (c == ' ') { // assumes first character is not space
                String newReversedWord = reverseInRange(sentence, i, j);
                newSentence += newReversedWord;
                i = j + 1;
            }
        }
        return newSentence;
    }

    public static class TelexEncoding { // O(1) space

        private static final HashMap<String, String> map = new HashMap();
        static {
            map.put(",", "COMMA");
            map.put(";", "SEMICOLON");
            map.put("?", "QUESTION MARK");
            map.put("!", "EXCLAIMATION MARK");
        }

        private static char[] makeRoomForExtraChars(String s) {
            int extraLength = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                String replaceWith = map.get("" + c);
                if (null != replaceWith) {
                    extraLength += replaceWith.length() - 1;
                }
            }
            char newChar[] = new char[extraLength + s.length()];
            for (int i = 0; i < s.length(); i++) {
                newChar[i] = s.charAt(i);
            }
            return newChar;
        }

        private static void shiftArrayToRight(char[] arr, int validLastIndex, int startIndex, int shiftLength) {
            for (int i = validLastIndex; i >= startIndex; i--) {
                if (arr[i] == 0) {
                    continue;
                }
                arr[i + shiftLength - 1] = arr[i];
                arr[i] = 0;
            }
        }

        private static void copyChars(char[] arr, String src, int startIndex) {
            for (int j = 0, i = startIndex; j < src.length(); i++, j++) {
                char srcChar = src.charAt(j);
                arr[i] = srcChar;
            }
        }

        private static String encodeCharArr(char[] arr, int originalLength) {
            int i = 0, totalShiftedLength = 0;
            for (; i < arr.length;) {
                char c = arr[i];
                String value = map.get("" + c);
                if (value != null) {
                    int shiftLength = value.length();
                    shiftArrayToRight(arr, originalLength + totalShiftedLength - 1, i + 1, shiftLength);
                    totalShiftedLength += shiftLength;
                    copyChars(arr, value, i);
                    i = totalShiftedLength;
                } else {
                    i++;
                }
            }
            return String.valueOf(arr);
        }

        public static String encode(String s) {
            char[] arr = makeRoomForExtraChars(s);
            return encodeCharArr(arr, s.length());
        }
    }

    public static class RunLengthEncoding {
        public static String encode(String s) { // pwwwwaaadexxxxxx = w4a3d1e1x6 - O(1) space
            String newStr = "";
            char current = s.charAt(0);
            int count = 1;
            for (int i = 1; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == current) {
                    count++;
                } else {
                    newStr += "" + current + count;
                    count = 1;
                }
                current = c;
            }
            newStr += "" + current + count;
            return newStr;
        }
    }

    public static class ValidIP {
        public static enum Part {
            doesNotStartWithZero {
                public boolean isValid(String part) {
                    return !part.equals("0") && !part.startsWith("0");
                }
            },
            withIn255 {
                public boolean isValid(String part) {
                    if (part.isEmpty()) {
                        return false;
                    }
                    return (Integer.valueOf(part) <= 255);
                }
            };
            public boolean isValid(String part) {
                return false;
            }
        }

        private static String makeInternal(String s, int maxParts, int startIndex, int endIndex, Integer finalPartCount,
                Integer maxIPLength) {
            Part[] checkers = new Part[] { Part.doesNotStartWithZero, Part.withIn255 };
            int maxLengthEachPart = 3;
            int minLength = maxParts;
            int maxLength = maxParts * maxLengthEachPart;
            if (s.length() > maxLength) {
                return null;
            }
            if (s.length() <= 1) {
                return null;
            }
            if (s.length() < maxLengthEachPart) {
                return null;
            }

            String ip = "";
            int start = startIndex, end = endIndex;
            end = end >= s.length() ? s.length() - 1 : end;
            boolean valid = false;
            if (start < 0 || end < 0 || start >= s.length() || end >= s.length()) {
                return ip;
            }

            do {
                String part = s.substring(start, end + 1);
                for (Part checker : checkers) {
                    valid = checker.isValid(part);
                    if (!valid) {
                        break;
                    }
                }
                if (valid) {
                    ip += "[" + part + "]"; // record
                    // System.out.println(ip);
                    // move to rest of string
                    finalPartCount++;
                    start = end + 1;
                    end = start + (maxLengthEachPart - 1);
                    maxIPLength += part.length();
                    makeInternal(s, 4, start, end, finalPartCount, maxIPLength);
                } else {
                    // current part is invalid
                    // try reducing length of the same part
                    end--;
                    if (end <= 0 || start == end) {
                        break;
                    }
                }

            } while (end < s.length());
            if (valid) {
                return ip;
            }
            return null;
        }

        public static boolean make(String s) {
            Integer count = 0, length = 0;
            String result = makeInternal(s, 4, 0, 2, count, length);
            return result != null;
        }
    }

    public static String lookAndSaySequenceString(int num) { // O(1) space

        if (num == 1) {
            return "1";
        }

        String s = lookAndSaySequenceString(num - 1);

        String result = "";
        int count = 1;
        char current = s.charAt(0);

        for (int i = 1; i < s.length(); i++) {
            char next = s.charAt(i);
            if (current == next) {
                count++;
            } else {
                result += "" + count + current;
                count = 1;
            }
            current = next;
        }

        result += "" + count + current;
        return result;
    }

    private static ArrayList<String> phoneNumPad = new ArrayList(); //to maintain order
    static {
        phoneNumPad.add("");
        phoneNumPad.add("");
        phoneNumPad.add("ABC");
        phoneNumPad.add("DEF");
        phoneNumPad.add("GHI");
        phoneNumPad.add("JKL");
        phoneNumPad.add("MNO");
        phoneNumPad.add("PQRS");
        phoneNumPad.add("TUV");
        phoneNumPad.add("WXYZ");
    }

    private static int digit(int num, int index) {
        ArrayList<Integer> list = new ArrayList();
        for(int quot = num ; quot != 0;) {
            list.add(quot % 10);
            quot = quot / 10;
        }
        Collections.reverse(list);
        if(index >= list.size()) {
            return -1;
        }
        return list.get(index);
    }

    private static ArrayList<String> combinationWithSingleCharFromEach(String str1, String str2) {
        ArrayList<String> allCombinations = new ArrayList();
        for(int j = 0 ; j < str1.length() ; j++) {
            char c = str1.charAt(j);
            for(int k = 0; k < str2.length() ; k++) {
                char l = str2.charAt(k);
                allCombinations.add(""+c+l);
            }
        }
        return allCombinations;
    }

    private static ArrayList<String> addSingleCharFromString(String s, List<String> list) {
        ArrayList<String> result = new ArrayList();
        for(int i = 0 ; i < s.length() ; i++) {
            char c = s.charAt(i);
            for(String item : list) {
                result.add(""+c+item);
            }
        }
        return result;
    }

    private static ArrayList<String> combinationOfStringsTakingSingleCharFromEach(ArrayList<String> list) {
        if(list.size() <= 1) {
            return list;
        }

        ArrayList<String> allCombinations = new ArrayList();
        String last = list.remove(list.size() - 1);
        int partition = list.size() - 1;
        ArrayList<String> partialResult = null;
        for(; partition >= 0; ) {
            String nextLast = list.remove(partition);
            if(partition < list.size()) {
                //there must be partialResult in list
                List<String> sublist = list.subList(partition, list.size());
                partialResult = addSingleCharFromString(nextLast, sublist);
                sublist.clear();
            }else {
                //compute first partial result
                partialResult = combinationWithSingleCharFromEach(nextLast,last);
            }
            
            partition --;
            list.addAll(partialResult);
            last = nextLast;
        }
        return list;
    }

    public static ArrayList<String> possibleWordsFromPhoneNumPad(int num) { // 34
        int digit = 0;
        ArrayList<String> choices = new ArrayList();
        for (int i = 0;; i++) {
            digit = digit(num, i);
            if (digit == -1){
                break;
            }
            choices.add(phoneNumPad.get(digit));
        }

        return combinationOfStringsTakingSingleCharFromEach(choices);
    }
}