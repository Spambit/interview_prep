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
public class TowerOfHanoi {
    public static void main(String[] args) {

        int SIZE = 10;
        Stack A = new Stack(1,SIZE);
        Stack B = new Stack(2,SIZE);
        Stack C = new Stack(3,SIZE);

        RandomNumber.fill(A);

        A.sort(false);

        Algo.towerOfHanoi(SIZE,A,C,B);
        A.prettyPrint();
        B.prettyPrint();
        C.prettyPrint();
    }
}

class Algo {
    public static void towerOfHanoi(int plateCount,Stack src, Stack dst, Stack using){
        if(plateCount <= 0){
            return;
        }

        if(plateCount == 1){
            dst.push(src.pop());
            return;
        }

        if(plateCount == 2){
            int topSrcData = src.pop();
            using.push(topSrcData);
            int remainingSrcData = src.pop();
            dst.push(remainingSrcData);
            int remainingDataInAuxStack = using.pop();
            dst.push(remainingDataInAuxStack);
            return;
        }

        towerOfHanoi(plateCount-1,src,using,dst);
        int remainingSrcData = src.pop();
        dst.push(remainingSrcData);
        towerOfHanoi(plateCount-1,using,dst,src);
    }
}
