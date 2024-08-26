package com.example.myapplication.ObjectManager;

public class hoaDonObj {
    //maHoaDon
    //maDatPhong
    //trangThai
    //tongTien
    //ngayThang
    private String maHoaDon, maDatPhong, trangThai, tongTien, ngayThang,maChiTietDV;

    public hoaDonObj(String maHoaDon, String maDatPhong
            , String trangThai, String tongTien, String ngayThang, String maChiTietDV) {
        this.maHoaDon = maHoaDon;
        this.maDatPhong = maDatPhong;
        this.trangThai = trangThai;
        this.tongTien = tongTien;
        this.ngayThang = ngayThang;
        this.maChiTietDV = maChiTietDV;
    }

    public String getMaChiTietDV() {
        return maChiTietDV;
    }

    public void setMaChiTietDV(String maChiTietDV) {
        this.maChiTietDV = maChiTietDV;
    }

    public hoaDonObj() {
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaDatPhong() {
        return maDatPhong;
    }

    public void setMaDatPhong(String maDatPhong) {
        this.maDatPhong = maDatPhong;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    public String getNgayThang() {
        return ngayThang;
    }

    public void setNgayThang(String ngayThang) {
        this.ngayThang = ngayThang;
    }
}
