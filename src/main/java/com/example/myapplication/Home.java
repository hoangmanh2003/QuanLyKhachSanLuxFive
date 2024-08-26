package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.AdapterManager.manHinhChinhAdapter;
import com.example.myapplication.DbManager.nhanVienDao;
import com.example.myapplication.ObjectManager.manHinhChinhObj;
import com.example.myapplication.ObjectManager.nhanVienObj;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private manHinhChinhAdapter adapter;
    private Intent mIntent;

    private nhanVienDao nvDao;
    private String maNV;
    private nhanVienObj item_nhanVien;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Lux Five Hotel Manager");
        mRecyclerView = findViewById(R.id.activity_home_recycleView);
        mDrawerLayout = findViewById(R.id.activity_home_drawer);
        mNavigationView = findViewById(R.id.activity_home_navigation);

        nvDao = new nhanVienDao(Home.this);
        //nVienObj
        //
        SharedPreferences sharedPreferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE);
        maNV = sharedPreferences.getString("maNv","");
        item_nhanVien = nvDao.getByMaNhanVien(maNV);
        View headerView = mNavigationView.getHeaderView(0);
        CircleImageView avatar = headerView.findViewById(R.id.title_navigation_img);
        if (item_nhanVien.getMaNhanVien() == null){
            avatar.setImageResource(R.drawable.user);
        }
        avatar.setImageURI(Uri.parse(item_nhanVien.getAnhNhanVien()));
        TextView name = headerView.findViewById(R.id.title_navigation_text);
        name.setText(item_nhanVien.getTenNhanVien());
        //
        adapter = new manHinhChinhAdapter(Home.this, new manHinhChinhAdapter.senData() {
            @Override
            public void sendDada(manHinhChinhObj items) {
                chuyenManHinh(items);
            }

            private void chuyenManHinh(manHinhChinhObj items) {
                switch (items.getName().trim()) {
                    case "Quản lý Tầng":
                        mIntent = new Intent(Home.this, quanLyTang.class);
                        startActivity(mIntent);
                        break;
                    case "Loại phòng":
                        mIntent = new Intent(Home.this, loaiPhong.class);
                        startActivity(mIntent);
                        break;
                    case "Khách hàng":
                        mIntent = new Intent(Home.this, khachHang.class);
                        startActivity(mIntent);
                        break;
                    case "Dịch vụ":
                        mIntent = new Intent(Home.this, dichVu.class);
                        startActivity(mIntent);
                        break;
                    case "Hóa đơn":
                        mIntent = new Intent(Home.this, hoaDon.class);
                        startActivity(mIntent);
                        break;
                    case "Nhân viên":
                        mIntent = new Intent(Home.this, nhanVien.class);
                        startActivity(mIntent);
                        break;
                    case "Doanh thu":
                        mIntent = new Intent(Home.this, doanhThu.class);
                        startActivity(mIntent);
                        break;
                    case "Quản lý phòng":
                        mIntent = new Intent(Home.this, quanLyPhong.class);
                        startActivity(mIntent);
                        break;
                    case "Đặt phòng":
                        mIntent = new Intent(Home.this, manHinhPhieuDat.class);
                        startActivity(mIntent);
                        break;
                    case "Phiếu đặt":
                        mIntent = new Intent(Home.this, quanLyPhieuDat.class);
                        startActivity(mIntent);
                }
            }
        });

        adapter.setmList(getmList());
        mRecyclerView.setAdapter(adapter);
        // click vào item trong navigation
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            private Intent intent;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_nav_taiKhoanCuaBan:
                        intent = new Intent(Home.this, updateNhanVien.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_nav_dangXuat:

                        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                        builder.setTitle("thoát ứng dụng ? ");
                        builder.setMessage("bạn muốn thoát ứng dụng !");
                        builder.setNegativeButton("có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                intent = new Intent(Home.this, manHinhDangNhap.class);
                                startActivity(intent);
                                finish();

                            }
                        });


                        builder.setPositiveButton("không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });


                        builder.create();

                        builder.show();
                        break;


                }
                return true;
            }
        });

    }

    // chung set giá trị cho list
    private List<manHinhChinhObj> getmList() {
        List<manHinhChinhObj> list = new ArrayList<>();
        list.add(new manHinhChinhObj(R.drawable.tang_icon, "Quản lý Tầng"));
        list.add(new manHinhChinhObj(R.drawable.phong_icon, "Quản lý phòng"));
        list.add(new manHinhChinhObj(R.drawable.reload, "Đặt phòng"));
        list.add(new manHinhChinhObj(R.drawable.lich, "Phiếu đặt"));
        list.add(new manHinhChinhObj(R.drawable.hoa_don_icon, "Hóa đơn"));
        list.add(new manHinhChinhObj(R.drawable.phong_don_icon, "Loại phòng"));
        list.add(new manHinhChinhObj(R.drawable.user, "Khách hàng"));
        list.add(new manHinhChinhObj(R.drawable.dich_vu_icon, "Dịch vụ"));

        list.add(new manHinhChinhObj(R.drawable.quan_ly_nhanvien_icon, "Nhân viên"));
        list.add(new manHinhChinhObj(R.drawable.thongke, "Doanh thu"));

        return list;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home_navigation:
                mDrawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}