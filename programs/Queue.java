public class Queue{
    private BinaryTreeNode[] elements = null;
    private int size;
    private int front = 0;
    private int rear = 0;
    private int INVALID_DATA = -999;
    public static Queue create(int size){
        return new Queue(size);
    }

    public Queue(int size){
      this.size = size;
      elements = new BinaryTreeNode[size];
      front = -1;
      rear = -1;
    }

    public void nq(BinaryTreeNode node){
        if(front == -1) front = 0;
        if(rear == -1) rear = 0;
        if(rear > size-1 && front > 0){
            rear = 0; //circular - must see geeksforgeeks
        }

        elements[rear] = node;
        rear++;
    }

    public BinaryTreeNode dq(){
        if(front > rear ){
            front = rear = 0;
        }
        BinaryTreeNode node = elements[front];
        elements[front] = null;
        front ++ ;
        return node;
    }

    public BinaryTreeNode peek(){
        if(front == -1){
            return null;
        }
        BinaryTreeNode peek = elements[front];
        if(peek != null){
          return BinaryTreeNode.create(peek.data); //copy
        }
        return null;
    }

    public boolean isEmpty(){
        return front > rear || (front == -1 && rear == -1);
    }

    public void prettyPrint(){
        System.out.print(" Front to rear : ");
        for (int i = front;i< rear ; i++ ) {
            BinaryTreeNode node = elements[i];
            if(node != null){
              System.out.print(" "+elements[i].data);
            }
        }
        System.out.println();
    }
}
