import java.io.Serializable;

public class EmployeeName implements Serializable {
    private String firstName;
    private String lastName;

    public EmployeeName (String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String toString() {
        return firstName + " " + lastName;
    }
}
