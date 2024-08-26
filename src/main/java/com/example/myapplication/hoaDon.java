package com.example.myapplication;

import static android.util.Log.d;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.AdapterManager.hoaDonAdapter;
import com.example.myapplication.AdapterManager.itemRcy_dichVu_chiTietPhieuDat;
import com.example.myapplication.DbManager.chiTietDichVuDao;
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

import java.util.ArrayList;
import java.util.List;

public class hoaDon extends AppCompatActivity {
       private RecyclerView recycleView_hoadon_activity;
        private hoaDonAdapter hoadon_adapter;
        private hoaDonDao mHoaDonDao;
        private datPhongDao mDatPhongDao;
        private datPhongObj mDatPhongObj;
        private nhanVienObj mNhanVien;
        private nhanVienDao mNhanVienDao;
        private khachHangObj mKhachHangObj;
        private khachHangDao mKhachHangDao;
        private phongDao mPhongDao;
        private phongObj mPhongObj;
        private itemRcy_dichVu_chiTietPhieuDat chiTietDV_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        getSupportActionBar().setTitle("Quản lý Hóa đơn");
        mHoaDonDao = new hoaDonDao(hoaDon.this);
        recycleView_hoadon_activity = findViewById(R.id.recycleView_hoadon_activity);
        mDatPhongDao = new datPhongDao(hoaDon.this);
        mKhachHangDao = new khachHangDao(hoaDon.this);
        mNhanVienDao = new nhanVienDao(hoaDon.this);
        mPhongDao = new phongDao(hoaDon.this);
        //
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycleView_hoadon_activity.setLayoutManager(layoutManager);
        hoadon_adapter = new hoaDonAdapter(hoaDon.this, new sendHoaDon() {
            @Override
            public void sendHoaDon(hoaDonObj items) {
                clickItenHoaDon(items);
            }
        });
        capNhapDuLieu();
        //
    }
    private List<hoaDonObj> getListData(){
        return mHoaDonDao.getAll();
    }
    private void capNhapDuLieu(){
        hoadon_adapter.setmList(getListData());
        recycleView_hoadon_activity.setAdapter(hoadon_adapter);
    }
    private void clickItenHoaDon(hoaDonObj items){
        mDatPhongObj = mDatPhongDao.getBymaDatPhong(items.getMaDatPhong());
        mNhanVien = mNhanVienDao.getByMaNhanVien(mDatPhongObj.getMaNhanVien());
        mKhachHangObj = mKhachHangDao.getByMaKh(mDatPhongObj.getMaKh());
        mPhongObj = mPhongDao.getByMaPhong(mDatPhongObj.getMaPhong());
        //
        chiTietDV_adapter = new itemRcy_dichVu_chiTietPhieuDat(hoaDon.this, new sendChiTietDichVu() {
            @Override
            public void sendChiTietDichVu(chiTietDichVuOBJ items) {
            }
        });
        chiTietDV_adapter.setmList(getListCTHV(items.getMaDatPhong()));
        //
        Dialog dialog = new Dialog(hoaDon.this);
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
        chiTietDichVuDao chiTietDichVuDao = new chiTietDichVuDao(hoaDon.this);
        return chiTietDichVuDao.getLisByMaDP(mahd);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Ngày tháng,tên phòng,CMT...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                hoadon_adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                hoadon_adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}