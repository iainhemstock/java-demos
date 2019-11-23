/**
 * A ByteArrayInputStream manages a stream that contains a byte array.
 * It can be constructed with a whole byte array or a subrange of a byte array.
 * The position read from can be reset back to the beginning (be default) by calling reset().
 * A position can be marked with mark() after which reset() resets the positon back to there instead.
 */

import java.io.ByteArrayInputStream;
import java.lang.Character;

public class App {
    private static int START_INDEX = 23;
    private static int LENGTH = 3;

    public static void main(String[] args) {
        new App();
    }

    public App() {
        String source = "abcdefghijklmnopqrstuvwxyz";
        byte[] sourceBytes = source.getBytes();

        // contains all 26 chars of the alaphabet
        ByteArrayInputStream bais = new ByteArrayInputStream(sourceBytes);
        printStream(bais); // prints abc..xyz

        // resets position back to beginning then skips to the middle position and
        // marks it to return to later
        bais.reset();
        bais.skip(bais.available() / 2);
        bais.mark(-1);
        printSecondHalfAlphabetLowerCaseThenSecondHalfAlphabetUpperCase(bais);

        // contains a subrange i.e. xyz
        ByteArrayInputStream baisSubrange = new ByteArrayInputStream(sourceBytes, START_INDEX, LENGTH);
        printStream(baisSubrange); // prints xyz

        // resets the buffer's position back to the start and
        // reprints the buffer's bytes but this time as upper case chars
        baisSubrange.reset();
        printStreamToUpperCase(baisSubrange); // prints XYZ
    }

    private void printStream(ByteArrayInputStream inputStream) {
        int condition = inputStream.available();
        for (int i = 0; i < condition; ++i)
            System.out.print((char) inputStream.read());
        System.out.println("");
    }

    private void printStreamToUpperCase(ByteArrayInputStream inputStream) {
        int condition = inputStream.available();
        for (int i = 0; i < condition; ++i)
            System.out.print(Character.toUpperCase((char) inputStream.read()));
        System.out.println("");
    }

    /**
     * The middle point of the stream is marked.
     * Then all further chars are printed.
     * By resetting to the marked point the same chars can be printed again this time uppercase.
     */
    private void printSecondHalfAlphabetLowerCaseThenSecondHalfAlphabetUpperCase(ByteArrayInputStream inputStream) {
        int condition = inputStream.available();
        for (int i = 0; i < condition; ++i) {
            System.out.print((char) inputStream.read());
        }

        System.out.println("");
        inputStream.reset(); // back to the mark()ed position

        for (int i = 0; i < condition; ++i) {
            System.out.print(Character.toUpperCase((char) inputStream.read()));
        }
        System.out.println("");
    }
}
