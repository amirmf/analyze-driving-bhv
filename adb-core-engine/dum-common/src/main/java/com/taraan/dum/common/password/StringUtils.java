package com.taraan.dum.common.password;

import java.util.Random;

public class StringUtils {
    private static final String SALT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final String SALT_NUMBER = "1234567890";

    public static String getSaltString(long length) {
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * SALT_CHARS.length());
            salt.append(SALT_CHARS.charAt(index));
        }
        return salt.toString();
    }
    public static String getSaltStringNumber(long length) {
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) {
            int index = (int) (rnd.nextFloat() * SALT_NUMBER.length());
            salt.append(SALT_NUMBER.charAt(index));
        }
        return salt.toString();
    }

}
