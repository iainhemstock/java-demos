/**
 * A FileInputStream deals with reading raw bytes from a file into the stream.
 * It can then be consumed a byte at a time or by means of a specified range.
 * It is not really designed for reading text files as there are better class choices.
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

class App {
    private FileInputStream fis;
    private char c;

    public static void main(String[] args) throws FileNotFoundException, IOException {
        new App();
    }

    public App() throws FileNotFoundException, IOException {
        fis = new FileInputStream("App.java");

        // total bytes available
        printTotalAvailableBytesLeft();

        // reads a byte's worth of data
        printSingleByte();

        printTotalAvailableBytesLeft();

        // reads the next 10 bytes
        printNumBytes(10);

        System.out.println("\n");
        printTotalAvailableBytesLeft();

        // print the rest of the file
        printRemainingBytes();

        // close the file stream
        fis.close();
    }

    private void printTotalAvailableBytesLeft() throws IOException {
        System.out.println(fis.available());
    }

    private void printSingleByte() throws IOException {
        c = (char) fis.read();
        System.out.println(c);

    }

    private void printNumBytes(int num) throws IOException {
        for (int i = 0; i < num; ++i) {
            c = (char) fis.read();
            System.out.print(c);
        }
    }

    private void printRemainingBytes() throws IOException {
        while (true) {
            int byt = fis.read();
            if (byt == -1) break;
            else {
                c = (char) byt;
                System.out.print(c);
            }
        }
    }
}
