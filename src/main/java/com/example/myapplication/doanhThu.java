package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.AdapterManager.hoaDonAdapter;
import com.example.myapplication.AdapterManager.itemRcy_dichVu_chiTietPhieuDat;
import com.example.myapplication.DbManager.chiTietDichVuDao;
import com.example.myapplication.DbManager.createDataBase;
import com.example.myapplication.DbManager.datPhongDao;
import com.example.myapplication.DbManager.hoaDonDao;
import com.example.myapplication.DbManager.khachHangDao;
import com.example.myapplication.DbManager.nhanVienDao;
import com.example.myapplication.DbManager.phongDao;
import com.example.myapplication.InterfaceManager.sendChiTietDichVu;
import com.example.myapplication.InterfaceManager.sendHoaDon;
import com.example.myapplication.ObjectManager.chiTietDichVuOBJ;
import com.example.myapplication.ObjectManager.datPhongObj;
import com.example.myapplication.ObjectManager.hoaDonObj;
import com.example.myapplication.ObjectManager.khachHangObj;
import com.example.myapplication.ObjectManager.nhanVienObj;
import com.example.myapplication.ObjectManager.phongObj;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class doanhThu extends AppCompatActivity {
    private EditText tuNgayM, denNgayM;
    private TextView tongTien;
    private Button btn_timKiem, btn_xoa;
    private ImageView datePK_1, datePK_2;
    private RecyclerView mRecyclerView;
    private hoaDonObj mHoaDonObj;
    private hoaDonDao mHoaDonDao;
    private hoaDonAdapter mHoaDonAdapter;
    private datPhongObj mDatPhongObj;
    private datPhongDao mDatPhongDao;private khachHangDao mKhachHangDao;
    private khachHangObj mKhachHangObj;
    private chiTietDichVuDao mChiTietDichVuDao;
    private itemRcy_dichVu_chiTietPhieuDat chiTietDV_adapter;
    private nhanVienObj mNhanVien; private phongDao mPhongDao; private phongObj mPhongObj;
    private nhanVienDao mNhanVienDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doanh_thu);
        mDatPhongDao = new datPhongDao(doanhThu.this);
        mNhanVienDao = new nhanVienDao(doanhThu.this);
        mKhachHangDao = new khachHangDao(doanhThu.this);
        mPhongDao = new phongDao(doanhThu.this);
        unitUI();
        getSupportActionBar().setTitle("Quản lý Doanh thu");
        mHoaDonAdapter = new hoaDonAdapter(doanhThu.this, new sendHoaDon() {
            @Override
            public void sendHoaDon(hoaDonObj items) {
                clickItenHoaDon(items);
            }
        });
        capNhapDuLieu(layTatCacHoaDon());
        tongTien.setText(tongTienTatCaHoaDon());
        datePK_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDatePicker(tuNgayM);
            }
        });
        datePK_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDatePicker(denNgayM);
            }
        });
        btn_timKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickVaoTimKiem(tuNgayM.getText().toString().trim(), denNgayM.getText().toString().trim());
            }
        });
        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickXoa();
            }
        });

    }
    private void clickVaoTimKiem(String tuNgay, String denNgay){
        if (tuNgay.isEmpty() || denNgay.isEmpty()){
            if (denNgay.isEmpty()){
                try {
                    capNhapDuLieu(mHoaDonDao.truyVanTheoTuNgay(tuNgay));
                    String tongTienQuery ;
                    tongTienQuery = mHoaDonDao.truyVanTongTienTuNgay(tuNgay);
                    Float tongTienDouble = Float.parseFloat(tongTienQuery);
                    String tienToTal = String.format("%.1f",tongTienDouble);
                    tongTien.setText("Tổng tiền: "+tienToTal+"VND");
                }
                catch (Exception e){
                    capNhapDuLieu(layTatCacHoaDon());
                    tongTien.setText(tongTienTatCaHoaDon());
                }

            }

            else {
                try {
                    capNhapDuLieu(mHoaDonDao.truyVanTheoDenNgay(denNgay));
                    String tongTienQuery ;
                    tongTienQuery = mHoaDonDao.truyVanTongTienDenNgay(denNgay);
                    Float tongTienDouble = Float.parseFloat(tongTienQuery);
                    String tienToTal = String.format("%.1f",tongTienDouble);
                    tongTien.setText("Tổng tiền: "+tienToTal+"VND");
                }
                catch (Exception e){
                    capNhapDuLieu(layTatCacHoaDon());
                    tongTien.setText(tongTienTatCaHoaDon());
                }
            }
        }
        else{
            try {
                capNhapDuLieu(mHoaDonDao.truyVanTheoKhoangNgay(tuNgay,denNgay));
                tongTien.setText(tinhTongTienTheoKhoang(tuNgay,denNgay));
            }
            catch (Exception e){
                capNhapDuLieu(null);
                tongTien.setText("Tổng tiền: 0 VND");
            }
            }

    }
    private void clickXoa(){
        tuNgayM.setText("");
        denNgayM.setText("");
        capNhapDuLieu(layTatCacHoaDon());
        tongTien.setText(tongTienTatCaHoaDon());
    }
    private String tinhTongTienTheoKhoang(String tuNgay, String denNgay){
        String tongTienQuery ;
        tongTienQuery = mHoaDonDao.truyVanTongTienTheoKhoanNgay(tuNgay,denNgay);
        Float tongTienDouble = Float.parseFloat(tongTienQuery);
        String tienToTal = String.format("%.1f",tongTienDouble);
        return  "Tổng tiền: "+tienToTal+" VND";
    }
    private String tongTienTatCaHoaDon(){
        String tongTienQuery ;
        tongTienQuery = mHoaDonDao.truyVanTongTien();
        if (tongTienQuery == null){
            return "Tổng tiền: 0 VND";
        }
        else{
            Float tongTienDouble = Float.parseFloat(tongTienQuery);
            String tienToTal = String.format("%.1f",tongTienDouble);
            return  "Tổng tiền: "+tienToTal+" VND";
        }
    }
    private void capNhapDuLieu(List<hoaDonObj> list){
        mHoaDonAdapter.setmList(list);
        mRecyclerView.setAdapter(mHoaDonAdapter);
    }
    private List<hoaDonObj> layTatCacHoaDon(){
        List<hoaDonObj> list = mHoaDonDao.getAll();
        return list;
    }
    private void clickDatePicker(EditText mEditText){
        DatePickerDialog dialog = new DatePickerDialog(doanhThu.this);
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

                mEditText.setText(time);
            }
        });
        dialog.show();
    }
    private void unitUI(){
        tuNgayM = findViewById(R.id.activity_doanh_thu_ed1);
        denNgayM = findViewById(R.id.activity_doanh_thu_ed2);
        datePK_1 = findViewById(R.id.activity_doanh_thu_image1);
        datePK_2 = findViewById(R.id.activity_doanh_thu_img2);
        btn_timKiem = findViewById(R.id.activity_doanh_thu_timKiem);
        btn_xoa = findViewById(R.id.activity_doanh_thu_xoa);
        mRecyclerView = findViewById(R.id.activity_doanh_thu_rcv);
        tongTien = findViewById(R.id.activity_doanh_thu_tongTien);
        //
        mHoaDonDao = new hoaDonDao(doanhThu.this);
    }
    private void clickItenHoaDon(hoaDonObj items){
        mDatPhongObj = mDatPhongDao.getBymaDatPhong(items.getMaDatPhong());
        mNhanVien = mNhanVienDao.getByMaNhanVien(mDatPhongObj.getMaNhanVien());
        mKhachHangObj = mKhachHangDao.getByMaKh(mDatPhongObj.getMaKh());
        mPhongObj = mPhongDao.getByMaPhong(mDatPhongObj.getMaPhong());
        //
        chiTietDV_adapter = new itemRcy_dichVu_chiTietPhieuDat(doanhThu.this, new sendChiTietDichVu() {
            @Override
            public void sendChiTietDichVu(chiTietDichVuOBJ items) {
            }
        });
        chiTietDV_adapter.setmList(getListCTHV(items.getMaDatPhong()));
        //
        Dialog dialog = new Dialog(doanhThu.this);
        dialog.setContentView(R.layout.dialog_chitiet_hoa_don);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView nguoiTao, phongThue, ngayDat, ngayTra, nguoiDat, tongTien, giaThue,gioTra, gioDat;
        ImageView dialog_cancel;
        RecyclerView mRecyclerView ;
        nguoiDat = dialog.findViewById(R.id.update_hoadon_tv_tenKhachHang);
        nguoiTao = dialog.findViewById(R.id.dialog_chitiet_hoadon_tv_NguoiTao);
        phongThue = dialog.findViewById(R.id.dialog_chitiet_hoadon_tv_tienPhong);
        ngayDat = dialog.findViewById(R.id.dialog_chitiet_hoadon_tv_NgayDat);
        ngayTra = dialog.findViewById(R.id.dialog_chitiet_hoadon_tv_NgayTra);
        mRecyclerView = dialog.findViewById(R.id.dialog_chitiet_hoaDon_recyclle_dichVu);
        tongTien = dialog.findViewById(R.id.dialog_chitiet_hoaDon_tvTongtien);
        giaThue = dialog.findViewById(R.id.dialog_chitiet_hoadon_tv_gia);
        gioTra = dialog.findViewById(R.id.dialog_chitiet_hoadon_tv_gioTra);
        gioDat = dialog.findViewById(R.id.dialog_chitiet_hoadon_tv_gioDat);
        dialog_cancel = dialog.findViewById(R.id.dialog_chitiet_hoaDon_OutDialog);
        //
        gioTra.setText(mDatPhongObj.getGioRa());
        gioDat.setText(mDatPhongObj.getGioVao());
        nguoiDat.setText(mKhachHangObj.getTenKh());
        nguoiTao.setText(mNhanVien.getTenNhanVien());
        phongThue.setText(mPhongObj.getTenPhong());
        ngayDat.setText(mDatPhongObj.getCheckIn());
        ngayTra.setText(mDatPhongObj.getNgayRa());
        String tongTienF = String.format("%.1f",Float.parseFloat(items.getTongTien()));
        tongTien.setText(tongTienF+"VND");
        giaThue.setText(mDatPhongObj.getSoGioDat()+"/"+mDatPhongObj.getGiaTien());
        mRecyclerView.setAdapter(chiTietDV_adapter);
        dialog_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
    private List<chiTietDichVuOBJ> getListCTHV(String mahd){
        chiTietDichVuDao chiTietDichVuDao = new chiTietDichVuDao(doanhThu.this);
        return chiTietDichVuDao.getLisByMaDP(mahd);
    }
}