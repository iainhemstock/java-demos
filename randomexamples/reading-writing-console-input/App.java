import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        new App();
    }

    public App () {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter writer = new PrintWriter(System.out)) {

            writer.println("Enter the names of mountains you have climbed in the Lake District");
            writer.println("Type 'q' to stop");
            writer.flush();

            String input = "";
            List<String> inputs = new ArrayList<>();
            while ((input = reader.readLine()) != null) {
                if (input.equals("q")) break;
                inputs.add(input);
            }

            writer.printf("You have climbed %d mountains: %n", inputs.size());
            for (Integer i = 0; i < inputs.size(); i++) {
                writer.printf("-- %s %n", inputs.get(i));
            }

        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
