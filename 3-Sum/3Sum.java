import java.util.HashMap;
import java.utility.Scanner;

public class 3Sum{
  public static void main(String[] args) {
    Random random = new Random();
    int length = random.nextInt(100);

    int numbers [] = new int[length];
    for(int i = 0 ; i < length ; i++){
      numbers[i] = random.nextInt(3);
    }
  }
}
