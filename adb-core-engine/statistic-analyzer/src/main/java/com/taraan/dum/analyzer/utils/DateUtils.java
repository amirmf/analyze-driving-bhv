package com.taraan.dum.analyzer.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {
    private final static long SECOND_MILLIS = 1000;
    private final static long MINUTE_MILLIS = SECOND_MILLIS * 60;
    private final static long HOUR_MILLIS = MINUTE_MILLIS * 60;
    private final static long DAY_MILLIS = HOUR_MILLIS * 24;

    public static void main(String[] args) {
        System.out.println("from:" + getDate("2018-04-22 20:48:33").getTime());
        System.out.println("to:" + getDate("2018-04-22 21:10:44").getTime());
    }

    private DateUtils() {
    }

    public static String getDate(Date foreignBirth) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(foreignBirth);
    }

    public static Date getDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException();
        }
    }

    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return localDateTimeToDate(endOfDay);
    }

    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = dateToLocalDateTime(date);
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return localDateTimeToDate(startOfDay);
    }

    private static Date localDateTimeToDate(LocalDateTime startOfDay) {
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    private static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
    }

    public static int getHourDateDiffrent(Date laterDate, Date earlierDate) {
        return Math.abs((int) ((laterDate.getTime() / HOUR_MILLIS) - (earlierDate.getTime() / HOUR_MILLIS)));

    }

    public static int getDayDateDiffrent(Date laterDate, Date earlierDate) {
        return Math.abs((int) ((laterDate.getTime() / DAY_MILLIS) - (earlierDate.getTime() / DAY_MILLIS)));

    }

    public static int getRealDayDateDiffrent(Date laterDate, Date earlierDate) {
        return (int) ((laterDate.getTime() / DAY_MILLIS) - (earlierDate.getTime() / DAY_MILLIS));

    }
    public static boolean isBefore(Date date1, Date date2) {
        return date1.getTime() < date2.getTime();
    }

    public static boolean isAfter(Date date1, Date date2)
    {
        return date1.getTime() > date2.getTime();
    }

    public static boolean isBetween(Date date, Date fromDate, Date toDate) {
        return ((fromDate == null || isBefore(fromDate, date)) && (toDate == null || isAfter(toDate, date)));
    }

}
