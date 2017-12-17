package com.thesis.visageapp.helpers;

public class UrlHelper {
    public static final String ROOT_URL = "http://192.168.42.43:8080/visageapp";
    //public static final String ROOT_URL = "http://10.0.2.2:8080/visageapp";

    private static final String URL_SLASH = "/";

    private static final String URL_USERS = "/users";
    private static final String URL_PRODUCTS = "/products";
    private static final String URL_LOGIN = "/loginM";
    private static final String URL_SIGNUP = "/signupM";
    private static final String URL_UPDATE = "/updateM";
    private static final String URL_PASS_CHANGE = "/changepassM";
    private static final String URL_DELETE = "/deleteM";
    private static final String URL_ALL = "/allM";
    private static final String URL_FILTER = "/filterM";
    private static final String URL_FAV = "/favM";
    private static final String URL_ADD = "/add";
    private static final String URL_REMOVE = "/remove";
    private static final String URL_ORDER = "/orders";
    private static final String URL_CART = "/cart";
    private static final String URL_PROCESS_ORDER = "/processOrderM";

    public static String getLoginUrl() {
        return ROOT_URL + URL_USERS + URL_LOGIN;
    }

    public static String getSignupUrl() {
        return ROOT_URL + URL_USERS + URL_SIGNUP;
    }

    public static String getUpdateUrl() {
        return ROOT_URL + URL_USERS + URL_UPDATE;
    }

    public static String getChangePasswordUrl() {
        return ROOT_URL + URL_USERS + URL_PASS_CHANGE;
    }

    public static String getRemoveUrl() {
        return ROOT_URL + URL_USERS + URL_DELETE;
    }

    public static String getGetAllProductUrl() {
        return ROOT_URL + URL_PRODUCTS + URL_ALL;
    }

    public static String getFilterUrl() {
        return ROOT_URL + URL_PRODUCTS + URL_FILTER;
    }

    public static String getUserFavProductsUrl(String userId) {
        return ROOT_URL + URL_PRODUCTS + URL_FAV + URL_SLASH + userId;
    }

    public static String getFavProductAddictionUrl(String userId, String productId) {
        return ROOT_URL + URL_PRODUCTS + URL_FAV + URL_ADD + URL_SLASH + userId + URL_SLASH + productId;
    }

    public static String getFavProductRemovingUrl(String userId, String productId) {
        return ROOT_URL + URL_PRODUCTS + URL_FAV + URL_REMOVE + URL_SLASH + userId + URL_SLASH + productId;
    }

    public static String getOrderUrl(String userId) {
        return ROOT_URL + URL_ORDER + URL_CART + URL_PROCESS_ORDER + URL_SLASH + userId;
    }
}
