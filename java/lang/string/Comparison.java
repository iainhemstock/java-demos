public class Comparison {
    public static void main(String[] args) {

        //==========================================================================================
        // Examine whether two Strings are considered equal.
        // equals() is case-sensitive and returns true when both strings have the same chars in the
        // same order, false otherwise.
        // equalsIgnoreCase() is case-insensitive and returns true when both strings contain the same
        // characters (regardless of case) in the same order, false otherwise.
        //==========================================================================================
        "apple".equals("apple"); // true
        "apple".equals("APPLE"); // false
        "apple".equalsIgnoreCase("APPLE"); // true

        //==========================================================================================
        // Compare a substring of this string with a substring from other string.
        // Returns true if so, false otherwise.
        //==========================================================================================
        // do both strings have a region that matches "berry"?
        final int THIS_STR_START_INDEX = 5;
        final int THAT_STR_START_INDEX = 4;
        final int NUM_CHARS_TO_MATCH = 5;

        "strawberry".regionMatches(THIS_STR_START_INDEX, "raspberry",
                                    THAT_STR_START_INDEX, NUM_CHARS_TO_MATCH); // true

        "STRAWBERRY".regionMatches(THIS_STR_START_INDEX, "raspberry",
                                        THAT_STR_START_INDEX, NUM_CHARS_TO_MATCH); // false, case sensitive

        boolean shouldIgnoreCase = true;
        "STRAWBERRY".regionMatches(shouldIgnoreCase, THIS_STR_START_INDEX, "raspberry",
                                        THAT_STR_START_INDEX, NUM_CHARS_TO_MATCH); // true, case insensitive

        //==========================================================================================
        // String implements Comparable so we can compare this string with another string.
        // compareTo(String) returns a negative, zero or a positive number when this string comes
        // before, is equal to or comes after the other string in the dictionary order.
        //
        // By default, compareTo() takes into account case sensitivity and treats uppercase chars as
        // coming before lowercase letters as their numerical ASCII values do the same.
        // To ignore case, use compareToIgnoreCase() instead.
        //==========================================================================================
        "alan".compareTo("bob"); // comes before
        "bob".compareTo("bob"); // is equal
        "bob".compareTo("alan"); // comes after

        "Bob".compareTo("alan"); // comes before since the ASCII char 'B' is before the ASCII char 'a'.
        "Bob".compareToIgnoreCase("alan"); // comes after since case is ignored
    }
}
