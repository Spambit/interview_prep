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

public class RandomNumber {
    private static long prevRandomNess = System.currentTimeMillis();
    private static Random rand = new Random();
    public static void fill(StackInterface stack){
      for (int i = 0;i<stack.capacity();i++ ) {
          int  number = rand.nextInt(50) + 1;
          stack.push(number);
      }
    }

    public static int get(){
        return rand.nextInt(50) + 1;
    }
    
    public static long randNext(int below){
        long bit  = ((prevRandomNess >> 1) ^ (prevRandomNess >> 2) ^ (prevRandomNess >> 3) ^ (prevRandomNess >> 5) ) & 1;
        prevRandomNess =  (prevRandomNess >> 1) | (bit << 15);
        return prevRandomNess % below;
    }
}

