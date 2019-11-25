/**
 * A SequenceInputStream is a class that concatenates two or more InputStreams together.
 * It can be constructed by passing two InputStreams to a constructor or by
 * passing a custom Enumeration of InputStreams to the other constructor.
 */

import java.io.SequenceInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.List;
import java.util.Collections;

public class App {
    public static void main(String[] args) {
        new App();
    }

    public App() {

        // [1] create a sequence input stream with two file input streams
        try (SequenceInputStream sis = new SequenceInputStream(
                                            new FileInputStream("first.txt"),
                                            new FileInputStream("second.txt"))) {

            printStream(sis);
        }
        catch(FileNotFoundException ex) { ex.printStackTrace(); }
        catch(IOException ex) { ex.printStackTrace(); }

        // [2] create a sequence input stream with an enumeration of file input streams
        MyInputStreamEnumerator streamEnum = new MyInputStreamEnumerator(getFileNames());
        try (SequenceInputStream sis = new SequenceInputStream(streamEnum)) {
            printStream(sis);
        }
        catch(FileNotFoundException ex) { ex.printStackTrace(); }
        catch(IOException ex) { ex.printStackTrace(); }
    }

    private void printStream(SequenceInputStream stream) {
        try {
            int byt;
            while ((byt = stream.read()) != -1) {
                System.out.print((char)byt);
            }
        }
        catch(IOException ex) { ex.printStackTrace(); }
    }

    private Enumeration<String> getFileNames() {
        return Collections.enumeration(List.of("first.txt", "second.txt", "third.txt"));
    }

    private class MyInputStreamEnumerator implements Enumeration<FileInputStream> {
        private Enumeration<String> fileNames;

        public MyInputStreamEnumerator(Enumeration<String> fileNames) {
            this.fileNames = fileNames;
        }

        public boolean hasMoreElements() {
            return fileNames.hasMoreElements();
        }

        public FileInputStream nextElement() {
            FileInputStream ret = null;
            try {
                ret = new FileInputStream(fileNames.nextElement());
            } catch(FileNotFoundException ex) { ex.printStackTrace(); }
            return ret;
        }
    }
}
