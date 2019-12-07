import java.util.Comparator;

public class IdComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee thisEmpl, Employee thatEmpl) {
        return thisEmpl.getId() - thatEmpl.getId();
    }
}
