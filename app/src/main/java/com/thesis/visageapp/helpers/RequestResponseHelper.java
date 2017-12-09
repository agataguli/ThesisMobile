package com.thesis.visageapp.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thesis.visageapp.objects.User;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class RequestResponseHelper {
    public static String RESPONSE_CODE_SUCCESS = "200";
    public static String RESPONSE_CODE_FAIL = "201";

    public static String RESPONSE_CODE_ERROR_SIGNUP_LOGIN_DUPLICATE = "300";
    public static String RESPONSE_CODE_ERROR_SIGNUP_PESEL_DUPLICATE = "301";
    public static String RESPONSE_CODE_ERROR_SIGNUP_EMAIL_DUPLICATE = "301";

    private static String keyString = "inzyniery2017";
    private static String encMeth = "HmacSHA1";
    private static String charEnc = "UTF-8";

    public static User processUserStringJSON(String JSON) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(JSON, User.class);
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
