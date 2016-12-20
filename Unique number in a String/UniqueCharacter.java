public class UniqueCharacter{

  private static int getUniqueCharacter(String msg){
    int n1 = 0;
    int n2 = 0;

    int offset = 0;
    for(char c : msg.toCharArray()){
      if(c >= 'A' && c <= 'Z'){
        offset = 'A';
      }else if(c >= 'a' && c <= 'z'){
        offset = 'a';
      }
      if((n1 & (1 << (c - offset))) == 0){
        // First encounter
        n1 |= (1 << (c - offset));
      }else{
        // 2nd Encounter
        n2 |= (1 << (c - offset));
      }
    }
    n1 ^= n2;
    n2 = -1;
    while(n1 > 0){
      n2++;
      n1 >>= 1;
    }
    return 'A' + n2;
  }

  public static void main(String[] args) {
    System.out.println((char)getUniqueCharacter("gautmutam"));
  }
}

/*

Find the unique character in the string.

Complexity:
  Time: O(n)
  Space: O(1)

*/
