package com.thesis.visageapp.activities;

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
import com.thesis.visageapp.domain.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserProfileActivity extends AppCompatActivity {
    private static final String TAG = "UserProfileActivity";
    private User user = new User();
    private Bundle extras = new Bundle();
    private boolean onlyPreview = true;

    @Bind(R.id.textview_login_u)
    TextView loginTextView;
    @Bind(R.id.input_password_u)
    EditText passwordText;
    @Bind(R.id.input_rePassword_u)
    EditText rePasswordText;
    @Bind(R.id.input_name_u)
    EditText nameText;
    @Bind(R.id.input_surname_u)
    EditText surnameText;
    @Bind(R.id.input_email_u)
    EditText emailText;
    @Bind(R.id.input_phoneNumber_u)
    EditText phoneNumberText;
    @Bind(R.id.input_country_u)
    EditText countryText;
    @Bind(R.id.input_postCode_u)
    EditText postCodeText;
    @Bind(R.id.input_city_u)
    EditText cityText;
    @Bind(R.id.input_street_u)
    EditText streetText;
    @Bind(R.id.input_addressDetails_u)
    EditText addressDetailsText;
    @Bind(R.id.button_user_change_credentials_u)
    Button changeCredentialsButton;
    @Bind(R.id.button_user_change_password_u)
    Button changePasswordButton;
    @Bind(R.id.button_user_delete_account_u)
    Button deleteAccountButton;
    @Bind(R.id.textview_hint_u)
    TextView hintTextView;
    @Bind(R.id.button_back_u)
    Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        this.extras = getIntent().getExtras();
        if (this.extras != null) {
            this.user = RequestResponseStaticPartsHelper.processUserStringJSON(extras.getString(
                    RequestResponseStaticPartsHelper.USER_BUNDLE));
            this.prepareFormFromUser();
        }

        this.changeCredentialsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    changeBasicUserData();
                } catch (JSONException | NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
                    e.printStackTrace();
                }
            }
        });
        this.changePasswordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    changeUserPassword();
                } catch (JSONException | UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
                    e.printStackTrace();
                }
            }
        });
        this.deleteAccountButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    deleteUserAccount();
                } catch (JSONException | UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
                    e.printStackTrace();
                }
            }
        });

        this.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMenuActivity();
            }
        });
    }

    private void backToMenuActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtras(extras);
        intent.putExtra(RequestResponseStaticPartsHelper.USER_BUNDLE, new Gson().toJson(this.user));
        startActivity(intent);
        finish();
    }

    private void deleteUserAccount() throws InvalidKeyException, NoSuchAlgorithmException, JSONException, UnsupportedEncodingException {
        Log.d(TAG, "Removing user account processed");
        if (onlyPreview) {
            this.onlyPreview = !this.onlyPreview;
            this.setPasswordInputsEnableAndVisible(!this.onlyPreview, View.VISIBLE);
            this.setButtonsEnable(onlyPreview, onlyPreview, !onlyPreview);
        } else {
            if (!ValidateHelper.validateUpdateData(passwordText, rePasswordText, nameText, surnameText, emailText,
                    phoneNumberText, countryText, postCodeText, cityText, streetText, addressDetailsText, this)) {
                this.onUpdateFailed(RequestResponseStaticPartsHelper.RESPONSE_CODE_FAIL);
                return;
            }
            this.processRemoveUserAccount();
        }
    }

    private void processRemoveUserAccount() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, JSONException {
        JSONObject jsonBody = new JSONObject(new Gson().toJson(this.user));
        final String requestBody = jsonBody.toString();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlHelper.getRemoveUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals(RequestResponseStaticPartsHelper.RESPONSE_CODE_SUCCESS)) {
                            onRemoveSuccess();
                        } else {
                            onRemoveFailed(response);
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

    private void onRemoveFailed(String responseCode) {
        String errorToast = this.getResources().getString(R.string.delete_account_failed);
        if (responseCode.equals(RequestResponseStaticPartsHelper.RESPONSE_CODE_ERROR_INCORRECT_OLD_PASSWORD)) {
            getResources().getString(R.string.status_code_change_incorrect_old_password);
        }
        Toast.makeText(getBaseContext(), errorToast, Toast.LENGTH_SHORT).show();
    }

    private void onRemoveSuccess() {
        Toast.makeText(getBaseContext(), getResources().getString(R.string.deleteAccount_success),
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        this.user = null;
        this.extras = null;
        startActivity(intent);
        finish();
    }

    private void changeUserPassword() throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        Log.d(TAG, "Changing password processed");
        if (onlyPreview) {
            this.onlyPreview = !this.onlyPreview;
            this.setPasswordInputsEnableAndVisible(!this.onlyPreview, View.VISIBLE);
            this.setButtonsEnable(onlyPreview, !onlyPreview, onlyPreview);
            this.rePasswordText.setText(getResources().getString(R.string.insert_new_password));
        } else {
            if (!ValidateHelper.validateSinglePassword(this.passwordText, this) ||
                    !ValidateHelper.validateSinglePassword(this.rePasswordText, this)) {
                this.onUpdateFailed(RequestResponseStaticPartsHelper.RESPONSE_CODE_FAIL);
                return;
            }
            this.processChangeUserPassword();
        }
    }

    private void processChangeUserPassword() throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        User user = new User();
        user.setUserId(this.user.getUserId());
        user.setLogin(this.user.getLogin());
        user.setPassword(this.user.getPassword());
        user.setCity(this.rePasswordText.getText().toString());
        JSONObject jsonBody = new JSONObject(new Gson().toJson(user));
        final String requestBody = jsonBody.toString();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlHelper.getChangePasswordUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals(RequestResponseStaticPartsHelper.RESPONSE_CODE_SUCCESS)) {
                            onChangePasswordSuccess();
                        } else {
                            onChangePasswordFailed(response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

    private void onChangePasswordSuccess() {
        Toast.makeText(getBaseContext(), getResources().getString(R.string.changePassword_success),
                Toast.LENGTH_SHORT).show();
        this.restartActivity();

    }

    private void onChangePasswordFailed(String responseCode) {
        String errorToast = this.getResources().getString(R.string.change_password_failed);
        if (responseCode.equals(RequestResponseStaticPartsHelper.RESPONSE_CODE_ERROR_INCORRECT_OLD_PASSWORD)) {
            getResources().getString(R.string.status_code_change_incorrect_old_password);
        }
        Toast.makeText(getBaseContext(), errorToast, Toast.LENGTH_SHORT).show();
    }

    private void changeBasicUserData() throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        Log.d(TAG, "Changing credentials processed");
        if (onlyPreview) {
            this.onlyPreview = !this.onlyPreview;
            this.setPasswordInputsEnableAndVisible(!this.onlyPreview, View.VISIBLE);
            this.setButtonsEnable(!onlyPreview, onlyPreview, onlyPreview);
            this.setBasicTextInputsEnable(!onlyPreview);
        } else {
            if (!ValidateHelper.validateUpdateData(passwordText, rePasswordText, nameText, surnameText, emailText,
                    phoneNumberText, countryText, postCodeText, cityText, streetText, addressDetailsText, this)) {
                this.onUpdateFailed(RequestResponseStaticPartsHelper.RESPONSE_CODE_FAIL);
                return;
            }
            this.processUpdateUserData();
        }
    }

    private void onUpdateFailed(String responseCode) {
        String errorToast = this.getResources().getString(R.string.update_client_data_failed);
        if (responseCode.equals(RequestResponseStaticPartsHelper.RESPONSE_CODE_ERROR_UPDATE_EMAIL_DUPLICATE)) {
            getResources().getString(R.string.status_code_update_email_duplicate);
        } else {
            if (responseCode.equals(RequestResponseStaticPartsHelper.RESPONSE_CODE_ERROR_UPDATE_INCORRECT_PASSWORD)) {
                getResources().getString(R.string.status_code_update_incorrect_password);
            }
        }
        Toast.makeText(getBaseContext(), errorToast, Toast.LENGTH_SHORT).show();
    }

    private void processUpdateUserData() throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        this.prepareFormUser();
        JSONObject jsonBody = new JSONObject(new Gson().toJson(this.user));
        final String requestBody = jsonBody.toString();

        final StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlHelper.getUpdateUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals(RequestResponseStaticPartsHelper.RESPONSE_CODE_SUCCESS)) {
                            onUpdateSucess();
                        } else {
                            onUpdateFailed(response);
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

    private void onUpdateSucess() {
        Toast.makeText(getBaseContext(), getResources().getString(R.string.update_success),
                Toast.LENGTH_SHORT).show();
        this.restartActivity();
    }

    private void restartActivity() {
        setResult(RESULT_OK, null);
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtras(this.extras);
        intent.putExtra(RequestResponseStaticPartsHelper.USER_BUNDLE, new Gson().toJson(this.user));
        startActivity(intent);
        finish();
    }

    private void setButtonsEnable(boolean b1, boolean b2, boolean b3) {
        this.changeCredentialsButton.setEnabled(b1);
        this.changePasswordButton.setEnabled(b2);
        this.deleteAccountButton.setEnabled(b3);
    }

    private void setPasswordInputsEnableAndVisible(boolean enable, int visible) {
        this.passwordText.setVisibility(visible);
        this.passwordText.setEnabled(enable);
        this.rePasswordText.setVisibility(visible);
        this.passwordText.setEnabled(enable);
        this.passwordText.setText(null);
        this.rePasswordText.setText(null);
    }

    private void setBasicTextInputsEnable(boolean val) {
        loginTextView.setEnabled(val);
        nameText.setEnabled(val);
        surnameText.setEnabled(val);
        emailText.setEnabled(val);
        phoneNumberText.setEnabled(val);
        countryText.setEnabled(val);
        postCodeText.setEnabled(val);
        cityText.setEnabled(val);
        streetText.setEnabled(val);
        addressDetailsText.setEnabled(val);
    }

    private void prepareFormFromUser() {
        loginTextView.setText(user.getLogin());
        nameText.setText(user.getName());
        surnameText.setText(user.getSurname());
        emailText.setText(user.getEmail());
        phoneNumberText.setText(user.getPhoneNumber());
        countryText.setText(user.getCountry());
        postCodeText.setText(user.getPostCode());
        cityText.setText(user.getCity());
        streetText.setText(user.getStreet());
        addressDetailsText.setText(user.getAddressDetails());
    }

    public void prepareFormUser() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        user.setUserId(this.user.getUserId());
        user.setName(nameText.getText().toString());
        user.setLogin(this.user.getLogin());
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
}
