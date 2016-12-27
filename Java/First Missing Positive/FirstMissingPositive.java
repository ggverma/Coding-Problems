import java.util.Random;
import java.util.Arrays;

public class FirstMissingPositive{

  private int getMissingNumber(int [] nums){
    if(nums.length == 0) return 1;
        for(int i = 0 ; i < nums.length ; i++){
            // At each iteration, put the number in its correct place.
            if(nums[i] > 0 && nums[i] <= nums.length && nums[nums[i] - 1] != nums[i]){
                int t = nums[i];
                nums[i] = nums[t - 1];
                nums[t - 1] = t;
                i--;
                // This swap may replace a number from the front that needs to be thrown in the back.
                // eg. -1 4 2 1
                // when 4 is swapped, it becomes -1 1 2 4
                // now, again check at this index and adjust 1.
            }
        }

        // while the next number is 1 greater than the last and is valid, continue.
        int lastNumber = 0;
        for(int i = 0 ; i < nums.length ; i++){
            if(nums[i] > 0 && nums[i] <= nums.length){
                if(nums[i] == lastNumber + 1)lastNumber++;
                else return lastNumber + 1;
            }
        }
        return lastNumber + 1;
  }

  public static void main(String[] args) {
    Random random = new Random();
    int length = random.nextInt(100);

    int numbers [] = new int[length];
    for(int i = 0 ; i < length ; i++){
      numbers[i] = random.nextInt(20);
    }

    FirstMissingPositive obj = new FirstMissingPositive();

    System.out.println("Array: " + Arrays.toString(numbers));
    System.out.println("First missing number: " + obj.getMissingNumber(numbers));
  }
}

/*

/////////// Leet Code Challenge ///////////

Complexity:
  Time: O(n)
  Space: O(1)

Given an unsorted integer array, find the first missing positive integer.

For example,
Given [1,2,0] return 3,
and [3,4,-1,1] return 2.

Your algorithm should run in O(n) time and uses constant space.

*/
