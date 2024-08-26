package com.example.myapplication.ObjectManager;

public class nhanVienObj {
    //maNhanVien
    //tenNhanVien
    //anhNhanVien
    //soDienThoai
    //matKhau
    private String maNhanVien,tenNhanVien,anhNhanVien,soDienThoai,matKhau;

    public nhanVienObj(String maNhanVien,
                       String tenNhanVien, String anhNhanVien, String soDienThoai, String matKhau) {
        this.maNhanVien = maNhanVien;
        this.tenNhanVien = tenNhanVien;
        this.anhNhanVien = anhNhanVien;
        this.soDienThoai = soDienThoai;
        this.matKhau = matKhau;
    }

    public nhanVienObj() {
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getAnhNhanVien() {
        return anhNhanVien;
    }

    public void setAnhNhanVien(String anhNhanVien) {
        this.anhNhanVien = anhNhanVien;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }
}
