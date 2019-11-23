/**
 * A PrintStream is a wrapper around another OutputStream and adds formatting functionality to it.
 * For example, instead of writing a byte at a time with OutputStream.write(byte) a PrintStream offers:
 * -- print(string)
 * -- println(string)
 * -- printf(formatString, Object ... args).
 * As well as String the print*() methods support all types including int, boolean, char, float etc.
 * Custom objects are supported too as its toString() is called.
 *
 * System.out and System.err are instances of PrintStream.
 */

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;

 public class App {
     public static void main(String[] args) {
         new App();
     }

     public App() {

         // [1] wrap a FileOutputStream in a PrintStream
         try (FileOutputStream fos = new FileOutputStream("test.txt");
                PrintStream ps = new PrintStream(fos)) {

            ps.print("Confucius said: ");
            ps.println("'It does not matter how slowly you go so long as you do not stop'.");
            ps.printf("He was born in %d BC", 551);

         }
         catch(FileNotFoundException ex) { ex.printStackTrace(); }
         catch(IOException ex) { ex.printStackTrace(); }

        // [2] convenience constructor that accepts a filename as an argument and internally constructs
        // an OutputStreamWriter
        try (PrintStream ps = new PrintStream("test2.txt")){
            ps.print("Confucius said: ");
            ps.println("'Wheresoever you go, go with all your heart'.");
        }
        catch(FileNotFoundException ex) { ex.printStackTrace(); }

        // [3] convenience constructor that accepts a file as an argument and internally constructs
        // an OutputStreamWriter
        File file = new File("test3.txt");
        try (PrintStream ps = new PrintStream(file)) {
            ps.print("Confucius said: ");
            ps.println("'Life is really simple, but men insist on making it complicated.'.");
        }
        catch(FileNotFoundException ex) { ex.printStackTrace(); }
     }
 }
