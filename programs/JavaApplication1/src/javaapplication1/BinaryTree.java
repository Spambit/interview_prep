/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sambit
 */
public class BinaryTree {

    private BinaryTreeNode root;
    private Queue<BinaryTreeNode> internalQueue = Queue.create();

    public BinaryTreeNode getRoot() {
        return root;
    }
    
    private static boolean doesTheeNodesFormBST(BinaryTreeNode left, BinaryTreeNode root, BinaryTreeNode right) {
        if(root!= null && left == null && right == null) {
            return true;
        }
        
        if(root != null && left != null && right == null) {
            return root.data > left.data ;
        }
        
        if(root != null && left == null && right != null) {
            return root.data <= right.data ;
        }
        
        if(root != null && left != null && right != null) {
            return root.data <= right.data && root.data > left.data;
        }
        
        return false;
    }
    
    public static boolean isBST(BinaryTreeNode root) {
        if(root == null) {
            return false;
        }
        
        boolean isLeftSubTreeBST = true;
        boolean isRightSubTreeBST = true;
        boolean isLeftSmaller = true;
        boolean isRightBigger = true;
        boolean bst = false;
        if(root.left != null) {
            isLeftSubTreeBST = isBST(root.left);
        }
        
        if(root.right != null) {
            isRightSubTreeBST = isBST(root.right);
        }
        
        if(isLeftSubTreeBST && isRightSubTreeBST) {
            if(root.left != null) { 
               if(root.left.data < root.data ) {
                   //bst
                   isLeftSmaller = true;
               }else {
                   isLeftSmaller = false;
               }
            }
            
            if(root.right != null){
                if(root.right.data >= root.data ) {
                   //bst
                   isRightBigger = true;
               }else {
                    isRightBigger = false;
                }
            }
            
            bst = isRightBigger && isLeftSmaller;
                  
        }else {
            bst = false;
        }
        return bst;
    }

    public BinaryTreeNode insert(int data) {
        BinaryTreeNode newNode = BinaryTreeNode.create(data);
        BinaryTreeNode peek = internalQueue.peek();

        if (peek == null) {
            //empty tree - init
            this.root = newNode;
            newNode.level = 0;
        } else {
            if (peek.left == null) {
                peek.left = newNode;
            } else if (peek.right == null) {
                peek.right = newNode;
            }

            newNode.level = peek.level + 1;
            if (peek.left != null && peek.right != null) {
                internalQueue.poll();
            }
        }
        internalQueue.add(newNode);

        return newNode;
    }

    public void inorder(BinaryTreeNode node) {
        if (node == null) {
            return;
        }
        inorder(node.left);
        System.out.println();
        System.out.print("Node :" + node.data + " Level :" + node.level);
        if (node.left != null) {
            System.out.print(" Left : " + node.left.data);
        }
        if (node.right != null) {
            System.out.print(" Right : " + node.right.data);
        }

        if (node.right == null && node.left == null) {
            System.out.print(" Leaf ");
        }

        inorder(node.right);
    }

    public static class BST implements BinaryTreeInterface {

        private BinaryTreeNode root;

        public BinaryTreeNode insert(BinaryTreeNode root, int data, int level) {
            if (root == null) {
                BinaryTreeNode newNode = BinaryTreeNode.create(data);
                newNode.level = level;
                if (this.root == null) {
                    this.root = newNode;
                }
                return newNode;
            }

            if (data >= root.data) {
                root.right = insert(root.right, data, root.level + 1);
            } else {
                root.left = insert(root.left, data, root.level + 1);
            }
            return root;
        }

        public void inorder(BinaryTreeNode node) {
            if (node == null) {
                return;
            }
            inorder(node.left);
            System.out.println();
            System.out.print("Node :" + node.data + " Level :" + node.level);
            if (node.left != null) {
                System.out.print(" Left : " + node.left.data);
            }
            if (node.right != null) {
                System.out.print(" Right : " + node.right.data);
            }

            if (node.right == null && node.left == null) {
                System.out.print(" Leaf ");
            }

            inorder(node.right);
        }

        public BinaryTreeNode getRoot() {
            return root;
        }

        private BinaryTreeNode findInorderlyFirstElement(BinaryTreeNode root) {
            if (null == root) {
                return root;
            }

            BinaryTreeNode searchItem = findInorderlyFirstElement(root.left);
            if (searchItem != null) {
                return searchItem;
            }
            return root;
        }

        public BinaryTreeNode inorderSuccessor(BinaryTreeNode root, BinaryTreeNode node) {

            if (root == null) {
                return null;
            }

            if (node.right == null) {
                return node;
            }

            return findInorderlyFirstElement(node.right);

        }

        private DoubleLinkedList makeInorderlyDoubleLinkedList(BinaryTreeNode root) {
            if (root == null) {
                return null;
            }

            DoubleLinkedList leftlist = makeInorderlyDoubleLinkedList(root.left);
            DoubleLinkedListNode node = DoubleLinkedListNode.copyData(root);
            DoubleLinkedList rightlist = makeInorderlyDoubleLinkedList(root.right);

            DoubleLinkedListNode head = null;
            DoubleLinkedListNode tail = null;

            if (rightlist != null && leftlist == null) {
                rightlist.head.prev = node;
                node.next = rightlist.head;
                head = node;
                tail = rightlist.tail;
                return new DoubleLinkedList(head, tail);
            } else if (leftlist != null && rightlist == null) {
                leftlist.tail.next = node;
                node.prev = leftlist.head;
                head = leftlist.tail;
                tail = node;
                return new DoubleLinkedList(head, tail);

            } else if (leftlist != null && rightlist != null) {
                leftlist.tail.next = node;
                node.next = rightlist.head;
                node.prev = leftlist.tail;
                rightlist.head.prev = node;
                head = leftlist.head;
                tail = rightlist.tail;
                return new DoubleLinkedList(head, tail);
            }

            head = node;
            tail = node;
            return new DoubleLinkedList(head, tail);
        }

        public DoubleLinkedList toDoubleLinkedList(BinaryTreeNode root) {
            DoubleLinkedList list = makeInorderlyDoubleLinkedList(root);
            return list;
        }

        private BinaryTreeNode findInorderlyLastElement(BinaryTreeNode root) {
            if (root == null) {
                return null;
            }
            findInorderlyLastElement(root.left);
            BinaryTreeNode right = findInorderlyLastElement(root.right);
            if (right != null) {
                return right;
            }
            return root;
        }

        public BinaryTreeNode inorderPredecessor(BinaryTreeNode root) {
            if (root == null) {
                return null;
            }

            return findInorderlyLastElement(root.left);
        }

        public BinaryTreeNode inorderPredecessorIterative(BinaryTreeNode root) {
            if (root == null) {
                return null;
            }
            BinaryTreeNode left = root.left;
            while ((left != null && left.right != null) && left.right != root) {
                left = left.right;
            }

            return left;
        }

        public void morrisInorderlyPrint(BinaryTreeNode root) {
            if (root == null) {
                return;
            }

            BinaryTreeNode current = root;
            while (current != null) {
                //create temp link
                BinaryTreeNode inorderPredecessor = inorderPredecessorIterative(current);

                if (inorderPredecessor != null && inorderPredecessor.right == current) {
                    System.out.print(current.data + " ");
                    inorderPredecessor.right = null;
                    current = current.right;
                } else {
                    if (inorderPredecessor != null) {
                        inorderPredecessor.right = current;
                    }
                    if (current.left == null) {
                        System.out.print(current.data + " ");
                        current = current.right;
                    } else {
                        current = current.left;
                    }
                }
            }

        }

        public int numberOfNodes(BinaryTreeNode root) {
            if (root == null) {
                return 0;
            }
            int count = numberOfNodes(root.left) + numberOfNodes(root.right) + 1;
            return count;
        }

        public void printAllNodesAtLevel(BinaryTreeNode root) {
            int size = numberOfNodes(root);
            if (size <= 0) {
                return;
            }
            Queue<BinaryTreeNode> q = Queue.create();

            while (true) {
                BinaryTreeNode peek = q.peek();
                if (peek == null) {
                    q.add(root);
                } else {

                    if (peek.left != null) {
                        q.add(peek.left);
                    }

                    if (peek.right != null) {
                        q.add(peek.right);
                    }

                    q.poll();
                    System.out.print(peek.data + " ");
                }

                if (q.isEmpty()) {
                    break;
                }
            }
        }
    }

}
