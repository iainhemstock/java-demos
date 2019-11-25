import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.io.StringWriter;
import java.io.Writer;

public class App {
    public static void main(String[] args) {
        new App();
    }

    public App() {
        try (CharArrayWriter caw = new CharArrayWriter()) {

            // [1] basic write to CharArrayWriter
            char c = 'h';
            String s = new String("a string");
            char[] chars = s.toCharArray();

            caw.write(c); // writes 'h'
            caw.write(s); // writes 'a string'

            int startIndex = 2;
            int length = 6;
            caw.write(s, startIndex, length); // writes 'string'
            caw.write(chars, startIndex, length); // writes 'string'

            printWriterContents(caw);

            // [2] transfer buffer contents to another Writer instance
            // write the contents of chararraywriter to any subclass of Writer, for example a FileWriter...
            try (FileWriter fw = new FileWriter("test.txt")) {
                caw.writeTo(fw);
            }
            // ... or StringWriter
            try (StringWriter sw = new StringWriter()) {
                caw.writeTo(sw);
                printWriterContents(sw);
            }
        }
        catch(IOException ex) { ex.printStackTrace(); }
    }

    private void printWriterContents(Writer writer) {
        System.out.println(writer.toString());
    }
}
