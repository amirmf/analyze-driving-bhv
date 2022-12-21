package com.taraan.dum.common.globalization.simple;

import java.util.Calendar;

/**
 * Created aras Team.
 * User: Ardeshir
 * Date: Jan 18, 2010
 * Time: 4:05:33 PM
 */
public class Util {
    private Util() {
    }

    public static double getDiff(String fromPersianDate, String toPersianDate, int field) {
        if (field == 2) return getDiffMonth(fromPersianDate, toPersianDate);
        PersianCalendar fromCalendar = new PersianCalendar(fromPersianDate);
        PersianCalendar toCalendar = new PersianCalendar(toPersianDate);
        double counter = 0;
        double leapYear = 0;
        if (fromCalendar.before(toCalendar)) {
            int lastMonth = fromCalendar.getMonth();
            int lastYear = fromCalendar.getYear();
            while (fromCalendar.before(toCalendar)) {
                counter++;
                fromCalendar.add(Calendar.DAY_OF_MONTH, 1);
                if (lastMonth != fromCalendar.getMonth()) {
                    lastMonth = fromCalendar.getMonth();
                }

                if (lastYear != fromCalendar.getYear()) {
                    if (PersianCalendar.isLeep(lastYear))
                        leapYear++;
                    lastYear = fromCalendar.getYear();
                }
            }
        } else
            return 0;

        return getDiffResult(field, counter, leapYear);
    }

    private static double getDiffResult(int field, double counter, double leapYear) {
        switch (field) {
            case 5:
                return counter;
            case 1:
                return (counter - leapYear) / 365;
            default:
                return 0;

        }
    }

    public static double getDiffMonth(String fromPersianDate, String toPersianDate) {
        PersianCalendar fromCalendar = new PersianCalendar(fromPersianDate);
        PersianCalendar toCalendar = new PersianCalendar(toPersianDate);
        double monthCounter = 0;
        int counter = 1;
        if (fromCalendar.before(toCalendar)) {
            PersianCalendar tempFromCalendar = new PersianCalendar(fromPersianDate);
            while (tempFromCalendar.before(toCalendar)) {
                tempFromCalendar = new PersianCalendar(fromPersianDate);
                tempFromCalendar.add(Calendar.MONTH, counter);
                if (tempFromCalendar.after(toCalendar)) {
                    break;
                } else {
                    counter++;
                    monthCounter++;
                }
            }
            return monthCounter;
        } else
            return 0;
    }
}
