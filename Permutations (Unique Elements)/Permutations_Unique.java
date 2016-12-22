import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Permutations_Unique{

  public List<List<Integer>> getPermutations(int [] nums){

    List<List<Integer>> subPermutations = new ArrayList<>();

    if(nums.length == 0) return subPermutations;

    subPermutations.add(new ArrayList<Integer>());

    for(int i = 0 ; i < nums.length ; i++){
      // create subPermutations and then create their permutations with an additional number each time.
      subPermutations = getSubPermutation(nums[i], subPermutations);
    }

    return subPermutations;
  }

  private List<List<Integer>> getSubPermutation(int num, List<List<Integer>> lastPermutations){
    List<List<Integer>> newPermutations = new ArrayList<>();
    for(List<Integer> permutation : lastPermutations){
      for(int i = 0 ; i <= permutation.size() ; i++){
        List<Integer> newPermutation = new ArrayList<>();
        newPermutation.addAll(permutation.subList(0, i));
        newPermutation.add(num);
        newPermutation.addAll(permutation.subList(i, permutation.size()));
        newPermutations.add(newPermutation);
      }
    }

    return newPermutations;
  }

  public static void main(String[] args) {
    int arrayLength = 5;
    int nums [] = new int[arrayLength];

    for(int i = 1 ; i <= arrayLength ; i++){
      nums[i - 1] = i;
    }

    System.out.println("Input: " + Arrays.toString(nums));
    System.out.println("Permutations: ");

    Permutations_Unique obj = new Permutations_Unique();

    List<List<Integer>> permutations = obj.getPermutations(nums);

    for(List<Integer> permutation : permutations){
      System.out.print(permutation + "\t");
    }
  }
}

/*

Permutations of a unique string.

*/
