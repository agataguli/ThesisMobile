package com.thesis.visageapp.processors;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.thesis.visageapp.R;
import com.thesis.visageapp.helpers.RequestResponseStaticPartsHelper;
import com.thesis.visageapp.helpers.UrlHelper;
import com.thesis.visageapp.domain.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class VolleyRequestProcessor {
    private String responseCode = " 702";

    public static void processImageRequest(final ImageView iv, String productPhotoLink, final Context context) {
        ImageRequest imageRequest = new ImageRequest(productPhotoLink, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                iv.setImageBitmap(response);
            }
        }, 300, 300, null, null, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                // do not send any notification if imageRequest not response
            }
        });
        VolleySingleton.getInstance(context).addToRequestQueue(imageRequest);
    }

    public String processPostUserData(final String URL, User user, final Context context) throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        JSONObject jsonBody = new JSONObject(new Gson().toJson(user));
        final String requestBody = jsonBody.toString();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (URL == UrlHelper.getLoginUrl()) {
                            User user = RequestResponseStaticPartsHelper.processUserStringJSON(response);
                            if (user.getUserId().equals(context.getString(R.string.ERROR))) {
                                onUpdateFailed(context.getString(R.string.ERROR));
                            }
                        } else {
                            if (response.equals(RequestResponseStaticPartsHelper.RESPONSE_CODE_SUCCESS)) {
                                onUpdateSuccess();
                            } else {
                                onUpdateFailed(response);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, context.getString(R.string.loginRequestError),
                        Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return requestBody == null ? null : requestBody.getBytes();
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
        return this.responseCode;
    }

    private void onUpdateFailed(String responseCode) {
        this.responseCode = responseCode;
    }

    private void onUpdateSuccess() {
        this.responseCode = RequestResponseStaticPartsHelper.RESPONSE_CODE_SUCCESS;
    }

    public static void orderProductsFromCart(List allProductsIds, final Context context, String userId)
            throws JSONException {
        final String requestBody = new Gson().toJson(allProductsIds);
        String url = UrlHelper.getOrderUrl(userId);
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int i = 10;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, context.getString(R.string.orderRequestError),
                        Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return requestBody == null ? null : requestBody.getBytes();
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}
