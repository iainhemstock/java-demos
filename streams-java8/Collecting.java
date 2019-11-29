/**
 * The collect() method performs mutable reductions on a stream.
 *
 * A Collector object is passed to collect(). This Collector instance knows how to perform
 * some kind of reduction on the stream. The stream api provides many of the most common kind of reduction
 * behaviour in the form of Collector objects which are found in java.util.stream.Collectors.
 *
 * For example Collectors.averagingInt() will return a Collector that finds the average when applied
 * to the elements in a stream.
 *
 * Custom Collectors can be supplied by subclassing Collector.
 */

import java.util.stream.Stream;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.Arrays;
import java.util.ArrayDeque;
import java.util.stream.Collectors;
import java.util.stream.Collector;
import java.util.IntSummaryStatistics;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;

public class Collecting {

    private static List<Employee> employees = Arrays.asList(
        new Employee(1, "McCartney", Employee.DepartmentType.SALES, 25000),
        new Employee(2, "Lennon", Employee.DepartmentType.SALES, 15000),
        new Employee(3, "Harrison", Employee.DepartmentType.ACCOUNTING, 18000),
        new Employee(4, "Starr", Employee.DepartmentType.HR, 12000)
    );

    public static void main(String[] args) {

        // =========================================================================================
        // [1] collect employees into List
        // =========================================================================================
        List<Employee> listCollection = employees.stream()
            .collect(Collectors.toList());
        // =========================================================================================
        // [2] collect employees into Set
        // =========================================================================================
        Set<Employee> setCollection = employees.stream()
            .collect(Collectors.toSet());
        // =========================================================================================
        // [3] collect employees into map using their ID as key
        // =========================================================================================
        Map<Integer, Employee> mapOfEmployeesCollection = employees.stream()
            .collect(Collectors.toMap(Employee::getId, Function.identity()));
        // =========================================================================================
        // [4] collect IDs as keys and surnames as values into Map
        // =========================================================================================
        Map<Integer, String> mapCollection = employees.stream()
            .collect(Collectors.toMap(Employee::getId, Employee::getSurname));
        // =========================================================================================
        // [5] concat strings
        // output: "McCartneyLennonHarrisonStarr"
        // =========================================================================================
        String concat1 = employees.stream()
            .map(Employee::getSurname)
            .collect(Collectors.joining());
        // =========================================================================================
        // [6] concat strings with a comma and space in between each element
        // note that joining() is smart enough not to add the delimiter after the last string
        // output: "McCartney, Lennon, Harrison, Starr"
        // =========================================================================================
        String concat2 = employees.stream()
            .map(Employee::getSurname)
            .collect(Collectors.joining(", "));
        // =========================================================================================
        // [7] concat strings with delimiter separating each word and prefix appearing at the beginning
        // of the new string and suffix appearing at the end of the new string
        // output: "(McCartney, Lennon, Harrison, Starr)"
        // =========================================================================================
        String delimiter = ", ";
        String prefix = "(";
        String suffix = ")";
        String concat3 = employees.stream()
            .map(Employee::getSurname)
            .collect(Collectors.joining(delimiter, prefix, suffix));
        // =========================================================================================
        // [8] count the number of elements being collected
        // output: 4
        // =========================================================================================
        long employeesSize = employees.stream()
            .collect(Collectors.counting());
        // =========================================================================================
        // [9] Collect elements by grouping them together by some parameter.
        // The map's key type is the type of the value returned from groupingBy().
        // The map's value type, by default, is a List (although that can be changed, see below).
        // ouput: A map containing three lists:
        //      [1] a list containing the two Sales employees
        //      [2] a list containing the Accounting employee
        //      [3] a list containing the HR employee
        // usage: List<Employee> salesEmployees = map.get(Employee.DepartmentType.SALES);
        // =========================================================================================
        Map<Employee.DepartmentType, List<Employee>> groupMap = employees.stream()
            .collect(Collectors.groupingBy(Employee::getDepartmentType));
        // =========================================================================================
        // [10] The only difference between this example and the last one is that the grouped employees
        // are placed in Sets instead of the default List.
        Map<Employee.DepartmentType, Set<Employee>> groupMap2 = employees.stream()
            .collect(Collectors.groupingBy(Employee::getDepartmentType, Collectors.toSet()));
        // =========================================================================================
        // [11] Using a custom Collector
        // A Collector requires a supplier, accumulator, combiner and a finisher.
        // -- The supplier specifies the result container
        // -- The accumulator specifies how the reduction is performed
        // -- The combiner is used in parallel stream processing where accumulations are happening in
        // ---- parallel and so specifies how those partial results (suppliers) are combined
        // -- The finisher performs a final function on the result after all of the accumulation and
        // combining. Here it converts the int[] into an int.
        // This examples reimplements the existing Collector.counting() reduction routine.
        // =========================================================================================
        Supplier<int[]> supplier = () -> new int[1];
        BiConsumer<int[], Employee> accumulator = (result, employee) -> result[0] += 1;
        BinaryOperator<int[]> combiner = (result1, result2) -> {
            result1[0] += result2[0];
            return result1;
        };
        Function<int[], Integer> finisher = total -> total[0];

        // output: 4
        int customCountOfEmployees = employees.stream()
            .collect(Collector.of(supplier, accumulator, combiner, finisher));

        // far easier to just inline it all
        customCountOfEmployees = employees.stream()
            .collect(Collector.of(
                        () -> new int[1],
                        (result, employee) -> result[0] += 1,
                        (result1, result2) -> {
                            result1[0] += result2[0];
                            return result1;
                        },
                        total -> total[0]));

        // =========================================================================================
        // [12] Using a custom Collector subclass
        // This collector reduces the stream to the sum of all employee's salaries.
        // Using a custom collector like this absolutely makes the code cleaner but at the expense
        // of having to write the Collector subclass where all five inherited methods have to be
        // implemented even if not necessary for the current problem being solved.
        // output: 70000
        // =========================================================================================
        int totalSumOfSalaries = employees.stream()
            .collect(new SalarySumCollector());
        // =========================================================================================
        // [13] a collector that finds the average of some field of the elements in the stream
        // Options available are averagingInt(), averagingDouble() and averagingLong().
        // Result type is always a double.
        // =========================================================================================
        Double averageSalary = employees.stream()
            .collect(Collectors.averagingInt(Employee::getSalary));
        // =========================================================================================
        // [14] summimgInt(), summingDouble(), summingLong()
        // Sums together a field from all elements in the stream
        // =========================================================================================
        int summedSalaries = employees.stream()
            .collect(Collectors.summingInt(employee -> employee.getSalary()));
        // =========================================================================================
        // [15] summarizingInt(), summarizingDouble(), summarizingLong()
        // Returns an object with stats about the stream's elements.
        // =========================================================================================
        IntSummaryStatistics stats = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .collect(Collectors.summarizingInt(num -> num));

        stats.getAverage();
        stats.getCount();
        stats.getMin();
        stats.getMax();
        stats.getSum();
        // =========================================================================================
        // [16] collect the stream's contents into a new collection
        // To collect into the more common collections use the dedicated Collectors.to*() methods:
        // -- toMap(), toList(), toSet() etc
        // =========================================================================================
        ArrayDeque<Integer> deque = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
            .collect(Collectors.toCollection(ArrayDeque::new));

    }
}
