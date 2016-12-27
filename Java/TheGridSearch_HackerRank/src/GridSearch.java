import java.util.Scanner;

public class GridSearch {

    private static boolean check(int[][] matrix,int[][] toFind, int r, int c){
        for(int j = 0 ; j < toFind.length ; j++){
            for(int i = 0 ; i < toFind[0].length ; i++){
                if(matrix[j + r][c + i] != toFind[j][i]){
                    return false;
                }
            }
        }
        return true;
    }
    
    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        
        Scanner sc = new Scanner(System.in);
        
        int t = sc.nextInt();
        
        for( ; t > 0 ; t--){
            int r = sc.nextInt();
            int c = sc.nextInt();
            int[][] matrix = new int[r][c];
            for(int i = 0 ; i < r ; i++){
                for(int j = c ; j < c ; j++){
                    matrix[i][j] = sc.nextInt();
                }
            }
            int rI = sc.nextInt();
            int cI = sc.nextInt();
            
            int[][] toFind = new int[rI][cI];
            for(int i = 0 ; i < rI ; i++){
                for(int j = c ; j < cI ; j++){
                    matrix[i][j] = sc.nextInt();
                }
            }
            
            boolean exist = false;
            for(int i = 0 ; !exist && i < (matrix.length- toFind.length + 1) ; i++){
                for(int j = 0 ; j < (matrix[0].length - toFind[0].length + 1) ; j++){
                    if(check(matrix, toFind, i, j)){
                        exist = true;
                        break;
                    }
                }
            }
            if(exist){
                System.out.println("YES");
            }else{
                System.out.println("NO");
            }
        }
    }
}