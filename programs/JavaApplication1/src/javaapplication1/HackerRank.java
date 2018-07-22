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
public class HackerRank {

    // Complete the minimumBribes function below.
    public static void minimumBribes(int[] q) {
        boolean tooChaotic = false;
        int minimumBribes = 0;
        tooChaotic = findTooChaotic(q);
        if (tooChaotic) {
            System.out.print("Too chaotic");
        } else {
            minimumBribes = minimumBribesInternal(q);
            System.out.print(minimumBribes);
        }
    }

    private static int findNearestNumberIndex(int data, int currentIndex, int[] q) {
        int nearestIndex = -1;
        for (int i = currentIndex + 1; i < q.length; i++) {
            int currentData = q[i];
            if (data > currentData) {
                nearestIndex = i;
            }
        }
        return nearestIndex;
    }

    private static boolean findTooChaotic(int[] q) {
        boolean chaotic = false;
        for (int i = 0; i < q.length; i++) {
            int current = q[i];
            int nearestNumberIndex = findNearestNumberIndex(current, i, q);
            if (nearestNumberIndex == -1) {
                //current is smallest number - hence the sequence is correct till now.
                //continue;
            } else {
                //current gave bribe to nearestNumberIndex 
                int diffBetweenJumbleIndex = nearestNumberIndex - i;
                if (diffBetweenJumbleIndex > 2) {
                    chaotic = true;
                    break;
                }else {
                    //current gave bribe diffBetweenJumbleIndex times to nearestNumberIndex
                    chaotic = false;
                }
            }
        }
        return chaotic;
    }

    private static boolean isSorted(int[] q) {
        for (int i = 0, j = 1; j < q.length; i++, j++) {
            int prev = q[i];
            int next = q[j];
            if (prev > next) {
                return false;
            }
        }
        return true;
    }

    private static int minimumBribesInternal(int[] q) {
        int bribes = 0;
        boolean chaotic = false;
        for (int i = 0; i < q.length; i++) {
            int current = q[i];
            int nearestNumberIndex = findNearestNumberIndex(current, i, q);
            if (nearestNumberIndex == -1) {
                //current is smallest number - hence the sequence is correct till now.
                //continue;
            } else {
                //current gave bribe to nearestNumberIndex 
                int diffBetweenJumbleIndex = nearestNumberIndex - i;
                if (diffBetweenJumbleIndex > 2) {
                    chaotic = true;
                    break;
                }else {
                    //current gave bribe diffBetweenJumbleIndex times to nearestNumberIndex
                    chaotic = false;
                    bribes += diffBetweenJumbleIndex;
                }
            }
        }
        return bribes;
    }
}
