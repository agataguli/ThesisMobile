package com.thesis.visageapp.helpers;

import android.text.TextUtils;

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
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return !TextUtils.isEmpty(phoneNumber) && android.util.Patterns.PHONE.matcher(phoneNumber).matches();
    }

    public static boolean isValidCountry(String country) {
        return !country.isEmpty() && country.length() > 2 && country.length() < 20;
        // TODO change countries to enum class, enable by dropdown
    }

    public static boolean isValidPostCode(String postCode) {
        return !postCode.matches("\\d{2}-\\d{3}");
    }
}
