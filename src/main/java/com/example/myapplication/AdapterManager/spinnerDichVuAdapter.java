package com.example.myapplication.AdapterManager;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.ObjectManager.dichVuObj;
import com.example.myapplication.ObjectManager.loaiPhongObj;
import com.example.myapplication.R;

import java.util.List;

public class spinnerDichVuAdapter extends ArrayAdapter<dichVuObj> {

    public spinnerDichVuAdapter(@NonNull Context context, int resource, @NonNull List<dichVuObj> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_spinner_select,parent,false);
        TextView mTextView = convertView.findViewById(R.id.item_spinner_select_tenLoai);
        dichVuObj items = this.getItem(position);
        if (items != null){
            mTextView.setText(items.getTenDichVu());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_spinner_loai_phong,parent,false);
        TextView mTextView = convertView.findViewById(R.id.item_spinner_loai_phong_tenLoai);
        dichVuObj items = this.getItem(position);
        if (items != null){
            mTextView.setText(items.getTenDichVu());
        }
        return convertView;

    }
}
