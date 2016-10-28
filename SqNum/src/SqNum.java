import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class SqNum {

	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
		int maxNum = Integer.MIN_VALUE;
		int n[] = new int[t];
		for(int i = 0 ; i < t ; i++){
			int x = sc.nextInt();
			n[i] = x;
			if(n[i] > maxNum) maxNum = n[i];
		}
		
		boolean arr[] = new boolean[maxNum + 1];
		for(int i = 0 ; i < maxNum ; i++)
			arr[i] = false;
		
		for(int i = 2 ; i < maxNum ; i++){
			for(int j = i*i ; j < maxNum ; j += i*i){
				arr[j] = true;
			}
		}
		
		Arrays.sort(n);
		
		HashMap<Integer, Integer> map = new HashMap<>();
		
		int cT = 0;
		for(int i = 0 ; i <= n[0] ; i++){
			if(arr[i]) cT++;
		}
		
		map.put(n[0], cT);
		
		for(int i = 0 ; i < t - 1 ; i++){
			cT = map.get(n[i]);
			for(int j = n[i] + 1 ; j <= n[i + 1] ; j++){
				if(arr[j]) cT++;
			}
			map.put(n[i + 1], cT);
		}
		
		for(int i = 0 ; i < t ; i++){
			System.out.println(map.get(n[i]));
		}
	}*/

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
		long maxNum = Long.MIN_VALUE;
		long n[] = new long[t];
		for(int i = 0 ; i < t ; i++){
			n[i] = sc.nextLong();
			if(n[i] > maxNum) maxNum = n[i];
		}
		
		LinkedHashMap<Long, Boolean> primes = new LinkedHashMap<>();
		primes.put(0L, true);
		primes.put(1L, true);
		for(long i = 2 ; i < maxNum ; i++){
			primes.put(i, false);
		}
		HashMap<Long, Boolean> arr = new HashMap<>();
		for(long i = 2 ; i < maxNum ; i++){
			if(!primes.get(i)){
				for(long j = i * 2 ; j < maxNum ; j += i){
					primes.put(j, true);
				}
			}
//			for(long j = i*i ; j < maxNum ; j += i*i){
//				arr.put(j, true);
//			}
			
		}
		
		
//		Arrays.sort(n);
//		
//		HashMap<Long, Long> map = new HashMap<>();
//		
//		long cT = 0;
//		for(long i = 0 ; i <= n[0] ; i++){
//			if(arr.containsKey(i)) cT++;
//		}
//		
//		map.put(n[0], cT);
//		
//		for(int i = 0 ; i < t - 1 ; i++){
//			cT = map.get(n[i]);
//			for(long j = n[i] + 1 ; j <= n[i + 1] ; j++){
//				if(arr.containsKey(j)) cT++;
//			}
//			map.put(n[i + 1], cT);
//		}
//		
//		for(int i = 0 ; i < t ; i++){
//			System.out.println(map.get(n[i]));
//		}
	}
}
