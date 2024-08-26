package com.example.myapplication.ObjectManager;

public class khachHangObj {
    //soCMT
    //tenKh
    //ngaySinh
    //soDienThoai
    private String soCMT, tenKh, ngaySinh, soDienThoai;

    public khachHangObj(String soCMT, String tenKh, String ngaySinh, String soDienThoai) {
        this.soCMT = soCMT;
        this.tenKh = tenKh;
        this.ngaySinh = ngaySinh;
        this.soDienThoai = soDienThoai;
    }

    public khachHangObj() {
    }

    public String getSoCMT() {
        return soCMT;
    }

    public void setSoCMT(String soCMT) {
        this.soCMT = soCMT;
    }

    public String getTenKh() {
        return tenKh;
    }

    public void setTenKh(String tenKh) {
        this.tenKh = tenKh;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
}
