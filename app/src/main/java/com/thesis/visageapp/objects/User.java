package com.thesis.visageapp.objects;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
    private String login;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String country;
    private String postCode;
    private String city;
    private String street;
    private String addressDetails;
    private boolean active;

    public User() {

    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(String addressDetails) {
        this.addressDetails = addressDetails;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public JSONObject createJsonUser() throws JSONException {
        JSONObject json = new JSONObject();

        JSONObject userJson = new JSONObject();
        userJson.put("login",login);
        userJson.put("password",password);
        userJson.put("name",name);
        userJson.put("email",email);
        userJson.put("phoneNumber",phoneNumber);
        userJson.put("country",country);
        userJson.put("postCode",postCode);
        userJson.put("city",city);
        userJson.put("street",street);
        userJson.put("addressDetails",addressDetails);

        return userJson;
    }

}
