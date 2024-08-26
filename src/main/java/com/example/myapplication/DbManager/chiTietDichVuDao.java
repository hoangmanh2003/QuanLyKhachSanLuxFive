package com.example.myapplication.DbManager;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.ObjectManager.chiTietDichVuOBJ;

import java.util.ArrayList;
import java.util.List;

public class chiTietDichVuDao {
    SQLiteDatabase db;

    public chiTietDichVuDao(Context context) {
        createDataBase mCreateDataBase = new createDataBase(context);
        db = mCreateDataBase.getWritableDatabase();
    }

    ////maChiTietDV
    //    //maDichVu
    //    //maDatPhong
    //    //giaTien
    @SuppressLint("Range")
    public List<chiTietDichVuOBJ> get(String sql, String... agrs) {
        List<chiTietDichVuOBJ> mList = new ArrayList<>();
        Cursor mCursor = db.rawQuery(sql, agrs);
        while (mCursor.moveToNext()) {
            chiTietDichVuOBJ itemsResult = new chiTietDichVuOBJ();
            itemsResult.setMaChiTietDV(mCursor.getString(mCursor.getColumnIndex("maChiTietDV")));
            itemsResult.setMaDichVu(mCursor.getString(mCursor.getColumnIndex("maDichVu")));
            itemsResult.setMaDatPhong(mCursor.getString(mCursor.getColumnIndex("maDatPhong")));
            itemsResult.setSoLuong(mCursor.getString(mCursor.getColumnIndex("soLuong")));
            itemsResult.setGiaTien(mCursor.getString(mCursor.getColumnIndex("giaTien")));
            itemsResult.setTongTien(mCursor.getString(mCursor.getColumnIndex("tongTien")));
            mList.add(itemsResult);
        }
        return mList;
    }

    public List<chiTietDichVuOBJ> getAll() {
        String sql = "SELECT * FROM chiTietDichVu";
        return get(sql);
    }

    public Long inserChiTietDichVu(chiTietDichVuOBJ items) {
        ContentValues values = new ContentValues();
        values.put("maChiTietDV", items.getMaChiTietDV());
        values.put("maDichVu", items.getMaDichVu());
        values.put("maDatPhong", items.getMaDatPhong());
        values.put("giaTien", items.getGiaTien());
        values.put("soLuong", items.getSoLuong());
        values.put("tongTien", items.tinhTongTien());
        return db.insert("chiTietDichVu", null, values);
    }

    public int updateChiTietDichVu(chiTietDichVuOBJ items) {
        ContentValues values = new ContentValues();
        values.put("maChiTietDV", items.getMaChiTietDV());
        values.put("maDichVu", items.getMaDichVu());
        values.put("maDatPhong", items.getMaDatPhong());
        values.put("giaTien", items.getGiaTien());
        values.put("soLuong", items.getSoLuong());
        values.put("tongTien", items.getTongTien());
        return db.update("chiTietDichVu", values, "maChiTietDV = ?"
                , new String[]{items.getMaChiTietDV()});
    }

    public int deleteChiTietDichVu(String maChiTietDV) {
        return db.delete("chiTietDichVu", "maChiTietDV = ?", new String[]{maChiTietDV});
    }

    public int deletechiTietDichVuDaoByMaDatPhong(String maDatPhong) {
        return db.delete("chiTietDichVu", "maDatPhong = ?", new String[]{maDatPhong});
    }

    public chiTietDichVuOBJ getByMaDatPhong(String maDatPhong) {
        String sql = "SELECT * FROM chiTietDichVu WHERE maDatPhong = ?";
        List<chiTietDichVuOBJ> mList = get(sql, maDatPhong);
        return mList.get(0);
    }

    public chiTietDichVuOBJ getByMaDichVu(String maDichVu) {
        String sql = "SELECT * FROM chiTietDichVu WHERE maDichVu = ?";
        List<chiTietDichVuOBJ> mList = get(sql, maDichVu);
        return mList.get(0);
    }

    public List<chiTietDichVuOBJ> getByMaDP(String maDatPhong) {
        String sql = "SELECT * FROM chiTietDichVu WHERE maDatPhong = ?";
        List<chiTietDichVuOBJ> mList = get(sql, maDatPhong);
        return mList;
    }

    public List<String> getListDV(String maChiTietDV) {
        List<String> listMaDv = new ArrayList<>();
        String sql = "SELECT * FROM chiTietDichVu WHERE maChiTietDV = ?";
        List<chiTietDichVuOBJ> mList = get(sql, maChiTietDV);
        for (chiTietDichVuOBJ x : mList) {
            listMaDv.add(x.getMaDichVu());
        }
        return listMaDv;
    }

    @SuppressLint("Range")
    public float tongTienDv(String maDatPhong) {
        float tongTien = 0;
        List<chiTietDichVuOBJ> list = getByMaDP(maDatPhong);
        for (chiTietDichVuOBJ x : list) {
            tongTien += x.tongTien();
        }
        return tongTien;
    }
    public List<chiTietDichVuOBJ> getLisByMaDP(String maHD){
        String sql = "SELECT * FROM chiTietDichVu WHERE maDatPhong = ?";
        return get(sql,maHD);
    }
}
