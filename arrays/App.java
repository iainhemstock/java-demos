/**
 * Arrays can hold primitive and reference types.
 *
 * When creating arrays then the element type's initial value is set to 0 for
 * numerical types, false for boolean types and null for reference types.
 *
 * The brackets placement can be either after the object type or after the array name.
 * 'Integer[] array' is exactly equivalent to 'Integer array[]' though the former is more common.
 *
 * The length (or size) or an array can be found with the array's property 'length'. This property's
 * value refers to the capacity of the array.
 *
 * Arrays are covarient which means that the usual inheritance rules between types are supported
 * by arrays too. Since Number is a supertype of Integer then Integer can be used wherever Number is
 * specified. Similarly a Number[] is a supertype of Integer[] and therefore an Integer[] can be used
 * wherever Number[] is specified.
 */

public class App {

    App() {
        //==============================================================================================
        // Single dimension arrays
        //==============================================================================================
        Integer[] intArray = new Integer[10]; // array of 10 integers each initialized to 0
        Boolean[] booleanArray = new Boolean[10]; // array of 10 booleans each initialized to false;
        String[] stringArray = new String[10]; // array of 10 strings each initialized to null

        // Using array initializer, array size is automatically determined from the number of elements.
        Integer[] intArrayInitializer = { 1, 2, 3 };

        //==============================================================================================
        // Multi dimension arrays
        //==============================================================================================
        // 0 0 0 0
        // 0 0 0 0
        int[][] intMultiArray = new int[2][4]; // creates two arrays containing four elements each

        // Left-most dimension size is required for initial initialization but right-most dimension can
        // be allocated later.
        // There is no particular advantage to doing it like this other than the number of elements can
        // differ for each dimension.
        // 0 0 0 0
        // 0 0 0 0 0
        int[][] intMultiArray2 = new int[2][];
        intMultiArray2[0] = new int[4];
        intMultiArray2[1] = new int[5];

        //==============================================================================================
        // Array members
        // All members of Object are inherited into an array except clone(). An array provides its own
        // implementation of clone() instead. Multidimensional array cloning is shallow meaning that
        // only a single new array is created and subarrays are still shared with the original array.
        //==============================================================================================
        int capacity = stringArray.length;
        stringArray.equals(intArray); // inherited from Object
        stringArray.getClass(); // inherited from Object
        String[] clonedStringArray = stringArray.clone();

        //==============================================================================================
        // Arrays are covarient i.e. an array of a subtype can be assigned to an array of a supertype.
        //==============================================================================================
        Number[] numArray = new Integer[10];
    }
}
