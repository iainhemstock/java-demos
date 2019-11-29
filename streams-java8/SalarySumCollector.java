import java.util.stream.Collector;
import java.util.function.Supplier;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

public class SalarySumCollector implements Collector<Employee, List<Integer>, Integer> {

    /**
     * supplier() should return any kind of mutable storage object such as a collection or array
     */
    @Override
    public Supplier<List<Integer>> supplier() {
        // constructor method reference
        // for an Object array use: Object[]::new
        return ArrayList::new;
    }

    /**
     * A BiConsumer represents an operation that takes two inputs, does something with them and
     * returns nothing.
     */
    @Override
    public BiConsumer<List<Integer>, Employee> accumulator() {
        return (resultList, employee) -> {
            if (resultList.isEmpty()) resultList.add(employee.getSalary());
            else {
                Integer currentTotal = resultList.get(0);
                currentTotal += employee.getSalary();
                resultList.set(0, currentTotal);
            }
        };
    }

    /**
     * When parallel processing streams, combiner() knows how to combine the parallel objects represented
     * by the supplier.
     */
    @Override
    public BinaryOperator<List<Integer>> combiner() {
        return (resultList1, resultList2) -> {
            Integer currentTotal1 = resultList1.get(0);
            Integer currentTotal2 = resultList2.get(0);
            currentTotal1 += currentTotal2;
            resultList1.set(0, currentTotal1);
            return resultList1;
        };
    }

    /**
     * finisher() performs a final transformation from the supplier type to the final reduced type.
     */
    @Override
    public Function<List<Integer>, Integer> finisher() {
        return resultList -> resultList.get(0);
    }

    /**
     * Used for internal optimizations.
     * CONCURRENT: indicates that the accumulator function can support being called concurrently
     * -- from other threads
     * IDENTITY_FINISH: indicates that the finisher() function is the identity function and can be
     * -- omitted (i.e. the supplier type and the return type are the same type).
     * UNORDERED: inidicates that the operations do not need to commit to preserving the order of
     * -- elements as they are discovered whilst processing.
     */
    @Override
    public Set<Collector.Characteristics> characteristics() {
        return Set.of(
            Collector.Characteristics.UNORDERED);
    }

}
