package com.example.myapplication.ObjectManager;

import java.io.Serializable;

public class phongObj implements Serializable {
    //maPhong
    //maTang
    //tenPhong
    //maLoai
    //trangThai
    //soPhong
    private String maPhong,maTang,maLoai,tenPhong,trangThai,soPhong;

    public phongObj() {
    }

    public phongObj(String maPhong, String maTang, String maLoai, String tenPhong, String trangThai, String soPhong) {
        this.maPhong = maPhong;
        this.maTang = maTang;
        this.maLoai = maLoai;
        this.tenPhong = tenPhong;
        this.trangThai = trangThai;
        this.soPhong = soPhong;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getMaTang() {
        return maTang;
    }

    public void setMaTang(String maTang) {
        this.maTang = maTang;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(String soPhong) {
        this.soPhong = soPhong;
    }
}
