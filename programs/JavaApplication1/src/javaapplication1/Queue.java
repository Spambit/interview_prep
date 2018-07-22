/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author sambit
 */
public class Queue<E>{
    private ArrayList<E> elements = null;
    private int rear = -1;
    public static Queue create(){
        return new Queue();
    }

    private Queue(){
      elements = new ArrayList();
      rear = -1;
    }

    public void add(E node){
        if(rear == -1) rear = 0;
        elements.add(rear, node);
        rear++;
    }

    public E poll(){
        if(isEmpty()) {
            return null;
        }
        E node = (E)elements.get(0);
        elements.remove(node);
        rear--;
        if(rear == 0 ) {
            rear = -1;
        }
        return node;
    }

    public E peek(){
        if(isEmpty()){
            return null;
        }
        return elements.get(0);
    }

    public boolean isEmpty(){
        return rear == -1 ;
    }

    public void prettyPrint(){
        if(isEmpty()) {
            System.out.print("Empty.");
            return;
        }
        for (int i = 0;i< rear ; i++ ) {
            E node = elements.get(i);
            if(node != null){
              System.out.print(" "+elements.get(i).toString());
            }
        }
        System.out.println();
    }
}

