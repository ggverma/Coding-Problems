public class Sqrt {

  public static void main(String[] args){
    int num = Integer.parseInt(args[0]);//number whse square root is to be calculated
    System.out.println("Square root: " + new Sqrt().mySqrt(num));
  }

    public int mySqrt(int x) {
      long l = 0, r = x;
        for(long i = x / 2 ;  ; i = (l + r) / 2){
            if(i * i == x) return (int)i;
            else if(Math.abs(l - r) <= 1) return r * r > x ? (int)l : (int)r;
            else if(i * i > x){
                r = (l + r) / 2;
            }else{
                l = (l + r) / 2;
            }
        }
    }
}
