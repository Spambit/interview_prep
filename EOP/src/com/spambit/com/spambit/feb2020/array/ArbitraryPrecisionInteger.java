package com.spambit.com.spambit.feb2020.array;

/**
 * 123
 *  14
 *  ------
 *  0492 x 1 = 0492
 *  123 x 10 = 1230
 *  ----------------
 *              1722
 */

public class ArbitraryPrecisionInteger {
    public static int[] multiply(int[] num1, int[] num2) { // Works
        if (num1.length == 0 || num2.length == 0) {
            return new int[]{0};
        }
        char num1Sign = sign(num1);
        char num2Sign = sign(num2);
        if(num1Sign == '-') {
            num1[0] = 0 - num1[0];
        }
        if(num2Sign == '-') {
            num2[0] = 0 - num2[0];
        }
        char finalSign = decideMultiplySign(num1Sign, num2Sign);
        int resultSize = num1.length + num2.length;
        int[] result = new int[resultSize];
        int[] smallerLengthNum = num1.length > num2.length ? num2 : num1;
        int[] biggerLengthNum = num1.length > num2.length ? num1 : num2;
        int shiftPos = 0;
        for (int j = smallerLengthNum.length -1; j >= 0; j--) {
            int intermediateResult[] = multiplySingleDigit(biggerLengthNum, smallerLengthNum[j]);
            intermediateResult = Util.shiftLeftByPos(intermediateResult, shiftPos); // so that have trailing zeros
            shiftPos++;
            if (j == smallerLengthNum.length - 1) {
                result = intermediateResult;
            }else {
                result = add(result, intermediateResult);
            }
        }
        int leftZeros = countLeftZeros(result);
        result = Util.shiftLeftByPos(result, leftZeros);
        result[0] = finalSign == '-' ? 0 - result[0] : result[0];
        return result;
    }

    private static int countLeftZeros(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == 0) {
                continue;
            }
            return i;
        }
        return 0;
    }

    private static char sign(int arr[]) {
        return arr[0] < 0 ? '-' : '+';
    }

    private static char decideMultiplySign(char sign1, char sign2) {
        return (sign1 == '-' && sign2 == '+') || (sign2 == '-' && sign1 == '+') ? '-' : '+';
    }

    private static int[] multiplySingleDigit(int arr[], int digit) {
        int size = arr.length + 1;
        int result[] = new int[size];
        int resultIndex = result.length - 1;
        int carry = 0;
        for (int i = arr.length - 1 ; i >= 0; i--) {
            int digitMultiplication = arr[i] * digit + carry;
            int digitResult = digitMultiplication % 10;
            carry = digitMultiplication / 10;
            result[resultIndex] = digitResult;
            resultIndex--;
        }
        result[resultIndex] = carry;
        return result;
    }
    public static int[] add(int[] num1, int[] num2) { // Works
        if (num1.length == 0) {
            return num2;
        }
        if (num2.length == 0) {
            return num1;
        }
        int carry = 0;
        boolean mismatchedLength = num1.length != num2.length;
        int biggerLength = num1.length > num2.length ? num1.length : num2.length;
        int resultSize = mismatchedLength ? biggerLength : num1.length + 1;
        int result[] = new int[resultSize];
        for(int i = num1.length - 1, j = num2.length - 1, k = resultSize - 1; k >= 0; i--, j--, k--) {
            int num1Digit = i < 0 ? 0 : num1[i];
            int num2Digit = j < 0 ? 0 : num2[j];
            int addDigit = num1Digit + num2Digit + carry;
            int resultDigit = addDigit >= 10 ? addDigit - 10 : addDigit;
            result[k] = resultDigit;
            carry = addDigit >= 10 ? 1 : 0;
        }
        return result;
    }

    public static void testAdd() {
        int num1[] = new int[]{1,1,4,9,2};
        int num2[] = new int[]{5,9,7};
        int[] result = add(num1, num2);
        Util.print(result, "");
    }

    public static void testMultiply() {
        int num1[] = new int[] {9,9,9,3,4,5,6,7,8};
        int num2[] = new int[] {9,8,0};
        int[] result = multiply(num1, num2);
        Util.print(result, "");
    }

    public static void testMultiplySigned() {
        int num1[] = new int[] {-9,9,9,3,4,5,6,7,8};
        int num2[] = new int[] {9,8,0};
        int[] result = multiply(num1, num2);
        Util.print(result, "");
    }
    
}
