package com.taraan.dum.common.globalization.simple;


/**
 * Created aras Team.
 * User: Ardeshir
 * Date: Jan 18, 2010
 * Time: 4:02:35 PM
 */
public class PersianCalendar {

    private int year;
    private int month;
    private int day;

    /**
     * @param stringDate date must be this format ####/##/## or ####/#/#
     */
    public PersianCalendar(String stringDate) {
        String[] date = stringDate.split("/");
        this.year = Integer.parseInt(date[0]);
        this.month = Integer.parseInt(date[1]);
        this.day = Integer.parseInt(date[2]);
        if (this.month < 1 || this.month > 12)
            throw new IllegalArgumentException("Month value is " + (month < 1 ? "less" : "greater"));

        if (this.day < 1 || this.day > 31)
            throw new IllegalArgumentException("Day value is " + (day < 1 ? "less" : "greater"));

        if (this.month > 6 && this.day > 30)
            throw new IllegalArgumentException("Day value is greater");

        if (this.day == 30 && this.month == 12 && !this.isLeep()) {
                this.addYear(1);
                this.month = 1;
                this.day = 1;
        }
    }

    public static boolean isValidDate(String persianDate) {
        try {
            PersianCalendar persianCalendar = new PersianCalendar(persianDate);
            String[] date = persianDate.split("/");
            int year = Integer.parseInt(date[0]);
            if (persianCalendar.getYear() != year)
                throw new IllegalArgumentException("Year is leap");
        } catch (Exception ex) {
            return false;
        }

        return true;
    }

    /**
     * @return String PersianDate
     */
    public String getDate() {
        return this.year + "/" + this.month + "/" + this.day;
    }

    /**
     * @return String PersianDate Without Slash 00000000(0000/00/00)
     */
    public String getNumericDate() {
        return String.format("%04d%02d%02d", this.year, this.month, this.day);
    }

    /**
     * @param field Calendar Field java.util.Calendar.YEAR
     *              java.util.Calendar.MONTH
     *              java.util.Calendar.DAY_OF_MONTH
     * @param value int Amount
     * @return String PersianDate
     */
    public String add(int field, int value) {
        switch (field) {
            case 1:
                this.addYear(value);
                break;
            case 2:
                this.addMonth(value);
                break;
            case 5:
                this.addDay(value);
                break;
            default:
        }
        this.correctDate(); //13890331 Reza & Mirzaee
        return this.getDate();
    }

    /**
     * @param field Calendar Field java.util.Calendar.YEAR
     *              java.util.Calendar.MONTH
     *              java.util.Calendar.DAY_OF_MONTH
     * @return int Persian Field java.util.Calendar.YEAR
     * java.util.Calendar.MONTH
     * java.util.Calendar.DAY_OF_MONTH
     */
    public int get(int field) {
        switch (field) {
            case 1:
                return this.year;
            case 2:
                return this.month;
            case 5:
                return this.day;
            default:
        }
        return 0;
    }

    /**
     * @return java.util.Calendar.Year
     */
    public int getYear() {
        return this.year;
    }

    /**
     * @return java.util.Calendar.MONTH
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * @return java.util.Calendar.DAY_OF_MONTH
     */
    public int getDay() {
        return this.day;
    }

    /**
     * @param value int Amount
     * @return String PersianDate
     */
    private String addDay(int value) {
        if (value < 0) subtractDay(value);
        else {
            boolean eom = false;
            for (int index = 1; index <= value; index++) {
                if (this.month < 7) {
                    if (this.day == 31) {
                        this.addMonth(1);
                        eom = true;
                    }
                } else if (this.month != 12) {
                    if (this.day == 30) {
                        this.addMonth(1);
                        eom = true;
                    }
                } else {
                    if (this.day == 29) {
                        if (!this.isLeep()) {
                            this.addMonth(1);
                            eom = true;
                        }
                    } else if (this.day == 30) {
                        this.addMonth(1);
                        eom = true;
                    }
                }

                if (eom) {
                    this.day = 1;
                } else {
                    this.day += 1;
                }
            }
        }
        return this.getDate();
    }

    private String subtractDay(int value) {
        if (value < 0) value = value * -1;
        for (int index = 1; index <= value; index++) {
            getSubtractDayResult();
        }
        return this.getDate();
    }

    private void getSubtractDayResult() {
        if (this.day == 1) {
            if (this.month < 7) {
                this.subtractMonth(1);
                this.day = 31;
            } else if (this.month != 1) {
                this.subtractMonth(1);
                this.day = 30;
            }
        } else if (this.month == 1 && this.day == 31) {
            if (!this.isLeep()) {
                this.subtractMonth(1);
                this.day = 29;
            } else if (this.day == 1) {
                this.subtractMonth(1);
                this.day = 30;
            }
        } else {
            this.day -= 1;
        }
    }

    public boolean isLeep() {
        return isLeep(this.year);
    }

    public static boolean isLeep(int year) {
        long a = year - 474L;
        long b = mod(a, 2820L) + 474L;
        return mod((b + 38D) * 682D, 2816D) < 682L;
    }

    private static long mod(double a, double b) {
        return (long) (a - b * Math.floor(a / b));
    }

    public boolean isLeepMonth() {
        return isLeepMonth(this.month);
    }

    public static boolean isLeepMonth(int month) {
        return month < 7;
    }

    /**
     * @param value int Amount
     * @return String PersianDate
     */
    private String addMonth(int value) {
        if (value < 0) subtractMonth(value);
        else {
            for (int index = 1; index <= value; index++) {
                if (this.month == 12) {
                    this.addYear(1);
                    this.month = 1;
                } else {
                    this.month += 1;
                }
            }
        }
        return this.getDate();
    }

    private String subtractMonth(int value) {
        if (value < 0) value = value * -1;
        for (int index = 1; index <= value; index++) {
            if (this.month == 1) {
                this.subtractYear(1);
                this.month = 12;
            } else {
                this.month -= 1;
            }
        }
        return this.getDate();
    }

    private void correctDate()
    {
        if (this.month > 6 && this.month < 12 && this.day > 30)
            this.day = 30;

        if (this.month == 12 && this.day > 29)
            this.day = (this.isLeep()) ? 30 : 29;
    }

    /**
     * @param value int Amount
     * @return String PersianDate
     */
    private String addYear(int value) {
        if (value < 0) subtractYear(value);
        else {
            for (int index = 1; index <= value; index++) {
                this.year += 1;
            }
        }
        return this.getDate();
    }

    private String subtractYear(int value) {
        if (value < 0) value = value * -1;
        for (int index = 1; index <= value; index++) {
            this.year -= 1;
        }
        return this.getDate();
    }

    /**
     * @return String PersianDate Like getDate()
     */
    public String toString() {
        return this.getDate();
    }

    public boolean after(PersianCalendar calendar) {
        return Long.parseLong(this.getNumericDate()) > Long.parseLong(calendar.getNumericDate());
    }

    public boolean before(PersianCalendar calendar) {
        return Long.parseLong(this.getNumericDate()) < Long.parseLong(calendar.getNumericDate());
    }
}