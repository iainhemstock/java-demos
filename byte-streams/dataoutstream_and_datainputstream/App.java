/**
 * DataOutputStream and DataInputStream are classes that wrap an OutputStream and InputStream respectively.
 * They provide a convenient way to convert primitive types to raw bytes as they are written to the OutputStream.
 * They can be read back in with DataInputStream.
 */

import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

public class App{
    public static void main(String[] args) {
        new App();
    }

    public App() {

        try (OutputStream outputStream = new FileOutputStream("test.txt");
                InputStream inputStream = new FileInputStream("test.txt");) {

            // write the byte data to the dataoutputstream
            try (DataOutputStream dos = new DataOutputStream(outputStream)) {
                dos.writeUTF("A string");
                dos.writeInt(123);
                dos.writeFloat(3.14f);
                dos.writeBoolean(true);
            }
            catch(IOException ex) { ex.printStackTrace(); }

            // read the byte data from the dataoutputstream
            try (DataInputStream dis = new DataInputStream(inputStream)) {
                String s = dis.readUTF();
                int i = dis.readInt();
                float f = dis.readFloat();
                boolean b = dis.readBoolean();

                System.out.printf("Here are the values: %s, %d, %.2f, %b",
                                        s, i, f, b);
            }
            catch(IOException ex) { ex.printStackTrace(); }
        }
        catch(FileNotFoundException ex) { ex.printStackTrace(); }
        catch(IOException ex) { ex.printStackTrace(); }
    }
}
