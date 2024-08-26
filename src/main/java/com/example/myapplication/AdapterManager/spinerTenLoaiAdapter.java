package com.example.myapplication.AdapterManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.ObjectManager.loaiPhongObj;
import com.example.myapplication.R;

import org.w3c.dom.Text;

import java.util.List;

public class spinerTenLoaiAdapter extends ArrayAdapter<loaiPhongObj> {

    public spinerTenLoaiAdapter(@NonNull Context context, int resource, @NonNull List<loaiPhongObj> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_spinner_select,parent,false);
        TextView mTextView = convertView.findViewById(R.id.item_spinner_select_tenLoai);
        loaiPhongObj items = this.getItem(position);
        if (items != null){
            mTextView.setText(items.getTenLoaiPhong());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_spinner_loai_phong,parent,false);
        TextView mTextView = convertView.findViewById(R.id.item_spinner_loai_phong_tenLoai);
        loaiPhongObj items = this.getItem(position);
        if (items != null){
            mTextView.setText(items.getTenLoaiPhong());
        }

        return convertView;
    }
}
