package com.example.movieonline_api.model;

public class user {
    private String username;
    private String email;
    private String password;
    private String phone;
    private String gender;
    private String avatar;

    // Constructor
    public user(String username, String email, String password, String phone, String gender, String avatar) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.avatar = avatar;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
