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
import com.thesis.visageapp.helpers.RequestResponseHelper;
import com.thesis.visageapp.helpers.UrlHelper;
import com.thesis.visageapp.helpers.ValidateHelper;
import com.thesis.visageapp.helpers.VolleySingleton;
import com.thesis.visageapp.objects.User;

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
    private ProgressDialog progressDialog;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        this.progressDialog = new ProgressDialog(UserProfileActivity.this, R.style.AppTheme_Dark_Dialog);

        this.extras = getIntent().getExtras();
        if (this.extras != null) {
            this.user = RequestResponseHelper.processUserStringJSON(extras.getString(
                    RequestResponseHelper.USER_BUNDLE));
            this.prepareFormFromUser();
        }
        this.progressDialog.setIndeterminate(true);
        this.progressDialog.setMessage(this.getResources().getString(R.string.authorizing));


        this.changeCredentialsButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    changeBasicUserData();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                }
            }
        });

        this.changePasswordButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                changeUserPassword();
            }
        });

        this.deleteAccountButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteUserAccount();
            }
        });
    }

    private void deleteUserAccount() {
        Log.d(TAG, "Removing user account processed");
        this.progressDialog.show();
        this.setButtonsEnable(this.deleteAccountButton);
        this.setPasswordInputsEnableAndVisible(!this.onlyPreview, View.VISIBLE);
        this.progressDialog.hide();
    }

    private void changeUserPassword() {
        Log.d(TAG, "Changing password processed");
        this.progressDialog.show();
        this.setButtonsEnable(this.changePasswordButton);
        this.setPasswordInputsEnableAndVisible(!this.onlyPreview, View.VISIBLE);
        this.progressDialog.hide();
    }

    private void changeBasicUserData() throws JSONException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        Log.d(TAG, "Changing credentials processed");
        this.onlyPreview = !this.onlyPreview;
        if(!onlyPreview) {
            this.setPasswordInputsEnableAndVisible(!this.onlyPreview, View.VISIBLE);
            this.setButtonsEnable(!onlyPreview, onlyPreview, onlyPreview);
            this.setBasicTextInputsEnable(!onlyPreview);
        } else {
            if (!ValidateHelper.validateUpdateData(passwordText, rePasswordText, nameText, surnameText, emailText,
                    phoneNumberText, countryText, postCodeText, cityText, streetText, addressDetailsText, this)) {
                this.onUpdateFailed(RequestResponseHelper.RESPONSE_CODE_FAIL);
                return;
            }
            this.progressDialog.show();
            this.processUpdateUserData();
            this.progressDialog.hide();
        }
    }

    private void onUpdateFailed(String responseCode) {
        String errorToast = this.getResources().getString(R.string.update_client_data_failed);
        if (responseCode.equals(RequestResponseHelper.RESPONSE_CODE_ERROR_UPDATE_EMAIL_DUPLICATE)) {
            getResources().getString(R.string.status_code_update_email_duplicate);
        } else {
            if (responseCode.equals(RequestResponseHelper.RESPONSE_CODE_ERROR_UPDATE_INCORRECT_PASSWORD)) {
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
                        if (response.equals(RequestResponseHelper.RESPONSE_CODE_SUCCESS)) {
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
        setResult(RESULT_OK, null);
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtras(extras);
        intent.putExtra(RequestResponseHelper.USER_BUNDLE, new Gson().toJson(this.user));
        startActivity(intent);
        finish();
    }

    private void setButtonsEnable(Button pressedButton) {
        if (this.onlyPreview) {
            this.setButtonsEnable(true, true, true);
            this.restoreTextOfButtons();
            this.hintTextView.setText(this.getResources().getString(R.string.text_hint_default));
        } else {
            if (pressedButton == this.changeCredentialsButton) {
                this.setButtonsEnable(true, false, false);
                this.hintTextView.setText(this.getResources().getString(R.string.text_hint_change_credentials_insert_password));
                this.changeCredentialsButton.setText(this.getResources().getString(R.string.save));
            } else {
                if (pressedButton == this.changePasswordButton) {
                    this.setButtonsEnable(false, true, false);
                    this.hintTextView.setText(this.getResources().getString(R.string.text_hint_change_password));
                    this.changePasswordButton.setText(this.getResources().getString(R.string.save));
                } else {
                    this.setButtonsEnable(false, false, true);
                    this.hintTextView.setText(this.getResources().getString(R.string.text_hint_delete_acccount));
                    this.deleteAccountButton.setText(this.getResources().getString(R.string.save));
                }
            }
        }
    }

    private void restoreTextOfButtons() {
        this.changeCredentialsButton.setText(this.getResources().getString(R.string.changeCredentials));
        this.changePasswordButton.setText(this.getResources().getString(R.string.changePassword));
        this.deleteAccountButton.setText(this.getResources().getString(R.string.deleteAccount));
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
        user.setPassword(RequestResponseHelper.hashMessage(this.passwordText.getText().toString()));
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
