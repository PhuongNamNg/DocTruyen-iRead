package com.example.doctruyen_iread.Module;

import java.util.ArrayList;

public class Favorite {
    private  String storyID;
    private String favoriteName;
    private ArrayList<String> favListStoryID;

    @Override
    public String toString() {
        return "Favorite{" +
                "storyID='" + storyID + '\'' +
                ", favoriteName='" + favoriteName + '\'' +
                ", favListStoryID=" + favListStoryID +
                '}';
    }

    public Favorite() {
    }

    public Favorite(String favoriteID, String favoriteName, ArrayList<String> favListStoryID) {
        this.storyID = favoriteID;
        this.favoriteName = favoriteName;
        this.favListStoryID = favListStoryID;
    }

    public String getStoryID() {
        return storyID;
    }

    public void setStoryID(String favoriteID) {
        this.storyID = favoriteID;
    }

    public String getFavoriteName() {
        return favoriteName;
    }

    public void setFavoriteName(String favoriteName) {
        this.favoriteName = favoriteName;
    }

    public ArrayList<String> getFavListStoryID() {
        return favListStoryID;
    }

    public void setFavListStoryID(ArrayList<String> favListStoryID) {
        this.favListStoryID = favListStoryID;
    }
}

