package com.thesis.visageapp;

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

import com.thesis.visageapp.objects.User;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private User user;

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
    EditText addressDetails;
    @Bind(R.id.link_login_r)
    TextView loginLinkedText;
    @Bind(R.id.button_signup_r)
    Button signUpButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        user = new User();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        this.signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        this.loginLinkedText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup processed");

       /* if (!this.validateRegisterData()) {
            onSignupFailed();
            return;
        }*/

        this.signUpButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(this.getResources().getString(R.string.signingUp));
        progressDialog.show();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        createNewUser();
                        String postMessage = "";
                        try {

                            JSONObject userJson = user.createJsonUser();
                            postMessage = userJson.toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                         new SendDeviceDetails().execute(postMessage);

                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }




    public void onSignupSuccess() {
        signUpButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void createNewUser() {
        user.setName(nameText.getText().toString());
        user.setLogin(loginText.getText().toString());
        user.setPassword(passwordText.getText().toString());
        user.setSurname(surnameText.getText().toString());
        user.setEmail(emailText.getText().toString());
        user.setPhoneNumber(phoneNumberText.getText().toString());
        user.setCountry(countryText.getText().toString());
        user.setPostCode(postCodeText.getText().toString());
        user.setCity(cityText.getText().toString());
        user.setStreet(streetText.getText().toString());
        user.setAddressDetails(addressDetails.getText().toString());
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), this.getResources().getString(R.string.signUpFailed), Toast.LENGTH_SHORT).show();
        signUpButton.setEnabled(true);
    }

    public boolean validateRegisterData() {
        boolean isValidate = true;
        if (!ValidateHelper.isValidatePesel(this.peselText.getText().toString())) {
            this.peselText.setError(this.getResources().getString(R.string.peselError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidLogin(this.loginText.getText().toString())) {
            this.loginText.setError(this.getResources().getString(R.string.loginError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidPassword(this.passwordText.getText().toString())) {
            this.passwordText.setError(this.getResources().getString(R.string.passwordError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidRePassword(this.passwordText.getText().toString(),
                this.rePasswordText.getText().toString())) {
            this.rePasswordText.setError(this.getResources().getString(R.string.rePasswordError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidNameSurname(this.nameText.getText().toString())) {
            this.nameText.setError(this.getResources().getString(R.string.nameError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidNameSurname(this.nameText.getText().toString())) {
            this.surnameText.setError(this.getResources().getString(R.string.nameError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidEmail(this.nameText.getText().toString())) {
            this.emailText.setError(this.getResources().getString(R.string.emailError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidPhoneNumber(this.nameText.getText().toString())) {
            this.phoneNumberText.setError(this.getResources().getString(R.string.phoneError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidCountry(this.nameText.getText().toString())) {
            this.countryText.setError(this.getResources().getString(R.string.countryError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidPostCode(this.nameText.getText().toString())) {
            this.postCodeText.setError(this.getResources().getString(R.string.postCodeError));
            isValidate = false;
        }
        return isValidate;
    }
}