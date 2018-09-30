package javaapplication1;
public class Number {

    public static boolean isPrime(long number) {
        if(number <= 1) {
            return false;
        }
        int half =  (int) number / 2;
        for(int i = 2 ; i <= half ; i++ ) {
            if(number % i == 0) {
                //evenly devided
                return false;
            }
        }
        return true;
    }

    public static void enumerateAllPrimesBelow(long number) {
        for(int i = 0 ; i <= number ; i++ ) {
            if(isPrime(i)) {
                System.out.print(i + " ");
            }
        }
    }
}