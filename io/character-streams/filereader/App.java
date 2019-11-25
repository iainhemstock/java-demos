/**
 * A FileReader is a class that can read the contents of a file and is a sublass of InputStreamReader
 * and Reader.
 *
 * It reads the file one char at a time or by a subrange. Through inheritance it can also read the whole
 * file or a portion of the file into a char array.
 */

import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

public class App {
    public static void main(String[] args) {
            new App();
    }

    public App() {

        File file = new File("test.txt");
        try (FileReader fr1 = new FileReader("test.txt");
                FileReader fr2 = new FileReader(file)) {
            readData(fr1);
            readData(fr2);
        }
        catch(FileNotFoundException ex) { ex.printStackTrace(); }
        catch(IOException ex) { ex.printStackTrace(); }
    }

    private void readData(FileReader fr) {
        try {
            int c;
            while((c = fr.read()) != -1)
                System.out.print((char)c);
        } catch(IOException ex) { ex.printStackTrace(); }

    }
}
