package com.example.myapplication.ObjectManager;

import static android.util.Log.d;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class datPhongObj {
    private String maDatPhong, maKh,maNhanVien, maPhong,checkIn,
            gioVao,ghiChu,soGioDat, giaTien,gioRa,ngayRa
            ,maChiTietDV, tongTien, trangThai;

    public String checkTimeOut(){
        String timeIn = gioVao;
        String thoiGianDat = soGioDat;
        LocalTime t1 = LocalTime.parse(timeIn, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalTime t2 = LocalTime.parse(thoiGianDat, DateTimeFormatter.ofPattern("HH:mm:ss"));

        LocalTime sum = t1.plusHours(t2.getHour())
                .plusMinutes(t2.getMinute())
                .plusSeconds(t2.getSecond());
        String formattedTime = sum.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return formattedTime;

    }
    public String checkDayOut (){
        String dateString =  checkIn +" "+ gioVao ;
        String thoiGianDat = soGioDat;
        //
        LocalDateTime dateTime = LocalDateTime.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        LocalTime time = LocalTime.parse(thoiGianDat, DateTimeFormatter.ofPattern("HH:mm:ss"));
        LocalDateTime newDateTime = dateTime.plusHours(time.getHour()).plusMinutes(time.getMinute()).plusSeconds(time.getSecond());
        String newDateTimeString = newDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")); // Định dạng chuỗi kết quả
        return newDateTimeString;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public datPhongObj(String maDatPhong, String maKh, String maNhanVien,
                       String maPhong, String checkIn, String gioVao,
                       String ghiChu, String soGioDat,
                       String giaTien, String gioRa, String
                               ngayRa, String maChiTietDV , String tongTien) {
        this.maDatPhong = maDatPhong;
        this.maKh = maKh;
        this.maNhanVien = maNhanVien;
        this.maPhong = maPhong;
        this.checkIn = checkIn;
        this.gioVao = gioVao;
        this.ghiChu = ghiChu;
        this.soGioDat = soGioDat;
        this.giaTien = giaTien;
        this.gioRa = gioRa;
        this.ngayRa = ngayRa;
        this.maChiTietDV = maChiTietDV;
        this.tongTien = tongTien;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    public String getMaChiTietDV() {
        return maChiTietDV;
    }

    public void setMaChiTietDV(String maChiTietDV) {
        this.maChiTietDV = maChiTietDV;
    }

    public String getMaDatPhong() {
        return maDatPhong;
    }

    public void setMaDatPhong(String maDatPhong) {
        this.maDatPhong = maDatPhong;
    }

    public String getMaKh() {
        return maKh;
    }

    public void setMaKh(String maKh) {
        this.maKh = maKh;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getGioVao() {
        return gioVao;
    }

    public void setGioVao(String gioVao) {
        this.gioVao = gioVao;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getSoGioDat() {
        return soGioDat;
    }

    public void setSoGioDat(String soGioDat) {
        this.soGioDat = soGioDat;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    public String getGioRa() {
        return gioRa;
    }

    public void setGioRa(String gioRa) {
        this.gioRa = gioRa;
    }

    public String getNgayRa() {
        return ngayRa;
    }

    public void setNgayRa(String ngayRa) {
        this.ngayRa = ngayRa;
    }

    public datPhongObj() {
    }
}
