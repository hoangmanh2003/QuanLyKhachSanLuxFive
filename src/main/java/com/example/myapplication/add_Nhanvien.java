package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.DbManager.nhanVienDao;
import com.example.myapplication.InterfaceManager.sendNhanVien;
import com.example.myapplication.ObjectManager.nhanVienObj;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class add_Nhanvien extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_REQUEST = 123;
    private sendNhanVien dataIntent;

    List<nhanVienObj> listNhanVien;
    private static final int REQUEST_CODE = 1;

    ImageView dialog_add_nhan_vien_img_back;
    CircleImageView dialog_add_nhan_vien_avata;
    TextInputLayout dialog_add_nhan_vien_textInput_manv, dialog_add_nhan_vien_textInput_sdt, dialog_add_nhan_vien_textInput_matkhau,
            dialog_add_nhan_vien_textInput_matkhauNhaplai, dialog_add_nhan_vien_textInput_tenNv;
    TextInputEditText dialog_add_nhan_vien_Edt_manv, dialog_add_nhan_vien_Edt_sdt, dialog_add_nhan_vien_Edt_tenNv, dialog_add_nhan_vien_Edt_matkhau,
            dialog_add_nhan_vien_Edt_matkhauNhaplai;

    Button btn_them;
    nhanVienDao dao;
    nhanVien nv;
    Uri uri2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_update_nhan_vien);
        getSupportActionBar().setTitle("Thêm nhân Viên");

        intui();
        dao = new nhanVienDao(add_Nhanvien.this);

        dialog_add_nhan_vien_avata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openAddNhanVien();
                Log.d("zzz", "" + Uri.parse(String.valueOf(dialog_add_nhan_vien_avata.getId())));


            }
        });


    }


    private void intui() {
        dialog_add_nhan_vien_img_back = findViewById(R.id.dialog_add_nhan_vien_img_back);

        dialog_add_nhan_vien_avata = findViewById(R.id.dialog_add_nhan_vien_avata);
        // init text input
        dialog_add_nhan_vien_textInput_manv = findViewById(R.id.dialog_add_nhan_vien_textInput_manv);
        dialog_add_nhan_vien_textInput_sdt = findViewById(R.id.dialog_add_nhan_vien_textInput_sdt);
        dialog_add_nhan_vien_textInput_matkhau = findViewById(R.id.dialog_add_nhan_vien_textInput_matkhau);
        dialog_add_nhan_vien_textInput_matkhauNhaplai = findViewById(R.id.dialog_add_nhan_vien_textInput_matkhauNhaplai);
        dialog_add_nhan_vien_textInput_tenNv = findViewById(R.id.dialog_add_nhan_vien_textInput_tenNv);
        // init text edit text
        dialog_add_nhan_vien_Edt_manv = findViewById(R.id.dialog_add_nhan_vien_Edt_manv);
        dialog_add_nhan_vien_Edt_sdt = findViewById(R.id.dialog_add_nhan_vien_Edt_sdt);
        dialog_add_nhan_vien_Edt_tenNv = findViewById(R.id.dialog_add_nhan_vien_Edt_tenNv);
        dialog_add_nhan_vien_Edt_matkhau = findViewById(R.id.dialog_add_nhan_vien_Edt_matkhau);
        dialog_add_nhan_vien_Edt_matkhauNhaplai = findViewById(R.id.dialog_add_nhan_vien_Edt_matkhauNhaplai);

        btn_them = findViewById(R.id.dialog_add_nhan_vien_btnThem);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Glide.with(add_Nhanvien.this).load(data.getDataString()).into(dialog_add_nhan_vien_avata);
            uri2 = data.getData();
            Log.d("zzzz", data.getDataString());
        }
    }


    private void openAddNhanVien() {
        if (!dialog_add_nhan_vien_Edt_manv.getText().toString().isEmpty() && !dialog_add_nhan_vien_Edt_matkhau.getText().toString().isEmpty() &&
                !dialog_add_nhan_vien_Edt_matkhauNhaplai.getText().toString().isEmpty() &&
                !dialog_add_nhan_vien_Edt_sdt.getText().toString().isEmpty() && !dialog_add_nhan_vien_Edt_tenNv.getText().toString().isEmpty()) {
            if (dialog_add_nhan_vien_Edt_matkhauNhaplai.getText().toString().equals(dialog_add_nhan_vien_Edt_matkhau.getText().toString())) {
                if (!dao.CheckByMaNhanVien(dialog_add_nhan_vien_Edt_manv.getText().toString(), dialog_add_nhan_vien_Edt_matkhauNhaplai.getText().toString())) {
                    nhanVienObj nhanvien = new nhanVienObj();
                    nhanvien.setMaNhanVien(dialog_add_nhan_vien_Edt_manv.getText().toString());
                    nhanvien.setTenNhanVien(dialog_add_nhan_vien_Edt_tenNv.getText().toString());
                    nhanvien.setSoDienThoai(dialog_add_nhan_vien_Edt_sdt.getText().toString());
                    if (uri2 == null) {
                        int resID = getResources().getIdentifier("user", "drawable", getPackageName());
                        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + resID);

                        nhanvien.setAnhNhanVien(uri.toString());
                    } else {
                        nhanvien.setAnhNhanVien(uri2.toString());
                    }

                    nhanvien.setMatKhau(dialog_add_nhan_vien_Edt_matkhauNhaplai.getText().toString());
                    if (dao.insertNhanVien(nhanvien) > 0) {
                        Toast.makeText(add_Nhanvien.this, "thêm nhân viên Thành công", Toast.LENGTH_SHORT).show();

                        finish();

                    } else {
                        return;
                    }
                } else {
                    Toast.makeText(add_Nhanvien.this, "tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
                }
            } else {
                dialog_add_nhan_vien_textInput_matkhauNhaplai.setError("xem lại mật khẩu");
                dialog_add_nhan_vien_textInput_matkhau.setError("xe lại mật khẩu");
            }
        } else {
            dialog_add_nhan_vien_textInput_manv.setError(dialog_add_nhan_vien_Edt_manv.getText().toString().isEmpty() ? "mã nhân viên trống !" : "");
            dialog_add_nhan_vien_textInput_tenNv.setError(dialog_add_nhan_vien_Edt_tenNv.getText().toString().isEmpty() ? "tên nhân viên trống !" : "");
            dialog_add_nhan_vien_textInput_matkhau.setError(dialog_add_nhan_vien_Edt_matkhau.getText().toString().isEmpty() ? "mật khẩu không đc để trống" : "");
            dialog_add_nhan_vien_textInput_matkhauNhaplai.setError(dialog_add_nhan_vien_Edt_matkhauNhaplai.getText().toString().isEmpty() ? "không đc để trống" : "");
            if (dialog_add_nhan_vien_Edt_sdt.getText().toString().isEmpty()) {
                dialog_add_nhan_vien_textInput_sdt.setError("sdt không được để trông");
            } else {
                if (TextUtils.isDigitsOnly(dialog_add_nhan_vien_Edt_sdt.getText().toString()) && dialog_add_nhan_vien_Edt_sdt.getText().toString().length() == 10) {
                    dialog_add_nhan_vien_textInput_sdt.setError("");
                } else {
                    dialog_add_nhan_vien_textInput_sdt.setError("không phải số điện thoại");
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        } else {
            Toast.makeText(nv, "chưa cấp quyền thư viện", Toast.LENGTH_SHORT).show();
        }


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