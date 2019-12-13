package com.iainhemstock;

import java.util.List;
import java.util.Arrays;
import java.util.Formatter;

public class MinimumFieldWidth {
    public static void main(String[] args) {

        List<Formatter> formatters = Arrays.asList(
            //======================================================================================
            // Specifying minimum width
            // Puting a minimum width between the % and the specifier i.e. %12f, says that the resulting
            // string will have a minimum width of 12. If it is less then it will be padded with
            // spaces.
            // Putting a zero and a minimum width between the % and the specifier i.e. %012f, says
            // that the minimum width of the ouput will be 12 and zeros will be used for padding if
            // it less.
            //======================================================================================
            new Formatter().format("|%f|%n|%12f|%n|%012f|", 10.12345, 10.12345, 10.12345),

            new Formatter().format("%n"), // newline

            new Formatter().format("%4d %4d %4d", 1, 1, 1),
            new Formatter().format("%4d %4d %4d", 11, 11, 11),
            new Formatter().format("%4d %4d %4d", 111, 111, 111),
            new Formatter().format("%4d %4d %4d", 1111, 1111, 1111)
        );

        for (Formatter f : formatters) {
            System.out.println(f);
        }
    }
}
