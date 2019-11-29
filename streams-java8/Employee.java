public class Employee {

    public enum DepartmentType {
        ACCOUNTING,
        HR,
        SALES
    }

    private int id;
    private String surname;
    DepartmentType departmentType;
    private int salary;

    public Employee(int id) {
        this.id = id;
    }

    public Employee(int id, String surname) {
        this.id = id;
        this.surname = surname;
    }

    public Employee(int id, String surname, DepartmentType departmentType) {
        this.id = id;
        this.surname = surname;
        this.departmentType = departmentType;
    }

    public Employee(int id, String surname, DepartmentType departmentType, int salary) {
        this.id = id;
        this.surname = surname;
        this.departmentType = departmentType;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getSurname() {
        return surname;
    }

    public DepartmentType getDepartmentType() {
        return this.departmentType;
    }

    public int getSalary() {
        return this.salary;
    }

    public boolean equals(Employee other) {
        return this.id == other.id &&
            this.surname.equals(other.surname) &&
            this.departmentType == other.departmentType &&
            this.salary == other.salary;
    }

    public String toString() {
        return "id=" + id + ", surname=" + surname;
    }
}
