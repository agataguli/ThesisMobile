package com.thesis.visageapp.helpers;

import android.content.Context;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;

import com.thesis.visageapp.R;

public class ValidateHelper {
    public static boolean isValidLogin(String s) {
        return !s.isEmpty() && s.length() > 5 && s.length() < 17;
    }

    public static boolean isValidPassword(String s) {
        return !s.isEmpty() && s.length() > 7 && s.length() < 17;
    }

    public static boolean isValidPesel(String s) {
        return !s.isEmpty() && s.matches("[0-9]{11}");
    }

    public static boolean isValidRePassword(String password, String rePassword) {
        return !password.isEmpty() && !rePassword.isEmpty() && rePassword.equals(password);
    }

    public static boolean isValidNameSurname(String s) {
        return !s.isEmpty() && Character.isUpperCase(s.charAt(0)) && s.length() > 2 && s.length() < 16;
    }

    public static boolean isValidProductNameBrand(String s) {
        return s.isEmpty() || (!s.isEmpty() && s.length() > 1 && s.length() < 20);
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

    public static boolean validateSinglePassword(EditText passwordText, Context context) {
        if (!ValidateHelper.isValidPassword(passwordText.getText().toString())) {
            passwordText.setError(context.getString(R.string.passwordError));
            return false;
        }
        return true;
    }

    public static boolean validatePasswords(EditText passwordText, EditText rePasswordText, Context context) {
        boolean isValid = true;
        if (!ValidateHelper.isValidRePassword(rePasswordText.getText().toString(),
                passwordText.getText().toString())) {
            rePasswordText.setError(context.getString(R.string.rePasswordError));
            isValid = false;
        }
        return isValid && validateSinglePassword(passwordText, context);
    }

    public static boolean validateUpdateData(EditText passwordText, EditText rePasswordText, EditText nameText,
                                             EditText surnameText, EditText emailText, EditText phoneNumberText,
                                             EditText countryText, EditText postCodeText, EditText cityText,
                                             EditText streetText, EditText addressDetailsText, Context context) {
        boolean isValid = true;
        if (!ValidateHelper.isValidNameSurname(nameText.getText().toString())) {
            nameText.setError(context.getString(R.string.nameError));
            isValid = false;
        }
        if (!ValidateHelper.isValidNameSurname(surnameText.getText().toString())) {
            surnameText.setError(context.getString(R.string.nameError));
            isValid = false;
        }
        if (!ValidateHelper.isValidEmail(emailText.getText().toString())) {
            emailText.setError(context.getString(R.string.emailError));
            isValid = false;
        }
        if (!ValidateHelper.isValidPhoneNumber(phoneNumberText.getText().toString())) {
            phoneNumberText.setError(context.getString(R.string.phoneError));
            isValid = false;
        }
        if (!ValidateHelper.isValidCountry(countryText.getText().toString())) {
            countryText.setError(context.getString(R.string.countryError));
            isValid = false;
        }
        if (!ValidateHelper.isValidPostCode(postCodeText.getText().toString())) {
            postCodeText.setError(context.getString(R.string.postCodeError));
            isValid = false;
        }
        if (!ValidateHelper.isValidCity(cityText.getText().toString())) {
            cityText.setError(context.getString(R.string.cityError));
            isValid = false;
        }
        if (!ValidateHelper.isValidStreet(streetText.getText().toString())) {
            streetText.setError(context.getString(R.string.streetError));
            isValid = false;
        }
        if (!ValidateHelper.isValidAddressDetails(addressDetailsText.getText().toString())) {
            addressDetailsText.setError(context.getString(R.string.addressDetailsError));
            isValid = false;
        }
        return isValid && validatePasswords(passwordText, rePasswordText, context);
    }

    public static boolean validateUserData(EditText loginText, EditText passwordText,
                                           EditText rePasswordText, EditText nameText, EditText surnameText,
                                           EditText emailText, EditText phoneNumberText, EditText countryText,
                                           EditText postCodeText, EditText cityText, EditText streetText,
                                           EditText addressDetailsText, Context context) {
        boolean isValid = true;
        if (!ValidateHelper.isValidLogin(loginText.getText().toString())) {
            loginText.setError(context.getString(R.string.loginError));
            isValid = false;
        }
        return isValid && validateUpdateData(passwordText, rePasswordText, nameText, surnameText,
                emailText, phoneNumberText, countryText, postCodeText, cityText, streetText, addressDetailsText, context);
    }

    public static boolean validateProductFilterData(EditText productName, EditText productBrand, 
                                                    EditText productPriceMin, EditText productPriceMax,
                                                    CheckBox productCatBrushes, CheckBox productCatFurniture,
                                                    CheckBox productCatAccessories, Context context) {
        boolean isValid = true;
        if (!ValidateHelper.isValidProductNameBrand(productName.getText().toString())) {
            productName.setError(context.getString(R.string.productNameBrandError));
            isValid = false;
        }
        if (!ValidateHelper.isValidProductNameBrand(productBrand.getText().toString())) {
            productBrand.setError(context.getString(R.string.productNameBrandError));
            isValid = false;
        }
        if(!ValidateHelper.isValidProductPrice(productPriceMin.getText().toString())) {
            productPriceMin.setError(context.getString(R.string.productPriceErrorFilter));
            isValid = false;
        }
        if(!ValidateHelper.isValidProductPrice(productPriceMax.getText().toString())) {
            productPriceMax.setError(context.getString(R.string.productPriceErrorFilter));
            isValid = false;
        }
        if(!ValidateHelper.isValidProductCategoryFilter(productCatBrushes, productCatAccessories, productCatFurniture)) {
            productCatBrushes.setError(context.getString(R.string.productCatFilterError));
            isValid = false;
        }

        return isValid;
    }

    private static boolean isValidProductCategoryFilter(CheckBox c1, CheckBox c2, CheckBox c3) {
        return c1.isChecked() || c2.isChecked() || c3.isChecked();
    }

    private static boolean isValidProductPrice(String s) {
        return s.isEmpty() || s.matches("\\d+");
    }
}
