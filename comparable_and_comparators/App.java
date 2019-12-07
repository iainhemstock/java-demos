import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.TreeSet;
import java.util.Set;
import java.util.Comparator;

public class App {
    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
            new Employee(1, "Martin", "Smith"),
            new Employee(2, "Sarah", "Pearson"),
            new Employee(3, "Colin", "Jenkins"),
            new Employee(4, "Elizabeth", "Carlton"),
            new Employee(5, "John", "Smith"));

        //==========================================================================================
        // Collections.sort(List<T>) sorts the elements of a List.
        // It requires that the List's element type to be a Comparable subclass. Thus it uses the
        // element type's implementation of compareTo(T) method to sort the collection.
        //==========================================================================================
        Collections.sort(employees);

        //==========================================================================================
        // Collections.sort(List<T>, Comparator<? super T>) can accept a Comparator that specifies
        // how the collection should be sorted. The sorting algorithm uses Comparator's compare(T, T)
        // method.
        //
        // Since Comparator is a functional interface it is possible to represent one with a lambda.
        //
        // Comparator offers some predefined Comparators for comparing integer, double and long keys:
        //      Comparator.comparingInt()
        //      Comparator.comparingDouble()
        //      Comparator.comparingLong()
        //
        // It is possible to chain together Comparators when a collection should be sorted by more
        // than one condition. For example, sorting by last name first then within that outcome
        // sorting by first name.
        //==========================================================================================

        // Three different ways to sort by some key, in this example, employee Id.
        // The sorting outcome is the same after each call to sort().
        Collections.sort(employees, (thisEmpl, thatEmpl) -> thisEmpl.getId() - thatEmpl.getId());
        Collections.sort(employees, new IdComparator());
        Collections.sort(employees, Comparator.comparingInt(Employee::getId));

        //==========================================================================================
        // comparing() takes a function that extracts a key from the object to sort by. As usual, a
        // method reference or lambda can be used.
        //==========================================================================================
        Collections.sort(employees, Comparator.comparing(Employee::getFirstName));
        Collections.sort(employees, Comparator.comparing(empl -> empl.getFirstName()));

        //==========================================================================================
        // More complex sort by chaining together Comparators.
        // Sorts employees by last name then by first name.
        // comparing() and thenComparing() both return a Comparator instance.
        //==========================================================================================
        Collections.sort(employees,
            Comparator.comparing(Employee::getLastName).thenComparing(Employee::getFirstName));

        Collections.sort(employees,
            new LastNameComparator().thenComparing(new FirstNameComparator()));

        //==========================================================================================
        // A Comparator can be reversed:
        //      reverseOrder() reverses the natural ordering
        //      reversed() reverses the ordering of the invoking Comparator
        //==========================================================================================
        Collections.sort(employees, Comparator.reverseOrder());
        Collections.sort(employees, Comparator.comparingInt(Employee::getId).reversed());
        Collections.sort(employees, new IdComparator().reversed());

        //==========================================================================================
        // Sorting array with Comparator
        //==========================================================================================
        Employee[] employeesArray = employees.toArray(new Employee[employees.size()]);
        Arrays.sort(employeesArray, Comparator.comparingInt(Employee::getId));


    }
}
