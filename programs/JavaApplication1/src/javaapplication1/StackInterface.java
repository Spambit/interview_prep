/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

/**
 *
 * @author sambit
 */
public interface StackInterface {
    public boolean push(int data);
    public int pop();
    public boolean isFull();
    public int depth();
    public boolean isEmpty();
    public void sort(boolean isIncreasingOrder);
    public boolean recursiveSort();
    public int peek();
    public int capacity();
    public void prettyPrint();
}

