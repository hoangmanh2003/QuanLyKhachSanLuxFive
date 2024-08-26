package com.example.myapplication.DbManager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.ObjectManager.tangObj;

import java.util.ArrayList;
import java.util.List;

public class tangDao {
    SQLiteDatabase db;

    public tangDao(Context context) {
        createDataBase mCreateDataBase = new createDataBase(context);
        db = mCreateDataBase.getWritableDatabase();
    }
    @SuppressLint("Range")
    public List<tangObj> get(String sql, String...agrs){
        List<tangObj> mList = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,agrs);
        while (cursor.moveToNext()){
            tangObj mTangObj = new tangObj();
            mTangObj.setMaTang(cursor.getString(cursor.getColumnIndex("maTang")));
            mTangObj.setTenTang(cursor.getString(cursor.getColumnIndex("tenTang")));
            mTangObj.setSoPhong(cursor.getString(cursor.getColumnIndex("soPhong")));
            mList.add(mTangObj);
        }
        return mList;
    }
    public List<tangObj> getAll(){
        String sql = "SELECT * FROM tang";
        return get(sql);
    }
    public tangObj getByMaTang(String maTang){
        String sql = "SELECT * FROM tang WHERE maTang = ?";
        List<tangObj> mList = get(sql,maTang);
        return mList.get(0);
    }
    public Long insertTang(tangObj items){
        ContentValues values = new ContentValues();
        values.put("maTang",items.getMaTang());
        values.put("tenTang",items.getTenTang());
        values.put("soPhong",items.getSoPhong());
        return db.insert("tang",null, values);
    }
    public int updateTang(tangObj items){
        ContentValues values = new ContentValues();
        values.put("maTang",items.getMaTang());
        values.put("tenTang",items.getTenTang());
        values.put("soPhong",items.getSoPhong());
        return db.update("tang",values, "maTang = ?", new String[]{items.getMaTang()});
    }
    public int deleteTang(String maTang){
        return db.delete("tang","maTang = ?", new String[]{maTang});
    }
    //maTang
    //tenTang
    //soPhong
    //tang
}
