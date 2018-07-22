/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 *
 * @author sambit
 */
public class Array {

    public static class SubsetSum {

        public static class ImprovedRecusive {

            public static HashSet<Integer> findSubsetWhichSumUpto(int[] arr, int sum) {
                HashSet<Integer> hashset = null;
                for (int i = 0; i< arr.length ; i ++) {
                    hashset = findSubsetWhichSumUptoInternal(arr,sum,i,arr.length - 1);
                    if(!hashset.isEmpty()){
                        break;
                    }
                }
                System.out.println("Accessed array: "+arrayAccessedCounter);
                return hashset;
            }

            static int arrayAccessedCounter = 0;
            private static HashSet<Integer> findSubsetWhichSumUptoInternal(int[] arr, int sum, int startIndex, int endIndex) {
                HashSet<Integer> list = new HashSet();
                int data = arr[startIndex];
                arrayAccessedCounter++;
                System.out.println("Looking for subset which sum upto: "+sum + " start: "+ startIndex + " end: "+endIndex);
                if(data > sum) {
                    System.out.println("Returning empty list because " + data + " is greater than " + sum);
                    return list;
                }
                if (data == sum ) {
                    System.out.println("Adding the value : " +sum + " because " + data + " is equal to " + sum);
                    list.add(data);
                    return list;
                }
                if ((startIndex == endIndex)) {
                    System.out.println("Returning list because startIndex and endIndex are equal which is : "+startIndex);
                    return list;
                }
                
                int findSum = sum - data;
                HashSet<Integer> subsetsWhichSumUptoFindSum = new HashSet();
                System.out.println("Now looking for subset which sum upto remaining sum : "+findSum + " because value now is : "+data);
                
                for (int i = startIndex + 1; i <= endIndex; i++) {
                    System.out.println("Saecrh start from : "+i);
                    subsetsWhichSumUptoFindSum = findSubsetWhichSumUptoInternal(arr, findSum, i, endIndex);
                    System.out.println("Search end from : "+i); 
                    if(!subsetsWhichSumUptoFindSum.isEmpty()) {
                        System.out.println("Found a Search end from : "+i+" so breaking from the loop." ); 
                        break;
                    }
                }
                
                System.out.println("Search complete for sum : "+sum + " in elements from: "+startIndex + " to endIndex: "+endIndex);
               
                if (!subsetsWhichSumUptoFindSum.isEmpty()) {
                    list.addAll(subsetsWhichSumUptoFindSum);
                    System.out.println("Found sum:  "+subsetsWhichSumUptoFindSum);
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

            private static ArrayList<HashSet<Integer>> amendListByCreatingNewEntryAddingNumberToEachEntry(ArrayList<HashSet<Integer>> list, int num) {
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

            private static ArrayList<HashSet<Integer>> findAllPossibleSubset(int[] arr, ArrayList<HashSet<Integer>> list, int startIndex, int endIndex) {
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
        int arr1[] = new int[]{1, 0, 0, 1, 1, 0, 0};
        int arr2[] = new int[]{0, 1, 0, 1, 1, 0, 1};
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

    //at least one +ve number in array
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
        int max_so_far = 0,
                max_ending_here = 0,
                index = 0,
                startIndex = 0,
                endIndex = 0;
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
        int min_so_far = 0,
                min_ending_here = 0,
                index = 0,
                startIndex = 0,
                endIndex = 0;
        while (index < array.length) {
            int item = array[index];
            min_ending_here = min_ending_here + item;
            if (min_ending_here > 0) {
                min_ending_here = 0;
                startIndex = index; //TODO: startIndex output is wrong
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
                //if both items are equal - ignore one of them if not already present in resulting array
                //else ignore both of them and increment both iterator
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
            //arr1 exhausted
            //put all elements from arr2 to arr
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
    
    public static int[] removeFrom(int[] arr, int data){
        int[] newArr = new int[arr.length - 1];
        int i = 0;
        for (int item : arr) {
            if(item != data) {
                newArr[i] = item;
            }
            i++;
            if(i >= newArr.length) break;
        }
        return newArr;
    }
    
    public static int[] insertTo(int arr[], int data) {
        int[] newArr = Arrays.copyOf(arr, arr.length + 1);
        newArr[arr.length] = data;
        return newArr;
    }

    public static int[] intersectionOfSortedArray(int[] arr1, int[] arr2) {
        //only the common elements
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
