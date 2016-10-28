import java.util.HashSet;
import java.util.Scanner;

class WAJPHelper{
	int j1C, j2C;
	HashSet<String> states;
	public WAJPHelper() {
		// TODO Auto-generated constructor stub
		
		states = new HashSet<>();
	}
	
	public boolean canMeasureWater(int c1, int c2, int z) {
		
		j1C = c1 < c2 ? c2 : c1;//j1Cis the larger jug
		j2C = c1 < c2 ? c1 : c2;
		if(j1C == j2C){
			return false;
		}
		return isPossible(0, 0, z);
//		boolean isPoss = isPossible(0, 0, z);
//		int temp = j1C;
//		j1C = j2C;
//		j2C = temp;
//		states = new HashSet<>();
//		return isPoss || isPossible(0, 0, z);
    }
	
	private boolean isPossible(int w1, int w2, int z){
		
		if(w1 == z || w2 == z) return true;
		boolean isPoss = false;
		if(states.contains(String.valueOf(w1) + String.valueOf(w2))){
//			System.out.println("Already done");
			return false;
		}else{
//			System.out.println(w1 + " " + w2);
			states.add(String.valueOf(w1) + String.valueOf(w2));
			int w1Init = w1;
			int w2Init = w2;
			//pour 1 -> 2, if possible
			int waterShifted = 0;
			if(w2 != j2C && w1 > 0){
//				System.out.println("1->2");
				while(w2 != j2C && w1 != 0){
					w1--;
					w2++;
					waterShifted++;
				}
				isPoss = isPossible(w1, w2, z);
				w2 -= waterShifted;
				w1 += waterShifted;
//				System.out.println("Got back! w1=" + w1 + " w2=" + w2);
			}
			
			//pour 2 -> 1, if possible
			waterShifted = 0;
			if(w1 != j1C && w2 > 0 && !isPoss){
//				System.out.println("2->1");
				while(w1 != j1C && w2 != 0){
					w2--;
					w1++;
					waterShifted++;
				}
				isPoss = isPossible(w1, w2, z);
				w1 -= waterShifted;
				w2 += waterShifted;
//				System.out.println("Got back! w1=" + w1 + " w2=" + w2);
			}
			
			//fill Jug 1, if not filled completely
			if(w1 != j1C && !isPoss){
//				System.out.println("1 filled");
				w1 = j1C;
				isPoss = isPossible(w1, w2, z);
				w1 = w1Init;
//				System.out.println("Got back! w1=" + w1 + " w2=" + w2);
			}
			
			//fill Jug 2, if not filled completely
			if(w2 != j2C && !isPoss){
//				System.out.println("2 filled");
				w2 = j2C;
				isPoss = isPossible(w1, w2, z);
				w2 = w2Init;
//				System.out.println("Got back! w1=" + w1 + " w2=" + w2);
			}
			
			//spill j1, if possible
			if(w1 > 0 && !isPoss){
				w1 = 0;
				isPoss = isPossible(w1, w2, z);
				w1 = w1Init;
			}
			
			//spill j2, if possible
			if(w2 > 0 && !isPoss){
				w2 = 0;
				isPoss = isPossible(w1, w2, z);
				w2 = w2Init;
			}
		}
		return isPoss;
	}
}
public class WaterAndJugProblem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter jug 1 capacity: ");// + String.valueOf(0) + String.valueOf(0)
		int j1C = sc.nextInt();
		System.out.print("Enter jug 2 capacity: ");
		int j2C = sc.nextInt();
		System.out.print("Enter target: ");
		int z = sc.nextInt();
		System.out.print("" + new WAJPHelper().canMeasureWater(j1C, j2C, z));
	}

}

