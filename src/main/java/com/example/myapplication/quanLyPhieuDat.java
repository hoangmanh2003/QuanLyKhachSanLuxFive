package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.AdapterManager.datPhongAdapter;
import com.example.myapplication.DbManager.datPhongDao;
import com.example.myapplication.DbManager.phongDao;
import com.example.myapplication.InterfaceManager.sendDatPhong;
import com.example.myapplication.ObjectManager.datPhongObj;
import com.example.myapplication.ObjectManager.phongObj;

import java.util.List;

public class quanLyPhieuDat extends AppCompatActivity {
    private datPhongAdapter adapter;
    private RecyclerView mRecyclerView;
    private datPhongDao mDatPhongDao;
    private phongDao mPhongDao;
    private phongObj mPhongObj;
     @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_phieu_dat);
        getSupportActionBar().setTitle("Quản lý phiếu đặt");
        mRecyclerView = findViewById(R.id.quanLyPhieuDat_rcv);
         mPhongDao = new phongDao(quanLyPhieuDat.this);
        mDatPhongDao = new datPhongDao(quanLyPhieuDat.this);
        adapter = new datPhongAdapter(quanLyPhieuDat.this, new sendDatPhong() {
            @Override
            public void sendDatPhong(datPhongObj items) {
                if (items.getTrangThai().equals("4")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(quanLyPhieuDat.this);
                    builder.setTitle("Bạn thật sự muốn xóa phiếu này");
                    builder.setPositiveButton("Đồng ý ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mDatPhongDao.deleteDatPhong(items.getMaDatPhong());
                            Toast.makeText(quanLyPhieuDat.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            capNhapDuLieu();
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
                else {
                    clickLenDon(items);
                }

            }
        });
        capNhapDuLieu();

    }

    private void clickLenDon(datPhongObj items) {
        mPhongObj = mPhongDao.getByMaPhong(items.getMaPhong());
        if (mPhongObj.getTrangThai().toLowerCase().equals("đang dùng") ||
                mPhongObj.getTrangThai().toLowerCase().equals("quá hạn")){
            AlertDialog.Builder builder = new AlertDialog.Builder(quanLyPhieuDat.this);
            builder.setTitle("Phòng hiện tại đang được sử dụng");
            builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(quanLyPhieuDat.this);
            builder.setTitle("Bạn có muốn lên đơn không ? ");
            builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton("Lên đơn ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    items.setTrangThai("1");
                    mDatPhongDao.updateDatPhong(items);
                    mPhongObj.setTrangThai("Đang dùng");
                    mPhongDao.updatePhong(mPhongObj);
                    capNhapDuLieu();
                }
            });
            builder.show();

        }

    }

    private void capNhapDuLieu(){
        adapter.setmList(layPhieuDat());
        mRecyclerView.setAdapter(adapter);
    }
    private List<datPhongObj> layPhieuDat(){
        List<datPhongObj> list = mDatPhongDao.layPhieuDat("3");
        return list;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem =menu.findItem(R.id.menu_search);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) menuItem.getActionView();
        searchView.setQueryHint("Nhập tên hoặc ngày đặt...");
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