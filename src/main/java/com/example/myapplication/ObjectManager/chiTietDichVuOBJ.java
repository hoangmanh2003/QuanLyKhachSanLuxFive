package com.example.myapplication.ObjectManager;

public class chiTietDichVuOBJ {
    //maChiTietDV
    //maDichVu
    //maDatPhong
    //giaTien
    private String maChiTietDV;
    private String maDichVu;
    private String maDatPhong;
    private String soLuong;
    private String giaTien;
    private String tongTien;

    public chiTietDichVuOBJ(String maChiTietDV, String maDichVu, String maDatPhong, String soLuong, String giaTien, String tongTien) {
        this.maChiTietDV = maChiTietDV;
        this.maDichVu = maDichVu;
        this.maDatPhong = maDatPhong;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.tongTien = tongTien;

    }
    public double tinhTongTien(){
        return Double.parseDouble(soLuong) * Double.parseDouble(giaTien);
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    public chiTietDichVuOBJ() {
    }

    public String getMaChiTietDV() {
        return maChiTietDV;
    }

    public void setMaChiTietDV(String maChiTietDV) {
        this.maChiTietDV = maChiTietDV;
    }

    public String getMaDichVu() {
        return maDichVu;
    }

    public void setMaDichVu(String maDichVu) {
        this.maDichVu = maDichVu;
    }

    public String getMaDatPhong() {
        return maDatPhong;
    }

    public void setMaDatPhong(String maDatPhong) {
        this.maDatPhong = maDatPhong;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }
    public double tongTien(){
        double tongTien = Double.parseDouble(soLuong)*Double.parseDouble(giaTien);
        return tongTien;
    }

}
