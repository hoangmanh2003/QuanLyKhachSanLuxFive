package com.example.myapplication.DbManager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.ObjectManager.hoaDonObj;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class hoaDonDao {
    SQLiteDatabase db;

    public hoaDonDao(Context context) {
        createDataBase mCreateDataBase = new createDataBase(context);
        db =  mCreateDataBase.getWritableDatabase();
    }
    // //maHoaDon
    //    //maDatPhong
    //    //trangThai
    //    //tongTien
    //    //ngayThang
    //    //maChiTietDV
    @SuppressLint("Range")
    public List<hoaDonObj> get(String sql, String...agrs ){
        List<hoaDonObj> mList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, agrs);
        while (cursor.moveToNext()){
            hoaDonObj hoaDonObj = new hoaDonObj();
            hoaDonObj.setMaHoaDon(cursor.getString(cursor.getColumnIndex("maHoaDon")));
            hoaDonObj.setTongTien(cursor.getString(cursor.getColumnIndex("tongTien")));
            hoaDonObj.setNgayThang(cursor.getString(cursor.getColumnIndex("ngayThang")));
            hoaDonObj.setMaDatPhong(cursor.getString(cursor.getColumnIndex("maDatPhong")));
            hoaDonObj.setMaChiTietDV(cursor.getString(cursor.getColumnIndex("maChiTietDV")));
            mList.add(hoaDonObj);
        }
        return mList;
    }
    public List<hoaDonObj> getAll(){
        String sql = "SELECT * FROM hoaDonThanhToan";
        return get(sql);
    }
    public Long inserHoaDonThanhToan(hoaDonObj items){
        ContentValues values = new ContentValues();
        values.put("maHoaDon",items.getMaHoaDon());
        values.put("tongTien",items.getTongTien());
        values.put("ngayThang",items.getNgayThang());
        values.put("maDatPhong",items.getMaDatPhong());
        values.put("maChiTietDV",items.getMaChiTietDV());
        return db.insert("hoaDonThanhToan",null,values);

    }
    public int updateHoaDonThanhToan(hoaDonObj items){
        ContentValues values = new ContentValues();
        values.put("maHoaDon",items.getMaHoaDon());
        values.put("tongTien",items.getTongTien());
        values.put("ngayThang",items.getNgayThang());
        values.put("maDatPhong",items.getMaDatPhong());
        values.put("maChiTietDV",items.getMaChiTietDV());
        return db.update("hoaDonThanhToan",values,"maHoaDon = ?"
                ,new String[]{items.getMaHoaDon()});

    }
    public int deleteHoaDonThanhToan(String maHoaDon){
        return db.delete("hoaDonThanhToan","maHoaDon = ?", new String[]{maHoaDon});
    }
    public hoaDonObj getByMaHoaDon(String maHoaDon){
        String sql = "SELECT * FROM hoaDonThanhToan WHERE maHoaDon  = ?";
        List<hoaDonObj> list = get(sql,maHoaDon);
        return list.get(0);
    }
    public hoaDonObj getByMaChiTietDV(String maChiTietDV){
        String sql = "SELECT * FROM hoaDonThanhToan WHERE maChiTietDV  = ?";
        List<hoaDonObj> list = get(sql,maChiTietDV);
        return list.get(0);
    }
    public hoaDonObj getByMaDatPhong(String maDatPhong){
        String sql = "SELECT * FROM hoaDonThanhToan WHERE maDatPhong  = ?";
        List<hoaDonObj> list = get(sql,maDatPhong);
        return list.get(0);
    }
    public List<hoaDonObj> truyVanTheoKhoangNgay(String tuNgay, String denNgay){
        String sql = "SELECT * FROM hoaDonThanhToan WHERE ngayThang  BETWEEN ? AND ? ";
        return get(sql,new String[]{tuNgay,denNgay});
    }
    public List<hoaDonObj> truyVanTheoTuNgay(String tuNgay){
        String sql = "SELECT * FROM hoaDonThanhToan WHERE ngayThang  BETWEEN ? AND ? ";
        return get(sql,new String[]{tuNgay,layNgayHienTai()});
    }
    public List<hoaDonObj> truyVanTheoDenNgay(String denNgay){
        String sql = "SELECT * FROM hoaDonThanhToan WHERE ngayThang  BETWEEN ? AND ? ";
        return get(sql,new String[]{layNgayHienTai(),denNgay});
    }
    @SuppressLint("Range")
    public String truyVanTongTienTheoKhoanNgay (String tuNgay, String denNgay){
        String result = "0";
        String sql = "SELECT SUM(tongTien) as tongTruyVan FROM hoaDonThanhToan WHERE ngayThang  BETWEEN ? AND ? ";
        Cursor cursor = db.rawQuery(sql, new String[]{tuNgay, denNgay});
        while (cursor.moveToNext()){
            result = cursor.getString(cursor.getColumnIndex("tongTruyVan"));
        }
       return result;
    }
    @SuppressLint("Range")
    public String truyVanTongTien (){
        String result = "0";
        String sql = "SELECT SUM(tongTien) as tongTruyVan FROM hoaDonThanhToan ";
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()){
            result = cursor.getString(cursor.getColumnIndex("tongTruyVan"));
        }
        return result;
    }
    @SuppressLint("Range")
    public String truyVanTongTienTuNgay(String tuNgay){
        String result = "0";
        String sql = "SELECT SUM(tongTien) as tongTruyVan FROM hoaDonThanhToan WHERE ngayThang  BETWEEN ? AND ? ";
        Cursor cursor = db.rawQuery(sql, new String[]{layNgayHienTai(),tuNgay});
        while (cursor.moveToNext()){
            result = cursor.getString(cursor.getColumnIndex("tongTruyVan"));
        }
        return result;
    }
    @SuppressLint("Range")
    public String truyVanTongTienDenNgay(String tuNgay){
        String result = "0";
        String sql = "SELECT SUM(tongTien) as tongTruyVan FROM hoaDonThanhToan WHERE ngayThang  BETWEEN ? AND ? ";
        Cursor cursor = db.rawQuery(sql, new String[]{tuNgay,layNgayHienTai()});
        while (cursor.moveToNext()){
            result = cursor.getString(cursor.getColumnIndex("tongTruyVan"));
        }
        return result;
    }
    private String layNgayHienTai(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String monthString = month+"";
        String dayString = day+"";
        if (month < 10){
            monthString = "0"+month;
        }
        if (day<10){
            dayString = "0"+day;
        }
        return year+"-"+monthString+"-"+dayString;
    }
}
