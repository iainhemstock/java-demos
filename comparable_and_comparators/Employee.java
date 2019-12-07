/**
 * A Comparable subclass allows an object to compare itself against another object of the same type
 * for ordering i.e. which comes first, the invoking object or the object arg?
 *
 * By implementing the compareTo(T) method an object can decide on which factor to base comparison with.
 * For example, in this Employee class it is natural for an Employee to compare itself to another
 * Employee based on the Id but could just as easily have been by last name.
 *
 * The Comparable interface is allowing us to decide what the natural ordering of the objects are. A
 * Comparator, by contrast, is more powerful and allows us to define different strategies of ordering
 * when we don't necessarily want the natural order.
 */

import java.lang.Comparable;

public class Employee implements Comparable<Employee> {

    /**
     * This implementation decides that the natural order of employees is by their IDs.
     * This method should return a negative int, zero or a positive int when this object is less than,
     * equal to or greater than the object arg.
     */
    public int compareTo(Employee thatEmployee) {
        return getId() - thatEmployee.getId();
    }

    private int id;
    private String firstName;
    private String lastName;

    Employee(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String toString() {
        return id + ": " + firstName + " " + lastName;
    }
}
