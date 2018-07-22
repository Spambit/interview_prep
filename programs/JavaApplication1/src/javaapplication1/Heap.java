/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author sambit
 */
public class Heap {
    public static class Min {
        public static Heap.Min create(int arr[]) {
            Heap.Min minHeap = new Heap.Min();
            for (int i : arr) {
                minHeap.insert(i);
            }
            return minHeap;
        }
        
        public void print(){ 
            for (Integer integer : list) {
                System.out.print(" "+integer);
            }
        }
        
        private final ArrayList<Integer> list = new ArrayList();
        private int indexOfParent(int dataIndex) {
            int reminder = (int)((dataIndex - 2 ) % 2);
            
            if(reminder == 0){
                return (int)((dataIndex - 2 ) / 2);
            }
            
            reminder = (int)((dataIndex - 1 ) % 2);
            if(reminder == 0) {
                return (int)((dataIndex - 1 ) / 2);
            }
            
            return -1;
        }
        
        private void rebalance(int data) {
            if(list.isEmpty()) {
                return;
            }
            
            int dataIndex = list.indexOf(data);
            if(dataIndex == -1) {
                return;
            }
            
            int parentIndex = indexOfParent(dataIndex);
            if(parentIndex == -1) {
                return;
            }
            
            int parent = list.get(parentIndex);
            if(parent > data) {
                list.set(parentIndex, data);
                list.set(dataIndex,parent);
                rebalance(data);
            }
        }
        
        private void insert(int data) { 
            list.add(data);
            rebalance(data);
        }
    }
    
}
