package com.example.doctruyen_iread.Module;

import java.util.ArrayList;

public class TheLoai {
    private String theloaiId;
    private String theloaiTen;
    private String theloaiMieuTa;
    private ArrayList<String> storyId;

    public TheLoai () {}

    public TheLoai(String theloaiId, String theloaiTen, String theloaiMieuTa, ArrayList<String> storyId) {
        this.theloaiId = theloaiId;
        this.theloaiTen = theloaiTen;
        this.theloaiMieuTa = theloaiMieuTa;
        this.storyId = storyId;
    }

    public String getTheloaiId() {
        return theloaiId;
    }

    public void setTheloaiId(String theloaiId) {
        this.theloaiId = theloaiId;
    }

    public String getTheloaiTen() {
        return theloaiTen;
    }

    public void setTheloaiTen(String theloaiTen) {
        this.theloaiTen = theloaiTen;
    }

    public String getTheloaiMieuTa() {
        return theloaiMieuTa;
    }

    public void setTheloaiMieuTa(String theloaiMieuTa) {
        this.theloaiMieuTa = theloaiMieuTa;
    }

    public ArrayList<String> getStoryId() {
        return storyId;
    }

    public void setStoryId(ArrayList<String> storyId) {
        this.storyId = storyId;
    }
}
