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
public class LinkedList {

    public Node head;

    public static class Node {

        int data;
        Node next;

        public static Node create(int data) {
            Node node = new Node();
            node.data = data;
            node.next = null;
            return node;
        }
    }

    public static LinkedList createWithArray(int[] arr) {
        if (arr.length == 0) {
            return null;
        }

        LinkedList list = new LinkedList();
        Node temp = list.head;
        for (int item : arr) {
            Node newNode = Node.create(item);
            if (list.head == null) {
                list.head = newNode;
                temp = newNode;
            } else {
                while(temp.next != null) {
                    temp = temp.next ;
                }
                temp.next = newNode;
            }
        }

        return list;
    }

    public void print() {
        Node temp = head;
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
    }

    public void deleteNodesThatHasBiggerNodeAtRight() {
        Node prev = null;
        Node current = head;
        Node next = head.next;

        if (head.next == null) {
            return;
        }

        while (current != null && next != null) {
            if (next.data > current.data) {
                if (head == current) {
                    head = next;
                    current = head;
                    next = current.next;
                } else {
                    if (prev != null) {
                        prev.next = next;
                        current = next;
                        next = next.next;
                    }
                }
            } else {
                prev = current;
                current = next;
                next = next.next;
            }
        }
    }

}
