package com.example.myapplication;

import static android.util.Log.d;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.AdapterManager.loaiPhongAdapter;
import com.example.myapplication.AdapterManager.phongAdapter;
import com.example.myapplication.DbManager.loaiPhongDao;
import com.example.myapplication.InterfaceManager.sendLoaiPhong;
import com.example.myapplication.ObjectManager.chiTietDichVuOBJ;
import com.example.myapplication.ObjectManager.datPhongObj;
import com.example.myapplication.ObjectManager.dichVuObj;
import com.example.myapplication.ObjectManager.hoaDonObj;
import com.example.myapplication.ObjectManager.khachHangObj;
import com.example.myapplication.ObjectManager.loaiPhongObj;
import com.example.myapplication.ObjectManager.nhanVienObj;
import com.example.myapplication.ObjectManager.phongObj;
import com.example.myapplication.ObjectManager.tangObj;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class loaiPhong extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private loaiPhongAdapter mAdapter;
    private Intent mIntent;
    private Bundle mBundle;
    private loaiPhongDao mLoaiPhongDao;
    private FloatingActionButton flb_AddLoaiPhong;
    private String id_admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loai_phong);
        getSupportActionBar().setTitle("Quản lý Loại phòng");

        mRecyclerView = findViewById(R.id.activity_quan_ly_loai_phong_recycleView);
        flb_AddLoaiPhong = findViewById(R.id.btnAddLoaiPhong);
        mLoaiPhongDao = new loaiPhongDao(loaiPhong.this);
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        id_admin = sharedPreferences.getString("maNv","");
        if (id_admin.equals("admin1")){
            flb_AddLoaiPhong.setVisibility(View.VISIBLE);
        }
        else{
            flb_AddLoaiPhong.setVisibility(View.INVISIBLE);
        }

        mAdapter = new loaiPhongAdapter(loaiPhong.this, new sendLoaiPhong() {

            @Override
            public void sendLoaiPhong(loaiPhongObj items) {
                mIntent = new Intent(loaiPhong.this, quanLyTang_phong.class);
                mBundle = new Bundle();
                mBundle.putSerializable("items", (Serializable) items);
                mIntent.putExtra("bundle_senTang", mBundle);
                startActivity(mIntent);
            }
        });
       capNhatRec();

        flb_AddLoaiPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openThemLoaiPhongDialog(Gravity.CENTER);
            }

        });

    }
    private void openThemLoaiPhongDialog(int gravity){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_loai_phong);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity =gravity;
        window.setAttributes(windowAttribute);

        //Xử lý click ra ngoài dialog
        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        Button btnHuy =dialog.findViewById(R.id.dialog_add_loaiPhong_tenLoai_btnHuy);
        Button btnThem =dialog.findViewById(R.id.dialog_add_loaiPhong_btnThem);
        EditText tenLoai;
        tenLoai = dialog.findViewById(R.id.dialog_add_loaiPhong_tenLoai);

        //Xử lý nút trong Dialog
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (tenLoai.getText().toString().isEmpty())
            {
                if (tenLoai.getText().toString().isEmpty()){
                    tenLoai.setError("Không được để trống");
                    return;
                }
            }
            else
            {
                    loaiPhongObj itemInsert = new loaiPhongObj();
                    itemInsert.setTenLoaiPhong(tenLoai.getText().toString().trim());
                    mLoaiPhongDao.insertLoaiPhong(itemInsert);
                    capNhatRec();
                    dialog.cancel();
                    Toast.makeText(loaiPhong.this, "Thêm thành công", Toast.LENGTH_SHORT).show();

                }
            }
        });

        dialog.show();
    }
    public void capNhatRec(){
        mAdapter.setmList(getListLoaiPhong());
        mRecyclerView.setAdapter(mAdapter);
    }

    public List<loaiPhongObj> getListLoaiPhong() {
        List<loaiPhongObj> list = mLoaiPhongDao.getAll();
        return list;
    }
}