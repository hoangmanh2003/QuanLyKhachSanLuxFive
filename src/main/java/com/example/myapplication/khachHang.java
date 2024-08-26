package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.AdapterManager.khachHangAdapter;
import com.example.myapplication.DbManager.khachHangDao;
import com.example.myapplication.InterfaceManager.sendKhachHang;
import com.example.myapplication.ObjectManager.khachHangObj;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class khachHang extends AppCompatActivity {
    AlertDialog dialog;
    private RecyclerView recyclerView;
    private khachHangAdapter khachHangAdapter;
    private khachHangDao dao;
    private List<khachHangObj> list;

    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);
        getSupportActionBar().setTitle("Quản lý Khách hàng");
        recyclerView = findViewById(R.id.rcvKhachHang);
        floatingActionButton = findViewById(R.id.btnThemKhachHang);
        dao = new khachHangDao(khachHang.this);
        khachHangAdapter = new khachHangAdapter(khachHang.this, new sendKhachHang() {
            @Override
            public void sendKhachHang(khachHangObj items) {

            }
        });
        getData();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAddKhachHang();
            }
        });
    }

    private void getData(){
        khachHangAdapter.setmList(getListKhachHang());
        recyclerView.setAdapter(khachHangAdapter);
    }

    private List<khachHangObj> getListKhachHang(){
        list = dao.getAll();
        return list;
    }

    private void showDialogAddKhachHang(){
        AlertDialog.Builder builder = new AlertDialog.Builder(khachHang.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_add_khach_hang,null);
        EditText edtCMT = view.findViewById(R.id.dialog_add_khach_hang_edittext_cmt);
        EditText edtTen = view.findViewById(R.id.dialog_add_khach_hang_textEdit_nameKhach);
        EditText edtNgaySinh = view.findViewById(R.id.dialog_add_khach_hang_textEdit_ngaySinh);
        EditText edtSDT = view.findViewById(R.id.dialog_add_khach_hang_textEdit_soDt);
        Button btnThem = view.findViewById(R.id.dialog_add_khach_hang_btnThemKhachHang);
        ImageView imageView = view.findViewById(R.id.dialog_khach_hang_vien_img_back);
        builder.setView(view);

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String CMT = edtCMT.getText().toString();
                String Ten = edtTen.getText().toString();
                String ngaySinh = edtNgaySinh.getText().toString();
                String SDT = edtSDT.getText().toString();

                if (CMT.isEmpty() || Ten.isEmpty() || ngaySinh.isEmpty() || SDT.isEmpty()){
                    if (CMT.isEmpty()){
                        edtCMT.setError("Moi nhap day du thong tin");
                        return;
                    }
                    if (Ten.isEmpty()){
                        edtCMT.setError("Moi nhap day du thong tin");
                        return;
                    }
                    if (ngaySinh.isEmpty()){
                        edtNgaySinh.setError("Moi nhap day du thong tin");
                        return;
                    }
                    if (SDT.isEmpty()){
                        edtSDT.setError("Moi nhap day du thong tin");
                        return;
                    }
                }else{
                    khachHangObj khachHang = new khachHangObj();
                    khachHang.setSoCMT(CMT.trim());
                    khachHang.setTenKh(Ten.trim());
                    khachHang.setNgaySinh(ngaySinh.trim());
                    khachHang.setSoDienThoai(SDT.trim());
                    if (dao.checkKhachHang(CMT.trim())){
                        dao.insertKhachHangObj(khachHang);
                        Toast.makeText(khachHang.this, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        getData();
                    }
                    else{
                        edtCMT.setError("Khách hàng đã tồn tại");
                    }

                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Nhập chứng minh thư, tên, sdt");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                khachHangAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                khachHangAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}