import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class LongestAP {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		int t = sc.nextInt();
		
		for( ; t>0 ; t--){
			int n = sc.nextInt();
			int arr[] = new int[n];
			
			for(int i = 0 ; i < n ; i++){
				arr[i] = sc.nextInt();
			}
			
			HashMap<Integer, Integer> map = new HashMap<>();
			HashMap<Integer, ArrayList<Integer>> answer = new HashMap<>();
			int d = arr[1] - arr[0];
			map.put(d, 1);
			ArrayList<Integer> nL = new ArrayList<>();
			nL.add(arr[0]);
			nL.add(arr[1]);
			answer.put(d, nL);
			for(int i = 2 ; i < n ; i++){
				HashSet<Integer> dToInc = new HashSet<>();
				for(int j = i - 1 ; j >= 0 ; j--){
					d = arr[i] - arr[j];
					//System.out.println(d);
					if(map.containsKey(d)){
						if(answer.get(d).get(answer.get(d).size() - 1) == arr[j]){
							dToInc.add(d);							
						}else{
							
						}
					}else{
						map.put(d, 1);
						nL = new ArrayList<>();
						nL.add(arr[j]);
						nL.add(arr[i]);
						answer.put(d, nL);
					}
				}
				//System.out.println(dToInc);
				for(int q : dToInc){
					//System.out.print(q + ": " + map.get(q) + " ");
					int x = map.get(q);
					x++;
					map.put(q, x);
					nL = answer.get(q);
					nL.add(q + nL.get(nL.size() - 1));
					answer.put(q, nL);
				}
				//System.out.println();
			}
			
//			for(int x : map.keySet()){
//				System.out.println(x + ": " + map.get(x));
//			}
			
			int max = Integer.MIN_VALUE, mD = 0;
			
			for(int i : map.keySet()){
				if(map.get(i) > max){
					max = map.get(i);
					mD = i;
				}
			}
			
//			for(int x : map.values()){
//				if(x > max){
//					max = x;
//				}
//			}
			System.out.println(max + 1 + "MD = " + mD + "\nAP: ");
			for(int i : answer.get(mD)){
				System.out.print(i + " ");
			}
			/*int prevTerm = sc.nextInt();
			int nextTerm = sc.nextInt();
			int d = nextTerm - prevTerm;
			
			int mC = 1;
			int c = 1;
			
			for(int i = 2 ; i < n ; i++){
				prevTerm = nextTerm;
				nextTerm = sc.nextInt();
				if(nextTerm - prevTerm == d) c++;
				else{
					if(c > mC) mC = c;
					c = 0;
					d = nextTerm - prevTerm;
				}
			}
			System.out.println(mC);*/
			
		}
	}

}
