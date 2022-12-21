package com.taraan.dum.common.globalization;


import com.taraan.dum.common.globalization.util.NumberFormatter;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created aras Team.
 * User: ardeshir
 * Date: Apr 29, 2017
 * Time: 10:33:12 AM
 */
public class DateTimeFormatter
{
    private DateTimeFormatter() {
    }

    /**
     * Convert GregorianDate to PersianDate string short format
     *
     * @param date java.util.Date
     * @return String sample '1384/01/23'
     */
    public static String toShortDate(Date date)
    {
        return String.format("%1$tY/%1$tm/%1$td", new PersianCalendar(setTime(date, TimeOfDay.NOON)));
    }

    /**
     * Convert string short format GregorianDate to PersianDate string short format
     *
     * @param date String sample '2017/01/23'
     * @return String sample '1384/01/23'
     */
    public static String toShortDate(String date)
    {
        try
        {
            return toShortDate(DateFormat.getDateTimeInstance().parse(date));
        }
        catch (Exception ex)
        {
            return "";
        }
    }

    /**
     * Convert GregorianDate to PersianDate string long format
     *
     * @param date java.util.Date
     * @return String sample 'يكشنبه 3 مرداد 1384'
     */
    public static String toLongDate(Date date)
    {
        PersianCalendar persianCalendar = new PersianCalendar(setTime(date, TimeOfDay.NOON));
        return persianCalendar.getPersianWeekDayName() + " " +
               persianCalendar.getDateFields().getDay() + " " +
               persianCalendar.getPersianMonthName() + " " +
               persianCalendar.getDateFields().getYear();
    }

    /**
     * Convert GregorianDate to PersianDate string long format
     *
     * @param date java.util.Date
     * @return String sample 'سوم مرداد یک هزار و سیصدو هشاتدو نه'
     */
    public static String toLongDateLetter(Date date)
    {
        PersianCalendar persianCalendar = new PersianCalendar(setTime(date, TimeOfDay.NOON));
        return NumberFormatter.stringNumberToPersianText(String.valueOf(persianCalendar.getDateFields().getDay())) + " " + persianCalendar.getPersianMonthName() + " " + NumberFormatter.stringNumberToPersianText(String.valueOf(persianCalendar.getDateFields().getYear()));
    }

    /**
     * Convert string short date format GregorianDate to PersianDate string long format
     *
     * @param date String sample '2017/01/23'
     * @return String sample 'يكشنبه 3 مرداد 1384'
     */
    public static String toLongDate(String date)
    {
        try
        {
            return toLongDate(DateFormat.getInstance().parse(date));
        }
        catch (Exception ex)
        {
            return "";
        }
    }

    /**
     * Convert GregorianDate to PersianDate string short date and time format
     *
     * @param date java.util.Date
     * @return String sample '1384/12/11 23:40:12'
     */
    public static String toStringDateAndTime(Date date)
    {
        return toShortDate(date) + String.format(" %tT", date);
    }

    /**
     * Convert string short date format GregorianDate to PersianDate
     * string short date and time format with time
     *
     * @param date String sample '2017/01/23'
     * @return String sample '1384/12/11 23:40:12'
     */
    public static String toStringDateAndTime(String date)
    {
        try
        {
            return toStringDateAndTime(DateFormat.getInstance().parse(date));
        }
        catch (Exception ex)
        {
            return "";
        }
    }

    /**
     * Convert string short date format PersianDate to GregorianDate
     *
     * @param date String '1384/01/23'
     * @return java.util.Date
     */
    public static Date toDate(String date)
    {
        try
        {
            String[] temp = getDateExpression(date.split(" ")[0]).split("/");
            PersianCalendar persianCalendar = new PersianCalendar();
            persianCalendar.setDateFields(Integer.parseInt(temp[0]),
                                          Integer.parseInt(temp[1]) - 1,
                                          Integer.parseInt(temp[2]));

            checkConvertDate(temp, persianCalendar);
            return persianCalendar.getTime();
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    public static String getDateExpression(String date)
    {
        if (date.contains("*"))
        {
            String[] currentDate = DateTimeFormatter.toShortDate(new Date()).split("/");
            String[] value = date.split("/");
            if (value[0].trim().equals("*")) value[0] = currentDate[0];
            if (value[1].trim().equals("*")) value[1] = currentDate[1];
            if (value[2].trim().equals("*")) value[2] = currentDate[2];
            return value[0] + "/" + value[1] + "/" + value[2];
        }
        return date;
    }

    /**
     * Convert string short date and time format PersianDate to GregorianDate
     *
     * @param dateAndTime String sample '1384/11/12 23:23:23'
     * @return java.util.Date
     */
    public static Date toDateAndTime(String dateAndTime)
    {
        try
        {
            String[] temp = dateAndTime.split(" ");
            String[] date = getDateExpression(temp[0]).split("/");
            String[] time = temp[1].split(":");
            PersianCalendar persianCalendar = new PersianCalendar();
            persianCalendar.setDateFields(Integer.parseInt(date[0]),
                                          Integer.parseInt(date[1]) - 1,
                                          Integer.parseInt(date[2]));

            persianCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
            persianCalendar.set(Calendar.MINUTE, Integer.parseInt(time[1]));
            persianCalendar.set(Calendar.SECOND, Integer.parseInt(time[2]));
            persianCalendar.set(Calendar.MILLISECOND, 0);

            checkConvertDate(date, persianCalendar);
            return persianCalendar.getTime();
        }
        catch (Exception ex)
        {
            return null;
        }
    }

    private static void checkConvertDate(String[] inputDate, PersianCalendar persianCalendar)
    {
        String[] date = toShortDate(persianCalendar.getTime()).split("/");
        int sourceYear = Integer.parseInt(inputDate[0]);
        int destinationYear = Integer.parseInt(date[0]);
        int sourceMonth = Integer.parseInt(inputDate[1]);
        int destinationMonth = Integer.parseInt(date[1]);
        int sourceDay = Integer.parseInt(inputDate[2]);
        int destinationDay = Integer.parseInt(date[2]);
        if (sourceYear != destinationYear)
            persianCalendar.add(Calendar.YEAR, sourceYear - destinationYear);

        if (sourceMonth != destinationMonth)
            persianCalendar.add(Calendar.MONTH, sourceMonth - destinationMonth);

        if (sourceDay != destinationDay)
            persianCalendar.add(Calendar.DAY_OF_YEAR, sourceDay - destinationDay);
    }

    /**
     * Set Time for param[date] to first of day(00:00:01) or last of day(23:59:59) or now
     *
     * @param date      java.util.Date
     * @param timeOfDay Enum type of time of day
     * @return java.util.Date (param[date])
     */
    public static Date setTime(Date date, TimeOfDay timeOfDay)
    {
        if (date == null)
            return null;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (timeOfDay)
        {
            case FIRST_OF_DAY:
                return setTime(calendar, 0, 0, 0, 1);
            case THE_2TH_SECOND_OF_DAY:
                return setTime(calendar, 0, 0, 0, 2);
            case LAST_OF_DAY:
                return setTime(calendar, 23, 59, 59, 999);
            case NOW:
                Calendar now = Calendar.getInstance();
                return setTime(calendar,
                               now.get(Calendar.HOUR_OF_DAY),
                               now.get(Calendar.MINUTE),
                               now.get(Calendar.SECOND),
                               now.get(Calendar.MILLISECOND));
            case NOON:
                return setTime(calendar, 12, 0, 0, 1);
            default:
                return date;
        }
    }

    private static Date setTime(Calendar calendar, int hours, int minute, int second, int milliSecond)
    {

        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, milliSecond);
        return calendar.getTime();
    }

    public static boolean isLeapYear(int year)
    {
        return new PersianCalendar().isLeapYear(year);
    }

    public static boolean isLeapYear(String year)
    {
        return new PersianCalendar().isLeapYear(Integer.parseInt(year));
    }

    public static class Gregorian
    {
        private Gregorian() {
        }

        /**
         * Convert string short date format GregorianDate to GregorianDate
         *
         * @param date String '2010/01/23'
         * @return java.util.Date
         */
        public static Date toDate(String date)
        {
            try
            {
                String[] temp = getDateExpression(date.split(" ")[0]).split("/");
                Calendar gregorianCalendar = Calendar.getInstance();
                gregorianCalendar.set(Integer.parseInt(temp[0]),
                                      Integer.parseInt(temp[1]) - 1,
                                      Integer.parseInt(temp[2]));

                return gregorianCalendar.getTime();
            }
            catch (Exception ex)
            {
                return null;
            }
        }

        public static Date toDate(String year, String month, String day, String hour, String minute, String second)
        {
            return toDate(Integer.parseInt(year),
                          Integer.parseInt(month),
                          Integer.parseInt(day),
                          Integer.parseInt(hour),
                          Integer.parseInt(minute),
                          Integer.parseInt(second));
        }

        public static Date toDate(int year, int month, int day, int hour, int minute, int second)
        {
            try
            {
                Calendar gregorianCalendar = Calendar.getInstance();
                gregorianCalendar.set(year,
                                      month - 1,
                                      day,
                                      hour,
                                      minute,
                                      second);

                return gregorianCalendar.getTime();
            }
            catch (Exception ex)
            {
                return null;
            }
        }
    }
}
