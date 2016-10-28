import java.util.Scanner;

public class BleakNumber {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		int t= sc.nextInt();
		for( ; t > 0 ; t--){
			int n = sc.nextInt();
			if(n <= 0){
				System.out.println(0);
				continue;
			}
			boolean f = false;
			for(int i = 1 ; i < n ; i++){
				int sb = 0;
				int x = i;
				while(x != 0){
					if((x & 1) == 1) sb++;
					x = x >> 1;
				}
				//System.out.println("i = " + i + " sb = " + sb);
				if(sb + i == n){
					System.out.println(0);
					f = true;
					break;
				}
			}
			if(!f)
			System.out.println(1);
		}
	}

}
