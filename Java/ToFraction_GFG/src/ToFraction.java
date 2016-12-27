import java.util.*;
import java.util.Scanner;
import java.lang.StringBuilder;

class ToFraction {
    
    private static Scanner sc;
    
	public static void main (String[] args) {
		//code
		sc = new Scanner(System.in);
		
		int t = sc.nextInt();
		
		for( ; t> 0; t--){
		    LinkedHashMap<Integer, String> record = new LinkedHashMap<>();
		    
		    int a = sc.nextInt();
		    int b = sc.nextInt();
		    
		    StringBuilder sb = new StringBuilder();
		    
		    sb.append(a / b);
		    
		    a %= b;
		    
		    if(a != 0){
		    	sb.append(".");
		    }
		    StringBuilder fraction = new StringBuilder();
		    boolean outer = true;
		    while(a != 0){
		    	
		    	
		    	int x = a;
		    	a *= 10;
		    	while(a < b){
		    		a *= 10;
		    		fraction.append("0");
		    	}
		    	
		    	
		    	fraction.append(a / b);
		    	System.out.println("F: " + fraction);
		    	int j = 0;
		    	record.put(x, "" + (a / b));
		    	for(int i : record.keySet()){
		    		record.put(i, fraction.toString().substring(j, fraction.length()));
		    		j++;
		    	}
		    	
		    	if(!outer) break;
		    	a %= b;
		    	int y = a;
		    	for(int n = 0 ; n < 5 ; n++){
		    		if(record.containsKey(y)){
		    			String rep = record.get(y);
		    			fraction.replace(fraction.length() - rep.length(), fraction.length(), "");
		    			fraction.append("(").append(rep).append(")");
		    			outer = false;
		    			break;
		    		}
		    		y *= 10;
		    	}
		    }
		    for(int i : record.keySet()){
		    	System.out.println(i + ": " + record.get(i));
		    }
		    System.out.println(sb.append(fraction));
		}
	}
}