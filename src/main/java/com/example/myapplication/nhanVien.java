package com.example.myapplication;





import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.Manifest;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.View;
import android.widget.Toast;

import com.example.myapplication.AdapterManager.nhanVienAdapter;
import com.example.myapplication.DbManager.nhanVienDao;
import com.example.myapplication.ObjectManager.nhanVienObj;
import com.google.android.material.floatingactionbutton.FloatingActionButton;





import java.util.List;




public class nhanVien extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_REQUEST = 123;
    RecyclerView recycleView_nhanvien_activity;
    FloatingActionButton floating_btn_nhanvien_activity;

    List<nhanVienObj> listNhanVien;
    nhanVienDao dao ;
    private String id_admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_vien);
        getSupportActionBar().setTitle("Quản lý Tài Khoản Nhân Viên");
        initUi();
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        id_admin = sharedPreferences.getString("maNv","");
        if (id_admin.equals("admin1")){
            floating_btn_nhanvien_activity.setVisibility(View.VISIBLE);
        }
        else{
            floating_btn_nhanvien_activity.setVisibility(View.INVISIBLE);
        }

        loadData();
        floating_btn_nhanvien_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkStoragePermission()){
                    Intent intent = new Intent(nhanVien.this , add_Nhanvien.class);
                    startActivity(intent);
                }else {
                    return;
                }
            }
        });

    }

    public void loadData() {

        dao = new nhanVienDao(getApplicationContext());
        listNhanVien = dao.getAll();
        nhanVienAdapter adapter = new nhanVienAdapter(nhanVien.this ,listNhanVien);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(nhanVien.this);
        recycleView_nhanvien_activity.setLayoutManager(linearLayoutManager);
        recycleView_nhanvien_activity.setAdapter(adapter);
    }


    private void initUi() {
        recycleView_nhanvien_activity = findViewById(R.id.recycleView_nhanvien_activity);
        floating_btn_nhanvien_activity = findViewById(R.id.floating_btn_nhanvien_activity);

    }


    public boolean checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_REQUEST);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode  == STORAGE_PERMISSION_REQUEST){
            if (grantResults.length > 0 && grantResults[0]  == PackageManager.PERMISSION_GRANTED){

            }
        }else {
            Toast.makeText(nhanVien.this, "chưa cấp quyền thư viện", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        loadData();
    }
}