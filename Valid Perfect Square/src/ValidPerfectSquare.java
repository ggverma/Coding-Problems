import java.util.Scanner;

class VPSHelper{
	public boolean isPerfectSquare(int num) {
		if(num == 0) return true;
        else if(num == 1) return true;
        for(int i = 2 ; i * i <= num ; i++){
            if(i * i == num){
                return true;
            }
        }
        return false;
    }
	
}
public class ValidPerfectSquare {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter number to check if it's a valid perfect square: ");
		int num = sc.nextInt();
		System.out.println(new VPSHelper().isPerfectSquare(num));
	}

}
