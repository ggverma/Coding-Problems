import java.util.Random;
import java.util.HashMap;

public class LongestValidParenthesis{
  public int getLongestValidParenthesisLength(String exp){
    if(exp.equals("")) return 0;

    // Use HashMap for lower space usage. But it increases time by order of O(1 + n/m) ... the overhead of HashMap.
    //HashMap<Integer, Integer> map = new HashMap<>();
    int [] arr = new int[exp.length()];

    int level = 0;
    int continuum = 0;
    int maxLength = 0;
    for(char c : exp.toCharArray()){
      if(c == '('){
        level++;
        continuum = 0;
      }else{
        if(level > 0){
          //map.put(level, 0);
          arr[level] = 0;
          level--;
          continuum += 2;
        //   if(map.containsKey(level)){
        //     continuum += map.get(level);
        //   }
          continuum += arr[level];
          //map.put(level, continuum);
          arr[level] = continuum;
        //   if(map.get(level) > maxLength) maxLength = map.get(level);
          if(arr[level] > maxLength) maxLength = arr[level];
        }else{
          // refresh the map
          arr = new int[exp.length()];
        }
      }
    }

    return maxLength;
    
  }

  public static void main(String[] args) {
    Random random = new Random();

    String exp = "";

    int expLength = random.nextInt(20);

    for(int i = 0 ; i < expLength ; i++){
      if(random.nextInt(2) == 0){
        exp += '(';
      }else{
        exp += ')';
      }
    }

    LongestValidParenthesis obj = new LongestValidParenthesis();

    System.out.println("Expression: " + exp + "\nLongest Valid Parenthesis Length: " + obj.getLongestValidParenthesisLength(exp));
  }
}

/*

///// Leet Code Challenge ////////

Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed) parentheses substring.

For "(()", the longest valid parentheses substring is "()", which has length = 2.

Another example is ")()())", where the longest valid parentheses substring is "()()", which has length = 4.

*/
