import java.util.HashMap;

public class LongestUniqueSubstring {

    private static HashMap<Character, Integer> map;

    public static void main(String[] args){
      if(args.length < 1){
        System.out.println("Please enter the string as argument.");
        return;
      }
      System.out.println("The longest unique substring in " + args[0] + " is " + lengthOfLongestSubstring(args[0]));
    }

    public static String lengthOfLongestSubstring(String s) {
        map = new HashMap<>();
        int count = -1;
        int cp = 0;
        int lp = -1;
        String ls = "";

        // O(n) where n = length of string.
        for(Character c : s.toCharArray()){
            if(map.containsKey(c) && map.get(c) >= lp){
              if(count < cp - lp - 1){
                count =  cp - lp - 1;
                ls = s.substring(lp + 1, cp);
              }
              lp = map.get(c);
            }
            map.put(c, new Integer(cp));
            cp++;
        }
        if(count < cp - lp - 1){
          count =  cp - lp - 1;
          ls = s.substring(lp + 1, cp);
        }
        return ls;
    }
}
