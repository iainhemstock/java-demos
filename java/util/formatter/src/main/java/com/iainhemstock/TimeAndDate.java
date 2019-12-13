package com.iainhemstock;

import java.util.Formatter;
import java.util.List;
import java.util.Arrays;
import java.util.Calendar;

public class TimeAndDate
{
    public static void main( String[] args )
    {
        List<Formatter> formatters = Arrays.asList(
            //======================================================================================
            // Time and date (%t)
            // The %t specifier requires another specifier to describe the portion and precise format.
            // The argument to the %t specifier must be either a Calendar, Date, Long, long or
            // TemporalAccessor.
            //======================================================================================
            new Formatter().format("abbr weekday:                   %ta", Calendar.getInstance()),
            new Formatter().format("full weekday:                   %tA", Calendar.getInstance()),
            new Formatter().format("abbr month:                     %tb", Calendar.getInstance()),
            new Formatter().format("full month:                     %tB", Calendar.getInstance()),
            new Formatter().format("standard date and time:         %tc", Calendar.getInstance()),
            new Formatter().format("first two digits of year:       %tC", Calendar.getInstance()),
            new Formatter().format("day of month (01-31):           %td", Calendar.getInstance()),
            new Formatter().format("month/day/year:                 %tD", Calendar.getInstance()),
            new Formatter().format("day of month (1-31):            %te", Calendar.getInstance()),
            new Formatter().format("year-month-day:                 %tF", Calendar.getInstance()),
            new Formatter().format("abbr month:                     %th", Calendar.getInstance()),
            new Formatter().format("hour (00-23):                   %tH", Calendar.getInstance()),
            new Formatter().format("hour (01-12):                   %tI", Calendar.getInstance()),
            new Formatter().format("day of year (001-366):          %tj", Calendar.getInstance()),
            new Formatter().format("hour (0-23):                    %tk", Calendar.getInstance()),
            new Formatter().format("hour (1-12):                    %tl", Calendar.getInstance()),
            new Formatter().format("millisecond (000-999):          %tL", Calendar.getInstance()),
            new Formatter().format("month (01-13):                  %tm", Calendar.getInstance()),
            new Formatter().format("minute (00-59):                 %tM", Calendar.getInstance()),
            new Formatter().format("nano (000000000-999999999):     %tN", Calendar.getInstance()),
            // new Formatter().format("AM/PM lowercase:                %tP", Calendar.getInstance()),
            new Formatter().format("milliseconds from 1/1/1970:     %tQ", Calendar.getInstance()),
            new Formatter().format("12 hour format (hh:mm:ss):      %tr", Calendar.getInstance()),
            new Formatter().format("24 hour format (hh:mm):         %tR", Calendar.getInstance()),
            new Formatter().format("seconds (00-60):                %tS", Calendar.getInstance()),
            new Formatter().format("seconds from 1/1/1970:          %ts", Calendar.getInstance()),
            new Formatter().format("24 hour format (hh:mm:ss):      %tT", Calendar.getInstance()),
            new Formatter().format("year without century:           %ty", Calendar.getInstance()),
            new Formatter().format("year including century:         %tY", Calendar.getInstance()),
            new Formatter().format("offset from UTC:                %tz", Calendar.getInstance()),
            new Formatter().format("time zone:                      %tZ", Calendar.getInstance())
        );

        for (Formatter f : formatters)
            System.out.println(f);

    }
}
