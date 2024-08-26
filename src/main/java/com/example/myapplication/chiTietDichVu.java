package com.example.myapplication;

import static android.util.Log.d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.AdapterManager.chiTietDichVuAdapter;
import com.example.myapplication.AdapterManager.spinnerDichVuAdapter;
import com.example.myapplication.DbManager.chiTietDichVuDao;
import com.example.myapplication.DbManager.dichVuDao;
import com.example.myapplication.InterfaceManager.sendChiTietDichVu;
import com.example.myapplication.ObjectManager.chiTietDichVuOBJ;
import com.example.myapplication.ObjectManager.dichVuObj;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class chiTietDichVu extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private FloatingActionButton btn_add;
    private String maDatPhong;
    private Intent mIntent;
    private chiTietDichVuAdapter adapter;
    private chiTietDichVuDao mChiTietDichVuDao;
    private dichVuDao mDichVuDao;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_dich_vu);
        //
        mChiTietDichVuDao = new chiTietDichVuDao(chiTietDichVu.this);
        mDichVuDao = new dichVuDao(chiTietDichVu.this);
        adapter = new chiTietDichVuAdapter(chiTietDichVu.this, new sendChiTietDichVu() {
            @Override
            public void sendChiTietDichVu(chiTietDichVuOBJ items) {
                return;
            }
        });
        //
        getSupportActionBar().setTitle("Các dịch vụ đã dùng");
        mRecyclerView = findViewById(R.id.activity_chi_tiet_dich_vu_rcv);
        btn_add = findViewById(R.id.activity_chi_tiet_dich_vu_btnAdd);
        mIntent = getIntent();
        maDatPhong = mIntent.getStringExtra("id_phieuDatPhong");
        //
        capNhapDuLieu();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themChiTietDichVu(maDatPhong);
            }
        });

    }

    private void themChiTietDichVu(String id_chiTietDichVu) {
        dichVuDao mDichVuDao = new dichVuDao(chiTietDichVu.this);
        List<dichVuObj> mlstDV = mDichVuDao.getAll();
        if (mlstDV.size() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(chiTietDichVu.this);
            builder.setTitle("Hiện tại chưa có dịch vụ nào !");
            builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
        else {
            Dialog dialog = new Dialog(chiTietDichVu.this,
                    androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            dialog.setContentView(R.layout.dialog_add_chi_tiet_dich_vu);
            Spinner spinner = dialog.findViewById(R.id.dialog_add_chi_tiet_dich_vu_spinner);
            EditText giaDv = dialog.findViewById(R.id.dialog_add_chi_tiet_dich_vu_giaTien);
            EditText soLuong = dialog.findViewById(R.id.dialog_add_chi_tiet_dich_vu_soLuong);
            Button btn_add = dialog.findViewById(R.id.dialog_add_chi_tiet_dich_vu_btnAdd);
            Button btn_huy = dialog.findViewById(R.id.dialog_add_chi_tiet_dich_vu_btnHuy);
            //
            spinnerDichVuAdapter adapter = new spinnerDichVuAdapter(chiTietDichVu.this,
                    R.layout.item_spinner_select, getDichVu());
            spinner.setAdapter(adapter);
            //
            btn_huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dichVuObj itemsDV = (dichVuObj) spinner.getSelectedItem();
                    if (soLuong.getText().toString().isEmpty() || giaDv.getText().toString().isEmpty()) {
                        if (soLuong.getText().toString().isEmpty()) {
                            soLuong.setError("Nhập số lượng");
                            return;
                        } else {
                            giaDv.setError("Nhập giá tiền");
                            return;
                        }
                    } else {
                        chiTietDichVuOBJ CTDVinsert = new chiTietDichVuOBJ();
                        CTDVinsert.setSoLuong(soLuong.getText().toString().trim());
                        CTDVinsert.setMaDichVu(itemsDV.getMaDichVu());
                        CTDVinsert.setGiaTien(giaDv.getText().toString().trim());
                        CTDVinsert.setMaDatPhong(id_chiTietDichVu);
                        CTDVinsert.setTongTien(String.valueOf(CTDVinsert.tinhTongTien()));
                        mChiTietDichVuDao.inserChiTietDichVu(CTDVinsert);
                        capNhapDuLieu();
                        dialog.cancel();
                        Toast.makeText(chiTietDichVu.this, "Thêm dịch vụ thành công"
                                , Toast.LENGTH_SHORT).show();
                    }

                }
            });
            dialog.show();
        }

    }
    private void capNhapDuLieu(){
        adapter.setmList(getChiTietDichVu());
        mRecyclerView.setAdapter(adapter);
    }
    private List<chiTietDichVuOBJ> getChiTietDichVu(){
        return mChiTietDichVuDao.getByMaDP(maDatPhong);
    }
    private List<dichVuObj> getDichVu (){
        return mDichVuDao.getAll();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_back:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}