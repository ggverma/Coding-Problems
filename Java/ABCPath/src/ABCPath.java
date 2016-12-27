/*
You will be given a 2-dimensional grid of letters. Write a method to find the length of the longest path of consecutive letters, starting at 'A'. Paths can step from one letter in the grid to any adjacent letter (horizontally, vertically, or diagonally). 

For example, in the following grid, there are several paths from 'A' to 'D', but none from 'A' to 'E': 
    { "ABE",
      "CFG",
      "BDH",
      "ABC" }


One such path is: 
    A B .
    C . .
    . D .
    . . .
    (spaces are for clarity only)


so, for this grid, your method should return 4. 

Constraints

 
- grid will contain between 1 and 50 elements, inclusive.

 
- Each element of grid will be between 1 and 50 characters long, inclusive.

 
- Each element of grid will have the same length.

 
- grid will contain only uppercase letters ('A'-'Z').
*/
import java.util.ArrayList;

class AlphabetPos{
	int rowNum;
	int colNum;
	
	public AlphabetPos(int x, int y) {
		// TODO Auto-generated constructor stub
		rowNum = x;
		colNum = y;
	}
}

class ABCPathHelper{
	public int length(String[] grid){
		ArrayList<AlphabetPos> aPositions = findA(grid);
		
		if(aPositions.size() == 0){
			return 0;
		}
		
		int max = 0;
		for(AlphabetPos x : aPositions){
			int lengthFromHere = findMaxLength(grid, x, Character.valueOf('A'), 1);
			if(lengthFromHere > max){
				max = lengthFromHere;
			}
		}
		return max;
	}
	
	public int findMaxLength(String[] grid, AlphabetPos lastAlphabet, int lastAlphabetValue, int lengthNow){
		int maxLengthH = lengthNow;
		int maxLengthV = lengthNow;
		int maxLengthD = lengthNow;
		if((lastAlphabet.colNum + 1) < grid[0].length() && grid[lastAlphabet.rowNum].charAt(lastAlphabet.colNum + 1) == lastAlphabetValue + 1)
			maxLengthH = findMaxLength(grid, new AlphabetPos(lastAlphabet.rowNum, lastAlphabet.colNum + 1), lastAlphabetValue + 1, lengthNow + 1);
		else if((lastAlphabet.colNum - 1) >= 0 && grid[lastAlphabet.rowNum].charAt(lastAlphabet.colNum - 1) == lastAlphabetValue + 1)
			maxLengthH = findMaxLength(grid, new AlphabetPos(lastAlphabet.rowNum, lastAlphabet.colNum - 1), lastAlphabetValue + 1, lengthNow + 1);
		else if((lastAlphabet.rowNum + 1) < grid.length && grid[lastAlphabet.rowNum + 1].charAt(lastAlphabet.colNum) == lastAlphabetValue + 1)
			maxLengthV = findMaxLength(grid, new AlphabetPos(lastAlphabet.rowNum + 1, lastAlphabet.colNum), lastAlphabetValue + 1, lengthNow + 1);
		else if((lastAlphabet.rowNum - 1) >= 0 && grid[lastAlphabet.rowNum - 1].charAt(lastAlphabet.colNum) == lastAlphabetValue + 1)
			maxLengthV = findMaxLength(grid, new AlphabetPos(lastAlphabet.rowNum - 1, lastAlphabet.colNum), lastAlphabetValue + 1, lengthNow + 1);
		else if((lastAlphabet.rowNum + 1) < grid.length && (lastAlphabet.colNum + 1) < grid[0].length() && grid[lastAlphabet.rowNum + 1].charAt(lastAlphabet.colNum + 1) == lastAlphabetValue + 1)
			maxLengthD = findMaxLength(grid, new AlphabetPos(lastAlphabet.rowNum + 1, lastAlphabet.colNum + 1), lastAlphabetValue + 1, lengthNow + 1);
		else if((lastAlphabet.rowNum + 1) < grid.length && (lastAlphabet.colNum - 1) >= 0 && grid[lastAlphabet.rowNum + 1].charAt(lastAlphabet.colNum - 1) == lastAlphabetValue + 1)
			maxLengthD = findMaxLength(grid, new AlphabetPos(lastAlphabet.rowNum + 1, lastAlphabet.colNum - 1), lastAlphabetValue + 1, lengthNow + 1);
		else if((lastAlphabet.rowNum - 1) >= 0 && (lastAlphabet.colNum + 1) < grid[0].length() && grid[lastAlphabet.rowNum - 1].charAt(lastAlphabet.colNum + 1) == lastAlphabetValue + 1)
			maxLengthD = findMaxLength(grid, new AlphabetPos(lastAlphabet.rowNum - 1, lastAlphabet.colNum + 1), lastAlphabetValue + 1, lengthNow + 1);
		else if((lastAlphabet.rowNum - 1) >= 0 && (lastAlphabet.colNum - 1) >= 0 && grid[lastAlphabet.rowNum - 1].charAt(lastAlphabet.colNum - 1) == lastAlphabetValue + 1)
			maxLengthD = findMaxLength(grid, new AlphabetPos(lastAlphabet.rowNum - 1, lastAlphabet.colNum - 1), lastAlphabetValue + 1, lengthNow + 1);
		else
			return lengthNow;
		
		int max1 = maxLengthD > maxLengthH ? maxLengthD : maxLengthH;
		max1 = max1 > maxLengthV ? max1 : maxLengthV;
		
		return max1;
	}
	
	public ArrayList<AlphabetPos> findA(String[] grid){
		ArrayList<AlphabetPos> alphabetAPositions = new ArrayList<>();
		for(int i = 0 ; i < grid.length ; i++){
			for(int j = 0 ; j < grid[i].length() ; j++){
				if(grid[i].charAt(j) == 'A'){
					alphabetAPositions.add(new AlphabetPos(i, j));
				}
			}
		}
		return alphabetAPositions;
	}
}

public class ABCPath {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//String[] grid = { "ABE", "CFG", "BDH", "ABC" };
		//String[] grid = { "BCDEFGHIJKLMNOPQRSTUVWXYZ" };
		//String[] grid = { "C", "D", "B", "A" };
		String[] grid = { "KCBVNRXSPVEGUEUFCODMOAXZYWEEWNYAAXRBKGACSLKYRVRKIO", "DIMCZDMFLAKUUEPMPGRKXSUUDFYETKYQGQHNFFEXFPXNYEFYEX", "DMFRPZCBOWGGHYAPRMXKZPYCSLMWVGMINAVRYUHJKBBRONQEXX", "ORGCBHXWMTIKYNLFHYBVHLZFYRPOLLAMBOPMNODWZUBLSQSDZQ", "QQXUAIPSCEXZTTINEOFTJDAOBVLXZJLYOQREADUWWSRSSJXDBV", "PEDHBZOVMFQQDUCOWVXZELSEBAMBRIKBTJSVMLCAABHAQGBWRP", "FUSMGCSCDLYQNIXTSTPJGZKDIAZGHXIOVGAZHYTMIWAIKPMHTJ", "QMUEDLXSREWNSMEWWRAUBFANSTOOJGFECBIROYCQTVEYGWPMTU", "FFATSKGRQJRIQXGAPLTSXELIHXOPUXIDWZHWNYUMXQEOJIAJDH", "LPUTCFHYQIWIYCVOEYHGQGAYRBTRZINKBOJULGYCULRMEOAOFP", "YOBMTVIKVJOSGRLKTBHEJPKVYNLJQEWNWARPRMZLDPTAVFIDTE", "OOBFZFOXIOZFWNIMLKOTFHGKQAXFCRZHPMPKGZIDFNBGMEAXIJ", "VQQFYCNJDQGJPYBVGESDIAJOBOLFPAOVXKPOVODGPFIYGEWITS", "AGVBSRLBUYOULWGFOFFYAAONJTLUWRGTYWDIXDXTMDTUYESDPK", "AAJOYGCBYTMXQSYSPTBWCSVUMNPRGPOEAVVBGMNHBXCVIQQINJ", "SPEDOAHYIDYUJXGLWGVEBGQSNKCURWYDPNXBZCDKVNRVEMRRXC", "DVESXKXPJBPSJFSZTGTWGAGCXINUXTICUCWLIBCVYDYUPBUKTS", "LPOWAPFNDRJLBUZTHYVFHVUIPOMMPUZFYTVUVDQREFKVWBPQFS", "QEASCLDOHJFTWMUODRKVCOTMUJUNNUYXZEPRHYOPUIKNGXYGBF", "XQUPBSNYOXBPTLOYUJIHFUICVQNAWFMZAQZLTXKBPIAKXGBHXX" };
		//String[] grid = { "EDCCBA", "EDCCBA" };
		//String[] grid = { "AMNOPA", "ALEFQR", "KDABGS", "AJCHUT", "AAIWVA", "AZYXAA" };
		
		ABCPathHelper helper = new ABCPathHelper();
		
		System.out.println("Max: " + helper.length(grid));
	}

}
