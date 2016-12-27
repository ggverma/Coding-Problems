import java.util.Random;
import java.util.Arrays;
import java.util.List;

import java.util.ArrayList;

public class Permutations_Duplicate{

  public List<List<Integer>> getPermutations(int [] nums){
    List<List<Integer>> permutations = new ArrayList<>();

    if(nums.length == 0) return permutations;

    Arrays.sort(nums);

    int i = 0;

    permutations.add(new ArrayList<Integer>());
    List<Integer> subList = new ArrayList<>();

    while(i < nums.length){
      // extract a sublist that contains all same elements
      while(i < nums.length - 1 && nums[i] == nums[i + 1]){
        subList.add(nums[i]);
        i++;
      }
      subList.add(nums[i]);
      i++;
      // get all permutations of previous permutations with this subList.
      permutations = weave(permutations, subList);
      subList = new ArrayList<Integer>();
    }

    return permutations;
  }

  private List<List<Integer>> weave(List<List<Integer>> permutations, List<Integer> list){
    List<List<Integer>> newPermutations = new ArrayList<>();
    for(List<Integer> permutation : permutations){
      // newPermutations contains all the new permutations of this permutation and incoming list.
      weaveAndAdd(permutation, list, newPermutations, new ArrayList<Integer>());
    }
    return newPermutations;
  }

  private void weaveAndAdd(List<Integer> list1, List<Integer> list2, List<List<Integer>> newPermutations, List<Integer> list){
    // weave works like this:
    // weave: [2, 2] with [3, 3]:
    // l1 = [2, 2] l2 = [3, 3] l = []
      // l1 = [2] l2 = [3, 3] l = [2]
        // l1 = [] l2 = [3, 3] l = [2, 2]
          // l1 = [] l2 = [3] l = [2, 2, 3]
            // l1 = [] l2 = [] l = [2, 2, 3, 3] add
        // l1 = [2] l2 = [3] l = [2, 3]
          // l1 = [] l2 = [3] l = [2, 3, 2]
            // l1 = [] l2 = [] l = [2, 3, 2, 3] add
          // l1 = [2] l2 = [] l = [2, 3, 3]
            // l1 = [] l2 = [] l = [2, 3, 3, 2] add
            // so on....
    if(list1.size() != 0){
      int num = list1.get(0);
      list1.remove(0);
      list.add(num);
      weaveAndAdd(list1, list2, newPermutations, list);
      list.remove(list.size() - 1);
      list1.add(0, num);
    }
    if(list2.size() != 0){
      int num = list2.get(0);
      list2.remove(0);
      list.add(num);
      weaveAndAdd(list1, list2, newPermutations, list);
      list.remove(list.size() - 1);
      list2.add(0, num);
    }
    if(list1.size() == 0 && list2.size() == 0){
      newPermutations.add(new ArrayList<Integer>(list));
    }
  }

  public static void main(String[] args) {
    Random random = new Random();

    int arrayLength = 4;

    int nums [] = new int[arrayLength];

    for(int i = 0 ; i < arrayLength ; i++){
      nums[i] = random.nextInt(3);
    }

    Permutations_Duplicate obj = new Permutations_Duplicate();

    System.out.println("Input: " + Arrays.toString(nums));
    System.out.println("Permutaitons: ");

    List<List<Integer>> permutations = obj.getPermutations(nums);

    for(List<Integer> permutation : permutations){
      System.out.print(permutation + "\t");
    }
  }
}

/*

Generate all permutations of a string, when duplicates are present.

*/
