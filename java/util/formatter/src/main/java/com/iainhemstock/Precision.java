package com.iainhemstock;

import java.util.List;
import java.util.Arrays;
import java.util.Formatter;

public class Precision {
    public static void main(String[] args) {

        List<Formatter> formatters = Arrays.asList(
            //======================================================================================
            // Precision: %-.-t
            // The meaning differs depending on which specifier it is being applied to.
            // For floating point the first (-) specifies the total minimum width of the output (
            // including decimal places) and the second (-) specifies the number of decimal places.
            // For strings the first (-) specifies the minimum width of the output and the second (-)
            // specifies the maximum width of the ouput.
            // // The first (-) can be ommited in all cases.
            // Precision can be applied to floats (%f), scientific numbers (%e) and strings (%s)
            //======================================================================================
            new Formatter().format("3 decimal places:                       %.3f", 3.14159265359),
            new Formatter().format("7 chars wide to 3 decimals:             %7.3f", 3.14159265359),
            new Formatter().format("3 decimal places:                       %.3e", 3.30e23),
            new Formatter().format("no more than 7 chars wide:              %.7s", "this is a long string"),
            new Formatter().format("at least 5 chars, no more than 7:       %5.7s", "this")
        );

        for (Formatter f : formatters)
            System.out.println(f);

    }
}
