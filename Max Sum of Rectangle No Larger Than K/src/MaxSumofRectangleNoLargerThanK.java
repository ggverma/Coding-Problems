//INCOMPLETE
class MaxSumRectHelper{
	private int maxSum;
	public int maxSumSubmatrix(int[][] matrix, int k) {
        maxSum = Integer.MIN_VALUE;
        int memoLength = (matrix[0].length) * (matrix[0].length + 1) / 2;
        int memo[] = new int[memoLength];
    }
	
	private int maxSumSubmatrix(int[][] matrix, int k, int row, int col, int sum, int[] memo, int memoID) {
		if(col == matrix[0].length) return 0;
		if(sum > maxSum) matrix = sum;
		if(row == matrix.length - 2){
			for(int i = col ; i < matrix[0].length ; i++){
				int nSum = matrix[matrix.length - 1][col] + matrix[matrix.length - 2][col];
				memo[memoID] = nSum;
				memoID++;
				nSum += maxSumSubmatrix(matrix, k, row, col + 1, nSum, memo, memoID);
				memo[memoID] = nSum;
			}
		}
	}
}
public class MaxSumofRectangleNoLargerThanK {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
