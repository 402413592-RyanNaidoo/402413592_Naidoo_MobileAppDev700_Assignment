package com.example.ryannaidoo_mobileappdevelopment.smartpantrymanager;

public class User {
    public long id;
    public String username;
    public String phone;
    public String password;
    public User() {}
    public User(long id, String username, String phone, String password) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.password = password;
    }

}
