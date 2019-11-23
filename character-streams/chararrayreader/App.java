import java.io.CharArrayReader;
import java.io.Reader;
import java.lang.String;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        new App();
    }

    public App() {
        String source = "abcdefghijklmnopqrstuvwxyz";
        char[] charSource = source.toCharArray();

        // [1] construct a CharArrayReader wrapping a char array
        try (CharArrayReader car = new CharArrayReader(charSource)) {
            print(car);
        }


        // [2] construct a CharArrayReader from a subrange of a char array
        int startIndex = 23;
        int length = 3;
        try (CharArrayReader car = new CharArrayReader(charSource, startIndex, length)) {
            print(car);
        }
    }

    private void print(Reader reader) {
        try {
            int c;
            while((c = reader.read()) != -1)
                System.out.print((char)c);
            System.out.println("");
        }
        catch(IOException ex) { ex.printStackTrace(); }
    }
}
