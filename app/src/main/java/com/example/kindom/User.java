package com.example.kindom;

public class User {

    public static final String USER_GROUP_ADMIN = "admin";
    public static final String USER_GROUP_USER = "user";

    private String name;
    private String userGroup;
    private int postalCode;
    private String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String userGroup, int postalCode, String email) {
        this.name = name;
        this.userGroup = userGroup;
        this.postalCode = postalCode;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        if (userGroup == User.USER_GROUP_ADMIN || userGroup == User.USER_GROUP_USER) {
            this.userGroup = userGroup;
        }
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}