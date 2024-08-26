package com.example.myapplication.ObjectManager;

public class loaiPhongObj {
    //maLoai
    //tenLoaiPhong
    //giaThue
    private String maLoai, tenLoaiPhong, giaThue;

    public loaiPhongObj() {
    }
    public loaiPhongObj(String maLoai, String tenLoaiPhong, String giaThue) {
        this.maLoai = maLoai;
        this.tenLoaiPhong = tenLoaiPhong;
        this.giaThue = giaThue;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoaiPhong() {
        return tenLoaiPhong;
    }

    public void setTenLoaiPhong(String tenLoaiPhong) {
        this.tenLoaiPhong = tenLoaiPhong;
    }

    public String getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(String giaThue) {
        this.giaThue = giaThue;
    }
}
