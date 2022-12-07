package com.example.doctruyen_iread.Module;

import java.util.ArrayList;

public class UserObj {
    private String userID;
    private String userEmail;
    private String userName;
    private Integer userAge;
    private String usersFavorite;
    private Integer userToken;

    public UserObj() {}


    public UserObj(String userID, String userEmail, String userName, Integer userAge, String usersFavorite, Integer userToken) {
        this.userID = userID;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userAge = userAge;
        this.usersFavorite = usersFavorite;
        this.userToken = userToken;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public String getUsersFavorite() {
        return usersFavorite;
    }

    public void setUsersFavorite(String usersFavorite) {
        this.usersFavorite = usersFavorite;
    }

    public Integer getUserToken() {
        return userToken;
    }

    public void setUserToken(Integer userToken) {
        this.userToken = userToken;
    }

}
