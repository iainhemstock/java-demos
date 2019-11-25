/**
 * A BufferedWriter is used to make writing to streams more efficient when writing to those
 * streams are potentially expensive operations.
 *
 * For example, each call to write() on an OutputStreamWriter may be accessing the underlying stream
 * (possibly the filesystem or network etc).
 *
 * In BufferedWriter's case each call to write() writes the data to an internal buffer and is later
 * transfered to the underlying stream making it a faster and more efficient way to write to the stream.
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.lang.String;
import java.io.FileWriter;

public class App {
    public static void main(String[] args) {
        new App();
    }

    public App() {

        String source = "Only the wisest and stupidest of men never change";
        char[] sourceChars = source.toCharArray();

        /**
         * [1] effieciently write a string's chars one by one to the file stream.
         * The chars are initially written to BufferedWriter's internal buffer and later transfered
         * to the file output stream.
         */
         try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("test.txt")))) {
            for (Integer i = 0; i < source.length(); i++) {
                bw.write(sourceChars[i]);
            }
         }
         catch(FileNotFoundException ex) { ex.printStackTrace(); }
         catch(IOException ex) { ex.printStackTrace(); }


         /**
          * [2] same as the first example but in this case a BufferedWriter is constructed with a
          * convenience class called FileWriter which is a subclass of OutputStreamWriter.
          * This makes it unnecessary to construct an OutputStreamWriter explicitly.
          */
          try (BufferedWriter bw = new BufferedWriter(new FileWriter("test.txt"))) {
             for (Integer i = 0; i < source.length(); i++) {
                 bw.write(sourceChars[i]);
             }
          }
          catch(FileNotFoundException ex) { ex.printStackTrace(); }
          catch(IOException ex) { ex.printStackTrace(); }
    }
}
