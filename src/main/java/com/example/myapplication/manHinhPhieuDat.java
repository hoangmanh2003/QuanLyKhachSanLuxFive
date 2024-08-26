package com.example.myapplication;

import static android.util.Log.d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myapplication.AdapterManager.phongAdapter;
import com.example.myapplication.DbManager.datPhongDao;
import com.example.myapplication.DbManager.phongDao;
import com.example.myapplication.InterfaceManager.sendPhong;
import com.example.myapplication.ObjectManager.phongObj;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

public class manHinhPhieuDat extends AppCompatActivity implements View.OnClickListener {
    private EditText mEditText_d1,mEditText_d3;
    private ImageView mImageView_i1, mImageView_i3;
    private phongAdapter adapter;
    private phongDao mPhongDao;
    private datPhongDao mDatPhongDao;
    private RecyclerView mRecyclerView;
    private Button btn_search;
    private String hinhThucThue = "giờ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_phieu_dat);
        getSupportActionBar().setTitle("Phiếu đặt trước");
        mPhongDao = new phongDao(manHinhPhieuDat.this);
        mDatPhongDao = new datPhongDao(manHinhPhieuDat.this);
        unNitIu();
        mEditText_d1.setOnClickListener(this);
        mImageView_i1.setOnClickListener(this);
        mImageView_i3.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        adapter = new phongAdapter(manHinhPhieuDat.this, new sendPhong() {
            @Override
            public void sendPhong(phongObj items) {
                    clickItem(items);
            }
        });
        capNhapDuLieu(getPhongObj());
        //
    }

    private void clickItem(phongObj items) {
        if (mEditText_d1.getText().toString().isEmpty() || mEditText_d3.getText().toString().isEmpty()){
            AlertDialog.Builder builder = new AlertDialog.Builder(manHinhPhieuDat.this);
            builder.setTitle("Không được để trống ngày và giờ");
            builder.setNegativeButton("Thoát", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
        else {
            Dialog dialog = new Dialog(manHinhPhieuDat.this);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            dialog.setContentView(R.layout.dialog_phieu_dat);
            EditText tenPhong = dialog.findViewById(R.id.dialog_phieu_dat_tenPhong);
            EditText trangThai = dialog.findViewById(R.id.dialog_phieu_dat_trangThai);
            Button btn_huy = dialog.findViewById(R.id.dialog_phieu_dat_thoat);
            Button btn_taoPhieu = dialog.findViewById(R.id.dialog_phieu_dat_taoPhieu);
            tenPhong.setText(items.getTenPhong());
            trangThai.setText(items.getTrangThai());
            btn_huy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            btn_taoPhieu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(manHinhPhieuDat.this, taoPhieuDatPhong.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("itemSend", items);
                    bundle.putString("ngayDat", mEditText_d1.getText().toString().trim());
                    bundle.putString("gioDat", mEditText_d3.getText().toString().trim());
                    bundle.putString("trangThaiPhieu", "3");
                    intent.putExtra("intentSend", bundle);
                    startActivity(intent);
                    dialog.cancel();
                }
            });

            dialog.show();
        }
    }

    private void  unNitIu(){
        mEditText_d1 = findViewById(R.id.activity_man_hinh_phieu_dat_txtImage1);
        mImageView_i1 = findViewById(R.id.activity_man_hinh_phieu_dat_image1);
        mImageView_i3 = findViewById(R.id.activity_man_hinh_phieu_dat_image3);
        mRecyclerView = findViewById(R.id.activity_man_hinh_phieu_dat_Rc);
        btn_search = findViewById(R.id.btnSearch);
        mEditText_d3 = findViewById(R.id.activity_man_hinh_phieu_dat_txtImage3);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_man_hinh_phieu_dat_image3:
                TimePickerDialog timePickerDialog = new TimePickerDialog
                        (manHinhPhieuDat.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hourOfDayString = hourOfDay+"";
                        String minuteString = minute+"";
                        if (hourOfDay <9){
                            hourOfDayString = "0"+hourOfDay;
                        }
                        if (minute <10){
                            minuteString = "0"+minute;
                        }
                        mEditText_d3.setText(hourOfDayString+":"+minuteString+":00");
                    }
                },12,0,true);
                timePickerDialog.show();

                break;
            case R.id.activity_man_hinh_phieu_dat_txtImage1:
                break;
            case R.id.activity_man_hinh_phieu_dat_image1:
                clickImage(mEditText_d1);
                break;
            case R.id.btnSearch:

                    if (mEditText_d1.getText().toString().isEmpty() && mEditText_d3.getText().toString().isEmpty()) {
                        capNhapDuLieu(getPhongObj());
                        return;
                    }
                    else{
                        List<phongObj> list = mDatPhongDao.truyVanTaoPhieuCho(mEditText_d1.getText().toString().trim(),
                                mEditText_d3.getText().toString().trim());
                        d("ca" + "chung2", "onClick: size"+list.size());
                        capNhapDuLieu(list);
                    }
                }
        }

    private void clickImage(TextView mTextView){
        DatePickerDialog dialog = new DatePickerDialog(manHinhPhieuDat.this);
        dialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String monthString = (month+1)+"";
                String dayOfMonthString = dayOfMonth+"";
                if (month <10){
                    monthString = "0"+(month+1);
                }
                if (dayOfMonth <10){
                    dayOfMonthString = "0"+dayOfMonth;
                }
                String time = year+"-"+monthString+"-"+dayOfMonthString;

                mTextView.setText(time);
            }
        });
        dialog.show();
    }
    private void capNhapDuLieu(List<phongObj> list){
       adapter.setmList(list);
       mRecyclerView.setAdapter(adapter);
    }
    private List<phongObj> getPhongObj() {
        List<phongObj> list = mPhongDao.getAll();
        return list;
    }

    @Override
    protected void onResume() {
        super.onResume();
        capNhapDuLieu(getPhongObj());
    }

}