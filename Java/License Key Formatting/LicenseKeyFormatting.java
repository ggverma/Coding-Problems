import java.util.Random;

public class LicenseKeyFormatting{

  public static String formatPlate(String S, int K){
    StringBuilder sb = new StringBuilder();

    int count = 0;
    // reverse the input, otherwise traverse right to left.
    String s = new StringBuilder(S).reverse().toString();
    for(char c : s.toCharArray()){
        if(c == '-'){
            continue;
        }
        if(count == K){
            sb.append("-");
            count = 0;
        }
        count++;
        sb.append(Character.toUpperCase(c));
    }
    // reverse is needed because append always concatenates at end.
    // add at front (use linked list), no reverse needed.
    return sb.reverse().toString();
  }

  public static void main(String[] args) {
    Random rand = new Random();
    int stringLength = rand.nextInt(100);

    StringBuilder sb = new StringBuilder();
    for(int i = 0 ; i < stringLength ; i ++){
      int c = rand.nextInt(9);
      if(c < 3){
        sb.append((char)('a' + rand.nextInt(26)));
      }else if(c < 6){
        sb.append("-");
      }else{
        sb.append(rand.nextInt(10));
      }

    }

    int K = 1 + rand.nextInt(10);

    System.out.println("Input: " + sb + "\nK: " + K);

    System.out.println("Output: " + formatPlate(sb.toString(), K));
  }
}
