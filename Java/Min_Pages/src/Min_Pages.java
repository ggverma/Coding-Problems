import java.util.Scanner;

public class Min_Pages {

	static int result, m;
	
	static int pages[], stInd[];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
		for( ; t>0 ; t--){
			result = 0;
			int n = sc.nextInt();
			pages = new int[n];
			for(int i = 0 ; i < n ; i++){
				pages[i] = sc.nextInt();
			}
			m = sc.nextInt();
			stInd = new int[m + 1];
			for(int i = 0 ; i < m ; i++){
				stInd[i] = i;
			}
			stInd[m] = n;
			findMin();
		}
	}
	
	static boolean possible = true;
	
	static void findMin( ){
		if(!possible) return;
		int maxPages = Integer.MIN_VALUE;
		for(int i = 0 ; i < m ; i++){
			int thisSum = 0;
			for(int j = stInd[i] ; j < stInd[i + 1] ; j++){
				thisSum += pages[j];
			}
			if(thisSum > maxPages)
				maxPages = thisSum;
		}
		for(int i = 0 ; i < m ; i++){
			while(stInd[i] - stInd[i + 1] > 1){
				stInd[i]++;
				stInd[i + 1]--;
				findMin();
				stInd[i]--;
				stInd[i + 1]++;
			}
		}
	}

}
