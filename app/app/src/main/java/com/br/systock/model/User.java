package com.br.systock.model;

public class User {

    private String email;
    private String name;
    private String password;
    private String confirmpassword;
    private String street;
    private String neighbor;
    private String city;
    private String zipcode;
    private String state;
    private String number;
    private String token;

    public User(String name, String email, String password, String confirmpassword, String street, String neighbor, String city, String zipcode, String number, String token) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.confirmpassword = confirmpassword;
        this.street = street;
        this.neighbor = neighbor;
        this.city = city;
        this.zipcode = zipcode;
        this.number = number;
        this.token = token;


    }

    public String getEmail() {
        return this.email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword; }

    public String getStreet() {
        return this.street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNeighbor() {
        return this.neighbor;
    }

    public void setneighbor(String neighbor) {
        this.neighbor = neighbor;
    }

    public String getcity() {
        return this.city;
    }


    public void setcity(String city) {
        this.city = city;
    }

    public String getzipcode() {
        return this.zipcode;
    }


    public void setzipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getToken() {
        return this.token;
    }


    public void setToken(String token) {
        this.token = token;
    }}



