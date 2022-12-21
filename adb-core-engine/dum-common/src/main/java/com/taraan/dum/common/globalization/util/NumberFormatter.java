package com.taraan.dum.common.globalization.util;

/**
 * Created aras Team.
 * User: Reza
 * Date: Nov 13, 2010
 * Time: 10:09:37 AM
 */
public class NumberFormatter {
    private NumberFormatter() {
    }

    private static String[] parts = {"", "هزار", "میلیون", "میلیارد", "تریلیون"};
    private static String[] orders0 = {"", "یک", "دو", "سه", "چهار", "پنج", "شش", "هفت", "هشت", "نه"};
    private static String[] orders1 = {"", "", "بیست", "سی", "چهل", "پنجاه", "شصت", "هفتاد", "هشتاد", "نود"};
    private static String[] orders2 = {"", "يكصد", "دویست", "سیصد", "چهارصد", "پانصد", "ششصد", "هفتصد", "هشتصد", "نهصد"};
    private static String[] digits = {"ده", "یازده", "دوازده", "سیزده", "چهارده", "پانزده", "شانزده", "هفده", "هجده", "نوزده"};

    public static String stringNumberToPersianText(String input) {
        StringBuilder output = new StringBuilder();
        if (!input.equals("")) {
            String temp;
            int i = input.length() - 1;
            int j = 0;
            int part = 0;
            while (i >= 0) {
                temp = input.toCharArray()[i] + "";
                boolean b = ((input.toCharArray()[i - 1] == '1') & (j == 0)) && (i != 0);
                if (b) {
                    temp = input.substring(i - 1);
                    temp = stringTwoDigitToText(temp);
                    output.insert(0, temp);
                    if (((i - 1) != 0))
                        output.insert(0, " و ");
                    i = i - 2;
                    j++;
                } else {
                    temp = stringDigitToText(temp, j);
                    if (!temp.equals("")) {
                        output.insert(0, temp);
                        if (i != 0)
                            output.insert(0, " و ");
                    }
                    i--;
                }
                if (j == 2) {
                    part++;
                    if (i != -1 && (!reverse(separate(input)).split(",")[part].equals("000")))
                        output.insert(0, " " + stringPartsName(part) + " ");
                    j = 0;
                } else
                    j++;
            }
        }
        return output.toString();
    }

    private static String stringPartsName(int part) {
        if (part > parts.length - 1)
            return "";
        return parts[part];
    }

    private static String stringDigitToText(String digit, int order) {
        int value = Integer.parseInt(digit);
        if (order == 0)
            if (value > orders0.length - 1)
                return "";
            else
                return orders0[value];
        else if (order == 1)
            if (value > orders1.length - 1)
                return "";
            else
                return orders1[value];
        else if (order == 2)
            if (value > orders2.length - 1)
                return "";
            else
                return orders2[value];
        return "";
    }

    private static String stringTwoDigitToText(String digit) {
        int value = Integer.parseInt(digit.substring(1).substring(0, 1));
        if (value > digits.length - 1)
            return "";
        else
            return digits[value];
    }

    public static String separate(String value) {
        StringBuilder result = new StringBuilder();
        int counter = 1;
        for (int i = value.length(); i > 0; i--) {

            if ((counter > 3) && (counter % 3) == 1)
                result.insert(0, ",");
            result.insert(0, value.toCharArray()[i - 1]);
            counter++;
        }
        return result.toString();
    }

    public static String reverse(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++)
            result.insert(0, input.toCharArray()[i]);
        return result.toString();
    }
}

