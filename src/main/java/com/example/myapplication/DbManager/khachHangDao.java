package com.example.myapplication.DbManager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.ObjectManager.khachHangObj;

import java.util.ArrayList;
import java.util.List;

public class khachHangDao {
    SQLiteDatabase db;

    public khachHangDao(Context context) {
        createDataBase createDataBase = new createDataBase(context);
        db = createDataBase.getWritableDatabase();
    }
    //soCMT
    //tenKh
    //ngaySinh
    //soDienThoai
    @SuppressLint("Range")
    public List<khachHangObj> get(String sql, String...agrs){
        List<khachHangObj> mList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,agrs);
        while (cursor.moveToNext()){
            khachHangObj khachHangObj = new khachHangObj();
            khachHangObj.setSoCMT(cursor.getString(cursor.getColumnIndex("soCMT")));
            khachHangObj.setTenKh(cursor.getString(cursor.getColumnIndex("tenKh")));
            khachHangObj.setNgaySinh(cursor.getString(cursor.getColumnIndex("ngaySinh")));
            khachHangObj.setSoDienThoai(cursor.getString(cursor.getColumnIndex("soDienThoai")));
            mList.add(khachHangObj);
        }
        return mList;
    }
    public List<khachHangObj> getAll(){
        String sql = "SELECT * FROM khachHang";
        return get(sql);
    }
    public khachHangObj getByMaKh (String makh){
        String sql = "SELECT * FROM khachHang WHERE soCMT = ?";
        List<khachHangObj> list = get(sql,makh);
        return list.get(0);
    }
    public Long insertKhachHangObj(khachHangObj items ){
        ContentValues values = new ContentValues();
        values.put("soCMT",items.getSoCMT());
        values.put("tenKh",items.getTenKh());
        values.put("ngaySinh",items.getNgaySinh());
        values.put("soDienThoai",items.getSoDienThoai());
        return db.insert("khachHang", null,values);
    }
    public boolean checkKhachHang(String CMT){
        String sql = "SELECT * FROM khachHang WHERE soCMT = ?";
        List<khachHangObj> list = get(sql,CMT);
        if (list.size()>0){
            return false;
        }
        else {
            return true;
        }
    }
    public int updateKhachHangObj(khachHangObj items ){
        ContentValues values = new ContentValues();
        values.put("soCMT",items.getSoCMT());
        values.put("tenKh",items.getTenKh());
        values.put("ngaySinh",items.getNgaySinh());
        values.put("soDienThoai",items.getSoDienThoai());
        return db.update("khachHang", values,"soCMT = ?", new String[]{items.getSoCMT()});

    }
    public int deleteKhachHangObj (String soCMT){
        return db.delete("khachHang","soCMT", new String[]{soCMT});
    }


}
