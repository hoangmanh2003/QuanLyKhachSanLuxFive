package com.example.myapplication.AdapterManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DbManager.khachHangDao;
import com.example.myapplication.DbManager.loaiPhongDao;
import com.example.myapplication.InterfaceManager.sendKhachHang;
import com.example.myapplication.ObjectManager.khachHangObj;
import com.example.myapplication.ObjectManager.loaiPhongObj;
import com.example.myapplication.R;
import com.example.myapplication.khachHang;

import java.util.ArrayList;
import java.util.List;

public class khachHangAdapter  extends RecyclerView.Adapter<khachHangAdapter.khachHangViewHolder>
                                implements Filterable {
    private Context mContext;
    private List<khachHangObj> mList;
    private List<khachHangObj> mListOld;
    private sendKhachHang mListener;
    private khachHangDao dao;

    public khachHangAdapter(Context mContext, sendKhachHang mListener) {
        this.mContext = mContext;
        this.mListener = mListener;
    }
    public void setmList(List<khachHangObj> mList){
        this.mList = mList;
        this.mListOld = this.mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public khachHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khach_hang
        ,parent,false);
        return new khachHangViewHolder(mView);

    }

    @Override
    public void onBindViewHolder(@NonNull khachHangViewHolder holder, int position) {
        khachHangObj items = mList.get(position);
        if (items == null){
            return;
        }
       holder.item_khach_hang_tv_ten.setText(items.getTenKh());
        holder.item_khach_hang_tv_sdt.setText(items.getSoDienThoai());

        holder.item_khach_hang_tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialogUpdateKhachHang(Gravity.CENTER,position,mList.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mList != null){
            return mList.size();
        }
        return 0;
    }
    public final class khachHangViewHolder extends RecyclerView.ViewHolder {
        TextView item_khach_hang_tv_ten , item_khach_hang_tv_sdt,item_khach_hang_tv_update;
        public khachHangViewHolder(@NonNull View itemView) {
            super(itemView);
            item_khach_hang_tv_ten = itemView.findViewById(R.id.item_khach_hang_tv_ten);
            item_khach_hang_tv_sdt = itemView.findViewById(R.id.item_khach_hang_tv_sdt);
            item_khach_hang_tv_update = itemView.findViewById(R.id.item_khach_hang_tv_update);
        }
    }
    private void ShowDialogUpdateKhachHang(int gravity, int pos, khachHangObj khachHangObj){
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_khach_hang);
        dao = new khachHangDao(mContext);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

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
        EditText edtCMT,edtTen,edtNgaySinh,edtSDT;
        edtCMT = dialog.findViewById(R.id.dialog_update_khach_hang_edittext_cmt);
        edtTen = dialog.findViewById(R.id.dialog_update_khach_hang_textEdit_nameKhach);
        edtNgaySinh = dialog.findViewById(R.id.dialog_update_khach_hang_textEdit_ngaySinh);
        edtSDT = dialog.findViewById(R.id.dialog_update_khach_hang_textEdit_soDt);
        ImageView imgBack = dialog.findViewById(R.id.dialog_khach_hang_vien_img_back);

        Button btnUpdate = dialog.findViewById(R.id.dialog_update_nhan_vien_btnUpdateKhachHang);

        edtCMT.setText(khachHangObj.getSoCMT());
        edtTen.setText(khachHangObj.getTenKh());
        edtNgaySinh.setText(khachHangObj.getNgaySinh());
        edtSDT.setText(khachHangObj.getSoDienThoai());

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CMT = edtCMT.getText().toString();
                String Ten = edtTen.getText().toString();
                String ngaySinh = edtNgaySinh.getText().toString();
                String SDT = edtSDT.getText().toString();
                if (CMT.isEmpty() || Ten.isEmpty() || ngaySinh.isEmpty() || SDT.isEmpty()){
                    if (CMT.isEmpty()){
                        edtCMT.setError("Không được để trống");
                        return;
                    }
                    if (Ten.isEmpty()){
                        edtTen.setError("Không được để trống");
                        return;
                    }
                    if (ngaySinh.isEmpty()){
                        edtNgaySinh.setError("Không được để trống");
                        return;
                    }
                    if (SDT.isEmpty()){
                        edtSDT.setError("Không được để trống");
                        return;
                    }
                }
                else{
                    boolean checkTonTaiML = dao.checkKhachHang(CMT.trim());
                    khachHangObj itemUpdate = new khachHangObj();
                    itemUpdate.setSoCMT(CMT.trim());
                    itemUpdate.setTenKh(Ten.trim());
                    itemUpdate.setNgaySinh(ngaySinh.trim());
                    itemUpdate.setSoDienThoai(SDT.trim());
                    if (dao.updateKhachHangObj(itemUpdate)>0){
                        dialog.cancel();
                        updateData(pos , itemUpdate);
                        Toast.makeText(mContext, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(mContext, "Mã loại đã tồn tại", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        dialog.show();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if (search.isEmpty()){
                    mList = mListOld;
                }
                else{
                    List<khachHangObj> list = new ArrayList<>();
                    for (khachHangObj x: mListOld){
                        if (x.getSoCMT().toLowerCase().contains(search.toLowerCase())||
                        x.getTenKh().toLowerCase().contains(search.toLowerCase())||
                        x.getSoDienThoai().toLowerCase().contains(search.toLowerCase())){
                            list.add(x);
                        }
                    }
                    mList = list;
                }
                FilterResults results = new FilterResults();
                results.values = mList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                    mList = (List<khachHangObj>) results.values;
                    notifyDataSetChanged();
            }
        };
    }
    public void updateData(int position, khachHangObj updatedObject){
        mList.set(position,updatedObject); // updating source
        notifyDataSetChanged(); // notify adapter to refresh
    }

}
