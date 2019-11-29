/**
 * This function performs some kind of operation on each element and returns a new stream
 * containing the results of those operations. That new stream may not be the same type as the
 * original stream.
 */

import java.util.Arrays;
import java.util.List;
import java.util.Collection;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.stream.Collector;
import java.util.stream.IntStream;
import java.util.stream.DoubleStream;
import java.util.stream.LongStream;

public class Mapping {

    public static void main(String[] args) {

        //==========================================================================================
        // Map the lowercase letters to a new stream where they have been transformed into uppercase.
        //==========================================================================================
        Stream<Character> stream = Stream.of( 'a', 'b', 'c' )
            .map(Character::toUpperCase);

        System.out.print("Char array mapped to uppercase: ");
        stream.forEach(c -> System.out.print(c));
        System.out.println("");

        //==========================================================================================
        // Map the chars to a new stream containing the ascii value of each char
        // and return them in a list of integers
        //==========================================================================================
        List<Integer> charsListToAsciiIntegerList = Stream.of( 'a', 'b', 'c' )
            .map(c -> (int) c)
            .collect(Collectors.toList());

        System.out.println("The new Integer list contains: " + charsListToAsciiIntegerList);

        //==========================================================================================
        // flatMap() discards the collection by extracting its elements and returns a new stream with
        // those contents.
        // For example, a Stream<List<Integer>> would be flattened to a Stream<Integer>.
        //==========================================================================================
        Stream<List<String>> stringListStream = Stream.of(
            Arrays.asList("This", "is", "line", "one"),
            Arrays.asList("This", "is", "line", "two"),
            Arrays.asList("This", "is", "line", "three"));

        Stream<String> stringStream = stringListStream.flatMap(Collection::stream);

        //==========================================================================================
        // Map to an IntStream / DoubleStream / LongStream
        //==========================================================================================
        IntStream intStream2 = Stream.of(1, 2, 3, 4, 5)
            .mapToInt(i -> i);

        DoubleStream doubleStream = Stream.of(1.5, 2.6, 3.7, 4.8, 5.9)
            .mapToDouble(d -> d);

        LongStream longStream = Stream.of(1L, 2L, 3L, 4L, 5L)
            .mapToLong(l -> l);


    }
}
