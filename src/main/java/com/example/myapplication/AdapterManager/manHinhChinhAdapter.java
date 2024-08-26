package com.example.myapplication.AdapterManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ObjectManager.manHinhChinhObj;
import com.example.myapplication.R;

import java.util.List;

public class manHinhChinhAdapter extends RecyclerView.Adapter<manHinhChinhAdapter.manHinhChinhViewHolder>{
    private Context mContext;
    private List<manHinhChinhObj> mList;
    private List<manHinhChinhObj> mListOld;
    private senData mListener;

    public manHinhChinhAdapter(Context mContext, senData mListener) {
        this.mContext = mContext;
        this.mListener = mListener;
    }
    public void setmList(List<manHinhChinhObj> mList){
        this.mList = mList;
        this.mListOld = this.mList;
        notifyDataSetChanged();
    }
    public interface senData{
        void sendDada(manHinhChinhObj items);
    }

    @NonNull
    @Override
    public manHinhChinhViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout_main,parent,false);
        return new manHinhChinhViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull manHinhChinhViewHolder holder, int position) {
            manHinhChinhObj items = mList.get(position);
            if (items == null){
                return;
            }
            holder.mImageView.setImageResource(items.getImage());
            holder.mTextView.setText(items.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.sendDada(items);
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

    public class manHinhChinhViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextView;
        public manHinhChinhViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.item_layout_main_image);
            mTextView = itemView.findViewById(R.id.item_layout_main_name);
        }
    }
}
