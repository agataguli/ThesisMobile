package com.thesis.visageapp;

public class ValidateHelper {


    public static boolean validateLogin(String login) {
        return !login.isEmpty() && login.length()>5 && login.length()<17;
    }


    public static boolean validatePassword(String password) {
        return !password.isEmpty() && password.length() > 7 && password.length() < 17;
    }
}
