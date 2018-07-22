public class BinaryTreeClient {
    public static void main(String[] args) {

        /*
        BST binaryTree = new BST();

        binaryTree.insert(binaryTree.getRoot(),10,0);
        binaryTree.insert(binaryTree.getRoot(),1,0);
        binaryTree.insert(binaryTree.getRoot(),3,0);
        binaryTree.insert(binaryTree.getRoot(),2,0);
        binaryTree.insert(binaryTree.getRoot(),5,0);
        binaryTree.insert(binaryTree.getRoot(),9,0);
        binaryTree.insert(binaryTree.getRoot(),4,0);
        binaryTree.insert(binaryTree.getRoot(),20,0);

        BinaryTreeNode node = binaryTree.getRoot().left.right;

        binaryTree.inorder(binaryTree.getRoot());

        BinaryTreeNode successor = binaryTree.inorderSuccessor(binaryTree.getRoot(),node);
        System.out.println("Inorder successor of "+node.data+" is :"+successor.data);
        */

        BinaryTree bt = new BinaryTree();
        for (int i = 0;i<10 ;i++ ) {
            bt.insert(i+1);
        }
        bt.inorder(bt.getRoot());
    }
}

class BST implements BinaryTreeInterface{
    private BinaryTreeNode root ;
    public BinaryTreeNode insert(BinaryTreeNode root,int data,int level){
        if(root == null){
            BinaryTreeNode newNode = BinaryTreeNode.create(data);
            newNode.level = level;
            if(this.root == null){
                this.root = newNode;
            }
            return newNode;
        }

        if(data > root.data){
            root.right = insert(root.right,data,root.level+1);
        }else {
            root.left = insert(root.left,data,root.level+1);
        }
        return root;
    }

    public void inorder(BinaryTreeNode node){
      if(node == null){
          return;
      }
      inorder(node.left);
      System.out.println();
      System.out.print("Node :"+node.data+" Level :"+node.level);
      if(node.left != null){
          System.out.print(" Left : "+node.left.data);
      }
      if(node.right != null){
          System.out.print(" Right : "+node.right.data);
      }

      if(node.right == null && node.left == null){
          System.out.print(" Leaf ");
      }

      inorder(node.right);
    }
    public BinaryTreeNode getRoot(){
        return root;
    }

    private BinaryTreeNode findInorderlyFirstElement(BinaryTreeNode root){
        if(null == root){
            return root;
        }

        BinaryTreeNode searchItem = findInorderlyFirstElement(root.left);
        if(searchItem != null){
            return searchItem;
        }
        return root;
    }
	  public BinaryTreeNode inorderSuccessor(BinaryTreeNode root,BinaryTreeNode node){

        if(root == null){
            return null;
        }

        if(node.right == null){
            return node;
        }

        return findInorderlyFirstElement(node.right);

    }

    public BinaryTreeNode toDoubleLinkedList(BinaryTreeNode root){
        return null;
    }
}

class BinaryTree {
    private BinaryTreeNode root;
    private Queue internalQueue = Queue.create(10);
    public BinaryTreeNode getRoot(){
        return root;
    }
    public BinaryTreeNode insert(int data){
        BinaryTreeNode newNode = BinaryTreeNode.create(data);
        BinaryTreeNode peek = internalQueue.peek();

        if(peek == null){
            //empty tree - init
            this.root = newNode;
            newNode.level = 0;
        }else {
            if(peek.left == null){
                peek.left = newNode;
            }else if(peek.right == null){
                peek.right = newNode;
            }

            newNode.level = peek.level + 1;
            if(peek.left != null && peek.right != null){
                internalQueue.dq();
            }
        }
        internalQueue.nq(newNode);

        return newNode;
    }

    public void inorder(BinaryTreeNode node){
        if(node == null){
            return;
        }
        inorder(node.left);
        System.out.println();
        System.out.print("Node :"+node.data+" Level :"+node.level);
        if(node.left != null){
            System.out.print(" Left : "+node.left.data);
        }
        if(node.right != null){
            System.out.print(" Right : "+node.right.data);
        }

        if(node.right == null && node.left == null){
            System.out.print(" Leaf ");
        }

        inorder(node.right);
    }
}
