import java.util.List;
import java.util.Arrays;

public class StringOperations {
    public static void main(String[] args) {

        String s;

        //==========================================================================================
        // Java creates a String object everytime a string literal is specified and so the literal
        // can be used just as if it was a String object.
        //==========================================================================================
        int length = "string literal".length();
        boolean isFalse = "banana".startsWith("f");
        char[] charArray = "Manhattan, New York".toCharArray();

        //==========================================================================================
        // Concatenating strings with +
        //==========================================================================================
        s = "abc" + "def";
        s = "abc" + new String("def");
        s = new String("abc") + new String("def");

        //==========================================================================================
        // Concatenating non-String data types.
        // A non String type is converted into its string representation ONLY when the object it is
        // being concatenated with is a String.
        //
        // Conversions are performed by String.valueOf(val). For primitive types it returns a String
        // of the value. For object types String.valueOf(val) calls the toString() method on the object.
        //==========================================================================================
        s = "The answer is " + 8; // "The answer is 8"
        s = "The answer is " + 4 + 4; // "The answer is 44"
        s = "The answer is " + (4 + 4); // "The answer is 8"

        List<Integer> list = Arrays.asList(1,2,3);
        s = "The list is " + list; // "The list is [1,2,3]"

        //==========================================================================================
        // Joining strings with delimeter
        //==========================================================================================
        String delim = " "; // single char delimeter
        String.join(delim, "Hello", "world"); // "Hello world"

        delim = ", "; // multi char delimeter
        String.join(delim, "John", "Paul", "George", "Ringo"); // "John, Paul, George, Ringo"

        System.out.println();
    }
}
