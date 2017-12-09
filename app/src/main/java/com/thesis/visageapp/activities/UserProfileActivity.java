package com.thesis.visageapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

import com.thesis.visageapp.R;
import com.thesis.visageapp.helpers.RequestResponseHelper;
import com.thesis.visageapp.objects.User;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UserProfileActivity extends AppCompatActivity {
    private User user = new User();

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);

        if(savedInstanceState != null) {
            user = RequestResponseHelper.processUserStringJSON(savedInstanceState.getString
                    (RequestResponseHelper.USER_BUNDLE));
            this.prepareFormFromUser();
        }
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
}
