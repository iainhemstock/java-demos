/**
 * A BufferedReader provides an efficient way to read from a stream when reading from that stream
 * could potentially be an expensive operation.
 *
 * For example, each call to read() on a stream could be accessing a filesystem or network etc.
 *
 * But when that stream is wrapped in a BufferedReader then the stream's data is buffered and
 * subsequent calls to read() are accessing the internal buffer rather than the filesystem or netowrk etc.
 *
 * BufferedReader offers readLine() which returns a String.
 *
 * BuffeedReader can also return all lines as a java.util.Stream for further processing.
 */

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.Reader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.io.BufferedWriter;
import java.lang.Character;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class App {
    public static void main(String[] args) {
        new App();
    }

    public App () {
        try (BufferedReader br = new BufferedReader(new FileReader("lyrics.txt"))) {
            br.mark(1000);
            readCharAtATime(br);
            br.reset();
            readLineAtATime(br);
            br.reset();
            readLinesByStreamIntoList(br);
            br.reset();
            readLinesByStreamIntoStringBuilder(br);
            br.reset();
            writeUppercaseLyricsToFile(br, "uppercase_lyrics.txt");
        }
        catch(FileNotFoundException ex) { ex.printStackTrace(); }
        catch(IOException ex) { ex.printStackTrace(); }
    }

    private void readCharAtATime(Reader reader) {
        System.out.println("Reading a character at a time:");
        try {
            int c;
            while((c = reader.read()) != -1) {
                System.out.print((char)c);
            }
            System.out.println("");
        }
        catch(IOException ex) { ex.printStackTrace(); }
    }

    private void readLineAtATime(BufferedReader reader) {
        System.out.println("Reading a line at a time:");
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("");
        }
        catch(IOException ex) { ex.printStackTrace(); }
    }

    private void readLinesByStreamIntoList(BufferedReader reader) throws IOException {
        System.out.println("Reading lines from stream into a list:");

        List<String> lines = reader.lines().collect(Collectors.toList());
        System.out.println(lines);
        System.out.println("");
    }

    private void readLinesByStreamIntoStringBuilder(BufferedReader reader) throws IOException {
        System.out.println("Reading lines from stream into a stringbuilder:");

        StringBuilder sb = new StringBuilder();
        reader.lines().forEach(line -> sb.append(line + System.lineSeparator()));
        System.out.println(sb.toString());
        System.out.println("");
    }

    private void writeUppercaseLyricsToFile(BufferedReader br, String filename) {
        // could use the form:
        // BufferedWriter bw = new BufferedWriter(new FileWriter(filename))
        // to create a BufferedWriter
        try (BufferedWriter bw = Files.newBufferedWriter(Paths.get(filename))) {
            int c;
            while ((c = br.read()) != -1) {
                bw.write(Character.toUpperCase((char)c));
            }
        }
        catch(Exception ex) { ex.printStackTrace(); }
    }
}
