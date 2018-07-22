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
public class Stack implements StackInterface{
  public int size = 10;
  private int tag = 0;
  public final static int INVALID_DATA = -999;
  private int []elements = null;
  private int currentIndex = 0;

  public Stack(int tag,int size){
      this.tag = tag;
      this.size = size;
      elements = new int[this.size];
  }

  public int getTag(){
      return tag;
  }

  public boolean push(int data){
      if(currentIndex >= this.size){
          System.out.println("Stack overflow :"+this.getTag());
          return false;
      }
      elements[currentIndex] = data;
      currentIndex++;
      return true;
  }

  public int pop(){
      currentIndex -- ;
      if(currentIndex < 0){
          System.out.println("Stack underflow :"+this.getTag());
          return INVALID_DATA;
      }
      int popedElement = elements[currentIndex];
      elements[currentIndex] = 0;
      return popedElement;
  }

  public int capacity(){
      return size;
  }

  public boolean isFull(){
      return currentIndex >= this.size;
  }

  public boolean isEmpty(){
      return currentIndex <= 0;
  }

  public int depth(){
      return currentIndex - 1;
  }
  
  public boolean recursiveSort(){
      boolean sorted = false;
      if(this.isEmpty()) {
          System.out.println("Stack now is empty. Returning false.");
          return sorted;
      }
      
      this.prettyPrint();
      int top = this.pop();
      System.out.println("Poped : "+top);
      sorted = recursiveSort();
      
      int peek = this.peek();
      System.out.println("Peek : "+peek+ " Top: "+top);
      if(peek > top) {
          int next = this.pop();
          this.push(top);
          this.push(next);
          sorted = false;
          System.out.println("Rearranging top and popped item. Might not be sorted. Stack now: ");
          this.prettyPrint();
      
      }else {
          this.push(top);
          sorted = true;
          System.out.println("Sorted now: ");
          this.prettyPrint();
      }
      
      if(!sorted) {
        sorted = recursiveSort();
      }
      
      System.out.println("Winding up stack.");
      this.prettyPrint();
      
      return sorted;
  }

  public void sort(boolean isIncreasingOrder){
      int initialDepth = this.depth();
      Stack auxStack = new Stack(4,initialDepth+1);
      while(true) {
          System.out.println("++++++++++++ Start ++++++++++");
          int data = pop();
          System.out.println("Data : "+data);
          prettyPrint();
          int auxStackData = auxStack.peek();
          if(auxStack.isEmpty()){
              auxStack.push(data);
          }else {
              if(isIncreasingOrder){
                if(data < auxStackData){
                    auxStack.push(data);
                }else {
                  int auxStackDepth = auxStack.depth();
                  System.out.println("Aux stack depth : "+auxStackDepth);
                  for (int i = 0;i<=auxStackDepth ;i++ ) {
                      int top = auxStack.peek();
                      System.out.println("Peek aux stack data : "+top);
                      if(data > top){
                          System.out.println("Push to src stack data : "+top);
                          auxStack.pop();
                          push(top);
                      }else {
                          break;
                      }
                  }
                  auxStack.push(data);
                }
              }else {
                if(data > auxStackData){
                    auxStack.push(data);
                }else {
                  int auxStackDepth = auxStack.depth();
                  System.out.println("Aux stack depth : "+auxStackDepth);
                  for (int i = 0;i<=auxStackDepth ;i++ ) {
                      int top = auxStack.peek();
                      System.out.println("Peek aux stack data : "+top);
                      if(data < top){
                          System.out.println("Push to src stack data : "+top);
                          auxStack.pop();
                          push(top);
                      }else {
                          break;
                      }
                  }
                  auxStack.push(data);
                }
              }
          }

          auxStack.prettyPrint();
          prettyPrint();
          if(isEmpty()){
              int depth = auxStack.depth();
              for (int i = 0; i <= depth; i++ ) {
                  push(auxStack.pop());
              }
              System.out.println("Final output ....");
              auxStack.prettyPrint();
              prettyPrint();
              break;
          }

          System.out.println("++++++++++++ End ++++++++++");
      }
  }

  public void prettyPrint(){
    System.out.println();
    System.out.print("Stack[ "+this.getTag()+" ] : ");
    for(int i = 0;i <= depth() ;i++){
      System.out.print(" "+elements[i]);
    }
  }

  public int peek(){
      int index = currentIndex - 1;
      if(index < 0){
          return INVALID_DATA;
      }
      return elements[index];
  }

}

