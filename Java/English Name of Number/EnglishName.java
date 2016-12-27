import java.util.HashMap;

class SolutionHelper{

    private HashMap<Integer, String> places, unary, binary, tens;

    public SolutionHelper(){

        places = new HashMap<>();
        unary = new HashMap<>();
        binary = new HashMap<>();
        tens = new HashMap<>();

        places.put(0, "");
        places.put(1, "Thousand ");
        places.put(2, "Million ");
        places.put(3, "Billion ");

        unary.put(1, "One ");
        unary.put(2, "Two ");
        unary.put(3, "Three ");
        unary.put(4, "Four ");
        unary.put(5, "Five ");
        unary.put(6, "Six ");
        unary.put(7, "Seven ");
        unary.put(8, "Eight ");
        unary.put(9, "Nine ");

        binary.put(2, "Twenty ");
        binary.put(3, "Thirty ");
        binary.put(4, "Forty ");
        binary.put(5, "Fifty ");
        binary.put(6, "Sixty ");
        binary.put(7, "Seventy ");
        binary.put(8, "Eighty ");
        binary.put(9, "Ninety ");

        tens.put(10, "Ten ");
        tens.put(11, "Eleven ");
        tens.put(12, "Twelve ");
        tens.put(13, "Thirteen ");
        tens.put(14, "Fourteen ");
        tens.put(15, "Fifteen ");
        tens.put(16, "Sixteen ");
        tens.put(17, "Seventeen ");
        tens.put(18, "Eighteen ");
        tens.put(19, "Nineteen ");
    }

    private StringBuilder getRepresentation(int num, int position){
        StringBuilder sb = new StringBuilder();
        if(num == 0) return sb;
        if(num / 100 > 0){
            // Ternary
            int x = (num - num % 100) / 100;
            if(x > 0){
                sb.append(unary.get(x));
                sb.append("Hundred ");
            }
        }
        num %= 100;
        if(num / 10 > 0){
            // Binary
            if(num < 20){
              sb.append(tens.get(num));
              sb.append(places.get(position));
              return sb;
            }else{
              int x = (num - num % 10) / 10;
              if(x > 0){
                  sb.append(binary.get(x));
              }
            }
        }
        num %= 10;
        if(num > 0){
            // Unary
            if(num > 0)
                sb.append(unary.get(num));
        }
        sb.append(places.get(position));
        return sb;
    }

    public String getEnglishName(int num){
        StringBuilder sb = new StringBuilder();
        int p = 0;
        while(num > 0){
            StringBuilder sb2 = getRepresentation(num %1000, p);
            sb2.append(sb);
            sb = sb2;
            p++;
            num = (num - num % 1000) / 1000;
        }
        return sb.toString();
    }
}

public class EnglishName {

    public static void main(String[] args) {
      System.out.println(new EnglishName().numberToWords(Integer.parseInt(args[0])));
    }

    public String numberToWords(int num) {
        if(num == 0) return "Zero";
        SolutionHelper sh = new SolutionHelper();
        return sh.getEnglishName(num.trim());
    }
}

/*

/////////// Leet Code Question ////////////
Convert a non-negative integer to its english words representation. Given input is guaranteed to be less than 231 - 1.

For example,
123 -> "One Hundred Twenty Three"
12345 -> "Twelve Thousand Three Hundred Forty Five"
1234567 -> "One Million Two Hundred Thirty Four Thousand Five Hundred Sixty Seven"

*/
