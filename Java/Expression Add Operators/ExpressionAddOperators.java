/*
 *
 * Given a string that contains only digits 0-9 and a target value, return all possibilities to add binary operators (not unary) +, -, or * between the digits so they evaluate to the target value.
 * Examples:
"123", 6 -> ["1+2+3", "1*2*3"]
"232", 8 -> ["2*3+2", "2+3*2"]
"105", 5 -> ["1*0+5","10-5"]
"00", 0 -> ["0+0", "0-0", "0*0"]
"3456237490", 9191 -> []
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

class EAOHelper{

//	HashMap<String, Integer> cache;
	public ArrayList<String> getWays(String expr, int result){
		ArrayList<String> solutions = new ArrayList<>();
		if(expr.equals("") || expr == null) return solutions;
		HashMap[] maps = getWays(expr, result, 0);
		for(HashMap map : maps) System.out.println(map);
		HashMap<String, Integer> map = (HashMap<String, Integer>)maps[maps.length - 1];
		for(String exp : map.keySet()){
			if(map.get(exp) == result){
				solutions.add(exp);
			}
		}
		return solutions;
	}

	private int getMultValue(String str){
		Stack<Integer> numStack = new Stack<>();
		StringBuilder sb = new StringBuilder();
		boolean lastMult = false;
//		System.out.println(str);
		for(int i = 0 ; i < str.length() ; i++){
			if(str.charAt(i) >= '0' && str.charAt(i) <= '9')
				sb.append(str.charAt(i));
			else{
				if(lastMult){
					numStack.push(numStack.pop() * Integer.parseInt(sb.toString()));
					sb = new StringBuilder();
					lastMult = false;
				}
				if(str.charAt(i) == '+'){
					if(sb.length() != 0){
						numStack.push(Integer.parseInt(sb.toString()));
						sb = new StringBuilder();
					}
				}else if(str.charAt(i) == '-'){
					if(sb.length() != 0){
						numStack.push(Integer.parseInt(sb.toString()));
						sb = new StringBuilder();
					}
					sb.append('-');
				}else{
					if(sb.length() != 0){
						numStack.push(Integer.parseInt(sb.toString()));
						sb = new StringBuilder();
					}
					lastMult = true;
				}
			}
		}
		int n = Integer.parseInt(sb.toString());
		while(!numStack.isEmpty()){
			int n2 = numStack.pop();
			if(lastMult){
				n *= n2;
				lastMult = false;
			}else{
				n += n2;
			}
		}
		return n;
	}

	private HashMap<String, Integer>[] getWays(String expr, int result, int index){
//		System.out.println(index);
		if(index == expr.length() - 1){
			HashMap map[] = new HashMap[1];
			map[0] = new HashMap<String, Integer>();
			map[0].put(expr.charAt(expr.length() - 1) + "", Integer.parseInt(expr.substring(expr.length() - 1)));
			return map;
		}
		HashMap[] receivedMaps = getWays(expr, result, index + 1);

		HashMap map[] = new HashMap[receivedMaps.length + 1];
		for(int i = 0 ; i < receivedMaps.length ; i++){
			map[i] = receivedMaps[i];
		}
		map[receivedMaps.length] = new HashMap<String, Integer>();
		for(int i = index + 1, mapIndex = receivedMaps.length - 1 ; i < expr.length() ; i++, mapIndex--){
			int n1 = Integer.parseInt(expr.substring(index, i));
			for(String key : ((HashMap<String, Integer>)map[mapIndex]).keySet()){
				map[receivedMaps.length].put(n1 + "+" + key, n1 + (int)map[mapIndex].get(key));

				String subtKey = n1 + "-" + key;

				map[receivedMaps.length].put(subtKey, getMultValue(subtKey));
				String multKey = n1 + "*" + key;
				int ans =  getMultValue(multKey);
//				System.out.println(multKey + " = " + ans);
				map[receivedMaps.length].put(multKey, ans);
			}
			map[receivedMaps.length].put(n1 + "", n1);
		}
		return map;
	}
}

public class ExpressionAddOperators {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter string: ");
		String expr = sc.nextLine();
		System.out.print("Enter desired result: ");
		int result = sc.nextInt();
		System.out.println("Total ways: " + new EAOHelper().getWays(expr, result));
	}

}
