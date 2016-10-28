import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

class Work_Break_Helper{
	
	private HashMap<Integer, ArrayList<String>> map;
	
	private Stack<Integer> points;
	
	public Work_Break_Helper() {
		// TODO Auto-generated constructor stub
		map = new HashMap<>();
		points = new Stack<>();
	}
	
	public void printAllValidStrings(HashSet<String> dictionary, String input, int inc, int pointer, int lastPointer){
//		String right = input.substring(pointer);
//		System.out.println(pointer + " " + lastPointer + " r: " + right + " STACK: " + points);
		if(pointer > input.length()) return;
		else if(pointer == input.length()){
			@SuppressWarnings("unchecked")
			Stack<Integer> copyPoints = (Stack<Integer>)points.clone();
			int endPointer = input.length();
			int startPointer = copyPoints.pop();
			String result = input.substring(startPointer, endPointer);
			ArrayList<String> suffixes = map.get(pointer);
			if(suffixes == null){
				suffixes = new ArrayList<>();
			}
			suffixes.add(" " + result);
			endPointer = startPointer;
			while(!copyPoints.isEmpty()){
				startPointer = copyPoints.pop();				
//				System.out.println("SP: " + startPointer + " EP: " + endPointer);
				result = input.substring(startPointer, endPointer) + " " + result;
				suffixes = map.get(pointer);
				if(suffixes == null){
					suffixes = new ArrayList<>();
				}
				suffixes.add(" " + result);
				endPointer = startPointer;
			}
			result = input.substring(0, endPointer) + " " + result;
			System.out.println(result);
		}else{
			while(pointer <= input.length()){
				String left = input.substring(lastPointer, pointer);
				if(dictionary.contains(left)){
					if(!map.containsKey(pointer)){
						points.push(pointer);
						if(pointer == input.length())
							printAllValidStrings(dictionary, input, inc, pointer, pointer);
						else
							printAllValidStrings(dictionary, input, inc, pointer + inc, pointer);
						points.pop();
					}else{
						String prefix = "";
						if(!points.isEmpty()){
						Stack<Integer> copyPoints = (Stack<Integer>)points.clone();
						int endPointer = pointer;
						int startPointer = copyPoints.pop();
						prefix = input.substring(startPointer, endPointer);
						endPointer = startPointer;
						while(!copyPoints.isEmpty()){
							startPointer = copyPoints.pop();				
							prefix = input.substring(startPointer, endPointer) + " " + prefix;
							endPointer = startPointer;
						}
						prefix = input.substring(0, endPointer) + " " + prefix;
						}
						prefix += " ";
						for(String suffix : map.get(pointer)){
							System.out.println(prefix + suffix);
						}
					}
				}
				pointer++;
			}
		}
	}
}

public class Work_Break {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int totalCases = sc.nextInt();
		for(int i = 0 ; i < totalCases ; i++){
			HashSet<String> dictionary = new HashSet<>();
			int dictionaryLength = sc.nextInt();
			int minWordLength = Integer.MAX_VALUE;
			sc.nextLine();
			for(int j = 0 ; j < dictionaryLength ; j++){
				String newEntry = sc.nextLine();
				dictionary.add(newEntry);
				minWordLength = newEntry.length() < minWordLength ? newEntry.length() : minWordLength;
			}
			String input = sc.nextLine();
			new Work_Break_Helper().printAllValidStrings(dictionary, input, minWordLength, 0, 0);
//			for(String x : dictionary)
//				System.out.println(x);
		}
	}

}
