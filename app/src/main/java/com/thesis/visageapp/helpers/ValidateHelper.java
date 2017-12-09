package com.thesis.visageapp.helpers;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;

import com.thesis.visageapp.R;

public class ValidateHelper {
    public static boolean isValidLogin(String s) {
        return !s.isEmpty() && s.length() > 5 && s.length() < 17;
    }

    public static boolean isValidPassword(String s) {
        return !s.isEmpty() && s.length() > 7 && s.length() < 17;
    }

    public static boolean isValidatePesel(String s) {
        return !s.isEmpty() && s.matches("[0-9]{11}");
    }

    public static boolean isValidRePassword(String password, String rePassword) {
        return !password.isEmpty() && !rePassword.isEmpty() && rePassword.equals(password);
    }

    public static boolean isValidNameSurname(String s) {
        return !s.isEmpty() && Character.isUpperCase(s.charAt(0)) && s.length() > 2 && s.length() < 16;
    }

    public static boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return !TextUtils.isEmpty(phoneNumber) && android.util.Patterns.PHONE.matcher(phoneNumber).matches();
    }

    public static boolean isValidCountry(String country) {
        return !country.isEmpty() && Character.isUpperCase(country.charAt(0)) && country.length() > 2 && country.length() < 20;
        // TODO change countries to enum class, enable by dropdown
    }

    public static boolean isValidPostCode(String postCode) {
        return !postCode.matches("\\d{2}-\\d{3}");
    }

    public static boolean isValidCity(String s) {
        return !s.isEmpty() && Character.isUpperCase(s.charAt(0)) && s.length() > 1 && s.length() < 33;
    }

    public static boolean isValidStreet(String s) {
        return !s.isEmpty() && Character.isUpperCase(s.charAt(0)) && s.length() > 1 && s.length() < 33;
    }

    public static boolean isValidAddressDetails(String s) {
        return !s.isEmpty();
    }

    public static boolean validateUpdateData(EditText passwordText, EditText rePasswordText, EditText nameText,
                              EditText surnameText, EditText emailText, EditText phoneNumberText,
                              EditText countryText, EditText postCodeText, EditText cityText,
                              EditText streetText, EditText addressDetailsText, Context context) {
        boolean isValidate = true;
        if (!ValidateHelper.isValidPassword(passwordText.getText().toString())) {
            String p = passwordText.getText().toString();
            Log.d("ad",p);
            passwordText.setError(context.getString(R.string.passwordError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidRePassword(rePasswordText.getText().toString(),
                passwordText.getText().toString())) {
            String p = passwordText.getText().toString();
            Log.d("ad",p);

            rePasswordText.setError(context.getString(R.string.rePasswordError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidNameSurname(nameText.getText().toString())) {
            nameText.setError(context.getString(R.string.nameError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidNameSurname(surnameText.getText().toString())) {
            surnameText.setError(context.getString(R.string.nameError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidEmail(emailText.getText().toString())) {
            emailText.setError(context.getString(R.string.emailError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidPhoneNumber(phoneNumberText.getText().toString())) {
            phoneNumberText.setError(context.getString(R.string.phoneError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidCountry(countryText.getText().toString())) {
            countryText.setError(context.getString(R.string.countryError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidPostCode(postCodeText.getText().toString())) {
            postCodeText.setError(context.getString(R.string.postCodeError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidCity(cityText.getText().toString())) {
            cityText.setError(context.getString(R.string.cityError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidStreet(streetText.getText().toString())) {
            streetText.setError(context.getString(R.string.streetError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidAddressDetails(addressDetailsText.getText().toString())) {
            addressDetailsText.setError(context.getString(R.string.addressDetailsError));
            isValidate = false;
        }
        return isValidate;
    }

    public static boolean validateUserData(EditText peselText, EditText loginText, EditText passwordText,
                                           EditText rePasswordText, EditText nameText, EditText surnameText,
                                           EditText emailText, EditText phoneNumberText, EditText countryText,
                                           EditText postCodeText, EditText cityText, EditText streetText,
                                           EditText addressDetailsText, Context context) {
        boolean isValidate = true;
        if (!ValidateHelper.isValidatePesel(peselText.getText().toString())) {
            peselText.setError(context.getString(R.string.peselError));
            isValidate = false;
        }
        if (!ValidateHelper.isValidLogin(loginText.getText().toString())) {
            loginText.setError(context.getString(R.string.loginError));
            isValidate = false;
        }
        isValidate = isValidate && validateUpdateData(passwordText,rePasswordText,nameText,surnameText,
                emailText,phoneNumberText,countryText,postCodeText,cityText,streetText,addressDetailsText,context);
        return isValidate;
    }
}
