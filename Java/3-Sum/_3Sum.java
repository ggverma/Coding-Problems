import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

public class _3Sum{

  private ArrayList<ArrayList<Integer>> get3Sums(int [] numbers){
    ArrayList<ArrayList<Integer>> _3sums = new ArrayList<>();
    if(numbers.length < 2) return null;

    Arrays.sort(numbers);
    if(numbers[0] >= 0) return null;
    if(numbers[numbers.length - 1] <= 0) return null;

    for(int i = 0 ; i < numbers.length - 2; i++){
      if(i == 0 || (i > 0 && numbers[i] != numbers[i - 1])){
        int from = i + 1, till = numbers.length - 1;
        int sum = 0 - numbers[i];
        while(from < till){
          if(numbers[from] + numbers[till] == sum){
            // Found a triplet!
            ArrayList<Integer> list = new ArrayList<>();
            list.add(numbers[i]);
            list.add(numbers[from]);
            list.add(numbers[till]);
            _3sums.add(list);
            while(from < till && numbers[from] == numbers[from + 1]) from++;
            while(till > from && numbers[till] == numbers[till - 1]) till--;
            from++;
            till--;
          }else if(numbers[from] + numbers[till] > sum){
            while(from > till && numbers[till] == numbers[till + 1]) till--;
            till--;
          }else{
            while(from < till && numbers[from] == numbers[from - 1]) from++;
            from++;
          }
        }
      }
    }

    return _3sums;
  }

  public static void main(String[] args) {
    // Random random = new Random();
    // int length = random.nextInt(1000);
    //
    // int numbers [] = new int[length];
    // for(int i = 0 ; i < length ; i++){
    //   numbers[i] = random.nextInt(79) - 30;
    // }

    _3Sum obj = new _3Sum();

    int [] numbers = new int[]{-4,-2,-2,-2,0,1,2,2,2,3,3,4,4,6,6};

    ArrayList<ArrayList<Integer>> _3sums = obj.get3Sums(numbers);

    System.out.println(Arrays.toString(numbers));

    for(ArrayList<Integer> list : _3sums){
      for(int i : list){
        System.out.print(i + ", ");
      }
      System.out.println();
    }

  }
}

/*

//////////// LeetCode Challenge //////////////

Given an array S of n integers, are there elements a, b, c in S such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

Note: The solution set must not contain duplicate triplets.

For example, given array S = [-1, 0, 1, 2, -1, -4],

A solution set is:
[
  [-1, 0, 1],
  [-1, -1, 2]
]

*/
