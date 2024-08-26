package com.example.myapplication.ObjectManager;

public class dichVuObj {
    //maDichVu
    //tenDichVu
    //giaDichVu
    //maChiTietDV
    //soLuong
    private String maDichVu, tenDichVu;

    public dichVuObj(String maDichVu, String tenDichVu) {
        this.maDichVu = maDichVu;
        this.tenDichVu = tenDichVu;
    }

    public dichVuObj() {
    }

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }
}
