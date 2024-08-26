package com.example.myapplication.AdapterManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DbManager.loaiPhongDao;
import com.example.myapplication.InterfaceManager.sendLoaiPhong;
import com.example.myapplication.ObjectManager.loaiPhongObj;
import com.example.myapplication.R;
import com.example.myapplication.loaiPhong;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class loaiPhongAdapter extends RecyclerView.Adapter<loaiPhongAdapter.loaiPhongViewHolder>
                                implements Filterable {
    private Context mContext;
    private List<loaiPhongObj> mList;
    private List<loaiPhongObj> mListNew;

    private sendLoaiPhong mListener;
    private loaiPhongDao mloaiPhongDao;

    public loaiPhongAdapter(Context mContext, sendLoaiPhong mListener) {
        this.mContext = mContext;
        this.mListener = mListener;
    }
    public void setmList(List<loaiPhongObj> mList){
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public loaiPhongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loai_phong
        ,parent,false);
        return new loaiPhongViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull loaiPhongViewHolder holder, @SuppressLint("RecyclerView") int position) {
        loaiPhongObj mloaiPhongObj = mList.get(position);
        if (mloaiPhongObj == null){
            return;
        }

        holder.edtTenLoaiPhong.setText(mloaiPhongObj.getTenLoaiPhong());

        //onclick Sửa hiện dialog
        holder.loaiPhongItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSuaLoaiPhongDialog(Gravity.CENTER, position);
            }
        });
        holder.edtTenLoaiPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSuaLoaiPhongDialog(Gravity.CENTER, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mList!= null){
            return mList.size();
        }
        return 0;
    }
    public final class loaiPhongViewHolder extends RecyclerView.ViewHolder {
        //khai báo các phần tử trong View
        TextInputEditText edtMaLoaiPhong,edtTenLoaiPhong;
        CardView loaiPhongItem;
        public loaiPhongViewHolder(@NonNull View itemView) {
            super(itemView);
            //gán view cho từng phần tử
            edtTenLoaiPhong = itemView.findViewById(R.id.edtTenLoaiPhong);
            loaiPhongItem = itemView.findViewById(R.id.loaiPhongItem);
        }
    }

    private void openSuaLoaiPhongDialog(int gravity, int pos){
        //Tạo Dialog
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_loai_phong);
        mloaiPhongDao = new loaiPhongDao(mContext);
        loaiPhongObj mloaiPhongObj = mList.get(pos);


        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity =gravity;
        window.setAttributes(windowAttribute);

        //Xử lý click ra ngoài dialog
        if(Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }
        //Khai báo các phần tử dialog
        Button btnHuy =dialog.findViewById(R.id.dialog_update_loai_phong_btnHuy);
        Button btnSua =dialog.findViewById(R.id.dialog_update_loai_phong_btnSua);
        EditText maLoai, tenLoai;
        tenLoai = dialog.findViewById(R.id.dialog_update_loai_phong_edtTenLoaiPhong);

        tenLoai.setText(mloaiPhongObj.getTenLoaiPhong());

        //Xử lý nút trong Dialog
            //Nút hủy thoát Dialog
            btnHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            //Nút Sửa thông tin
            btnSua.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tenLoai.getText().toString().isEmpty())
                    {
                        if (tenLoai.getText().toString().isEmpty()){
                            tenLoai.setError("Không được để trống");
                            return;
                        }
//                        if (maLoai.getText().toString().isEmpty()){
//                            maLoai.setError("Không được để trống");
//                            return;

                    }
                    else
                    {
//                        boolean checkTonTaiML = checkMaLoai(maLoai.getText().toString().trim());
//                        if (checkTonTaiML){
                            loaiPhongObj itemUpdate = new loaiPhongObj();
                            loaiPhongDao loaiPhongDao = new loaiPhongDao(mContext);
//                            itemUpdate.setMaLoai(maLoai.getText().toString().trim());
                            itemUpdate.setTenLoaiPhong(tenLoai.getText().toString().trim());
                            mloaiPhongDao.updateLoaiPhong(itemUpdate);
                            dialog.cancel();
                            updateData(pos , itemUpdate);
                            Toast.makeText(mContext, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    }
//                        else{
//                            Toast.makeText(mContext, "Mã loại đã tồn tại", Toast.LENGTH_SHORT).show();
//                        }


                }
        });


        dialog.show();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
    }


    public boolean checkMaLoai(String maLoai){
        boolean check = true;
        List<loaiPhongObj> loaiPhongs = mloaiPhongDao.getAll();
        if (loaiPhongs == null){
            check = true;
        }
        else{
            for (loaiPhongObj items : loaiPhongs){
                if (items.getMaLoai().toLowerCase().equals(maLoai)){
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
    private List<loaiPhongObj> getListLoaiPhong() {
        List<loaiPhongObj> list = mloaiPhongDao.getAll();
        return list;
    }

    public void updateData(int position, loaiPhongObj updatedObject){
        mList.set(position,updatedObject); // updating source
        notifyDataSetChanged(); // notify adapter to refresh
    }
}
