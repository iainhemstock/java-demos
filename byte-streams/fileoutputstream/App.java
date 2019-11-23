/**
 * FileOutputStream deals with writing raw bytes such as image data to a file.
 * A String can conveniently return its contents as a byte array which can be written to a file output stream. 
 */

import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

public class App {

    public static void main(String[] args) {
        new App();
    }

    public App() {
        try (FileOutputStream fos1 = new FileOutputStream("all_lyrics.txt");
                FileOutputStream fos2 = new FileOutputStream("single_char_lyrics.txt");
                FileOutputStream fos3 = new FileOutputStream("char_range_lyrics.txt");
                FileOutputStream fos4 = new FileOutputStream("all_lyrics.txt", true);
                FileOutputStream fos5 = new FileOutputStream(new File("uppercase_lyrics.txt"))) {

            byte[] sourceBytes = getSource().getBytes();

            writeAllBytesToFile(sourceBytes, fos1);
            writeFirstByteToFile(sourceBytes[0], fos2);
            writeByteRangeToFile(sourceBytes, fos3);
            appendBytesToFile(sourceBytes, fos4);
            writeUppercaseLyricsToFile(getSource().toUpperCase().getBytes(), fos5);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }
    }

    private void writeAllBytesToFile(byte[] bytes, FileOutputStream fos)
    throws IOException {
        fos.write(bytes);
    }

    private void writeFirstByteToFile(byte byt, FileOutputStream fos)
    throws IOException {
        fos.write(byt);
    }

    private void writeByteRangeToFile(byte[] bytes, FileOutputStream fos)
    throws IOException {
        int startIndex = 15;
        int length = 45;
        fos.write(bytes, startIndex, length);
    }

    /**
     * This file output stream was constructed to append bytes rather than overwrite existing bytes
     */
    private void appendBytesToFile(byte[] bytes, FileOutputStream fos)
    throws IOException {
        writeAllBytesToFile(bytes, fos);
    }

    private void writeUppercaseLyricsToFile(byte[] bytes, FileOutputStream fos)
    throws IOException {
        writeAllBytesToFile(bytes, fos);
    }

    private String getSource() {
        return new String("We used to swim the same moonlight waters" +
                    "Oceans away from the wakeful day" +
                    "My fall will be for you" +
                    "My fall will be for you" +
                    "My love will be in you" +
                    "If you be the one to cut me" +
                    "I'll bleed forever" +
                    "Scent of the sea before waking afterwards" +
                    "Brings me to thee" +
                    "Into the blue memory" +
                    "My fall will be for you" +
                    "Into the blue memory" +
                    "A siren from the deep came to me" +
                    "Sang my name my longing" +
                    "Still I write my songs about that dream of mine" +
                    "Worth everything I may ever be" +
                    "The Child will be born again" +
                    "That siren carried him to me" +
                    "First of them true lovers" +
                    "Singing on the shoulders of an angel" +
                    "Without care for love n' loss" +
                    "Bring me home or leave me be" +
                    "My love in the dark heart of the night" +
                    "I have lost the path before me" +
                    "the one behind will lead me" +
                    "Take me" +
                    "Cure me" +
                    "Kill me" +
                    "Bring me home" +
                    "Every way" +
                    "Every day" +
                    "Just another loop in the hangman's noose" +
                    "Take me, cure me, kill me, bring me home" +
                    "Every way, every day" +
                    "I keep on watching us sleep" +
                    "Relive the old sin of" +
                    "Adam and Eve" +
                    "Of you and me" +
                    "Forgive the adoring beast" +
                    "Redeem me into childhood" +
                    "Show me myself without the shell" +
                    "Like the advent of May" +
                    "I'll be there when you say" +
                    "Time to never hold our love" +
                    "My fall will be for you");
    }
}
