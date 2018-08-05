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
import java.util.Random;

public class Sol33{
    public static void main(String[] args) {
        Random rand = new Random();
        SetOfStacks stackPool = new SetOfStacks();
        for (int i = 0;i<14 ;i++ ) {
            int  randomBelowFifty = rand.nextInt(50) + 1;
            stackPool.push(randomBelowFifty);
        }

        stackPool.prettyPrint();

        int data = stackPool.popAt(1);
        System.out.println("Poped data :"+data);

        stackPool.prettyPrint();

        data = stackPool.popAt(0);
        System.out.println("Poped data :"+data);

        stackPool.prettyPrint();

        data = stackPool.popAt(5);
        System.out.println("Poped data :"+data);

        stackPool.prettyPrint();

        data = stackPool.popAt(2);
        System.out.println("Poped data :"+data);

        stackPool.prettyPrint();
    }
}

class SetOfStacks implements  StackInterface{
  private static int MAX_SIZE = 5;
  private int currentUsableStackTag = 0;
  private static Stack []collectionOfStacks = new Stack[SetOfStacks.MAX_SIZE];
  static {
      for (int i = 0;i < MAX_SIZE ; i++){
          collectionOfStacks[i] = new Stack(i,i);
      }
  }
  
  public boolean recursiveSort() {
      throw new UnsupportedOperationException("Not supported yet.");
  }
  
  public boolean push(int data){
      for(int i = 0; i < SetOfStacks.MAX_SIZE ; i++){
        Stack stack = collectionOfStacks[i];
        if(!stack.isFull()){
            currentUsableStackTag = stack.getTag();
            return stack.push(data);
        }
      }
      return false;
  }
  public int pop(){
      Stack stack = collectionOfStacks[currentUsableStackTag];
      int data = stack.pop();
      if(stack.isEmpty()){
          currentUsableStackTag --;
          if(currentUsableStackTag <= 0 ){
              currentUsableStackTag = 0;
          }
      }
      return data;
  }

  public int popAt(int index){

      System.out.println("Pop at :"+index);
      if(index < 0 || index >= SetOfStacks.MAX_SIZE){
          return Stack.INVALID_DATA;
      }

      Stack stackInUse = collectionOfStacks[index];
      int data = stackInUse.pop();
      //adjust all stacks if a stack in middle has been poped.
      if(index >= SetOfStacks.MAX_SIZE - 1){
          //pop was performed on last stack in the set.
          //do no manupulation in stacks
      }else {
          //One item has been poped.
          //Take one one item from next stack and push it in the current stack.

          for (int i = index;i<currentUsableStackTag ;i++ ) {
            Stack currentStack = collectionOfStacks[i];
            int nextStackIndex = i + 1;
            Stack nextStack = collectionOfStacks[nextStackIndex];
            int popedDataFromNextStackAtBegining = popAtBegining(nextStack);
            currentStack.push(popedDataFromNextStackAtBegining);
          }
      }

      return data;
  }

  private int popAtBegining(final Stack stack){

      Stack tempStack = new Stack(10,stack.depth());
      int initialDepth = stack.depth();
      for(int i = 0;i<=initialDepth;i++){
          int data = stack.pop();
          if(data != Stack.INVALID_DATA){
              tempStack.push(data);
          }
      }

      int popedData = tempStack.pop();
      for (int i = 0;i<=initialDepth - 1 ;i++ ) {
          stack.push(tempStack.pop());
      }

      return popedData;
  }

  public boolean isFull(){
      Stack lastStack = collectionOfStacks[SetOfStacks.MAX_SIZE - 1];
      return lastStack.isFull();
  }

  public boolean isEmpty(){
    Stack firstStack = collectionOfStacks[0];
    return firstStack.isFull();
  }

  public int depth(){
      return currentUsableStackTag;
  }

  public void prettyPrint(){
      for(int i = 0;i <= this.depth();i++){
          Stack stack = collectionOfStacks[i];
          stack.prettyPrint();
      }
  }

    @Override
    public void sort(boolean isIncreasingOrder) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int peek() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int capacity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

