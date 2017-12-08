package com.thesis.visageapp.objects;

public class User {
    private String userId;
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
    private boolean active = true;

    public User() {
    }

    public User(String userId, String login, String password, String name, String surname, String email, String phoneNumber, String country, String postCode, String city, String street, String addressDetails) {
        this.userId = userId;
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.country = country;
        this.postCode = postCode;
        this.city = city;
        this.street = street;
        this.addressDetails = addressDetails;
        this.active = true;
    }

    public User(String userId, String login, String password, String name, String surname, String email, String phoneNumber, String country, String postCode, String city, String street, String addressDetails, boolean active) {
        this(userId, login, password, name, surname, email, phoneNumber, country, postCode, city, street, addressDetails);
        this.active = active;
    }

    public String getUserId() {
        return userId;
    }


    public void setUserId(String pesel) {
        this.userId = pesel;
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
}
