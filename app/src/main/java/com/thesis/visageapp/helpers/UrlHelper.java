package com.thesis.visageapp.helpers;

public class UrlHelper {
    public static final String ROOT_URL = "http://192.168.42.43:8080/visageapp";
    //public static final String ROOT_URL = "http://10.0.2.2:8080/visageapp";

    private static final String URL_SLASH ="/";
    private static final String URL_USERS = "/users";
    private static final String URL_LOGIN = "/loginM";
    private static final String URL_SIGNUP = "/signupM";

    public static String getLoginUrl() {
        return ROOT_URL + URL_USERS + URL_LOGIN;
    }


    public static String getSignupUrl() {
        return ROOT_URL + URL_USERS + URL_SIGNUP;
    }
}
