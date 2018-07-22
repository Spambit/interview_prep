import java.util.Random;

public class RandomNumber {
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
}
