import java.util.Scanner;


public class EvaluateExpression{

  private int evaluate(String exp){
    return Integer.parseInt(evaluate(exp, 0));
  }

  private String evaluate(String exp, int i){
    System.out.println(exp + " " + i);
    if(i >= exp.length()) return "";
    int j = i;
    int k = i;
    if(exp.length() == 1) return exp;

    while(exp.charAt(i) == ' ') i++;
    while(exp.charAt(i) >= '0' && exp.charAt(i) <= '9') i++;
    i++;
    int a = Integer.parseInt(exp.substring(k, i));

    while(exp.charAt(i) == ' ') i++;
    char op = exp.charAt(i);
    i++;
    k = i;

    while(exp.charAt(i) == ' ') i++;
    while(exp.charAt(i) >= '0' && exp.charAt(i) <= '9') i++;
    int b = Integer.parseInt(exp.substring(k, i + 1));

    System.out.println("op=" + op);

    switch(op){
      case '-':
      System.out.println("sd");
      evaluate(exp, i + 1);
      exp = exp.substring(0, j) + (a - b);
      if(i + 1 < exp.length()) exp += exp.substring(i + 1, exp.length());
      break;
      case '+':
      System.out.println("sd");
      evaluate(exp, i + 1);
      exp = exp.substring(0, j) + (a + b);
      if(i + 1 < exp.length()) exp += exp.substring(i + 1, exp.length());
      break;
      case '/':
      exp = exp.substring(0, j) + (a / b);
      if(i + 1 < exp.length()) exp += exp.substring(i + 1, exp.length());
      evaluate(exp, i + 1);
      break;
      case '*':
        exp = exp.substring(0, j) + (a * b);
        if(i + 1 < exp.length()) exp += exp.substring(i + 1, exp.length());
        System.out.println(exp + "sadad");
        evaluate(exp, i + 1);
        break;
      default:
        System.out.println("Invalid string!");
        return "-1";
    }
    return exp;
  }

  public static void main(String[] args){
    Scanner sc = new Scanner(System.in);

    String exp = sc.nextLine();

    System.out.println("Result: " + new EvaluateExpression().evaluate(exp));
  }
}
