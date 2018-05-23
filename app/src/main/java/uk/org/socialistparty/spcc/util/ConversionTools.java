package uk.org.socialistparty.spcc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ConversionTools {
    private static Calendar calendar = Calendar.getInstance();
    private static SimpleDateFormat dayNumberFormat = new SimpleDateFormat(
            "d", Locale.getDefault());
    private static SimpleDateFormat monthNumberFormat = new SimpleDateFormat(
            "M", Locale.getDefault());
    private static SimpleDateFormat yearNumberFormat = new SimpleDateFormat(
            "yy", Locale.getDefault());
    private static SimpleDateFormat dayNameFormat = new SimpleDateFormat(
            "EEEE", Locale.getDefault());
    private static SimpleDateFormat monthAndYearFormat = new SimpleDateFormat(
            "MMMM yy", Locale.getDefault());

    public static String getDayNumberFromTimeStamp(long date){
        calendar.setTimeInMillis(date);
        return dayNumberFormat.format(calendar.getTime());
    }

    public static int getDayIntFromTimeStamp(long date){
        calendar.setTimeInMillis(date);
        return Integer.parseInt(dayNumberFormat.format(calendar.getTime()));
    }

    public static int getMonthIntFromTimeStamp(long date){
        calendar.setTimeInMillis(date);
        return Integer.parseInt(monthNumberFormat.format(calendar.getTime()));
    }

    public static int getYearIntFromTimeStamp(long date){
        calendar.setTimeInMillis(date);
        return Integer.parseInt(yearNumberFormat.format(calendar.getTime()));
    }

    public static String getDayNameFromTimeStamp(long date){
        calendar.setTimeInMillis(date);
        return dayNameFormat.format(calendar.getTime());
    }

    public static String getMonthAndYearFromTimeStamp(long date){
        calendar.setTimeInMillis(date);
        return monthAndYearFormat.format(calendar.getTime());
    }
}
