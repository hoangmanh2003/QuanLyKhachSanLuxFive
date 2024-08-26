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

import com.example.myapplication.DbManager.chiTietDichVuDao;
import com.example.myapplication.DbManager.dichVuDao;
import com.example.myapplication.InterfaceManager.sendChiTietDichVu;
import com.example.myapplication.ObjectManager.chiTietDichVuOBJ;
import com.example.myapplication.ObjectManager.dichVuObj;
import com.example.myapplication.R;

import java.util.List;

public class chiTietDichVuAdapter extends
                            RecyclerView.Adapter<chiTietDichVuAdapter.chiTietDichVuViewHolder> {
    private Context mContext;
    private List<chiTietDichVuOBJ> mList;
    private List<chiTietDichVuOBJ> mListOld;
    private sendChiTietDichVu listener;
    private dichVuDao mDichVuDao;

    public chiTietDichVuAdapter(Context mContext, sendChiTietDichVu listener) {
        this.mContext = mContext;
        this.listener = listener;
        mDichVuDao = new dichVuDao(mContext);
    }
    public void setmList(List<chiTietDichVuOBJ> mList){
        this.mList = mList;
        this.mListOld = this.mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public chiTietDichVuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chi_tiet_dich_vu
                        ,parent,false);
        return new chiTietDichVuViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull chiTietDichVuViewHolder holder, int position) {
        chiTietDichVuOBJ items = mList.get(position);
        if (items== null){
            return;
        }
        dichVuObj itemDichVu = mDichVuDao.getByMaDV(items.getMaDichVu());
        holder.tenDichVu.setText(itemDichVu.getTenDichVu());
        holder.soLuong.setText(items.getSoLuong());
        holder.tongTien.setText(items.tongTien()+"");
    }

    @Override
    public int getItemCount() {
        if (mList != null){
            return mList.size();
        }
        return 0;
    }
    public final class chiTietDichVuViewHolder extends RecyclerView.ViewHolder{
       TextView tenDichVu, soLuong, tongTien;
        public chiTietDichVuViewHolder(@NonNull View itemView) {
            super(itemView);
            tenDichVu = itemView.findViewById(R.id.item_chi_tiet_dich_vu_tenDV);
            soLuong = itemView.findViewById(R.id.item_chi_tiet_dich_vu_soLuong);
            tongTien = itemView.findViewById(R.id.item_chi_tiet_dich_vu_tongTien);
        }
    }

}
