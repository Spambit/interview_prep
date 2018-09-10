/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author sambit
 */
public class Array {

    private static ArrayList<Integer> convertToDigitArrayAnother(int number){
        int reminder = number % 10;
        ArrayList<Integer>  list = new ArrayList();
        int quot = (int) (number / 10);

        if(quot == 0 && reminder != 0) {
            list.add(number);
            return list;
        }

        while(quot != 0) {
            list.add(reminder);
            reminder = quot % 10;
            quot = (int) (quot / 10);
        }
        
        list.add(reminder);

        Collections.reverse(list);
        return list;
    }
    private static int digit(int number, int position) {
        ArrayList<Integer> convertedDigitArray = convertToDigitArrayAnother(number);
        return convertedDigitArray.get(position);
    }
    public static List<Integer> bubbleSortAccordingToFirstDigit(List<Integer> A) {
        for(int i = 0  ; i < A.size() - 1; i++ ) {
            int current = A.get(i);
            int smallest = current;
            int smallestFirstDigit = digit(current,0);
            int k = 0;
            for( int j = i+1; j < A.size(); j++) {
                //find the smallest in this
                int data = A.get(j);
                int firstDigit = digit(data,0);
                if(smallestFirstDigit > firstDigit) {
                    smallest = data;
                    smallestFirstDigit = firstDigit;
                    k = j;
                }
            }
            //swap
            if(current != smallest) {
                A.set(k, current);
                A.set(i, smallest);
            }
        }
        return A;
    }

    public static String makeLargestNumberAsString(String number1, String number2) {
        char ch1='a';
        char ch2='a';
        for(int i = 0 , j = 0; ;) {
            if(i < number1.length()){
                ch1 = number1.charAt(i);
                i++;
            }

            if(j < number2.length()) {
                ch2 = number2.charAt(j);
                j++;
            }

            if(ch1 > ch2) {
                return number1+number2;
            }
            if(ch2 > ch1) {
                return number2+number1;
            }

            if(i >= number1.length() && j >= number2.length()) {
                return number1+number2;
            }
        }
    }

    private static String largestNumberInternal(List<Integer> list){
        String num1 = ""+list.get(0), num2 = "";
        for(int j = 1; j < list.size(); j++) {
            num2 = ""+list.get(j);
            String biggestNumber = makeLargestNumberAsString(num1, num2);
            num1 = biggestNumber;
        }
        return num1;
    }
    
    // DO NOT MODIFY THE LIST. IT IS READ ONLY
    public static String largestNumber(final List<Integer> A) {
        List<Integer> sortedList = Array.bubbleSortAccordingToFirstDigit(A);
        return largestNumberInternal(sortedList);
    }

    public static ArrayList<Integer> closestTripletSum(int arr[], int target) {
        return naiveClosestTripletSum(arr, target);
    }

    private static ArrayList<Integer> naiveClosestTripletSum(int arr[], int target) {
        ArrayList<Integer> list = new ArrayList();
        int minDistanceToTarget = 0;
        for (int k = 0; k < arr.length; k++) {
            Integer top = arr[k];
            for (int i = k + 1; i < arr.length; i++) {
                int middle = arr[i];
                for (int j = i + 1; j < arr.length; j++) {
                    int bottom = arr[j];
                    int sum = top + middle + bottom;
                    if (sum == target) {
                        list.clear();
                        list.add(top);
                        list.add(middle);
                        list.add(bottom);
                        return list;
                    }

                    int distanceToTarget = Math.abs(target - sum);
                    if (minDistanceToTarget == 0 || minDistanceToTarget > distanceToTarget) {
                        minDistanceToTarget = distanceToTarget;
                        list.clear();
                        list.add(top);
                        list.add(middle);
                        list.add(bottom);
                    }
                }
            }
        }
        return list;
    }

    public static ArrayList<Integer> tripletSum(int arr[], int target) {
        return betterTripletSum(arr, target);
    }

    private static ArrayList<Integer> betterTripletSum(int arr[], int target) {
        ArrayList<Integer> sortedList = createSortedListWithArr(arr);
        ArrayList<Integer> result = new ArrayList();
        for (int i = 0; i < sortedList.size(); i++) {
            Integer data = sortedList.get(i);
            int twoSumTarget = target - data;
            ArrayList<Integer> twoSumList = findTwoSum(sortedList, i + 1, twoSumTarget);
            if (!twoSumList.isEmpty()) {
                result.add(data);
                copyArrayList(result, twoSumList);
                break;
            }
        }
        return result;
    }

    private static ArrayList<Integer> findTwoSum(ArrayList<Integer> sortedList, int startIndex, int target) {
        ArrayList<Integer> result = new ArrayList();
        for (int i = startIndex, j = sortedList.size() - 1; i < j;) {
            int leading = sortedList.get(i);
            int trailing = sortedList.get(j);
            int sum = trailing + leading;
            if (sum == target) {
                result.add(leading);
                result.add(trailing);
                return result;
            }
            if (sum > target) {
                j--;
            } else {
                i++;
            }
        }
        return result;
    }

    private static ArrayList<Integer> naiveTripletSum(int arr[], int target) {
        // all posible triplet and find the match with the target
        ArrayList<Integer> result = new ArrayList();
        for (int i = 0; i < arr.length; i++) {
            int data = arr[i];
            int twoSumTarget = target - data;
            // System.out.println(twoSumTarget);
            ArrayList<Integer> twoSumList = findTwoSum(arr, i + 1, twoSumTarget);
            // System.out.println(twoSumList);
            if (!twoSumList.isEmpty()) {
                result.add(data);
                copyArrayList(result, twoSumList);
                break;
            }
        }
        return result;
    }

    private static ArrayList<Integer> findTwoSum(int arr[], int startIndex, int target) {
        ArrayList<Integer> list = new ArrayList();
        for (int i = startIndex; i < arr.length; i++) {
            int current = arr[i];
            for (int j = i + 1; j < arr.length; j++) {
                int next = arr[j];
                int sum = current + next;
                if (sum == target) {
                    list.add(current);
                    list.add(next);
                    return list;
                }
            }
        }
        return list;
    }

    private static void copyArrayList(ArrayList<Integer> dst, ArrayList<Integer> src) {
        for (Integer data : src) {
            dst.add(data);
        }
    }

    private static void copyForMaxSumSubArray(ArrayList<Integer> dst, ArrayList<Integer> src) {
        for (Integer data : src) {
            dst.add(data);
        }
    }

    private static int sumOfStagingListItems(ArrayList<Integer> set) {
        int sum = 0;
        for (Integer data : set) {
            sum += data;
        }

        return sum;
    }

    public int maxSubArrayKadenes(final List<Integer> A) {
        int workingSum = 0, stagingSum = 0;

        for (int i = 0; i < A.size(); i++) {
            int data = A.get(i);
            workingSum += data;
            if (workingSum < 0) {
                workingSum = 0;
            }
            if (stagingSum < workingSum) {
                stagingSum = workingSum;
            }
            if (data > stagingSum) {
                stagingSum = data;
            }
        }

        return stagingSum;
    }

    // DO NOT MODIFY THE LIST. IT IS READ ONLY - interviewBit
    public static int maxSubArray(final List<Integer> A) {
        ArrayList<Integer> stagingSet = new ArrayList();
        ArrayList<Integer> workingSet = new ArrayList();
        int sum = 0;
        for (int i = 0; i < A.size(); i++) {
            sum = A.get(i);
            if (stagingSet.isEmpty()) {
                stagingSet.add(sum);
            }
            workingSet.add(sum);

            int stagingSum = sumOfStagingListItems(stagingSet);
            if (stagingSum < sum) {
                stagingSet.clear();
                copyForMaxSumSubArray(stagingSet, workingSet);
                // System.out.print(stagingSet);
            }

            for (int j = i + 1; j < A.size(); j++) {
                sum += A.get(j);
                workingSet.add(A.get(j));
                int stagingSum2 = sumOfStagingListItems(stagingSet);
                // System.out.print("Sum : "+sum+ " Staging Sum: "+stagingSum2);
                if (stagingSum2 < sum) {
                    stagingSet.clear();
                    copyForMaxSumSubArray(stagingSet, workingSet);
                    // System.out.print(stagingSet);
                }
            }
            workingSet.clear();
        }

        // ystem.out.print(stagingSet);
        return sumOfStagingListItems(stagingSet);
    }

    public static class Pair {
        int item1;
        int item2;

        public static Pair create(int one, int two) {
            Pair p = new Pair();
            p.item1 = one;
            p.item2 = two;
            return p;
        }

        public int getSum() {
            return item1 + item2;
        }

        public String toString() {
            return " ( " + item1 + " , " + item2 + " ) ";
        }
    }

    public static Pair closestPairWithSum(int arr[], int target) {
        ArrayList<Integer> sortedList = createSortedListWithArr(arr);
        return closestPairWithSumInternal(sortedList, target);
    }

    public static Pair pairWithSum(int arr[], int target) {
        ArrayList<Integer> sortedList = createSortedListWithArr(arr);
        for (int i = 0, j = sortedList.size() - 1; i < j;) {
            int sum = sortedList.get(i) + sortedList.get(j);
            if (sum == target) {
                return Pair.create(sortedList.get(i), sortedList.get(j));
            }
            if (sum > target) {
                j--;
            } else {
                i++;
            }
        }
        return null;
    }

    private static ArrayList<Integer> createSortedListWithArr(int arr[]) {
        arr = Sort.bubble(arr);

        ArrayList<Integer> sortedList = new ArrayList();
        for (Integer item : arr) {
            sortedList.add(item);
        }
        return sortedList;
    }

    public static Pair closestPairWithSumInternal(ArrayList<Integer> sortedList, int target) {
        Pair foundPair = null;
        int smallestDistnceTowardsTarget = 0;
        int currentDistanceTowardsTarget = 0;
        int i = 0, j = sortedList.size() - 1;
        for (; i < j;) {
            int leadingItem = sortedList.get(i);
            int trailingItem = sortedList.get(j);
            int sum = leadingItem + trailingItem;

            if (sum == target) {
                foundPair = Pair.create(leadingItem, trailingItem);
                break;
            } else if (sum > target) {
                currentDistanceTowardsTarget = Math.abs(target - sum);
                j--;
            } else {
                currentDistanceTowardsTarget = Math.abs(target - sum);
                i++;
            }
            if (smallestDistnceTowardsTarget == 0 || smallestDistnceTowardsTarget > currentDistanceTowardsTarget) {
                smallestDistnceTowardsTarget = currentDistanceTowardsTarget;
                foundPair = Pair.create(leadingItem, trailingItem);
            }
        }
        return foundPair;
    }

    public static HashSet<Integer> largestConsecutiveSequence(int arr[]) {
        arr = Array.Sort.bubble(arr);
        boolean transactionOn = true;
        HashSet<Integer> workingSeq = new HashSet();
        HashSet<Integer> stagingSeq = new HashSet();
        for (int i = 0, j = 1; j < arr.length; i++, j++) {
            if (arr[j] - arr[i] == 1) {
                workingSeq.add(arr[i]);
                workingSeq.add(arr[j]);
            } else {
                if (stagingSeq.size() < workingSeq.size()) {
                    stagingSeq.clear();
                    copyHashSet(workingSeq, stagingSeq);
                }

                workingSeq.clear();
            }
        }

        if (stagingSeq.size() < workingSeq.size()) {
            stagingSeq.clear();
            copyHashSet(workingSeq, stagingSeq);
        }
        return stagingSeq;
    }

    private static void copyHashSet(HashSet<Integer> src, HashSet<Integer> dst) {
        for (Integer val : src) {
            dst.add(val);
        }
    }

    public static int[] addOneToNumber(int digits[]) {
        return convertInternal(digits);
    }

    public static int[] addOneToVeryBigNumber(int digits[]) {
        return addOneToVeryBigNumberInternal(digits);
    }

    private static int[] addOneToVeryBigNumberInternal(int[] digits) {
        int newDigits[] = new int[digits.length];
        int carry = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            int val = digits[i] + carry;
            if (val > 9) {
                carry = 1;
                newDigits[i] = 0;
            } else {
                carry = 0;
                newDigits[i] = val;
            }
        }
        int newarr[] = newDigits;
        if (carry == 1) {
            newarr = new int[newDigits.length + 1];
            for (int i = 1, j = 0; j < newDigits.length; j++, i++) {
                newarr[i] = newDigits[j];
            }
            newarr[0] = 1;
        }

        return newarr;
    }

    private static int[] convertInternal(int digits[]) {
        int number = convertToNumber(digits);
        number += 1;
        int newDigits[] = convertToDigitArray(number);
        return newDigits;
    }

    private static int numberOfDigits(int number) {
        int i = 1;
        while ((int) (number / 10) != 0) {
            number = (int) (number / 10);
            i++;
        }
        return i;
    }

    private static int[] convertToDigitArray(int number) {
        int numberOfDigits = numberOfDigits(number);
        int arr[] = new int[numberOfDigits];
        int multiplier = 1;
        for (int i = 0; i < numberOfDigits - 1; i++) {
            multiplier *= 10;
        }
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (number / multiplier);
            number %= multiplier;
            multiplier /= 10;
        }
        return arr;
    }

    private static int convertToNumber(int digits[]) {
        int num = 0;
        for (int i = digits.length - 1, j = 0; i >= 0; i--, j++) {
            int digit = digits[j];
            int multiplier = 1;
            for (int k = 1; k <= i; k++) {
                multiplier = multiplier * 10;
            }
            num += digit * multiplier;
        }
        return num;
    }

    public static class Rotate {
        public static void left(int arr[], int pos) {
            pos = pos % arr.length;
            for (int i = 0; i < pos; i++) {
                rotateLeftByOnePlace(arr);
            }
        }

        private static void rotateLeftByOnePlace(int arr[]) {
            int data = arr[0];
            int j = 1, i = 0;
            for (; j < arr.length; j++, i++) {
                arr[i] = arr[j];
            }

            arr[j - 1] = data;
        }
    }

    public static class Search {
        public static class Range {
            int start;
            int end;

            private Range(int start, int end) {
                this.start = start;
                this.end = end;
            }

            public static Range make(int start, int end) {
                return new Range(start, end);
            }

            public boolean isDataInRange(int data) {
                return data > start && data < end;
            }

            public boolean startsWith(int data) {
                return data == start;
            }

            public boolean endsWith(int data) {
                return data == end;
            }

        }

        public static int positionAtWhichItemShouldBeInserted(int sortedArr[], int data) {
            return positionAtWhichItemShouldBeInsertedInternal(sortedArr, data, 0, sortedArr.length - 1);
        }

        private static int positionAtWhichItemShouldBeInsertedInternal(int sortedArr[], int data, int startIndex,
                int endIndex) {
            int mid = (int) ((startIndex + endIndex) / 2);
            if (data == sortedArr[mid]) {
                return mid;
            }

            if (mid - 1 > 0 && data < sortedArr[mid] && sortedArr[mid - 1] < data) {
                return mid;
            }

            if (mid + 1 < sortedArr.length - 1 && data > sortedArr[mid] && data < sortedArr[mid + 1]) {
                return mid + 1;
            }

            if (mid == 0 && sortedArr[mid] > data) {
                return mid;
            }

            if (mid >= sortedArr.length - 2 && sortedArr[mid] < data) {
                return sortedArr.length;
            }

            if (data > sortedArr[mid]) {
                return positionAtWhichItemShouldBeInsertedInternal(sortedArr, data, mid, endIndex);
            } else {
                return positionAtWhichItemShouldBeInsertedInternal(sortedArr, data, startIndex, mid);
            }
        }

        public static int binarySearch(int[] arr, int data) {
            if (!isSorted(arr, 0, arr.length - 1)) {
                return -1;
            }
            return binarySearchInternal(arr, data, 0, arr.length - 1, -1);
        }

        public static boolean rangeSearch(int arr[], Range range) {
            return naiveRangeSearch(arr, range);
        }

        private static boolean naiveRangeSearch(int arr[], Range range) {
            boolean transactionStarted = false, transactionEnd = false;
            for (int i = 0; i < arr.length; i++) {
                int data = arr[i];
                boolean startsWith = range.startsWith(data);
                boolean endsWith = range.endsWith(data);

                if (startsWith) {
                    transactionStarted = true;
                    transactionEnd = false;
                }

                if (endsWith) {
                    transactionEnd = true;
                }

                if (transactionStarted && transactionEnd) {
                    return true;
                }
            }
            return false;
        }

        private static int binarySearchInternal(int arr[], int data, int startIndex, int endIndex, int foundIndex) {

            int midIndex = (int) ((startIndex + endIndex) / 2);
            if (arr[midIndex] == data) {
                return midIndex;
            }

            if (midIndex == startIndex) {
                return -1;
            }

            if (data > arr[midIndex]) {
                foundIndex = binarySearchInternal(arr, data, midIndex + 1, endIndex, foundIndex);
            } else {
                foundIndex = binarySearchInternal(arr, data, startIndex, midIndex - 1, foundIndex);
            }

            return foundIndex;
        }

        private static boolean isSorted(int arr[], int startIndex, int endIndex) {
            int data = arr[startIndex];
            for (int i = startIndex + 1; i < endIndex; i++) {
                if (data > arr[i]) {
                    return false;
                }
                data = arr[i];
            }
            return true;
        }
    }

    public static void moveAll0sAtEnd(int arr[]) {
        for (int i = 0; i < arr.length; i++) {
            int data = arr[i];
            if (data == 0) {
                shiftArrayToLeftByOnePosition(arr, i + 1, arr.length - 1);
                arr[arr.length - 1] = 0;
            }
        }
    }

    private static void shiftArrayToLeftByOnePosition(int arr[], int startIndex, int endIndex) {
        if (startIndex > arr.length - 1 || startIndex <= 0) {
            return;
        }

        for (int i = startIndex; i <= endIndex; i++) {
            arr[i - 1] = arr[i];
        }
    }

    public static class Sort {

        public static int[] mergeTwoSortedArray(int[] arr1, int arr2[]) {
            int newArr[] = new int[arr1.length + arr2.length];
            return mergeInternal(newArr, arr1, 0, arr1.length - 1, arr2, 0, arr2.length - 1);
        }

        private static int[] mergeInternal(int dst[], int arr1[], int startIndex1, int endIndex1, int arr2[],
                int startIndex2, int endIndex2) {
            int i, k, j;
            for (i = startIndex1, k = 0, j = startIndex2; k < dst.length; k++) {
                int arr1data = 0;
                if (i <= endIndex1) {
                    arr1data = arr1[i];
                }

                int arr2data = 0;
                if (j <= endIndex2) {
                    arr2data = arr2[j];
                }

                if (arr1data < arr2data) {
                    dst[k] = arr1data;
                    i++;
                } else {
                    dst[k] = arr2data;
                    j++;
                }

                if (i == arr1.length && j < arr2.length) {
                    copyItems(arr2, j, endIndex2, dst, k + 1);
                    break;
                }

                if (j == arr2.length && i < arr1.length) {
                    copyItems(arr1, i, endIndex1, dst, k + 1);
                    break;
                }
            }
            return dst;
        }

        private static void copyItems(int src[], int startIndex, int endIndex, int dst[], int dstStartIndex) {
            for (int i = startIndex, j = dstStartIndex; i <= endIndex && j < dst.length; i++, j++) {
                dst[j] = src[i];
            }
        }

        public static int[] insertion(int[] arr) {
            for (int i = 1; i < arr.length; i++) {
                int data = arr[i];
                placeDataInProperPosition(arr, data, 0, i);
            }

            return arr;
        }

        // idea is - left part of array is always sorted and right is always unsorted.
        // Finally left part is the result.
        private static void placeDataInProperPosition(int arr[], int data, int startIndex, int endIndex) {
            for (int i = endIndex; i > startIndex; i--) {
                int prevData = arr[i - 1];
                if (data < prevData) {
                    arr[i - 1] = data;
                    arr[i] = prevData;
                }
            }
        }

        private static int indexOfMin(int arr[], int startIndex, int endIndex) {
            int minIndex = startIndex, min = arr[startIndex];
            for (int i = startIndex; i <= endIndex; i++) {
                int current = arr[i];
                if (min > current) {
                    minIndex = i;
                    min = current;
                }
            }
            return minIndex;
        }

        private static void findMinAndSwapWithData(int arr[], int data, int dataIndex, int startIndex) {
            int indexOfMin = indexOfMin(arr, startIndex, arr.length - 1);
            int minDataAtRight = arr[indexOfMin];
            if (data > minDataAtRight) {
                arr[dataIndex] = minDataAtRight;
                arr[indexOfMin] = data;
            }
        }

        public static int[] bubble(int[] arr) {
            for (int i = 0; i < arr.length - 1; i++) {
                int data = arr[i];
                findMinAndSwapWithData(arr, data, i, i + 1);
            }

            return arr;
        }
    }

    public static void print(int arr[]) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(" " + arr[i] + " ");
        }
    }

    public static class SubsetSum {

        public static class ImprovedRecusive {

            public static HashSet<Integer> findSubsetWhichSumUpto(int[] arr, int sum) {
                HashSet<Integer> hashset = null;
                for (int i = 0; i < arr.length; i++) {
                    hashset = findSubsetWhichSumUptoInternal(arr, sum, i, arr.length - 1);
                    if (!hashset.isEmpty()) {
                        break;
                    }
                }
                System.out.println("Accessed array: " + arrayAccessedCounter);
                return hashset;
            }

            static int arrayAccessedCounter = 0;

            private static HashSet<Integer> findSubsetWhichSumUptoInternal(int[] arr, int sum, int startIndex,
                    int endIndex) {
                HashSet<Integer> list = new HashSet();
                int data = arr[startIndex];
                arrayAccessedCounter++;
                System.out.println(
                        "Looking for subset which sum upto: " + sum + " start: " + startIndex + " end: " + endIndex);
                if (data > sum) {
                    System.out.println("Returning empty list because " + data + " is greater than " + sum);
                    return list;
                }
                if (data == sum) {
                    System.out.println("Adding the value : " + sum + " because " + data + " is equal to " + sum);
                    list.add(data);
                    return list;
                }
                if ((startIndex == endIndex)) {
                    System.out.println(
                            "Returning list because startIndex and endIndex are equal which is : " + startIndex);
                    return list;
                }

                int findSum = sum - data;
                HashSet<Integer> subsetsWhichSumUptoFindSum = new HashSet();
                System.out.println("Now looking for subset which sum upto remaining sum : " + findSum
                        + " because value now is : " + data);

                for (int i = startIndex + 1; i <= endIndex; i++) {
                    System.out.println("Saecrh start from : " + i);
                    subsetsWhichSumUptoFindSum = findSubsetWhichSumUptoInternal(arr, findSum, i, endIndex);
                    System.out.println("Search end from : " + i);
                    if (!subsetsWhichSumUptoFindSum.isEmpty()) {
                        System.out.println("Found a Search end from : " + i + " so breaking from the loop.");
                        break;
                    }
                }

                System.out.println("Search complete for sum : " + sum + " in elements from: " + startIndex
                        + " to endIndex: " + endIndex);

                if (!subsetsWhichSumUptoFindSum.isEmpty()) {
                    list.addAll(subsetsWhichSumUptoFindSum);
                    System.out.println("Found sum:  " + subsetsWhichSumUptoFindSum);
                    list.add(data);
                    System.out.println("Found a match and the list is : " + list);
                }
                return list;
            }
        }

        public static class NaiveRecusive {

            public static ArrayList<HashSet<Integer>> findSubsetWhichSumUpto(int[] arr, int sum) {
                HashSet<Integer> set = new HashSet();
                ArrayList<HashSet<Integer>> list = findSubsetWhichSumUptoInternal(arr, sum);
                return list;
            }

            private static ArrayList<HashSet<Integer>> amendListByCreatingNewEntryAddingNumberToEachEntry(
                    ArrayList<HashSet<Integer>> list, int num) {
                ArrayList<HashSet<Integer>> newList = new ArrayList();
                for (HashSet<Integer> hashSet : list) {
                    HashSet<Integer> newHashSet = new HashSet();
                    newHashSet.addAll(hashSet);
                    newHashSet.add(num);
                    newList.add(newHashSet);
                }
                list.addAll(newList);
                return list;
            }

            private static ArrayList<HashSet<Integer>> findSubsetWhichSumUptoInternal(int[] arr, int sum) {
                ArrayList<HashSet<Integer>> allPossibleSubSets = new ArrayList<HashSet<Integer>>();
                findAllPossibleSubset(arr, allPossibleSubSets, 0, arr.length - 1);
                ArrayList<HashSet<Integer>> list = new ArrayList<HashSet<Integer>>();
                for (HashSet<Integer> subset : allPossibleSubSets) {
                    int subsetsum = sumOfElementsInSet(subset);
                    if (subsetsum == sum) {
                        list.add(subset);
                    }
                }
                return list;
            }

            private static ArrayList<HashSet<Integer>> findAllPossibleSubset(int[] arr,
                    ArrayList<HashSet<Integer>> list, int startIndex, int endIndex) {
                if (arr.length == 0) {
                    return null;
                }

                if (startIndex != endIndex) {
                    list = findAllPossibleSubset(arr, list, startIndex + 1, endIndex);

                    int data = arr[startIndex];
                    list = amendListByCreatingNewEntryAddingNumberToEachEntry(list, data);
                } else {
                    HashSet<Integer> includeSet = new HashSet();
                    int data = arr[startIndex];
                    includeSet.add(data);

                    HashSet<Integer> emptyHashSet = new HashSet();

                    list.add(includeSet);
                    list.add(emptyHashSet);
                }

                return list;

            }

            private static int sumOfElementsInSet(HashSet<Integer> set) {
                int sum = 0;
                for (Integer el : set) {
                    sum += el;
                }
                return sum;
            }
        }
    }

    public static int longestCommonSpanWithSameSum() {
        int arr1[] = new int[] { 1, 0, 0, 1, 1, 0, 0 };
        int arr2[] = new int[] { 0, 1, 0, 1, 1, 0, 1 };
        int n = arr1.length;
        // Initialize result
        int maxLen = 0;

        // Initialize prefix sums of two arrays
        int preSum1 = 0, preSum2 = 0;

        // Create an array to store staring and ending
        // indexes of all possible diff values. diff[i]
        // would store starting and ending points for
        // difference "i-n"
        int diff[] = new int[2 * n + 1];

        // Initialize all starting and ending values as -1.
        for (int i = 0; i < diff.length; i++) {
            diff[i] = -1;
        }

        // Traverse both arrays
        for (int i = 0; i < n; i++) {
            // Update prefix sums
            preSum1 += arr1[i];
            preSum2 += arr2[i];

            // Comput current diff and index to be used
            // in diff array. Note that diff can be negative
            // and can have minimum value as -1.
            int curr_diff = preSum1 - preSum2;
            int diffIndex = n + curr_diff;

            // If current diff is 0, then there are same number
            // of 1's so far in both arrays, i.e., (i+1) is
            // maximum length.
            if (curr_diff == 0) {
                maxLen = i + 1;
            } // If current diff is seen first time, then update
              // starting index of diff.
            else if (diff[diffIndex] == -1) {
                diff[diffIndex] = i;
            } // Current diff is already seen
            else {
                // Find lenght of this same sum common span
                int len = i - diff[diffIndex];

                // Update max len if needed
                if (len > maxLen) {
                    maxLen = len;
                }
            }
        }
        return maxLen;
    }

    // at least one +ve number in array
    public static int findLargestSum(int[] array) {
        if (array.length == 0) {
            return 0;
        }

        int max_so_far = 0, max_ending_here = 0, index = 0;
        while (index < array.length) {
            int item = array[index];
            max_ending_here = max_ending_here + item;
            if (max_ending_here < 0) {
                max_ending_here = 0;
            }

            if (max_ending_here > max_so_far) {
                max_so_far = max_ending_here;
            }
            index++;
        }
        return max_so_far;
    }

    public static Tuple findLargestSumEx(int[] array) {
        int max_so_far = 0, max_ending_here = 0, index = 0, startIndex = 0, endIndex = 0;
        while (index < array.length) {
            int item = array[index];
            max_ending_here = max_ending_here + item;
            if (max_ending_here < 0) {
                max_ending_here = 0;
                startIndex = endIndex = 0;
            } else {
                if (startIndex == 0) {
                    startIndex = index;
                }
            }

            if (max_ending_here > max_so_far) {
                max_so_far = max_ending_here;
                endIndex = index;
            }
            index++;
        }
        return new Tuple(startIndex, endIndex, max_so_far);
    }

    public static Tuple findSmallestSum(int[] array) {
        int min_so_far = 0, min_ending_here = 0, index = 0, startIndex = 0, endIndex = 0;
        while (index < array.length) {
            int item = array[index];
            min_ending_here = min_ending_here + item;
            if (min_ending_here > 0) {
                min_ending_here = 0;
                startIndex = index; // TODO: startIndex output is wrong
            }

            if (min_ending_here < min_so_far) {
                min_so_far = min_ending_here;
                endIndex = index;
            }
            index++;
        }
        return new Tuple(startIndex, endIndex, min_so_far);
    }

    public static int largest(int[] array) {
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            int val = array[i];
            if (val > max) {
                max = val;
            }
        }
        return max;
    }

    public static Tuple largestPair(int[] array) {
        int max_sum = 0, first = 0, second = 0;
        for (int i = 0, j = 1; j < array.length; i++, j++) {
            int f = array[i];
            int s = array[j];
            int sum = f + s;

            if (sum > max_sum) {
                max_sum = sum;
                first = f;
                second = s;
            }
        }
        return new Tuple(first, second, max_sum);
    }

    private static int[] mergeSortInternal(int[] arr, int startIndex, int endIndex) {
        if (startIndex == endIndex) {
            int[] newArr = new int[1];
            newArr[0] = arr[startIndex];
            return newArr;
        }

        int size = (endIndex - startIndex) + 1;
        int halfSize = Math.abs(size / 2);
        int leftStart = startIndex;
        int leftEnd = startIndex + (halfSize - 1);
        int rightStart = leftEnd + 1;
        int rightEnd = endIndex;
        int[] leftSortedHalf = mergeSortInternal(arr, leftStart, leftEnd);
        int[] rightSortedHalf = mergeSortInternal(arr, rightStart, rightEnd);
        int[] joined = joinTwoSortedArray(leftSortedHalf, rightSortedHalf);
        return joined;
    }

    public static int[] recursiveMergeSort(int[] arr) {
        return mergeSortInternal(arr, 0, arr.length - 1);
    }

    public static int[] joinTwoSortedArray(int[] arr1, int[] arr2) {
        int totalSize = arr1.length + arr2.length;
        boolean arr1Exhaust = false, arr2Exhaust = false;
        int[] newArr = new int[totalSize];
        int arr1Item = Integer.MAX_VALUE;
        int arr2Item = Integer.MAX_VALUE;
        for (int arr1Iter = 0, arr2Iter = 0, k = 0; k < totalSize; k++) {
            if (arr1Iter >= arr1.length) {
                arr1Exhaust = true;
            }

            if (arr2Iter >= arr2.length) {
                arr2Exhaust = true;
            }

            if (!arr1Exhaust) {
                arr1Item = arr1[arr1Iter];
            }
            if (!arr2Exhaust) {
                arr2Item = arr2[arr2Iter];
            }
            if (!arr1Exhaust && arr2Exhaust) {
                newArr[k] = arr1Item;
                arr1Iter++;
            } else if (!arr2Exhaust && arr1Exhaust) {
                newArr[k] = arr2Item;
                arr2Iter++;
            } else {
                if (arr1Item < arr2Item) {
                    newArr[k] = arr1Item;
                    arr1Iter++;
                } else {
                    newArr[k] = arr2Item;
                    arr2Iter++;
                }
            }

            arr1Item = Integer.MAX_VALUE;
            arr2Item = Integer.MAX_VALUE;

        }

        return newArr;
    }

    private static int[] quicksortInternal(int[] arr, int startIndex, int endIndex) {

        if (startIndex == endIndex) {
            return arr;
        }

        int wall = -1;
        int pivot = 0;
        int currentIndex = 0;
        int rightAfterWallIndex = 0;
        int size = endIndex - startIndex + 1;
        while (true) {
            rightAfterWallIndex = wall + 1;

            pivot = arr[endIndex];
            if (rightAfterWallIndex >= endIndex) {
                break;
            }

            int currentItem = arr[currentIndex];

            if (pivot >= currentItem) {
                int temp = arr[rightAfterWallIndex];
                arr[rightAfterWallIndex] = arr[currentIndex];
                arr[currentIndex] = temp;
                wall++;
                currentIndex = wall + 1;
            } else {
                currentIndex++;
            }
        }

        quicksortInternal(arr, startIndex, wall);

        return arr;
    }

    public static int[] quicksort(int[] arr) {
        return quicksortInternal(arr, 0, arr.length - 1);
    }

    public static int[] unionOfSortedArray(int[] arr1, int[] arr2) {
        int size = arr1.length + arr2.length;
        int[] arr = new int[size];
        int arr1Iter = 0, arr2Iter = 0, k = 0;
        for (; arr1Iter < arr1.length && arr2Iter < arr2.length; k++) {
            int arr1Item = arr1[arr1Iter];
            int arr2Item = arr2[arr2Iter];
            if (arr1Item < arr2Item) {
                arr[k] = arr1Item;
                arr1Iter++;
            } else if (arr1Item > arr2Item) {
                arr[k] = arr2Item;
                arr2Iter++;
            } else {
                // if both items are equal - ignore one of them if not already present in
                // resulting array
                // else ignore both of them and increment both iterator
                int prevIndex = k == 0 ? k : k - 1;
                int data = arr[prevIndex];
                if (data != arr1Item) {
                    arr[k] = arr1Item;
                    arr1Iter++;
                } else {
                    k = prevIndex;
                    arr1Iter++;
                    arr2Iter++;
                }
            }
        }

        int[] unexhaustedArray = null;
        int exhaustPoint = 0;
        if (arr1Iter >= arr1.length) {
            // arr1 exhausted
            // put all elements from arr2 to arr
            unexhaustedArray = arr2;
            exhaustPoint = arr1Iter;
        } else {
            unexhaustedArray = arr1;
            exhaustPoint = arr2Iter;
        }

        for (int i = k, j = exhaustPoint; j < unexhaustedArray.length; i++, j++) {
            int prevIndex = i == 0 ? i : i - 1;
            int data = arr[prevIndex];
            int item = unexhaustedArray[j];
            if (data != item) {
                arr[i] = item;
            }
        }

        return arr;
    }

    public static int[] removeFrom(int[] arr, int data) {
        int[] newArr = new int[arr.length - 1];
        int i = 0;
        for (int item : arr) {
            if (item != data) {
                newArr[i] = item;
            }
            i++;
            if (i >= newArr.length) {
                break;
            }
        }
        return newArr;
    }

    public static int[] insertTo(int arr[], int data) {
        int[] newArr = Arrays.copyOf(arr, arr.length + 1);
        newArr[arr.length] = data;
        return newArr;
    }

    public static int[] intersectionOfSortedArray(int[] arr1, int[] arr2) {
        // only the common elements
        int size = arr1.length + arr2.length;
        int[] arr = new int[size];
        int arr1Item = 0, arr2Item = 0, k = 0, arr1Iter = 0, arr2Iter = 0;
        for (; arr1Iter < arr1.length && arr2Iter < arr2.length;) {
            arr1Item = arr1[arr1Iter];
            arr2Item = arr2[arr2Iter];
            int prevIndex = k == 0 ? k : k - 1;
            int prevData = arr[prevIndex];
            if (arr1Item < arr2Item) {
                arr1Iter++;
            } else if (arr1Item > arr2Item) {
                arr2Iter++;
            } else {
                if (prevData != arr1Item) {
                    arr[k] = arr1Item;
                    arr1Iter++;
                    k++;
                } else {

                    arr1Iter++;
                    arr2Iter++;
                }
            }
        }
        return arr;
    }
}
