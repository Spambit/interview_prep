package com.spambit.com.spambit.feb2020.array;

public class Util {
    public static void print(int[] arr, String delem) {
        for (int i = 0 ; i < arr.length; i++) {
            System.out.print(" " + arr[i] + (i != arr.length - 1 ? delem : ""));
        }
    }

    public static int[] shiftLeftByPos(int arr[], int pos) {
        if (pos == 0) {
            return arr;
        }
        int result[] = new int[pos + arr.length];
        for (int i = 0, j = 0; j < arr.length; i++, j++) {
            result[i] = arr[j];
        }
        return result;
    }
}
