package com.thesis.visageapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.thesis.visageapp.R;
import com.thesis.visageapp.helpers.RequestResponseHelper;
import com.thesis.visageapp.objects.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserProfileActivity extends AppCompatActivity {
    private static final String TAG = "UserProfileActivity";
    private User user = new User();
    private Bundle extras = new Bundle();
    private boolean onlyPreview = true;
    private final ProgressDialog progressDialog = new ProgressDialog(UserProfileActivity.this, R.style.AppTheme_Dark_Dialog);

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
                changeBasicUserData();
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
        this.setButtonsEnable(this.deleteAccountButton);
        this.setPasswordInputsVisibleAndEnable(!this.onlyPreview);
    }

    private void changeUserPassword() {
        Log.d(TAG, "Changing password processed");
        this.setButtonsEnable(this.changePasswordButton);
        this.setPasswordInputsVisibleAndEnable(!this.onlyPreview);
    }

    private void changeBasicUserData() {
        Log.d(TAG, "Changing credentials processed");
        this.setBasicTextInputsEnable(this.onlyPreview);
        this.setButtonsEnable(this.changeCredentialsButton);
        this.setPasswordInputsVisibleAndEnable(!this.onlyPreview);
    }

    private void setButtonsEnable(Button pressedButton) {
        this.onlyPreview = !this.onlyPreview;
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

    private void setPasswordInputsVisibleAndEnable(boolean enable) {
        int visible = View.GONE;
        if(enable) visible = View.VISIBLE;
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

    //ValidateHelper.validateUserData(peselText, loginText, passwordText, rePasswordText, nameText, surnameText,
    //emailText, phoneNumberText, countryText, postCodeText, cityText, streetText, addressDetailsText, this))

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
}
