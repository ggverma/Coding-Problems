import java.util.ArrayList;

public class LongestAbsoluteFilePath{

  public int getPathLength(String exp){
    ArrayList<String> path = new ArrayList<>(); // Contains the name of the directories that lead to the current working set

    int depth = 0; // at root
    int ml = 0; // maximal length
    StringBuilder dir_file_name = new StringBuilder(); // the name of the current directory or file.

    for(int i = 0 ; i < exp.length() ; i++){
      if(exp.charAt(i) == '.'){
        // file
        // formulate the name
        dir_file_name.append(".");
        i++;
        while(i < exp.length()  && (exp.charAt(i) == '.' || (exp.charAt(i) <= 'z' && exp.charAt(i) >= 'a') || (exp.charAt(i) <= 'Z' && exp.charAt(i) >= 'A'))){
          dir_file_name.append(exp.charAt(i));
          i++;
        }
        i--;
        int l = dir_file_name.length();
        for(String s : path){
            l+= s.length() + 1;
        }
        // name formulated
        dir_file_name = new StringBuilder();
        if(l > ml) ml = l;
      }else if(exp.charAt(i) == '\n'){
        // if it is a new line, add current directory name to path
        path.add(dir_file_name.toString());

        // as this marks new name.
        dir_file_name = new StringBuilder();
        // A break;
        i++;// As it is a new line
        int depthNow = 0;
        while(i < exp.length() && exp.charAt(i) == '\t'){
          depthNow++;
          i++;
        }
        i--;
        int toRemove = depth - depthNow;
        depth = depthNow;
        // remove the tags from path if the depth decreases or reaches same level
        while(toRemove >= 0){
          path.remove(path.size() - 1);
          toRemove--;
        }
      }else{
        dir_file_name.append(exp.charAt(i));
      }
    }
    return ml;
  }

  public static void main(String[] args) {
    LongestAbsoluteFilePath obj = new LongestAbsoluteFilePath();

    String exp = "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext";

    System.out.println("\nL: " + obj.getPathLength(exp));
  }
}

/*

////////////// LEET CODE CHALLENGE ////////////////////

Suppose we abstract our file system by a string in the following manner:

The string "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext" represents:

dir
    subdir1
    subdir2
        file.ext
The directory dir contains an empty sub-directory subdir1 and a sub-directory subdir2 containing a file file.ext.

The string "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext" represents:

dir
    subdir1
        file1.ext
        subsubdir1
    subdir2
        subsubdir2
            file2.ext
The directory dir contains two sub-directories subdir1 and subdir2. subdir1 contains a file file1.ext and an empty second-level sub-directory subsubdir1. subdir2 contains a second-level sub-directory subsubdir2 containing a file file2.ext.

We are interested in finding the longest (number of characters) absolute path to a file within our file system. For example, in the second example above, the longest absolute path is "dir/subdir2/subsubdir2/file2.ext", and its length is 32 (not including the double quotes).

Given a string representing the file system in the above format, return the length of the longest absolute path to file in the abstracted file system. If there is no file in the system, return 0.

Note:
The name of a file contains at least a . and an extension.
The name of a directory or sub-directory will not contain a ..
Time complexity required: O(n) where n is the size of the input string.

Notice that a/aa/aaa/file1.txt is not the longest file path, if there is another path aaaaaaaaaaaaaaaaaaaaa/sth.png.

*/
