package com.example.myapplication.AdapterManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DbManager.datPhongDao;
import com.example.myapplication.DbManager.phongDao;
import com.example.myapplication.InterfaceManager.sendHoaDon;
import com.example.myapplication.ObjectManager.datPhongObj;
import com.example.myapplication.ObjectManager.hoaDonObj;
import com.example.myapplication.ObjectManager.khachHangObj;
import com.example.myapplication.ObjectManager.phongObj;
import com.example.myapplication.R;

import java.util.List;

public class doanhthu_Adapter extends RecyclerView.Adapter<doanhthu_Adapter.DoanhThuHoder> implements Filterable {
    private Context context;
    private sendHoaDon mListener;
    private List<hoaDonObj> list;
    private List<hoaDonObj> listOld;
    private datPhongDao mDatPhongDao;
    private phongDao mPhongDao;
    private datPhongObj mDatPhongObj;
    private phongObj mPhongObj;

    public doanhthu_Adapter(Context context, sendHoaDon mListener){
        this.context = context;
        this.mListener = mListener;
        mDatPhongDao = new datPhongDao(context);
        mPhongDao = new phongDao(context);
    }

    public void setList(List<hoaDonObj> list){
        this.list = list;
        this.listOld = this.list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DoanhThuHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doanhthu,parent,false);
        return new DoanhThuHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoanhThuHoder holder, int position) {
        hoaDonObj items = list.get(position);
        if (items == null){
            return;
        }
        mDatPhongObj = mDatPhongDao.getBymaDatPhong(items.getMaDatPhong());
        mPhongObj = mPhongDao.getByMaPhong(mDatPhongObj.getMaPhong());

        holder.item_txt_so_phong.setText(items.getMaDatPhong());
//        holder.item_txt_ten_nhanVien.setText(items.get);
        holder.item_txt_so_tien.setText(items.getTongTien() + " VND");

    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            }
        };
    }

    public class DoanhThuHoder extends RecyclerView.ViewHolder {

        TextView item_txt_so_phong,item_txt_ten_nhanVien,item_txt_so_tien;

        public DoanhThuHoder(@NonNull View itemView) {
            super(itemView);
            item_txt_so_phong = itemView.findViewById(R.id.item_doanhthu_tv_tenphong);
            item_txt_ten_nhanVien = itemView.findViewById(R.id.item_doanhthu_tv_nhanvien);
            item_txt_so_tien = itemView.findViewById(R.id.item_doanhthu_tv_tongtienhoadon);
        }
    }


}
