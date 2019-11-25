import java.lang.String;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.ClassNotFoundException;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        new App();
    }

    public App () {

        // [1] serialize Employee
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("serial"))) {
            Employee emp = new Employee(123, new EmployeeName("John", "Smith"));
            oos.writeObject(emp);
            System.out.println(emp);
        }
        catch(FileNotFoundException ex) { ex.printStackTrace(); }
        catch(IOException ex) { ex.printStackTrace(); }


        // [2] deserialize Employee
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("serial"))) {
            Employee emp = (Employee) ois.readObject();
            System.out.println(emp);
        }
        catch(FileNotFoundException ex) { ex.printStackTrace(); }
        catch(ClassNotFoundException ex) { ex.printStackTrace(); }
        catch(IOException ex) { ex.printStackTrace(); }
    }
}
