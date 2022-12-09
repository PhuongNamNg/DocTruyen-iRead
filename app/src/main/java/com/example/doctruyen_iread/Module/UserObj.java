package com.example.doctruyen_iread.Module;

import java.util.ArrayList;

public class UserObj {
    private String userID;
    private String userEmail;
    private String userName;
    private Integer userAge;
    private String usersFavorite;
    private Integer userToken;
    private Integer userFollowed;
    private ArrayList<String> userFollow;

    public UserObj() {}


    public UserObj(String userID, String userEmail, String userName, Integer userAge, String usersFavorite, Integer userToken, Integer userFollowed, ArrayList<String> userFollow) {
        this.userID = userID;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userAge = userAge;
        this.usersFavorite = usersFavorite;
        this.userToken = userToken;
        this.userFollowed = userFollowed;
        this.userFollow = userFollow;
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

    public ArrayList<String> getUserFollow() {
        return userFollow;
    }

    public void setUserFollow(ArrayList<String> userFollow) {
        this.userFollow = userFollow;
    }

    public Integer getUserFollowed() {
        return userFollowed;
    }

    public void setUserFollowed(Integer userFollowed) {
        this.userFollowed = userFollowed;
    }
}
