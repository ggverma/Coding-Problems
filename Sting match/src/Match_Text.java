import java.io.FileInputStream;
import java.util.Scanner;

public class Match_Text {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner();
		
		
		
		System.out.println("Enter a: ");
		String a = sc.nextLine();
		sc.nextLine();
		sc.nextLine();
		System.out.println("Enter b: ");
		String b = sc.nextLine();
		
		boolean c = a.equals(b);
		
		if(c){
			System.out.println("Same");
		}else{
			System.out.println("NOT Same");
		}
	}

}
