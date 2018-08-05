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
public class GeeksForGeeks {
    public static class Amazon {
        public static class Rainwater {
            public static int amountOfWater(int []arr) {
                int[] trimedArray = trimArray(arr);
                return 0;
            }
            
            private static int[] trimArray(int[] arr) {
                int []trimmedArrayAtBeginning = trimArrayAtBeginning(arr);
                int []trimmedArrayAtEnd = trimArrayAtEnd(trimmedArrayAtBeginning);
                return trimmedArrayAtEnd;
            }
            
            private static int[] trimArrayAtEnd(int []arr) {
                int []newArray = new int[arr.length];
                int ret[] = null;
                int j = arr.length - 1;
                int numberOfTrailingZero = 0;
                for (int i = arr.length - 1; i< 0 ; i--) {
                    if(arr[i] == 0 ) {
                        numberOfTrailingZero ++;
                    }else {
                        newArray[j] = arr[i];
                        j--;
                        break;
                    }
                }
                
                ret = new int[arr.length - numberOfTrailingZero];
                for (int i = 0 ; i < arr.length - numberOfTrailingZero ; i++) {
                    ret[i] = newArray[i];
                }
                return ret;
            }
            
            private static int[] trimArrayAtBeginning(int []arr) {
                int []newArray = new int[arr.length];
                int ret[] = null;
                int numberOfLeadingZero = 0;
                int j = 0;
                for (int i = 0; i< arr.length ; i++) {
                    if(arr[i] == 0 ) {
                        numberOfLeadingZero++;
                    }else {
                        newArray[j] = arr[i];
                        j++;
                        break;
                    }
                }
                
                ret = new int[arr.length - numberOfLeadingZero];
                for (int i = 0 ; i < j ; i++) {
                    ret[i] = newArray[i];
                }
                return ret;
            }
        }
    }
}
