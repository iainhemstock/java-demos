/**
 * An InputStreamReader is a bridge between a Reader and an InputStream.
 *
 * An InputStreamReader typically deals with chars and char arrays whereas an InputStream deals with
 * bytes and byte arrays.
 *
 * So in a scenario where text needs to be read from an InputStream, all of the bytes would need to
 * be converted into chars manually as they are read from the stream to be used in the program.
 *
 * This is the job that the InputStreamReader does internally. It can read the bytes from the underlying
 * InputStream and present them as chars and char arrays.
 *
 * InputStreamReader does not support reset().
 *
 * Each call to any of the read() methods may access the underlying InputStream's source which can be a very
 * expensive operation, for example, repeated calls to the filesytem or network. Often a better
 * solution is to wrap an InputStreamReader in a BufferedReader which buffers stream data making read()
 * calls much more efficient.
 */

 import java.io.InputStreamReader;
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.IOException;
 import java.lang.String;
 import java.io.BufferedReader;

public class App {
    public static void main(String[] args) {
        new App();
    }

    public App() {

        /**
         * [1] read single char from stream
         * read() returns an int so requires casting to a char
         */
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream("lyrics.txt"))) {
            int c = isr.read();
            System.out.println((char)c);
        }
        catch(FileNotFoundException ex) { ex.printStackTrace(); }
        catch(IOException ex) { ex.printStackTrace(); }


        /**
         * [2] read the stream into a char array
         */
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream("lyrics.txt"))) {
            final int SIZE = 150;
            char[] lyricsCharArray = new char[SIZE];
            isr.read(lyricsCharArray);
            System.out.println(new String(lyricsCharArray));
        }
        catch(FileNotFoundException ex) { ex.printStackTrace(); }
        catch(IOException ex) { ex.printStackTrace(); }


        /**
         * [3] read a portion of the stream into a char array
         */
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream("lyrics.txt"))) {
            final int SIZE = 50;
            char[] lyricsCharArray = new char[SIZE];
            int startIndex = 0;
            int length = 32; // length of first line
            int numCharsRead = isr.read(lyricsCharArray, startIndex, length);
            System.out.println(new String(lyricsCharArray));
        }
        catch(FileNotFoundException ex) { ex.printStackTrace(); }
        catch(IOException ex) { ex.printStackTrace(); }

        System.out.println("");

        /**
         * [4] a more efficient way of reading from the stream.
         * BufferedReader buffers the data from the underlying stream and so the repeated calls to
         * read() are accessing that buffer rather than the filesystem making it a much faster operation.
         */
         try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("lyrics.txt")))) {
            int c;
            while ((c = br.read()) != -1)
                System.out.print((char)c);
         }
         catch(FileNotFoundException ex) { ex.printStackTrace(); }
         catch(IOException ex) { ex.printStackTrace(); }
    }
}
