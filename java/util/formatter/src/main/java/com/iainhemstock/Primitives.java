package com.iainhemstock;

import java.util.List;
import java.util.Arrays;
import java.util.Formatter;

public class Primitives {
    public static void main(String[] args) {

        List<Formatter> formatters = Arrays.asList(
            //======================================================================================
            // Numbers
            //======================================================================================
            new Formatter().format("integer decimal:                %d", 7),
            new Formatter().format("integer octal:                  %o", 123),
            new Formatter().format("float:                          %f", 3.14f),
            new Formatter().format("double:                         %f", 2.71828),
            new Formatter().format("scientific notation:            %e", 3.30e23), // %E for uppercase
            new Formatter().format("hexidecimal floating point:     %a", 512.0), // %A for uppercase
            new Formatter().format("hexidecimal integer:            %x", 1024), // %X for uppercase

            //======================================================================================
            // Chars and strings
            //======================================================================================
            new Formatter().format("char:                           %c", 'x'), // %C for uppercase
            new Formatter().format("string:                         %s", "this is a string"), // %S for uppercase

            //======================================================================================
            // Misc
            //======================================================================================
            new Formatter().format("boolean:                        %b", true), // %B for uppercase
            new Formatter().format("literal %%"),
            new Formatter().format("%n") // newline
        );

        for (Formatter f : formatters)
            System.out.println(f);
    }
}
