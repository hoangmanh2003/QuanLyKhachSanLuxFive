package com.example.myapplication.DbManager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.ObjectManager.loaiPhongObj;

import java.util.ArrayList;
import java.util.List;

public class loaiPhongDao {
    SQLiteDatabase db;

    public loaiPhongDao(Context context) {
        createDataBase mCreateDataBase = new createDataBase(context);
        db = mCreateDataBase.getWritableDatabase();
    }
    @SuppressLint("Range")
    public List<loaiPhongObj> get(String sql, String...agrs){
        List<loaiPhongObj> mList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,agrs);
        while (cursor.moveToNext()){
            loaiPhongObj mLoaiPhongObj = new loaiPhongObj();
            mLoaiPhongObj.setMaLoai(cursor.getString(cursor.getColumnIndex("maLoai")));
            mLoaiPhongObj.setTenLoaiPhong(cursor.getString(cursor.getColumnIndex("tenLoaiPhong")));
            //loaiPhong
            mList.add(mLoaiPhongObj);
        }
        return mList;
    }
    public List<loaiPhongObj> getAll(){
        String sql = "SELECT * FROM loaiPhong";
        return get(sql);
    }
    public Long insertLoaiPhong(loaiPhongObj items){
        ContentValues values = new ContentValues();
        values.put("maLoai",items.getMaLoai());
        values.put("tenLoaiPhong",items.getTenLoaiPhong());
        return db.insert("loaiPhong", null,values);

    }
    public int updateLoaiPhong(loaiPhongObj items){
        ContentValues values = new ContentValues();
        values.put("maLoai",items.getMaLoai());
        values.put("tenLoaiPhong",items.getTenLoaiPhong());
        return db.update("loaiPhong", values,"maLoai = ?", new String[]{items.getMaLoai()});

    }
    public loaiPhongObj getByMaLoaiPhong(String maLoai){
        String sql = "SELECT * FROM loaiPhong WHERE maLoai = ?";
        List<loaiPhongObj> mList = get(sql,maLoai);
        return mList.get(0);
    }
}
