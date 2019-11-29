import java.util.stream.Stream;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;
import java.util.Comparator;

public class Reducing {

    private static Integer[] NUMBERS = { 5, 3, 1, 76, 43, 32, 65, 87, 28, 13 };

    public static void main(String[] args) {

        /**
         * [5] reduce
         * reduction is a terminal operation and reduces the stream down to a single value
         * count(), min() and max() are examples of built in reduction operations
         * reduce() allows custom reduction methods to be written
         *
         * The first version of reduce() only requires a lambda that satisfys a BinrayOperator interface.
         * That accumulator method will take two parameters and operate on them to produce a result.
         *
         * The two params represent the current value of the running result and the next element in
         * the stream.
         */
        //==========================================================================================
        // reduce the stream down to the sum of all the numbers
        // Of course, Integer::sum satifies as an accumulator function too and could be used instead
        // of the lambda
        //==========================================================================================
        Optional<Integer> sumOfNumbers = Arrays.stream(NUMBERS)
            .reduce((subTotal, element) -> subTotal + element);

        System.out.printf("The sum of the numbers is: %d", sumOfNumbers.get());
        System.out.println("");

        //==========================================================================================
        // The second version of reduce() requires an identity value as well as the accumulator method.
        //
        // This identity value is the starting point for what will be the final result (and is the
        // value returned if the stream is empty).
        //
        // As a rule of thumb, the identity value should be something that some accumulator operation
        // involving the identiy value and any element in the stream should leave the element unchanged.
        //
        // For example, if the accumulator is adding the elements together or counting them for instance
        // then the identity value should be 0 as any element plus 0 will not change that element's value.
        // In the case where the elements are being multiplied against each other then the identity
        // value should be 1 as any element's value multiplied by 1 unchanges the element's value
        // (multiplying by 0 would obviously change the element's value to 0).
        //
        // This example demonstrates a custom count() reduction method.
        //==========================================================================================
        int identityValue = 1;
        int result = Stream.of(2, 5, 10)
            .reduce(identityValue, (subTotal, element) -> subTotal * element);

        System.out.printf("The result of multiplying the stream's elements is: %d %n", result);

        //==========================================================================================
        // Reduction is not just restricted to numerical values. A String stream's contents can be reduced
        // to a single String
        //==========================================================================================
        List<String> strings = Arrays.asList("This", "sequence", "of", "Strings",
            "will", "be", "reduced", "to", "a", "single", "String");

        Optional<String> s = strings.stream()
            .map(str -> str + " ") // add whitespace in between each String
            .reduce((subResult, element) -> subResult += element);

        System.out.println("The reduced String is: " + s.get());

        //==========================================================================================
        // find the total number of characters in the stream
        //==========================================================================================
        int totalNumOfChars = strings.stream()
            .reduce(0, (subTotal, element) -> subTotal += element.length(), Integer::sum);

        System.out.println("Total number of chars in string stream is: " + totalNumOfChars);

        //==========================================================================================
        // Min and max elements in stream
        // Can write custom comparison or use built in ones such as Integer::compare
        //==========================================================================================
        Optional<Integer> min = Arrays.stream(NUMBERS)
            .min((a, b) -> {
                if (a < b) return -1;
                else if (a == b) return 0;
                return 1;
            });

        Optional<Integer> max = Arrays.stream(NUMBERS)
            .max(Integer::compare);

        //==========================================================================================
        // Find if ALL elements match some predicate
        //==========================================================================================
        boolean allNumsAreLessThanTen = Arrays.stream(NUMBERS)
            .allMatch(n -> n < 10); // false

        boolean allNumsArePositve = Arrays.stream(NUMBERS)
            .allMatch(n -> n > -1); // true

        //==========================================================================================
        // Find if ANY element matches some predicate
        //==========================================================================================
        boolean anyNumIsLessThanTen = Arrays.stream(NUMBERS)
            .anyMatch(n -> n < 10); // true

        boolean anyNumIsNegative = Arrays.stream(NUMBERS)
            .anyMatch(n -> n < 0); // false

        //==========================================================================================
        // Find the first element in the stream
        // Returns empty Optional if the stream is empty
        //==========================================================================================
        Optional<Integer> first = Arrays.stream(NUMBERS)
            .findFirst(); // 5

        //==========================================================================================
        // Convert the stream into an array
        //==========================================================================================
        Integer[] intArray = Arrays.stream(NUMBERS)
            .toArray(Integer[]::new);

        int[] primitiveIntArray = Arrays.stream(NUMBERS)
            .mapToInt(x -> x) // map to IntStream first
            .toArray();


    }
}
