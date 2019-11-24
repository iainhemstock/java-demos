/**
 * An OutputStreamWriter is a bridge between a Writer and OutputStream.
 *
 * A Writer typically deals with chars and strings whereas an OutputStream deals with bytes.
 *
 * So in a scenario where text needs to be written to an OutputStream, all of the chars would need to be
 * converted into bytes before being written to the OutputStream.
 *
 * This is the job that OutputStreamWriter does internally. It can be written to using strings
 * (or partial strings), single chars and char arrays (or partial char arrays).
 *
 * It then internally converts its contents into bytes and writes those bytes to the OutputStream.
 *
 * FileWriter is a convenience class that subclasses OutputStreamWriter so instead of using the form:
 *      new OutputStreamWriter(new FileOutputStream("*.txt"))
 * it is possible to just use:
 *      new FileWriter("*.txt")
 * or any other of the various constructors FileWriter offers.
 */

import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        new App();
    }

    public App() {
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("test.txt"))) {
            String lyricsString = getLyrics();
            char[] lyricsCharArray = lyricsString.toCharArray();

            // [1] write whole string to stream
            osw.write(lyricsString);

            // [2] write single char to stream
            osw.write('\n');

            // [3] write partial string to stream
            int startIndex = 0;
            int length = 21;
            osw.write(lyricsString, startIndex, length);

            osw.write("\n\n");

            // [4] write partiol char array to stream
            startIndex = 22;
            length = 10;
            osw.write(lyricsCharArray, startIndex, length);

            osw.write("\n\n");

            // [5] write whole char array to stream
            osw.write(lyricsCharArray);
        }
        catch(FileNotFoundException ex) { ex.printStackTrace(); }
        catch(IOException ex) { ex.printStackTrace(); }
    }

    private String getLyrics() {
        return "A siren from the deep came to me\n" +
        "Sang my name my longing\n" +
        "Still I write my songs about that dream of mine\n" +
        "Worth everything I may ever be\n";
    }
}
