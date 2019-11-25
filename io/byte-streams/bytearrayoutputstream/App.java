/**
 * A ByteArrayOutputStream is a class that encapsulates a byte array.
 * Data can be written to the stream a byte at a time or with a subrange.
 * reset()ing the stream clears all data from the buffer.
 * A convenience method writeTo() enables writing to an OutputStream.
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.FileOutputStream;

public class App {
    public static void main(String[] args) throws IOException {
        new App();
    }

    public App() throws IOException {
        String source = "abcdefghijklmnopqrstuvwxyz";
        byte[] sourceBytes = source.getBytes();

        // write a single byte to the stream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(sourceBytes[25]);
        System.out.println(baos.toString()); // prints z

        // discards all data written to stream
        baos.reset();

        // write a range of bytes to the stream
        int startIndex = 23;
        int length = 3;
        baos.write(sourceBytes, startIndex, length);

        // write the stream's data to an OutputStream
        // [1] the built in System.out
        baos.writeTo(System.out); // prints xyz to the console
        System.out.println("");
        // [2] a FileOutputStream
        try (FileOutputStream fos = new FileOutputStream("file.txt")) {
            baos.writeTo(fos);
        }
        catch(IOException ex) { ex.printStackTrace(); }

        // prints the size of the stream
        System.out.println(baos.size()); // prints 3

        // returns stream data as byte array
        byte[] bytes = baos.toByteArray();
        for (int i = 0; i < length; ++i) {
            System.out.print((char)bytes[i]); // prints xyz
        }
        System.out.println("");
    }
}
