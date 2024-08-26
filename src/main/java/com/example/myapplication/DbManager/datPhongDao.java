package com.example.myapplication.DbManager;

import static android.util.Log.d;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.ObjectManager.datPhongObj;
import com.example.myapplication.ObjectManager.phongObj;

import java.util.ArrayList;
import java.util.List;

public class datPhongDao {
    private SQLiteDatabase db;
    private phongDao mPhongDao;

    public datPhongDao(Context context) {
        createDataBase mCreateDataBase = new createDataBase(context);
        db = mCreateDataBase.getWritableDatabase();
        mPhongDao = new phongDao(context);
    }

    @SuppressLint("Range")
    public List<datPhongObj> get(String sql, String...agrs){
        List<datPhongObj> mList = new ArrayList<>();
        Cursor mCursor = db.rawQuery(sql,agrs);
        while (mCursor.moveToNext()){
            datPhongObj items = new datPhongObj();
            items.setMaDatPhong(mCursor.getString(mCursor.getColumnIndex("maDatPhong")));
            items.setMaKh(mCursor.getString(mCursor.getColumnIndex("maKh")));
            items.setMaNhanVien(mCursor.getString(mCursor.getColumnIndex("maNhanVien")));
            items.setMaPhong(mCursor.getString(mCursor.getColumnIndex("maPhong")));
            items.setCheckIn(mCursor.getString(mCursor.getColumnIndex("checkIn")));
            items.setGioVao(mCursor.getString(mCursor.getColumnIndex("gioVao")));
            items.setGiaTien(mCursor.getString(mCursor.getColumnIndex("giaThue")));
            items.setGioRa(mCursor.getString(mCursor.getColumnIndex("gioRa")));
            items.setNgayRa(mCursor.getString(mCursor.getColumnIndex("checkOut")));
            items.setSoGioDat(mCursor.getString(mCursor.getColumnIndex("soGioThue")));
            items.setMaChiTietDV(mCursor.getString(mCursor.getColumnIndex("maChiTietDV")));
            items.setTongTien(mCursor.getString(mCursor.getColumnIndex("tongTien")));
            mList.add(items);
        }
        return mList;
    }
    public Long inserDatPhong(datPhongObj items){
        ContentValues values = new ContentValues();
        values.put("maDatPhong",items.getMaDatPhong());
        values.put("maKh",items.getMaKh());
        values.put("maNhanVien",items.getMaNhanVien());
        values.put("maPhong",items.getMaPhong());
        values.put("checkIn",items.getCheckIn());
        values.put("gioVao",items.getGioVao());
        values.put("checkOut",items.getNgayRa());
        values.put("gioRa",items.getGioRa());
        values.put("giaThue", items.getGiaTien());
        values.put("soGioThue",items.getSoGioDat());
        values.put("maChiTietDV",items.getMaChiTietDV());
        values.put("trangThai", items.getTrangThai());
        values.put("tongTien", items.getTongTien());
        return db.insert("datPhong",null,values);
    }
    public List<datPhongObj> layPhieuDat(String trangThai){
       String sql = "SELECT * FROM datPhong WHERE trangThai = ?";
       return get(sql, trangThai);
    }
    public int updateDatPhong(datPhongObj items){
        ContentValues values = new ContentValues();
        values.put("maDatPhong",items.getMaDatPhong());
        values.put("maKh",items.getMaKh());
        values.put("maNhanVien",items.getMaNhanVien());
        values.put("maPhong",items.getMaPhong());
        values.put("checkIn",items.getCheckIn());
        values.put("gioVao",items.getGioVao());
        values.put("giaThue",items.getGiaTien());
        values.put("checkOut",items.getNgayRa());
        values.put("gioRa",items.getGioRa());
        values.put("maChiTietDV",items.getMaChiTietDV());
        values.put("soGioThue",items.getSoGioDat());
        values.put("tongTien", items.getTongTien());
        values.put("trangThai", items.getTrangThai());
        return db.update("datPhong",values,"maDatPhong = ?"
                , new String[]{items.getMaDatPhong()});
    }
    public int deleteDatPhong(String maDatPhong){
        return db.delete("datPhong","maDatPhong = ?", new String[]{maDatPhong});
    }
    public boolean checkTaoPhieu(String ngayVe, String gioRa,String maPhong){
        String sql = "SELECT * FROM datPhong WHERE  checkIn <= ? AND " +
                "  gioVao <= ? AND maPhong = ? AND trangThai = '3' ";
        List<datPhongObj> list = get(sql, new String[]{ngayVe,gioRa, maPhong});
        if (list.size() == 0){
            return true;
        }
        else{
            return false;
        }
    }
    public datPhongObj getBymaDatPhong(String maDatPhong){
        String sql = "SELECT * FROM datPhong WHERE maDatPhong = ?";
        List<datPhongObj> mList = get(sql,maDatPhong);
        return mList.get(0);
    }
    public datPhongObj getBymaKh(String maKh){
        String sql = "SELECT * FROM datPhong WHERE maKh = ?";
        List<datPhongObj> mList = get(sql,maKh);
        return mList.get(0);
    }
    public datPhongObj getByMaNhanVien(String maNhanVien){
        String sql = "SELECT * FROM datPhong WHERE maNhanVien = ?";
        List<datPhongObj> mList = get(sql,maNhanVien);
        return mList.get(0);
    }
    public datPhongObj getByMaPhong(String maPhong){
        String sql = "SELECT * FROM datPhong WHERE maPhong = ? and trangThai = '1'";
        List<datPhongObj> mList = get(sql,maPhong);
        return mList.get(0);
    }
    public datPhongObj getByMaPhongChiTiet(String maPhong){
        String sql = "SELECT * FROM datPhong WHERE maPhong = ? and trangThai = '1'";
        List<datPhongObj> mList = get(sql,maPhong);
        return mList.get(0);
    }
    @SuppressLint("Range")
    public List<phongObj> truyVanTaoPhieuCho(String ngayVao, String gioVao){
        List<phongObj> list = new ArrayList<>();
        String sql2 = "SELECT DISTINCT phong.maPhong," +
                "phong.tenPhong," +
                "phong.maTang," +
                "phong.maLoai," +
                "phong.trangThai from phong  LEFT JOIN datPhong ON phong.maPhong = datPhong.maPhong \n" +
                "WHERE  checkOut < ? OR (checkOut <= ? AND gioRa <= ? OR phong.trangThai = 'Phòng trống')";
        Cursor cursor = db.rawQuery(sql2,new String[]{ngayVao,ngayVao,gioVao});
        while (cursor.moveToNext()){
            phongObj item = new phongObj();
            item.setMaPhong(cursor.getString(cursor.getColumnIndex("maPhong")));
            item.setTenPhong(cursor.getString(cursor.getColumnIndex("tenPhong")));
            item.setMaTang(cursor.getString(cursor.getColumnIndex("maTang")));
            item.setMaLoai(cursor.getString(cursor.getColumnIndex("maLoai")));
            item.setTrangThai(cursor.getString(cursor.getColumnIndex("trangThai")));
            list.add(item);

        }
        return list;
    }
}
