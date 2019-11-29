/**
 * [1] obtaining a stream
 */

import java.util.stream.Stream;
import java.util.stream.Stream.Builder;
import java.util.Arrays;
import java.util.List;
import java.awt.Point;

public class Instantiation {

    private static List<String> letters = Arrays.asList("a", "b", "c", "d", "e", "f", "g");
    private static Integer[] numbers = { 5, 3, 1, 76, 43, 32, 65, 87, 28, 13 };

    public static void main(String[] args) {


         // ... from collection
        Stream<String> streamFromCollection = letters.stream();

        // ... from array
        Stream<Integer> streamFdromArray = Arrays.stream(numbers);

        // ... from static method of Stream
        Stream<Point> streamFromStream = Stream.of(new Point(1, 2),
                                                    new Point(3, 4),
                                                    new Point(5, 6));

        // stream containing single item, empty stream if arg is null or an empty stream
        Stream<Point> singleElementStream = Stream.ofNullable(new Point(1,2));
        Stream<String> emptyStream = Stream.ofNullable(null);
        emptyStream = Stream.empty();

        // ... from Stream builder
        Stream.Builder<Double> builder = Stream.builder();
        builder.accept(3.14);
        builder.accept(6.28);
        builder.accept(9.56);
        Stream<Double> streamFromBuilder = builder.build();


    }
}
