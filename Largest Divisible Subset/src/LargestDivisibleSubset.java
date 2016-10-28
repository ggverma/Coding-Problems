import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class Solution {
    public ArrayList<Integer> largestDivisibleSubset(int[] nums) {
        if(nums.length == 0) return new ArrayList<Integer>();
        if(nums.length == 1){
        	ArrayList<Integer> list = new ArrayList<Integer>();
        	list.add(nums[0]);
        	return list;
        }
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
//        System.out.println(nums.length);
        recurse(0, nums, result);
        int maxSubsetLength = Integer.MIN_VALUE;
        int index = 0;
        for(int i = 0 ; i < result.size() ; i++){
        	
        	if(result.get(i).size() > maxSubsetLength){
        		maxSubsetLength = result.get(i).size();
        		index = i;
        	}
        }
        for(ArrayList<Integer> list : result){
        	System.out.println(list);
        }
        return result.get(index);
    }
    
    private void recurse(int from, int[] nums, ArrayList<ArrayList<Integer>> result){
//    	System.out.println(from + " " + nums.length);
        if(from == (nums.length - 1)){
        	ArrayList<Integer> list = new ArrayList<>();
        	list.add(nums[from]);
        	result.add(list);
        	return;
        }
        recurse(from + 1, nums, result);
        boolean anyValid = false;
        for(ArrayList<Integer> list : result){
        	boolean valid = true;
            for(int i : list){
                if(i % nums[from] == 0 || nums[from] % i == 0){
                    
                }else{
                	valid = false;
                	break;
                }
            }
            if(valid){
            	anyValid = true;
            	list.add(nums[from]);
            }
        }
        if(!anyValid){
        	ArrayList<Integer> list = new ArrayList<>();
        	list.add(nums[from]);
        	result.add(list);
        }
    }
}
public class LargestDivisibleSubset {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
//		System.out.print("Enter the array length: ");
//		int l = sc.nextInt();
//		int nums[] = new int[l];
//		System.out.print("Enter the array numbers: ");
//		for(int i = 0 ; i < l ; i++){
//			nums[i] = sc.nextInt();
//		}
		String s = sc.nextLine();
		s = s.replaceAll("\\[|\\]|\\s", "");
		String splits[] = s.split("\\,");
		int nums[] = new int[splits.length];
		for(int i = 0 ; i < splits.length ; i++){
			nums[i] = Integer.parseInt(splits[i]);
		}
		Arrays.sort(nums);
		System.out.println(nums);
		System.out.println("Max Subset: " + new Solution().largestDivisibleSubset(nums));
	}

}
