package xyz.ivan.x.util;

import java.util.regex.Pattern;

public class XUtil {
    public static boolean isValidPhoneNumber(String phoneNumber){
        String regex = "^1[3-9]\\d{9}$";
        return Pattern.matches(regex, phoneNumber);
    }
}
