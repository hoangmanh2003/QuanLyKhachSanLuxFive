package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.AdapterManager.tangAdapter;
import com.example.myapplication.DbManager.tangDao;
import com.example.myapplication.InterfaceManager.sendTang;
import com.example.myapplication.ObjectManager.tangObj;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.List;

public class quanLyTang extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private tangAdapter mAdapter;
    private FloatingActionButton btn_add;
    private Intent mIntent;
    private Bundle mBundle;
    private tangDao mTangDao;
    private String id_admin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_tang);
        getSupportActionBar().setTitle("Quản lý tầng");
        mRecyclerView = findViewById(R.id.activity_quan_ly_tang_recycleView);
        btn_add = findViewById(R.id.activity_quan_ly_tang_add);
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        id_admin = sharedPreferences.getString("maNv","");
        if (id_admin.equals("admin1")){
            btn_add.setVisibility(View.VISIBLE);
        }
        else{
            btn_add.setVisibility(View.INVISIBLE);
        }
        mTangDao = new tangDao(quanLyTang.this);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTang();
            }
        });
        mAdapter = new tangAdapter(quanLyTang.this, new sendTang() {

            @Override
            public void sendTang(tangObj items) {
                mIntent = new Intent(quanLyTang.this, quanLyTang_phong.class);
                mBundle = new Bundle();
                mBundle.putSerializable("items", items);
                mIntent.putExtra("bundle_senTang",mBundle);
                startActivity(mIntent);

            }
        });
       taiDuLieu();

    }
    private void taiDuLieu(){
        mAdapter.setmList(getListTang());
        mRecyclerView.setAdapter(mAdapter);
    }

    private List<tangObj> getListTang() {
        List<tangObj> list = mTangDao.getAll();
        return list;
    }
    private void addTang(){

        Dialog dialog = new Dialog(quanLyTang.this,
                androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog_Alert );
        dialog.setContentView(R.layout.dialog_add_tang);
        EditText editText_maTang, editText_tenTang;
        Button button_them;
        editText_maTang = dialog.findViewById(R.id.dialog_add_tang_maTang);
        editText_tenTang = dialog.findViewById(R.id.dialog_add_tang_tenTang);
        button_them = dialog.findViewById(R.id.dialog_add_tang_them);
        button_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    String maTang = editText_maTang.getText().toString().trim();
                    if (editText_maTang.getText().toString().isEmpty() ||
                            editText_tenTang.getText().toString().isEmpty()){
                        if (editText_maTang.getText().toString().isEmpty()){
                            editText_maTang.setError("Thiếu mã tầng");
                        }
                        else{
                            editText_tenTang.setError("Thiếu tên tầng");
                        }
                    }
                    else{
                        if (checkMaTang(maTang)){
                            tangObj items = new tangObj();
                            items.setMaTang(editText_maTang.getText().toString().trim());
                            items.setTenTang(editText_tenTang.getText().toString().trim());
                            mTangDao.insertTang(items);
                            taiDuLieu();
                            dialog.cancel();
                        }
                        else{
                            editText_maTang.setError("Mã tầng đã tồn tại");
                        }

                    }
                }
        });
        dialog.show();
    }
  // chungcv : kiểm tra mã tầng đã tồn tại hay chưa
    private boolean checkMaTang(String maTang) {
        boolean check = true;
        List<tangObj> list = mTangDao.getAll();
       if (list == null){
           check= true;
       }
       else{
           for (tangObj item : list){
               if (item.getMaTang().toLowerCase().equals(maTang.toLowerCase())){
                   check = false;
                   break;
               }
               else{
                   check = true;
               }
           }
       }
       return check;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem =menu.findItem(R.id.menu_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setQueryHint("Tên tầng, mã tầng...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}