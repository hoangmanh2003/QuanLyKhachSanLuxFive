package com.example.myapplication.DbManager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.ObjectManager.phongObj;

import java.util.ArrayList;
import java.util.List;

public class phongDao {
    SQLiteDatabase db;

    public phongDao(Context context) {
        createDataBase mCreateDataBase = new createDataBase(context);
        db = mCreateDataBase.getWritableDatabase();
    }
    //maPhong
    //maTang
    //tenPhong
    //maLoai
    //trangThai
    //soPhong
    @SuppressLint("Range")
    public List<phongObj> get(String sql, String...agrs){
        List<phongObj> mList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,agrs);
        while (cursor.moveToNext()){
            phongObj mPhongObj = new phongObj();
            mPhongObj.setMaPhong(cursor.getString(cursor.getColumnIndex("maPhong")));
            mPhongObj.setMaTang(cursor.getString(cursor.getColumnIndex("maTang")));
            mPhongObj.setTenPhong(cursor.getString(cursor.getColumnIndex("tenPhong")));
            mPhongObj.setMaLoai(cursor.getString(cursor.getColumnIndex("maLoai")));
            mPhongObj.setTrangThai(cursor.getString(cursor.getColumnIndex("trangThai")));
            mList.add(mPhongObj);
        }
        return mList;
    }
    public List<phongObj> getAll(){
        String sql = "SELECT * FROM phong";
        return get(sql);
    }
    public Long insertPhong(phongObj items){
        ContentValues values = new ContentValues();
        values.put("maPhong",items.getMaPhong());
        values.put("maTang",items.getMaTang());
        values.put("tenPhong",items.getTenPhong());
        values.put("maLoai",items.getMaLoai());
        values.put("trangThai",items.getTrangThai());
        return db.insert("phong", null,values);
    }
    public int updatePhong(phongObj items){
        ContentValues values = new ContentValues();
        values.put("maPhong",items.getMaPhong());
        values.put("maTang",items.getMaTang());
        values.put("tenPhong",items.getTenPhong());
        values.put("maLoai",items.getMaLoai());
        values.put("trangThai",items.getTrangThai());
        return db.update("phong", values,"maPhong = ?"
                , new String[]{items.getMaPhong()});
    }
    public int deletePhong(String maPhong){
        return db.delete("phong","maPhong = ?", new String[]{maPhong});
    }
    public phongObj getByMaPhong(String maPhong){
        String sql = "SELECT * FROM phong WHERE maPhong  = ?";
        List<phongObj> mList = get(sql,maPhong);
        return mList.get(0);
    }
    public List<phongObj> getByMaTang(String maTang){
        String sql = "SELECT * FROM phong WHERE maTang  = ?";
        List<phongObj> mList = get(sql,maTang);
        return mList;
    }
    public phongObj getByMaLoai(String maLoai){
        String sql = "SELECT * FROM phong WHERE maLoai  = ?";
        List<phongObj> mList = get(sql,maLoai);
        return mList.get(0);
    }
}
