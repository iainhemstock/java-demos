package com.iainhemstock;

import java.util.Formatter;
import java.util.List;
import java.util.Arrays;
import java.util.Calendar;

public class FormatFlags
{
    public static void main( String[] args )
    {
        List<Formatter> formatters = Arrays.asList(
            //======================================================================================
            // By default all justification is aligned right when the minimum width is greater than
            // the input. Using the (-) sign after the % makes it left justified.
            //======================================================================================
            new Formatter().format("|%10.5f|", 3.14159), // aligned right
            new Formatter().format("|%-10.5f|", 3.14159), // aligned left

            //======================================================================================
            // Output a (+) sign before any positive number
            //======================================================================================
            new Formatter().format("|%+d|", 123),

            //======================================================================================
            // Output a (space) before any positive number
            //======================================================================================
            new Formatter().format("|% d|", 123),

            //======================================================================================
            // Wrap a negative number in () rather than preceeding it with a (-) sign
            //======================================================================================
            new Formatter().format("%(d", -123),

            //======================================================================================
            // Make a long number more readable by adding commas into the output
            // 1,234,567 rather than 1234567S
            //======================================================================================
            new Formatter().format("%,d", 1234567),
            new Formatter().format("%,.2f", 1232455755267.2345)
        );

        for (Formatter f : formatters)
            System.out.println(f);
    }
}
