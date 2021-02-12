package com.spambit.com.spambit.feb2020.array;

/**
 * 0 1 2 1 2 1 0
 *
 * The problem I am faced here - I could not anticipate that to find next 0 after 0 1 2 and still points onePointer to keep
 * pointing the existing first 1, I had to traverse the entire array. Again the same process will be for 1 and 2. So, I would be
 * ending up traversing thrice which is bad complexity.
 *
 * 2 0 0 1 2 1 0 1 0
 *
 * try2 works
 *
 * But there is another solution is that - we count the array first for 0 and then 1 and then arr.length - (0 + 1) = 2's.
 * Then traverse the array again and replace as per that counts.
 * */

public class DutchNationalFlag {
    public static void try2() { // Works
        int []arr = new int[]{0, 1, 1, 0, 0, 0, 1, 2, 0, 0, 0, 1};
        int k = group(arr, 0, 0);
        group(arr, 1, k);
        print(arr);
    }

    static int group(int arr[], int num, int startIndex) {
        int k = -1; // points to swappable index - first non-num value
        for (int i = startIndex; i < arr.length; i++) {

            // see a value different than num and we see that for first time.
            // then save that index.
            // we would need this when we encounter another num in array.
            if (arr[i] != num && k == -1) {
                k = i;
            }

            // see the num and we have a swappable index in hand
            // then swap it and increment swappable index as we have to swap next one
            if (k != -1 && arr[i] == num) {
                //swap
                int temp = arr[k];
                arr[k] = arr[i];
                arr[i] = temp;
                k++;
            }
        }
        return k;
    }


    void sortInPlaceZeroOneTwoArray() {
        int []array = new int[]{1,0,0,2,1};
        Indicies indicies = setZeroOneTwoPointersAtTheirFirstOccurrence(array);
        for(int val : array) {
            SwapIndices swapIndices = indicies.shouldSwap();
            if (swapIndices != null) {
                swap(array, swapIndices.i, swapIndices.j);
            } else {
                // already sorted
                // zeroIndex now should search for next 0 and so on
                // int nextZeroIndex = findNextZero();
            }
        }
        print(array);
    }

    public static void print(int arr[]) {
        for (int val: arr) {
            System.out.print(" " + val);
        }
    }

    private void swap(int arr[], int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private Indicies setZeroOneTwoPointersAtTheirFirstOccurrence(int []arr) {
        Indicies indicies = new Indicies(-1,-1,-1);
        for (int i = 0 ; i < arr.length; i++) {
            if (indicies.isFull()) {
                return indicies;
            }

            if (arr[i] == 0 && indicies.zeroIndex != -1) {
                indicies.zeroIndex = i;
            }

            if (arr[i] == 1 && indicies.oneIndex != -1) {
                indicies.oneIndex = i;
            }

            if (arr[i] == 2 && indicies.twoIndex != -1) {
                indicies.twoIndex = i;
            }
        }
        return indicies;
    }

    public class Indicies {
        public int zeroIndex;
        public int oneIndex;
        public int twoIndex;
        public Indicies(int zeroIndex, int oneIndex, int twoIndex) {
            this.zeroIndex = zeroIndex;
            this.oneIndex = oneIndex;
            this.twoIndex = twoIndex;
        }

        SwapIndices shouldSwap() {
            if (zeroIndex > oneIndex) {
                 return new SwapIndices(zeroIndex, oneIndex);
            }
            if (oneIndex > twoIndex) {
                return new SwapIndices(oneIndex, twoIndex);
            }
            if (zeroIndex > twoIndex) {
                return new SwapIndices(zeroIndex, twoIndex);
            }
            return null;
        }

        boolean checkIfTupleValuesAreSorted() {
            return zeroIndex < oneIndex && oneIndex < twoIndex;
        }

        public boolean isFull() {
            return zeroIndex != -1 && oneIndex != -1 && twoIndex != -1 ;
        }

        @Override
        public String toString() {
            return "Zero : " + this.zeroIndex + " One : " + this.oneIndex + " Two : " + this.twoIndex;
        }
    }

    class SwapIndices {
        public int i;
        public int j;
        SwapIndices(int i , int j) {
            this.i = i;
            this.j = j;
        }
    }
}
