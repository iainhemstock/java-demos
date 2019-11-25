import java.io.Serializable;
import java.lang.String;

public class Employee implements Serializable {
    private int id;
    private EmployeeName name;

    public Employee (int id, EmployeeName name) {
        this.id = id;
        this.name = name;
    }

    public String toString() {
        return new String("[Employee] id=" + id + ", name=" + name);
    }
}
