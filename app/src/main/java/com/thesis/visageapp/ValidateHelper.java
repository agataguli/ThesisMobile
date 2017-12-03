package com.thesis.visageapp;

import android.text.TextUtils;

import java.util.regex.Pattern;

public class ValidateHelper {


    public static boolean isValidLogin(String s) {
        return !s.isEmpty() && s.length() > 5 && s.length() < 17;
    }


    public static boolean isValidPassword(String s) {
        return !s.isEmpty() && s.length() > 7 && s.length() < 17;
    }

    public static boolean isValidatePesel(String s) {
        return !s.isEmpty() && s.matches("[0-9]{11}");
    }

    public static boolean isValidRePassword(String password, String rePassword) {
        return !password.isEmpty() && !rePassword.isEmpty() && rePassword.equals(password);
    }

    public static boolean isValidNameSurname(String s) {
        return !s.isEmpty() && s.length() > 2 && s.length() < 16;
    }
    public static boolean isValidEmail(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return !TextUtils.isEmpty(phoneNumber) && android.util.Patterns.PHONE.matcher(phoneNumber).matches();
    }

    public static boolean isValidCountry(String country) {
        return !country.isEmpty() && country.length() > 2 && country.length() < 20;
        //TODO zamien to na dropdown
    }

    public static boolean isValidPostCode(String postCode) {
        return !postCode.isEmpty() && postCode.length() > 3 && postCode.length() < 8 && postCode.matches("[0-9]");
        //TODO dokladniejsza walidacja kodu pocztowego
    }
}
