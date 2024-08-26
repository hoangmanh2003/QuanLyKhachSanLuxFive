package com.example.myapplication.DbManager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.ObjectManager.dichVuObj;

import java.util.ArrayList;
import java.util.List;

public class dichVuDao {
    SQLiteDatabase db;

    public dichVuDao(Context context) {
        createDataBase mCreateDataBase = new createDataBase(context);
        db = mCreateDataBase.getWritableDatabase();
    }
    //maDichVu
    //tenDichVu
    //giaDichVu
    //
    //soLuong
    @SuppressLint("Range")
    public List<dichVuObj> get (String sql, String...agrs){
        List<dichVuObj> mList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, agrs);
        while (cursor.moveToNext()){
            dichVuObj dichVuObj = new dichVuObj();
            dichVuObj.setMaDichVu(cursor.getString(cursor.getColumnIndex("maDichVu")));
            dichVuObj.setTenDichVu(cursor.getString(cursor.getColumnIndex("tenDichVu")));
            mList.add(dichVuObj);
        }
        return mList;
    }
    public List<dichVuObj> getAll(){
        String sql = "SELECT * FROM dichVuThem";
        return get(sql);
    }
    public Long inserDichVuThem(dichVuObj items){
        ContentValues values = new ContentValues();
        values.put("maDichVu",items.getMaDichVu());
        values.put("tenDichVu",items.getTenDichVu());
        return db.insert("dichVuThem",null,values);
    }
    public int updateDichVuThem (dichVuObj items){
        ContentValues values = new ContentValues();
        values.put("maDichVu",items.getMaDichVu());
        values.put("tenDichVu",items.getTenDichVu());
        return db.update("dichVuThem",values,"maDichVu = ?"
                , new String[]{items.getMaDichVu()});
    }
    public int  deleteDichVuThem (String maDichVuThem){
        return db.delete("dichVuThem","maDichVu = ?"
                , new String[]{maDichVuThem});
    }
    public dichVuObj getByMaDV (String maDV){
        String sql = "SELECT * FROM dichVuThem WHERE maDichVu = ?";
        List<dichVuObj> list = get(sql,maDV);
        return list.get(0);
    }
}
