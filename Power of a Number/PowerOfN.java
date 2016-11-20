import java.util.HashMap;

public class PowerOfN {

    public static void main(String[] args){
        if(args.length < 2){
          System.out.println("Please enter <number> <power>");
          return;
        }
        System.out.println(myPow(Double.parseDouble(args[0]), Integer.parseInt(args[1])));
    }

    public static double myPow(double x, int n) {
        // Return 1 if power is 0
        if(n == 0) return 1;

        // Return number if number is 0 or 1.
        if(x == 0 || x == 1) return x;

        // Have to consider a long because of boundary case of integer n. There is no 2147483648 integer if n is -2147483648.
        long nn = n;

        // If power is negative, return 1 / x^positivePower
        boolean isPowNeg = false;
        if(n < 0){
            isPowNeg = true;
            nn = (long)n * -1;
        }

        long i = 1;

        // Stores all powers of power of 2.
        HashMap<Long, Double> map = new HashMap<>();

        double y = 1;

        while(i < nn){
            map.put(i, x);
            y = x;
            x *= x;

            // Makes it O(log(n))
            i *= 2;

            // Lost precision, return 0.
            if(x == 0) return 0;
            if(isPowNeg){
                if(1 / x == 0) return 0;
            }
        }
        // If nn was exactly a power of 2, we are done!
        if(i == nn){
          if(isPowNeg)  return 1 / x;
          return x;
        }

        // Else, we do bit manipulation.
        i /= 2;
        x /= y;

        // Number of multiplications left.
        i = nn - i;

        // A number like 000000000000101 requires 5 more multiplcations with the original number. or 4 + 1.
        // We already have numbers at these places from the map.
        int j = 0;
        long k = 1;
        while(i != 0){
            if((i&1) == 1){
                x *= map.get(k);
            }
            i >>= 1;
            k <<= 1;
        }
        if(isPowNeg) return 1 / x;
        return x;
    }
}
