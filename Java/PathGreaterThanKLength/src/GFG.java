import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/*
 * Given a graph, a source vertex in the graph and a number k, find if there is a simple path (without any cycle) starting from given source and ending at any other vertex.
Source vertex should always be  0.

Input:
First Line contains an integer T denoting the number of test cases. Then T test cases follow.
Each test case contains two lines. First Line contains three integers V, E and k representing vertices, edges of the graph and the required minimum length respectively. Second line contains 3 * E integers containing the information of all edges in the graph. Information of a single edge is a triplet in the following format: (Source Destination Distance). See example for more understanding.

Output:
For each test case print 1 if the path of atleast k distance exists, else print 0 in a new line.

Constraints:
1 <= T <= 30
2 <= V <= 5
1 <= E <= 20
1 <= k <= 100
 */

public class GFG {
	static class Node{
		int id;
		ArrayList<Node> adjacentNodes;
		
		public Node(int id){
			this.id = id;
			adjacentNodes = new ArrayList<>();
		}
		
		public void addAdjacentNode(Node node){
			adjacentNodes.add(node);
		}
		
	}
	
	static class Edge{
		int value;
		Node l1, l2;
		public Edge(int val, Node l1, Node l2){
			value = val;
			this.l1 = l1;
			this.l2 = l2;
		}
	}
	
	static HashMap<String, Edge> recordEdges;
	static HashMap<Integer, Node> recordVertices;
	
	static int pathLength = 0, k;
	
	static boolean pathFound = false;
	
	public static void main (String[] args) {
		//code
		Scanner sc = new Scanner(System.in);
		int t = sc.nextInt();
		for( ; t > 0 ; t--){
			recordEdges = new HashMap<>();
			recordVertices = new HashMap<>();
			int v = sc.nextInt();
			int e = sc.nextInt();
			k = sc.nextInt();
			
			for(int i = 0 ; i < v ; i++){
				Node newNode = new Node(i);
				recordVertices.put(i, newNode);
			}
			
			for(int i = 0 ; i < 3 * e ; i += 3){
				int x = sc.nextInt();
				int y = sc.nextInt();
				int val = sc.nextInt();
				recordEdges.put(Integer.toString(x) + Integer.toString(y), new Edge(val, recordVertices.get(x), recordVertices.get(y)));
				recordEdges.put(Integer.toString(y) + Integer.toString(x), recordEdges.get(Integer.toString(x) + Integer.toString(y)));
				recordVertices.get(x).addAdjacentNode(recordVertices.get(y));
				recordVertices.get(y).addAdjacentNode(recordVertices.get(x));
			}
			
//			for(Node node : recordVertices.values()){
//				System.out.print(node.id + ": ");
//				for(Node n : node.adjacentNodes)
//					System.out.print(n.id + ", ");
//				System.out.println();
//			}
			alreadyVistedNodes.add(recordVertices.get(0));
			visit(recordVertices.get(0));
			if(pathFound)
				System.out.println(1);
			else
				System.out.println(0);
			pathLength = 0;
			pathFound = false;
		}
	}
	
	static HashSet<Node> alreadyVistedNodes = new HashSet<>();
	
	public static void visit(Node root){
		if(pathFound) return;
		for(Node adjacentNode : root.adjacentNodes){
			if(pathFound) return;
			int pL = 0;
			if(!alreadyVistedNodes.contains(adjacentNode)){
				pL = recordEdges.get(root.id + "" + adjacentNode.id).value;
				pathLength += pL;
				//System.out.println("At Node " + root.id + ", Going to Node " + adjacentNode.id + " PL = " + pathLength);
				if(pathLength >= k){
					//System.out.println(1 + " & " + pathLength);
					pathFound = true;
					break;
				}
				alreadyVistedNodes.add(adjacentNode);
				visit(adjacentNode);
				alreadyVistedNodes.remove(adjacentNode);
				pathLength -= pL;
				//System.out.println(pathLength + "--");
			}
		}
	}
}
