/*
	PersianCalendar.java
	2013-09-24 14:56:36
	Copyright � Amir Mansouri Fard (amf.fard@gmail.com)
	
	This program is free software; you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation; either version 2 of the License, or
	(at your option) any later version.
	
	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	
	You should have received a copy of the GNU General Public License
	along with this program; if not, write to the Free Software
	Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/


package com.taraan.dum.common.globalization;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static com.taraan.dum.common.globalization.PersianCalendarUtils.*;

/**
 * This class is a subclass of <code>java.util.GregorianCalendar</code>,
 * with the added functionality that it can set/get date in the Persian
 * calendar system.
 * <p/>
 * The algorithms for conversion between Persian and Gregorian calendar systems
 * are placed in <code>{@link PersianCalendarHelper}</code> class.
 *
 * @version 2.1
 */

public class PersianCalendar extends GregorianCalendar {
    // Julian day 0, 00:00:00 hours (midnight); milliseconds since 1970-01-01 00:00:00 UTC (Gregorian Calendar)
    private static final long JULIAN_EPOCH_MILLIS = -210866803200000L;
    private static final long ONE_DAY_MILLIS = 24L * 60L * 60L * 1000L;

    /**
     * Get the Julian day corresponding to the date of this calendar.
     *
     * @return the Julian day corresponding to the date of this calendar.
     * @since 2.0
     */
    public long getJulianDay() {
        return div(getTimeInMillis() + 12600000 - JULIAN_EPOCH_MILLIS, ONE_DAY_MILLIS);
    }

    /**
     * Set the date of this calendar to the specified Julian day.
     *
     * @param julianDay the desired Julian day to be set as the date of this calendar.
     * @since 2.0
     */
    public void setJulianDay(long julianDay) {
        setTimeInMillis(JULIAN_EPOCH_MILLIS + julianDay * ONE_DAY_MILLIS + mod(getTimeInMillis() - JULIAN_EPOCH_MILLIS, ONE_DAY_MILLIS));
    }

    /**
     * Sets the date of this calendar object to the specified
     * Persian date (year, month, and day fields)
     *
     * @param year  the Persian year.
     * @param month the Persian month (zero-based).
     * @param day   the Persian day of month.
     * @since 1.0
     */
    public void setDateFields(int year, int month, int day) {
        setDateFields(new DateFields(year, month, day));
    }

    /**
     * Sets the date of this calendar object to the specified
     * Persian date fields
     *
     * @param dateFields the Persian date fields.
     * @since 1.0
     */
    public void setDateFields(DateFields dateFields) {
        int y = dateFields.getYear();
        int m = dateFields.getMonth();
        int d = dateFields.getDay();
        setJulianDay(PersianCalendarHelper.pj(y > 0 ? y : y + 1, m, d));
    }

    /**
     * Retrieves the date of this calendar object as the
     * Persian date fields
     *
     * @return the date of this calendar as Persian date fields.
     * @since 1.0
     */
    public DateFields getDateFields() {
        long julianDay = getJulianDay();
        long r = PersianCalendarHelper.jp(julianDay);
        long y = y(r);
        int m = m(r);
        int d = d(r);
        return new DateFields((int) (y > 0 ? y : y - 1), m, d);
    }

    /**
     * Persian month names.
     *
     * @since 1.1
     */
    protected static final String[] persianMonths =
            {
                    "فروردین",              // Farvardin
                    "اردیبهشت",             // Ordibehesht
                    "خرداد",                // Khordad
                    "تیر",                  // Tir
                    "مرداد",                // Mordad
                    "شهریور",               // Shahrivar
                    "مهر",                  // Mehr
                    "آبان",                 // Aban
                    "آذر",                  // Azar
                    "دی",                   // Dey
                    "بهمن",                 // Bahman
                    "اسفند"                 // Esfand
            };

    /**
     * Persian week day names.
     *
     * @since 1.1
     */
    protected static final String[] persianWeekDays =
            {
                    "شنبه",                 // shanbeh
                    "یکشنبه",               // yek-shanbeh
                    "دوشنبه",               // do-shanbeh
                    "سه شنبه",              // seh-shanbeh
                    "چهارشنبه",             // chahar-shanbeh
                    "پنجشنبه",              // panj-shanbeh
                    "جمعه"    //                   jom'eh
            };

    /**
     * Gives the name of the specified Persian month.
     *
     * @param month the Persian month (zero-based).
     * @return the name of the specified Persian month in Persian.
     * @since 1.1
     */
    public static String getPersianMonthName(int month) {
        return persianMonths[month];
    }

    /**
     * Gives the name of the current Persian month for this calendar's date.
     *
     * @return the name of the current Persian month for this calendar's date in Persian.
     * @since 1.3
     */
    public String getPersianMonthName() {
        return getPersianMonthName(getDateFields().getMonth());
    }

    /**
     * Gives the Persian name of the specified day of week.
     *
     * @param weekDay the day of week (use symbolic constants in the <code>java.util.Calendar</code> class).
     * @return the name of the specified day of week in Persian.
     * @since 1.1
     */
    public static String getPersianWeekDayName(int weekDay) {
        switch (weekDay) {
            case SATURDAY:
                return persianWeekDays[0];
            case SUNDAY:
                return persianWeekDays[1];
            case MONDAY:
                return persianWeekDays[2];
            case TUESDAY:
                return persianWeekDays[3];
            case WEDNESDAY:
                return persianWeekDays[4];
            case THURSDAY:
                return persianWeekDays[5];
            case FRIDAY:
                return persianWeekDays[6];
            default:
                return "";
        }
    }

    /**
     * Gives the Persian name of the current day of the week for this
     * calendar's date.
     *
     * @return the name of the current day of week for this calendar's date in Persian.
     * @since 1.3
     */
    public String getPersianWeekDayName() {
        return getPersianWeekDayName(get(DAY_OF_WEEK));
    }

    @Override
    public int get(int field) {
        switch (field) {
            case Calendar.DAY_OF_MONTH:
                return getDateFields().getDay();
            case Calendar.YEAR:
                return getDateFields().getYear();
            case Calendar.MONTH:
                return getDateFields().getMonth();
            default:
                return super.get(field);
        }
    }

    public PersianCalendar() {
    }

    public PersianCalendar(Date date) {
        this.setTime(date);
    }
}
