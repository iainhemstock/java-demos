/**
 * FileWriter is a convenience class that writes to a file.
 * All of the constructors except FileWriter(String filename boolean append) will overwrite the contents
 * of the file. By passing true as the append argument then any new data written will be appended to the
 * end of the file. By passing false it will overwrite the contents of the file.
 */

import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        new App();
    }

    public App() {

        // [1] write to a basic FileWriter
        try (FileWriter fw = new FileWriter("test.txt")) {
            String source = "Choose a job you love, and you will never have to work a day in your life";

            // write a single char
            fw.write(source.charAt(0));

            // write a range from a char arry
            int startIndex = 0;
            int length = source.length();
            fw.write(source.toCharArray(), startIndex, length);

            // write from a string source
            fw.write(source);

            // write a range from a String subrange
            fw.write(source, startIndex, length);
        }
        catch(FileNotFoundException ex) { ex.printStackTrace(); }
        catch(IOException ex) { ex.printStackTrace(); }


        // [2] write to a FileWriter that has been explicity told to overwrite any existing data
        boolean shouldAppend = false;
        try (FileWriter fw = new FileWriter("test.txt", shouldAppend)) {
            fw.write(new String("This will overwrite existing content in the file. "));
        }
        catch(FileNotFoundException ex) { ex.printStackTrace(); }
        catch(IOException ex) { ex.printStackTrace(); }


        // [3] write to a FileWriter by appending new data to the end of any existing content
        shouldAppend = true;
        try (FileWriter fw = new FileWriter("test.txt", shouldAppend)) {
            fw.write(new String("This will be appended. "));
        }
        catch(FileNotFoundException ex) { ex.printStackTrace(); }
        catch(IOException ex) { ex.printStackTrace(); }
    }
}
