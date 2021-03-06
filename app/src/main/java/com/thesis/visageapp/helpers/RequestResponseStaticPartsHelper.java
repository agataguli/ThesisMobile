package com.thesis.visageapp.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.thesis.visageapp.domain.Product;
import com.thesis.visageapp.domain.User;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class RequestResponseStaticPartsHelper {
    public static final String RESPONSE_CODE_SUCCESS = "200";
    public static final String RESPONSE_CODE_FAIL = "201";

    public static final String RESPONSE_CODE_ERROR_SIGNUP_LOGIN_DUPLICATE = "300";
    public static final String RESPONSE_CODE_ERROR_SIGNUP_PESEL_DUPLICATE = "301";
    public static final String RESPONSE_CODE_ERROR_SIGNUP_EMAIL_DUPLICATE = "302";

    public static final String RESPONSE_CODE_ERROR_UPDATE_INCORRECT_PASSWORD = "400";
    public static final String RESPONSE_CODE_ERROR_UPDATE_EMAIL_DUPLICATE = "401";

    public static final String RESPONSE_CODE_ERROR_INCORRECT_OLD_PASSWORD = "501";

    private static final String keyString = "inzyniery2017";
    private static final String encMeth = "HmacSHA1";
    private static final String charEnc = "UTF-8";

    public static final String USER_BUNDLE = "userbundle";
    public static final String USER_ID = "userid";
    public static final String PRODUCT_ID = "productid";

    public static final String LIST_FILTER_PRODUCT_URL = "productFilter";
    public static final String LIST_FILTER_PRODUCT_BODY = "productFilterBody";

    public static final String CATEGORY_BRUSHES = "PĘDZLE";
    public static final String CATEGORY_FURNITURE = "WYPOSAŻENIE";
    public static final String CATEGORY_ACCESSORIES = "AKCESORIA";

    public static User processUserStringJSON(String JSON) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(JSON, User.class);
    }

    public static List<Product> proceessProductsStringJson(String JSON) {
        Type listType = new TypeToken<List<Product>>() {}.getType();
        List<Product> products = new Gson().fromJson(JSON, listType);
        return products;
    }

    public static String hashMessage(String message) throws UnsupportedEncodingException, NoSuchAlgorithmException,
            InvalidKeyException {
        SecretKeySpec key = new SecretKeySpec((keyString).getBytes(charEnc), encMeth);
        Mac mac = Mac.getInstance(encMeth);
        mac.init(key);

        byte[] bytes = mac.doFinal(message.getBytes(charEnc));
        return new String(Base64.encodeBase64(bytes));
    }
}
