package com.thesis.visageapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.thesis.visageapp.R;
import com.thesis.visageapp.helpers.RequestResponseStaticPartsHelper;
import com.thesis.visageapp.helpers.UrlHelper;
import com.thesis.visageapp.helpers.ValidateHelper;
import com.thesis.visageapp.processors.VolleySingleton;
import com.thesis.visageapp.objects.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private User user = new User();

    @Bind(R.id.input_pesel_r)
    EditText peselText;
    @Bind(R.id.input_login_r)
    EditText loginText;
    @Bind(R.id.input_password_r)
    EditText passwordText;
    @Bind(R.id.input_rePassword_r)
    EditText rePasswordText;
    @Bind(R.id.input_name_r)
    EditText nameText;
    @Bind(R.id.input_surname_r)
    EditText surnameText;
    @Bind(R.id.input_email_r)
    EditText emailText;
    @Bind(R.id.input_phoneNumber_r)
    EditText phoneNumberText;
    @Bind(R.id.input_country_r)
    EditText countryText;
    @Bind(R.id.input_postCode_r)
    EditText postCodeText;
    @Bind(R.id.input_city_r)
    EditText cityText;
    @Bind(R.id.input_street_r)
    EditText streetText;
    @Bind(R.id.input_addressDetails_r)
    EditText addressDetailsText;
    @Bind(R.id.link_login_r)
    TextView loginLinkedText;
    @Bind(R.id.button_signup_r)
    Button signUpButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        this.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    signup();
                } catch (JSONException | UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
                    e.printStackTrace();
                }
            }
        });

        this.loginLinkedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        Log.d(TAG, "Login processed");
        if (!ValidateHelper.validateUserData(peselText, loginText, passwordText, rePasswordText, nameText, surnameText,
                emailText, phoneNumberText, countryText, postCodeText, cityText, streetText, addressDetailsText, this)) {
            onSignupFailed(RequestResponseStaticPartsHelper.RESPONSE_CODE_FAIL);
            return;
        }
        this.signUpButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(this.getResources().getString(R.string.signingUp));
        progressDialog.show();
        this.prepareFormUser();
        this.processSignup();
        progressDialog.hide();
    }

    private void processSignup() throws JSONException {
        JSONObject jsonBody = new JSONObject(new Gson().toJson(this.user));
        final String requestBody = jsonBody.toString();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlHelper.getSignupUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals(RequestResponseStaticPartsHelper.RESPONSE_CODE_SUCCESS)) {
                            onSignupSuccess();
                        } else {
                            onSignupFailed(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                Toast.makeText(getBaseContext(), getResources().getString(R.string.loginRequestError),
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
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }


    public void onSignupSuccess() {
        signUpButton.setEnabled(true);
        Toast.makeText(getBaseContext(), getResources().getString(R.string.signupSuccess),
                Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK, null);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void prepareFormUser() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        user.setUserId(peselText.getText().toString());
        user.setName(nameText.getText().toString());
        user.setLogin(loginText.getText().toString());
        user.setPassword(RequestResponseStaticPartsHelper.hashMessage(this.passwordText.getText().toString()));
        user.setSurname(surnameText.getText().toString());
        user.setEmail(emailText.getText().toString());
        user.setPhoneNumber(phoneNumberText.getText().toString());
        user.setCountry(countryText.getText().toString());
        user.setPostCode(postCodeText.getText().toString());
        user.setCity(cityText.getText().toString());
        user.setStreet(streetText.getText().toString());
        user.setAddressDetails(addressDetailsText.getText().toString());
    }

    public void onSignupFailed(String responseCode) {
        String errorToast = this.getResources().getString(R.string.signUpFailed);
        if (responseCode.equals(RequestResponseStaticPartsHelper.RESPONSE_CODE_ERROR_SIGNUP_LOGIN_DUPLICATE)) {
            getResources().getString(R.string.status_code_signup_login_duplicate);
        } else {
            if (responseCode.equals(RequestResponseStaticPartsHelper.RESPONSE_CODE_ERROR_SIGNUP_PESEL_DUPLICATE)) {
                getResources().getString(R.string.status_code_signup_pesel_duplicate);
            } else {
                if (responseCode.equals(RequestResponseStaticPartsHelper.RESPONSE_CODE_ERROR_SIGNUP_EMAIL_DUPLICATE)) {
                    getResources().getString(R.string.status_code_signup_email_duplicate);
                }
            }
        }
        Toast.makeText(getBaseContext(), errorToast, Toast.LENGTH_SHORT).show();
        signUpButton.setEnabled(true);
    }
}