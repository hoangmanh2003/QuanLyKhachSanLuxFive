package com.example.myapplication;

import static android.util.Log.d;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.AdapterManager.chiTietDichVuAdapter;
import com.example.myapplication.AdapterManager.itemRcy_dichVu_chiTietPhieuDat;
import com.example.myapplication.AdapterManager.spinnerDichVuAdapter;
import com.example.myapplication.DbManager.chiTietDichVuDao;
import com.example.myapplication.DbManager.datPhongDao;
import com.example.myapplication.DbManager.dichVuDao;
import com.example.myapplication.DbManager.khachHangDao;
import com.example.myapplication.DbManager.nhanVienDao;
import com.example.myapplication.DbManager.phongDao;
import com.example.myapplication.InterfaceManager.sendChiTietDichVu;
import com.example.myapplication.ObjectManager.chiTietDichVuOBJ;
import com.example.myapplication.ObjectManager.datPhongObj;
import com.example.myapplication.ObjectManager.dichVuObj;
import com.example.myapplication.ObjectManager.khachHangObj;
import com.example.myapplication.ObjectManager.nhanVienObj;
import com.example.myapplication.ObjectManager.phongObj;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalTime;
import java.util.List;


public class chiTietHoaDon extends AppCompatActivity {
    private Intent mIntent;
    private String maDatPhong;
    private TextView tenKH, tenNguoiTao, tenPhongThue,
    ngayDat, ngayTra,tongTien,tongSoGio, giaThue;
    private FloatingActionButton btn_add_dv;
    private datPhongDao mDatPhongDao;
    private RecyclerView mListView;
    private datPhongObj mDatPhongObj;
    private khachHangDao mKhachHangDao;
    private phongDao mPhongDao;
    private chiTietDichVuDao mChiTietDichVuDao;
    private nhanVienDao mNhanVienDao;
    private nhanVienObj mNhanVienObj;
    private itemRcy_dichVu_chiTietPhieuDat adapter;
    private dichVuDao mDichVuDao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don);
        mDichVuDao = new dichVuDao(chiTietHoaDon.this);
        mIntent = getIntent();
        maDatPhong = mIntent.getStringExtra("maDatPhong");
        getSupportActionBar().setTitle("Chi tiết phiếu đặt");
        InitUI();
        btn_add_dv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themChiTietDichVu(maDatPhong);
            }
        });
        adapter =new itemRcy_dichVu_chiTietPhieuDat(chiTietHoaDon.this, new sendChiTietDichVu() {
            @Override
            public void sendChiTietDichVu(chiTietDichVuOBJ items) {
                suaDv(items);
            }
        });
        capNhapDuLieu();

    }
    private List<dichVuObj> getDichVu (){
        return mDichVuDao.getAll();
    }

    private void suaDv(chiTietDichVuOBJ items) {
        Dialog dialog = new Dialog(chiTietHoaDon.this, androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        dialog.setContentView(R.layout.dialog_update_chi_tiet_dich_vu);
        EditText tenDV, soLuong, giaDV; Button btn_xoa, btn_update;
        tenDV = dialog.findViewById(R.id.dialog_update_chi_tiet_dich_vu_tenDV);
        soLuong =dialog.findViewById(R.id.dialog_update_chi_tiet_dich_vu_soLuong);
        giaDV = dialog.findViewById(R.id.dialog_update_chi_tiet_dich_vu_giaTien);
        btn_xoa = dialog.findViewById(R.id.dialog_update_chi_tiet_dich_vu_xoa);
        btn_update = dialog.findViewById(R.id.dialog_update_chi_tiet_dich_vu_sua);
        dichVuObj mDichVuObj = mDichVuDao.getByMaDV(items.getMaDichVu());
        //
        tenDV.setText(mDichVuObj.getTenDichVu());
        soLuong.setText(items.getSoLuong());
        giaDV.setText(items.getGiaTien());

        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    XoaChiTietDV(items);
                    dialog.cancel();
                }
                catch (Exception x){
                    Toast.makeText(chiTietHoaDon.this, "Sai định dạng", Toast.LENGTH_SHORT).show();
                }

            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    updateChiTietDv(items,soLuong.getText().toString().trim(), giaDV.getText().toString().trim());
                    dialog.cancel();
                }
                catch (Exception e){
                    Toast.makeText(chiTietHoaDon.this, "Sai định dạng", Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialog.show();
    }

    private void updateChiTietDv(chiTietDichVuOBJ items,String soLuongDVCT, String giaDVCT) {
        AlertDialog.Builder builder = new AlertDialog.Builder(chiTietHoaDon.this);
        builder.setTitle("Dịch vụ sẽ được cập nhập ?");
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                chiTietDichVuOBJ itemUpdte = new chiTietDichVuOBJ();
                itemUpdte.setMaChiTietDV(items.getMaChiTietDV());
                itemUpdte.setMaDatPhong(items.getMaDatPhong());
                itemUpdte.setGiaTien(giaDVCT.trim());
                itemUpdte.setMaDichVu(items.getMaDichVu());
                itemUpdte.setSoLuong(soLuongDVCT.trim());
                mChiTietDichVuDao.updateChiTietDichVu(itemUpdte);
                Toast.makeText(chiTietHoaDon.this, "Cập nhập thành công", Toast.LENGTH_SHORT).show();
                capNhapDuLieu();
                tinhTongTien();
            }
        });
        builder.show();

    }

    private void XoaChiTietDV(chiTietDichVuOBJ items){
        AlertDialog.Builder builder = new AlertDialog.Builder(chiTietHoaDon.this);
        builder.setTitle("Bạn muốn xóa dịch vụ ?");
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mChiTietDichVuDao.deleteChiTietDichVu(items.getMaChiTietDV());
                Toast.makeText(chiTietHoaDon.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                capNhapDuLieu();
                tinhTongTien();
            }
        });builder.show();

    }

    private List<chiTietDichVuOBJ> listChiTietDV (){
        mChiTietDichVuDao = new chiTietDichVuDao(chiTietHoaDon.this);
        return mChiTietDichVuDao.getByMaDP(maDatPhong);
    }
    private void capNhapDuLieu(){
        adapter.setmList(listChiTietDV());
        mListView.setAdapter(adapter);
    }

    private void InitUI() {
        tenKH = findViewById(R.id.activity_chi_tiet_hoa_don_tenKh);
        tenNguoiTao = findViewById(R.id.activity_chi_tiet_hoa_don_nguoiTao);
        tenPhongThue = findViewById(R.id.activity_chi_tiet_hoa_don_tenPhong);
        ngayDat = findViewById(R.id.activity_chi_tiet_hoa_don_ngayDat);
        ngayTra = findViewById(R.id.activity_chi_tiet_hoa_don_ngayTra);
        tongTien = findViewById(R.id.activity_chi_tiet_hoa_don_tongTien);
        mListView = findViewById(R.id.activity_chi_tiet_hoa_don_lv_chiTietDV);
        btn_add_dv = findViewById(R.id.activity_chi_tiet_hoa_don_btn_sua);
        tongSoGio = findViewById(R.id.activity_chi_tiet_hoa_don_tongSoGio);
        giaThue = findViewById(R.id.activity_chi_tiet_hoa_don_giaThue);
        //
        mDatPhongDao = new datPhongDao(chiTietHoaDon.this);
        mKhachHangDao = new khachHangDao(chiTietHoaDon.this);
        mPhongDao = new phongDao(chiTietHoaDon.this);
        mNhanVienDao = new nhanVienDao(chiTietHoaDon.this);
        mChiTietDichVuDao = new chiTietDichVuDao(chiTietHoaDon.this);
        //
        mDatPhongObj = mDatPhongDao.getBymaDatPhong(maDatPhong);
        phongObj mPhongObj = mPhongDao.getByMaPhong(mDatPhongObj.getMaPhong());
        khachHangObj mKhachHangObj = mKhachHangDao.getByMaKh(mDatPhongObj.getMaKh());
        tenKH.setText(mKhachHangObj.getTenKh());
        //
        tenPhongThue.setText(mPhongObj.getTenPhong());
        mNhanVienObj = mNhanVienDao.getByMaNhanVien(mDatPhongObj.getMaNhanVien());
        tenNguoiTao.setText(mNhanVienObj.getTenNhanVien());
        ngayDat.setText(mDatPhongObj.getGioVao()+"/"+mDatPhongObj.getCheckIn());
        ngayTra.setText(mDatPhongObj.getGioRa()+"/"+mDatPhongObj.getNgayRa());
        tongSoGio.setText(mDatPhongObj.getSoGioDat());
        giaThue.setText(mDatPhongObj.getGiaTien()+" VND");
        //

        tinhTongTien();
    }

   private void  tinhTongTien(){
       float tongTienF = Float.parseFloat(mDatPhongObj.getTongTien())+   Float.parseFloat(String.valueOf(tinhTongTienDV(mDatPhongObj.getMaDatPhong())));
       String tongTienString = String.format("%.1f", tongTienF);
       tongTien.setText(tongTienString);
   }
    private float tinhTongTienDV(String maDatPhong){
       return mChiTietDichVuDao.tongTienDv(maDatPhong);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_back,menu);
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
    private void themChiTietDichVu(String id_chiTietDichVu) {
        dichVuDao mDichVuDao = new dichVuDao(chiTietHoaDon.this);
        List<dichVuObj> mlstDV = mDichVuDao.getAll();
        if (mlstDV.size() == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(chiTietHoaDon.this);
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
            Dialog dialog = new Dialog(chiTietHoaDon.this,
                    androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
            dialog.setContentView(R.layout.dialog_add_chi_tiet_dich_vu);
            Spinner spinner = dialog.findViewById(R.id.dialog_add_chi_tiet_dich_vu_spinner);
            EditText giaDv = dialog.findViewById(R.id.dialog_add_chi_tiet_dich_vu_giaTien);
            EditText soLuong = dialog.findViewById(R.id.dialog_add_chi_tiet_dich_vu_soLuong);
            Button btn_add = dialog.findViewById(R.id.dialog_add_chi_tiet_dich_vu_btnAdd);
            Button btn_huy = dialog.findViewById(R.id.dialog_add_chi_tiet_dich_vu_btnHuy);
            //
            spinnerDichVuAdapter adapter = new spinnerDichVuAdapter(chiTietHoaDon.this,
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
                        Toast.makeText(chiTietHoaDon.this, "Thêm dịch vụ thành công"
                                , Toast.LENGTH_SHORT).show();
                        tinhTongTien();
                    }

                }
            });
            dialog.show();
        }

    }
}