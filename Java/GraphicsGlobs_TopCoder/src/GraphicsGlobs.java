/*
Problem Statement


Note: This problem statement includes images that may not appear if you are using a plugin. For best results, use the Arena editor. 

In the vector-drawing mode of the grafix software package, the user plots geometric objects of three kinds: arcs, circles, and polygons. These objects are grouped into collections known as globs. A newly made object automatically belongs to a glob of which it is the sole member. The user can later choose to merge two globs into one, or to split one glob so that each of its objects is delegated to a glob on its own. Each glob bears a unique identification number or ID, which is crucial to updating the document. Your job is to take a sequence of drawing instructions expressed in the grafix internal language, and execute them according to the rules laid out below. 

An instruction is a string that takes one the following forms. 
  make OBJ
  delete ID
  merge ID ID
  split ID


In these forms, "OBJ" is a placeholder for one of the following values. 
  arc
  circle
  polygon


Furthermore, "ID" is a placeholder for the string representation of an existing glob ID, which must be a non-negative integer. The string representation will not be padded with zeros, and no instruction will be padded with extraneous spaces on either side or between its tokens. 

Before you execute the first instruction, the document is empty, so no glob IDs are in use. When a "make" instruction is executed, you must form a new glob whose ID is the lowest non-negative integer that isn't currently in use as a glob ID. The sole member of the new glob will be an object of the type named by the "make" instruction. 

To execute a "delete" instruction, you take the glob whose ID is specified by the instruction, discard all the objects it contains, and liberate its ID for future use. 

The "merge" instruction specifies two different IDs. The first one identifies the target glob, to which you must add the members of the second glob, called the source glob. Immediately thereafter, the source glob is to be destroyed and its ID liberated. Note that globs do not contain other globs, but only objects. Thus, the consequence of a merge operation is that the target glob contains more objects than before, namely its prior contents as well as the objects drawn from the source glob. 

The "split" instruction liberates the ID of the specified glob and then reassigns each of its member objects to an individual glob bearing the lowest available ID, in the following order. First the arcs are reassigned, then the circles, and finally the polygons. The specified glob is finally discarded. Its ID, if it was not reused by one of its member objects, remains available. 

You are given a sequence of drawing instructions in the commands. After executing all instructions in order, you are to determine the contents of the glob whose ID is specified by the sel. If there is no such glob, return an empty . Otherwise, return an with three values declaring, in order, the number of arcs, circles, and polygons in that glob. 

 
- commands contains between 1 and 50 elements, inclusive

 
- each element of commands adheres to the format described above

 
- commands is a feasible sequence, so each ID refers to an existing glob when it is used

 
- sel is between 0 and 999, inclusive
  
 
Examples

 0) 
 
{"make polygon", "make circle", "make polygon", "merge 0 1", "merge 2 0", "split 2"}
 
 
0
  

Returns: { 0, 1, 0 }
  
  
 1) 
 
{"make circle", "make circle", "make arc", "merge 2 1", "delete 0", "split 2", "delete 0", "make polygon"}
 
 
0
  

Returns: { 0, 0, 1 }
 
 
 
  
 2) 
 
{"make circle", "make circle", "make arc", "merge 2 1", "delete 0", "split 2", "delete 0", "make polygon"}
 
 
2
  

Returns: { }
 
 
There is no glob with ID 2.
  
 3) 
 
{"make arc"}
 
 
999
  

Returns: { }
  
 4) 
 
{"make polygon", "make polygon", "make arc", "make circle", "make circle", "delete 3", "make polygon", "make arc", "make arc", "merge 1 3", "merge 1 4", "merge 2 1", "make arc", "make arc", "make circle", "make circle", "merge 6 5", "split 6", "merge 2 1"}
 
 
2
  

Returns: { 2, 1, 2 }
    
This problem statement is the exclusive and proprietary property of TopCoder, Inc. Any unauthorized use or reproduction of this information without the prior written consent of TopCoder, Inc. is strictly prohibited. (c)2003, TopCoder, Inc. All rights reserved. 
*/
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;

enum Shape{arc, circle, polygon};

class Glob implements Comparable <Glob>{
	public int id;
	public Shape shape;
	@Override
	public int compareTo(Glob glob) {
		// TODO Auto-generated method stub
		return this.shape.ordinal() - glob.shape.ordinal();
	}
}

class GraphicsGlobsHelper{
	
	SortedSet<Integer> idSet = new TreeSet<>();
	
	HashMap<Integer, ArrayList<Glob>> myMap = new HashMap<>();
	
	public GraphicsGlobsHelper() {
		// TODO Auto-generated constructor stub
		idSet.add(0);
	}
	
	public int[] execute(String[] commands, int sel) throws Exception{
		for(String command : commands){
			StringTokenizer tokens = new StringTokenizer(command, " ");
			String instuction = tokens.nextToken();
			if(instuction.equals("make")){ 
				addGlob(tokens.nextToken());
			}else if(instuction.equals("delete")){
				deleteFromMyMap(Integer.parseInt(tokens.nextToken()));
			}else if(instuction.equals("split")){
				
				int idOfGlobList = Integer.parseInt(tokens.nextToken());
				ArrayList<Glob> globList = myMap.get(idOfGlobList);
				
				Collections.sort(globList);
				
				for(int i = 0 ; i < globList.size() ; i++){
					addGlob(globList.get(i).shape.toString());
				}
				
				deleteFromMyMap(idOfGlobList);
			}else if(instuction.equals("merge")){
				merge(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()));
			}else{
				throw new Exception();
			}
		}
		
		
		int arcCount, circleCount, polyCount;
		arcCount = circleCount = polyCount = 0;
		
		if(myMap.containsKey(sel)){
			ArrayList<Glob> selList = myMap.get(sel);
		
			for(int i = 0 ; i < selList.size() ; i++){
				if(selList.get(i).shape == Shape.arc)
					arcCount++;
				else if(selList.get(i).shape == Shape.circle)
					circleCount++;
				else
					polyCount++;
			}
			
			return new int[]{arcCount, circleCount, polyCount};
		}else{
			return new int[]{};
		}
	}
	
	public void printIdSet(){
		System.out.print("Set: ");
		for(int i : idSet){
			System.out.print(i + " ");
		}
		System.out.println();
	}
	
	public void addGlob(String globShape){
		//printIdSet();
		Glob newGlob = new Glob();
		
		newGlob.id = idSet.first();
		
		idSet.remove(newGlob.id);
		if(idSet.size() == 0)
			idSet.add(newGlob.id + 1);
		
		if(!myMap.containsKey(newGlob.id))
			myMap.put(newGlob.id, new ArrayList<Glob>());
		ArrayList<Glob> aL = myMap.get(newGlob.id);
		aL.add(newGlob);
		myMap.put(newGlob.id, aL);
		
		if(globShape.equals("circle")){
			newGlob.shape = Shape.circle;
		}else if(globShape.equals("arc")){
			newGlob.shape = Shape.arc;
		}else{
			newGlob.shape = Shape.polygon;
		}
		
	}
	
	public void deleteFromMyMap(int id){
		//System.out.println("Deleting Object with ID: " + id);
		idSet.add(id);
		myMap.remove(id);
	}
	
	public void merge(int id1,int id2){
		//System.out.println("Merging Objects with ID1: " + id1 + " ID2: " + id2);
		ArrayList<Glob> listAtid1 = myMap.get(id1);
		ArrayList<Glob> listAtid2 = myMap.get(id2);
		
		for(int i = 0 ; i < listAtid2.size() ; i++){
			listAtid1.add(listAtid2.get(i));
		}
		
		deleteFromMyMap(id2);
	}
}

public class GraphicsGlobs {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		String[] command = 
			{"make polygon", "make polygon", "make arc", "make circle", "make circle", "delete 3", "make polygon", "make arc", "make arc", "merge 1 3", "merge 1 4", "merge 2 1", "make arc", "make arc", "make circle", "make circle", "merge 6 5", "split 6", "merge 2 1"};
		
		GraphicsGlobsHelper helper = new GraphicsGlobsHelper();
		
		int [] answer = helper.execute(command, 2);
		
		for(int ans : answer){
			System.out.print(ans + " ");
		}
	}

}
