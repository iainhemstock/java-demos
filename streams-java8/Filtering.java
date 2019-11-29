/**
 * [4] filter
 * this is an intermediate operation
 */

import java.util.stream.Stream;
import java.util.Arrays;

public class Filtering {

    private static Integer[] NUMBERS = { 5, 3, 1, 76, 43, 32, 65, 87, 28, 13 };

    public static void main(String[] args) {

        //==========================================================================================
        // filter out the odd numbers
        //==========================================================================================
        Stream<Integer> oddNumbers = Arrays.stream(NUMBERS)
            .filter(n -> (n % 2) == 1);

        //==========================================================================================
        // filter out even numbers over 50
        // this combines multiple filters processing the stream
        //==========================================================================================
        Stream<Integer> evenNumbersOver30 = Arrays.stream(NUMBERS)
            .filter(n -> (n % 2) == 0)
            .filter(n -> n > 30);

        //==========================================================================================
        // Return a new stream of the elements in this stream which is limited to a specified length
        //==========================================================================================
        long maxSize = 4;
        Stream<Integer> limitedStream = Arrays.stream(NUMBERS)
            .limit(maxSize); // returns a stream consisting of the first MAXSIZE elements

        //==========================================================================================
        // Return a new stream consisting of all the elements after the specified number of elements
        // have been skipped
        //==========================================================================================
        int numOfElementsToSkip = 5;
        Stream<Integer> skippedStream = Arrays.stream(NUMBERS)
            .skip(numOfElementsToSkip); 

    }
}
