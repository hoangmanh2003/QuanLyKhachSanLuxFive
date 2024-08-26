package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.AdapterManager.dichVuAdapter;
import com.example.myapplication.DbManager.dichVuDao;
import com.example.myapplication.InterfaceManager.sendDichVu;
import com.example.myapplication.ObjectManager.dichVuObj;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class dichVu extends AppCompatActivity {
    private FloatingActionButton btn_Add;
    private RecyclerView mRecyclerView;
    private dichVuDao mDichVuDao;
    private dichVuAdapter adapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dich_vu);
        getSupportActionBar().setTitle("Quản lý Dịch vụ");
        btn_Add = findViewById(R.id.activity_dich_vu_add);
        mRecyclerView = findViewById(R.id.activity_dich_vu_Rcv);

        mDichVuDao = new dichVuDao(dichVu.this);

        adapter = new dichVuAdapter(dichVu.this, new sendDichVu() {
            @Override
            public void sendDichVu(dichVuObj items) {
                    updateDichVu(items);
            }
        });
        capNhapRecyclerView();
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themDichVu();
            }
        });


    }

    private void updateDichVu(dichVuObj items) {
        Dialog dialog = new Dialog(dichVu.this,
                androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog_Alert);
        dialog.setContentView(R.layout.dialog_update_dich_vu);
        EditText mEditText_tenDichVu = dialog.findViewById(R.id.dialog_update_dichVu_tenDV);
        Button button_them =  dialog.findViewById(R.id.dialog_update_dichVu_them);
        Button button_huy =  dialog.findViewById(R.id.dialog_update_dichVu_huy);
        mEditText_tenDichVu.setText(items.getTenDichVu());
        button_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        button_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (mEditText_tenDichVu.getText().toString().isEmpty()){
                        mEditText_tenDichVu.setError("Không được để trông");

                    }
                    else{
                        dichVuObj dichVuObjUpdate = new dichVuObj();
                        dichVuObjUpdate.setMaDichVu(items.getMaDichVu());
                        dichVuObjUpdate.setTenDichVu(mEditText_tenDichVu.getText().toString().trim());
                        mDichVuDao.updateDichVuThem(dichVuObjUpdate);
                        capNhapRecyclerView();
                        dialog.cancel();
                        Toast.makeText(dichVu.this, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    mEditText_tenDichVu.setError("Dịch vụ đã tồn tại");
                }

            }
        });
        dialog.show();
    }

    private void capNhapRecyclerView(){
        adapter.setmList(layTatCaDichVu());
        mRecyclerView.setAdapter(adapter);
    }
    private List<dichVuObj> layTatCaDichVu(){
        List<dichVuObj> list = mDichVuDao.getAll();
        return list;
    }
    private void themDichVu(){
        Dialog dialog = new Dialog(dichVu.this,
                androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog_Alert);
        dialog.setContentView(R.layout.dialog_add_dich_vu);
        EditText mEditText_tenDichVu = dialog.findViewById(R.id.dialog_add_dichVu_tenDV);
        Button button_them =  dialog.findViewById(R.id.dialog_add_dichVu_them);
        Button button_huy =  dialog.findViewById(R.id.dialog_add_dichVu_huy);
        button_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        button_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (mEditText_tenDichVu.getText().toString().isEmpty()){
                        mEditText_tenDichVu.setError("Không được để trông");

                    }
                    else{
                        dichVuObj dichVuObjInsert = new dichVuObj();
                        dichVuObjInsert.setTenDichVu(mEditText_tenDichVu.getText().toString().trim());
                        mDichVuDao.inserDichVuThem(dichVuObjInsert);
                        capNhapRecyclerView();
                        dialog.cancel();
                        Toast.makeText(dichVu.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    mEditText_tenDichVu.setError("Dịch vụ đã tồn tại");
                }
            }
        });
        dialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem =menu.findItem(R.id.menu_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setQueryHint("Nhập tên dịch vụ...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}