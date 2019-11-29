/**
 * [3] sort
 * this is an intermediate operation
 */

import java.util.stream.Stream;
import java.util.Arrays;
import java.util.List;

public class Sorting {

    private static Integer[] numbers = { 5, 3, 1, 76, 43, 32, 65, 87, 28, 13 };

    public static void main(String[] args) {

        //==========================================================================================
        // default sorts the elements in the natural order
        //==========================================================================================
        Stream<Integer> sortedAsc = Arrays.stream(numbers)
            .sorted();

        System.out.print("The sorted-by-ascension numbers are: ");
        sortedAsc.forEach(n -> System.out.printf("%d ", n));
        System.out.println("");

        //==========================================================================================
        // Specify a custom sort that satisfies the Comparator interface.
        // This is a descending sort.
        //==========================================================================================
        Stream<Integer> sortedDesc = Arrays.stream(numbers)
            .sorted((i, j) -> {
                if (j < i) return -1;
                else if (i == j) return 0;
                return 1;
            });

        System.out.print("The sorted-by-descension numbers are: ");
        sortedDesc.forEach(n -> System.out.printf("%d ", n));
        System.out.println("");

        //==========================================================================================
        // Sort objects based on one of their properties
        // This example sorts the employees by their IDs.
        //==========================================================================================
        Stream<Employee> employeesStream = Stream.of(
            new Employee(40),
            new Employee(6),
            new Employee(2),
            new Employee(13)
        );

        Stream<Employee> sortedEmployees = employeesStream
            .sorted((thisEmpl, thatEmpl) -> {
                if (thisEmpl.getId() < thatEmpl.getId()) return -1;
                else if (thisEmpl.equals(thatEmpl)) return 0;
                else return 1;
            });

        System.out.print("The employees sorted-by-ascension numbers are: ");
        sortedEmployees.forEach(empl -> System.out.print(empl + " "));
        System.out.println("");
    }
}
