package com.example.doctruyen_iread.Module;

import java.util.ArrayList;

public class TheLoai {
    private String theloaiId;
    private String theloaiTen;
    private String theloaiMieuTa;

    public TheLoai () {}

    public TheLoai(String theloaiId, String theloaiTen, String theloaiMieuTa) {
        this.theloaiId = theloaiId;
        this.theloaiTen = theloaiTen;
        this.theloaiMieuTa = theloaiMieuTa;

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

}
