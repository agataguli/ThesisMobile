package com.thesis.visageapp.helpers;

public class UrlHelper {
    public static final String AGATA_URL = "http://192.168.42.43:8080/visageapp";
    public static final String LOCAL_EMULATOR_URL = "http://10.0.2.2:8080/visageapp";

    private static final String URL_SLASH ="/";
    private static final String URL_USERS = "/users";
    private static final String URL_LOGIN = "/loginM";

    public static String getUserLoginRequest(String login, String password, String url) {
        return url + URL_USERS + URL_LOGIN + URL_SLASH + login + URL_SLASH + password;
    }

}
