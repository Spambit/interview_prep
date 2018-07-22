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
public class DoubleLinkedListNode {
    int data;
    DoubleLinkedListNode prev;
    DoubleLinkedListNode next;
    
    public static DoubleLinkedListNode copyData(BinaryTreeNode btNode){
            DoubleLinkedListNode node = new DoubleLinkedListNode();
            node.data = btNode.data;
            node.prev = null;
            node.next = null;
            return node;
    }
}

class DoubleLinkedList {
    DoubleLinkedListNode head;
    DoubleLinkedListNode tail;

    public DoubleLinkedList(DoubleLinkedListNode head,DoubleLinkedListNode tail) {
        this.head = head;
        this.tail = tail;
    }
}
