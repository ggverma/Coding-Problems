import java.util.Arrays;
import java.util.Random;

public class MinMove{

  private int getMinMoves(int [] nums){
    if(nums.length == 0) return 0;

    Arrays.sort(nums);

    int from = 0;
    int to = nums.length - 1;
    int medInd = (from + to) / 2;
    int median = nums[medInd];

    from = 0;
    to = nums.length - 1;

    int ops = 0;

    while(from != medInd){
        ops += median - nums[from];
        from++;
    }
    while(to != medInd){
        ops += nums[to] - median;
        to--;
    }

    return ops;
  }

  public static void main(String[] args) {
    Random random = new Random();
    int length = random.nextInt(100);

    int numbers [] = new int[length];
    for(int i = 0 ; i < length ; i++){
      numbers[i] = random.nextInt(100);
    }

    MinMove obj = new MinMove();

    System.out.println("Array: " + Arrays.toString(numbers));
    System.out.println("Minimum moves: " + obj.getMinMoves(numbers));
  }
}

/*

//////// Leet Code Challenge //////////

Given a non-empty integer array, find the minimum number of moves required to make all array elements equal, where a move is incrementing a selected element by 1 or decrementing a selected element by 1.

You may assume the array's length is at most 10,000.

Example:

Input:
[1,2,3]

Output:
2

Explanation:
Only two moves are needed (remember each move increments or decrements one element):

[1,2,3]  =>  [2,2,3]  =>  [2,2,2]

*/
