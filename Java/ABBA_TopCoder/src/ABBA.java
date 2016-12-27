//WORKING
class ABBAHelper{
    public String canObtain(String initial, String target){
        if(target.length() < initial.length()) return "impossible";
        if(target.length() == initial.length()){
        	if(target.equals(initial)) return "possible";
        	else return "impossible";
        }
        
        //if(check(new StringBuilder().append(initial), new StringBuilder().append(target)))
        if(check(initial, target))
            return "possible";
        else
            return "impossible";
    }
    
    private boolean check(String initial, String target){
    	//System.out.println("In: " + initial);
    	if(target.length() < initial.length()) return false;
        if(initial.length() == target.length()){
            if(initial.equals(target)){
                return true;
            }else{
                return false;
            }
       	}
        
        StringBuilder inCopy = new StringBuilder().append(initial);
        boolean appendA = check(initial + "A", target);
        if(appendA)
            return true;
        //System.out.println("Appending B");
        boolean appendB = check(inCopy.reverse().append('B').toString(), target);
        if(appendB)
            return true;
        
        return false;
    }
}
public class ABBA{
    public static void main(String[] args){
        String in = "A";
        String tar = "BAB";
        
        ABBAHelper helper = new ABBAHelper();
        
        System.out.println(helper.canObtain(in, tar));
    }
}