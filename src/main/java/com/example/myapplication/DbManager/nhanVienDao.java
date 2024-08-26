package com.example.myapplication.DbManager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.myapplication.ObjectManager.nhanVienObj;

import java.util.ArrayList;
import java.util.List;

public class nhanVienDao {
    private SQLiteDatabase db;
    public nhanVienDao(Context context) {
        createDataBase mCreateDataBase = new createDataBase(context);
        db = mCreateDataBase.getWritableDatabase();
    }
    @SuppressLint("Range")
    public List<nhanVienObj> get(String sql, String...agrs){
        List<nhanVienObj> mList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, agrs);
        while (cursor.moveToNext()){
            nhanVienObj mNhanVien = new nhanVienObj();
            mNhanVien.setMaNhanVien(cursor.getString(cursor.getColumnIndex("maNhanVien")));
            mNhanVien.setTenNhanVien(cursor.getString(cursor.getColumnIndex("tenNhanVien")));
            mNhanVien.setSoDienThoai(cursor.getString(cursor.getColumnIndex("soDienThoai")));
            mNhanVien.setAnhNhanVien(cursor.getString(cursor.getColumnIndex("anhNhanVien")));
            mNhanVien.setMatKhau(cursor.getString(cursor.getColumnIndex("matKhau")));
            mList.add(mNhanVien);

        }
        return mList;
    }
    public List<nhanVienObj> getAll(){
        String sql = "SELECT * FROM nhanVien";
        return get(sql);
    }

    public Long insertNhanVien(nhanVienObj nhanVienObj){
        ContentValues values = new ContentValues();
        values.put("maNhanVien",nhanVienObj.getMaNhanVien());
        values.put("tenNhanVien",nhanVienObj.getTenNhanVien());
        values.put("soDienThoai",nhanVienObj.getSoDienThoai());
        values.put("anhNhanVien",nhanVienObj.getAnhNhanVien());
        values.put("matKhau",nhanVienObj.getMatKhau());
        return db.insert("nhanVien",null,values);
    }

    public Long deleteNhanVien(String manv){
        long res = db.delete("nhanVien" , "maNhanVien = ?" , new String[]{manv});
        return res;
    }
    public Long xoTen(String manv){
        long res = db.delete("nhanVien" , "tenNhanVien = ?" , new String[]{manv});
        return res;
    }
    public int updateNhanVien(nhanVienObj nhanVienObj){
        ContentValues values = new ContentValues();
        values.put("maNhanVien",nhanVienObj.getMaNhanVien());
        values.put("tenNhanVien",nhanVienObj.getTenNhanVien());
        values.put("soDienThoai",nhanVienObj.getSoDienThoai());
        values.put("anhNhanVien",nhanVienObj.getAnhNhanVien());
        values.put("matKhau",nhanVienObj.getMatKhau());
        return db.update("nhanVien",values,"maNhanVien = ?", new String[]{nhanVienObj.getMaNhanVien()});
    }
    public nhanVienObj getByMaNhanVien(String maNhanVien) {
        String sql = "SELECT * FROM nhanVien WHERE maNhanVien = ?";
        List<nhanVienObj> mList = get(sql,maNhanVien);
        return mList.get(0);
    }
    // check nhân viên tả về boolen
    public Boolean CheckByMaNhanVien(String maNhanVien , String mattkhau) {
        String sql = "SELECT * FROM nhanVien WHERE maNhanVien = ? AND matKhau = ? ";
        Cursor c = db.rawQuery(sql , new String[]{maNhanVien , mattkhau});
        if (c.getCount() > 0 ){
            return true;
        }
        return false;
    }




}
