package com.example.myapplication.ObjectManager;

import java.io.Serializable;

public class tangObj implements Serializable {
    //maTang
    //tenTang
    //soPhong
    private String maTang, tenTang, soPhong;

    public tangObj(String maTang, String tenTang, String soPhong) {
        this.maTang = maTang;
        this.tenTang = tenTang;
        this.soPhong = soPhong;
    }

    public tangObj() {
    }

    public String getMaTang() {
        return maTang;
    }

    public void setMaTang(String maTang) {
        this.maTang = maTang;
    }

    public String getTenTang() {
        return tenTang;
    }

    public void setTenTang(String tenTang) {
        this.tenTang = tenTang;
    }

    public String getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(String soPhong) {
        this.soPhong = soPhong;
    }
}
