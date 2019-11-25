/**
 * A class that defines static typical string operations
 */
public class MyStringOps {

    /**
     * static version of reverse(s)
     */
    static public String reverse(String s) {
        String result = "";
        for (int i = s.length() - 1; i >= 0; --i)
            result += s.charAt(i);
        return result;
    }

    /**
     * instance version of reverse(s)
     */
    public String reverseString(String s) {
        return MyStringOps.reverse(s);
    }

}
