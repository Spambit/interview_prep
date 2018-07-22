/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author sambit
 */
public class DP {
    private final static HashMap<String,Integer> memo = new HashMap<>();
    
    public static int minFrogJump(int[] arr) {
        clearMemo();
        return minFrogJumpInternal(arr,0,arr.length-1);
    }
    
    private static int minFrogJumpInternal(int[] arr, int startIndex ,int endIndex) { //assumes no duplicate
        
        //System.out.println("Find minimum jump from: "+startIndex+" to: "+endIndex);
        if(startIndex < 0 || endIndex >= arr.length) {
            return -1;
        }
        
        int value = getMemoizedResult(Integer.toString(arr[startIndex]));
        if(value != -1){
            return value;
        }
        
        int jumps = 0;
        if(arr[startIndex] >= endIndex - startIndex) {
           System.out.println("Minimum jump from: "+arr[startIndex]+" to the end is : 1");
           memoize(Integer.toString(arr[startIndex]), 1);
           return 1;
        }
        
        int numberInStartIndex = arr[startIndex], minJump = 0;
        int lookupEndIndex = startIndex + numberInStartIndex ;
        int lookupStartIndex = startIndex + 1;
        for(int i = lookupStartIndex; i < lookupEndIndex; i++) {
            minJump = Math.min(minFrogJumpInternal(arr,i,endIndex), minFrogJumpInternal(arr,i+1, endIndex)) + 1;
            System.out.println("Minimum jump from: "+arr[startIndex]+" to the end is : "+minJump);
            memoize(Integer.toString(arr[lookupStartIndex]),minJump);
        }
        
        jumps = minJump;
       
        return jumps;
    }
    
    private static void memoize(String key, int value) {
        Integer existingValue = memo.get(key);
        if(existingValue == null) { //not found
            memo.put(key, value);
        }
    }
    
    private static int getMemoizedResult(String key){
        Integer value = memo.get(key);
        return value != null ? value : -1;
    }
    
    private static void clearMemo(){
        memo.clear();
    }
    
}
