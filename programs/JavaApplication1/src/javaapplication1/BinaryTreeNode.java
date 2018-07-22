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
class BinaryTreeNode {
    int data;
    BinaryTreeNode left;
    BinaryTreeNode right;
    int level;

    public static BinaryTreeNode create(int data){
        BinaryTreeNode node = new BinaryTreeNode();
        node.data = data;
        node.left = null;
        node.right = null;
        node.level = -1;
        return node;
    }
}


interface BinaryTreeInterface{
    public BinaryTreeNode insert(BinaryTreeNode root,int data,int level);
    public void inorder(BinaryTreeNode root);
    // public void delete(int data) ;
    // public void preorder();
    // public void postorder();
    // public int minDepth();
    // public int maxDepth();
    // public int lowestCommonAnsestor(int data1,int data2);
    // public boolean isBalancedTree();
    // public boolean isBinarySearchTree();
    // public boolean makeBinarySearchTree();
    // public boolean dfs();
    // public boolean bfs();
    // public BinaryTreeInterface createFromArray(int[] array);
}

