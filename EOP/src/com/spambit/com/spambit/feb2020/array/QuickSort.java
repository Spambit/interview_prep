package com.spambit.com.spambit.feb2020.array;

public class QuickSort {
    public static void sort() {
        int arr[] = new int[] {9};
        int arr1[] = new int[] {2,1,3,4,7,2,1,5};
        int arr3[] = new int[] {8,6,4,2,1};
        int arr2[] = new int[] {7,6, 8};
        // sortAbs(arr3);
        partitionAtElement(arr1, 3);
        print(arr1);
    }

    private static void partitionAtElement(int[] arr, int pivotIndex) {
        if(pivotIndex < 0 && pivotIndex >= arr.length) {
            return;
        }
        IndexRange leftBucket = new IndexRange(0, pivotIndex - 1);
        IndexRange rightBucket = new IndexRange(pivotIndex + 1, arr.length - 1);
        // search for bigger element linearly in leftBucket
        // search for smaller element linearly in rightBucket
        // swap them as we see
        // if we don't see this in any bucket - shift that part and make room for the found single element
        int smallerValIndex = -1, biggerValIndex = -1;
        boolean foundBiggerAtLeftBucket = false, foundSmallerAtRightBucket = false, leftTraversalDone = false, rightTraversalDone = false;
        if (leftBucket.isValid(arr)) {
            for(int i = leftBucket.start,j = rightBucket.start; true ;) {
                // don't go beyond limit
                if (i > leftBucket.end) {
                    i = leftBucket.end;
                    leftTraversalDone = true;
                }
                if (j > arr.length - 1) {
                    j = arr.length - 1;
                    rightTraversalDone = true;
                }

                if ( leftTraversalDone && rightTraversalDone) {
                    //array fully traversed
                    break;
                }
                //search bigger element at left bucket
                if(arr[i] > arr[pivotIndex]) {
                    biggerValIndex = i;
                    foundBiggerAtLeftBucket = true;
                }
                // search smaller element at right bucket
                if(arr[j] < arr[pivotIndex]) {
                    smallerValIndex = j;
                    foundSmallerAtRightBucket = true;
                }
                if (!foundBiggerAtLeftBucket && foundSmallerAtRightBucket && !leftTraversalDone) {
                    i++;
                }
                if (foundBiggerAtLeftBucket && !foundSmallerAtRightBucket && !rightTraversalDone) {
                    j++;
                }
                if (!foundBiggerAtLeftBucket && !foundSmallerAtRightBucket) {
                    j++; i++;
                }
                // when you find both smaller and bigger element, swap between them
                if (foundBiggerAtLeftBucket && foundSmallerAtRightBucket) {
                    swap(arr, smallerValIndex, biggerValIndex);
                    foundBiggerAtLeftBucket = false;
                    foundSmallerAtRightBucket = false;
                    smallerValIndex = -1;
                    biggerValIndex = -1;
                }

                if (leftTraversalDone && !rightTraversalDone && foundSmallerAtRightBucket) {
                    // swap between pivotIndex + 1 and j then swap pivotIndex and pivotIndex + 1
                    swap(arr, pivotIndex + 1, j);
                    swap(arr, pivotIndex, pivotIndex + 1);
                    pivotIndex ++;
                }
                if (!leftTraversalDone && rightTraversalDone && foundBiggerAtLeftBucket) {
                    // swap between pivotIndex - 1 and i then swap pivotIndex and pivotIndex -1
                    swap(arr, pivotIndex - 1, i);
                    swap(arr, pivotIndex, pivotIndex - 1);
                    pivotIndex --;
                }
            }
        }
    }

    private static void sortAbs(int arr[]) {
        sortInternal(arr,0, arr.length - 1);
        print(arr);
        System.out.println(" ---> array length: " + arr.length + " Recursion counter: "+counter);
    }

    static int counter = 0;
    private static void sortInternal(int arr[], int startIndex, int endIndex) {
        if ( startIndex >= 0 && startIndex < arr.length
                && endIndex >= 0 && endIndex < arr.length
                && startIndex < endIndex
        ){
            ++counter;
            int partitionIndex = partitionAssumingPivotIsLastElement(arr, startIndex, endIndex);
            if (partitionIndex == -1) {
                //pivot was rightly positioned. example : 7, 6, 8 or 6, 7, 8
                partitionIndex = endIndex; //partition is now endIndex
            }
            if (partitionIndex >= 0) {
                sortInternal(arr, startIndex, partitionIndex - 1);
                sortInternal(arr, partitionIndex + 1, endIndex);
            }
        }
    }
    // all items left of pivot will be less than pivot and right of pivot will be more than pivot.
    // following function assumes pivotIndex will always be the last index.
    private static int partitionAssumingPivotIsLastElement(int arr[], int startIndex, int endIndex) {
        if (startIndex < 0 || endIndex > arr.length - 1) {
            return -1;
        }
        int pivotIndex = endIndex;
        int i = -1; //i holds the index whose left is smaller and right side is bigger than arr[i]
        int pivotVal = arr[pivotIndex];
        for ( int j = startIndex ; j <= endIndex; j++) {
            if (arr[j] > pivotVal && i == -1) {
                i = j;
                continue;
            }

            if(arr[j] < pivotVal && j != endIndex && i != -1) {
                swap(arr, i, j);
                i++;
            }
        }
//        shiftRightByOnePosition(arr, i);
//        if (i != -1) {
//            arr[i] = pivotVal;
//        }
        swap(arr, i, pivotIndex);
        return i;
    }

    private static void swap(int arr[], int i, int j) {
        if (i < 0 || j < 0) {
            return;
        }
        int temp = arr[j];
        arr[j] = arr[i];
        arr[i] = temp;
    }

    private static void print(int arr[]) {
        for (int val:arr) {
            System.out.print(val+", ");
        }
    }

    private static void shiftRightByOnePosition(int arr[], int startIndex) {
        if (startIndex < 0 ) {
            return;
        }
        // backward shift would be easier because in forward shift we have to shift the value of index first we are currently
        // shifting on.
        for (int i = arr.length - 1; i > startIndex; i--) {
            arr[i] = arr[i - 1];
        }
        arr[startIndex] = 0;
    }

    public static class IndexRange {
        int start = -1;
        int end = -1;
        IndexRange(int start, int end) {
            this.start = start;
            this.end = end;
        }
        boolean isValid(int arr[]) {
            return start >= 0 && start < arr.length && end >= 0 && end < arr.length && start != end;
        }
    }
}
