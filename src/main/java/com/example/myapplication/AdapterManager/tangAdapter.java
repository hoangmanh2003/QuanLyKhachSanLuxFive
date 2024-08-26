package com.example.myapplication.AdapterManager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.InterfaceManager.sendTang;
import com.example.myapplication.ObjectManager.tangObj;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class tangAdapter extends RecyclerView.Adapter<tangAdapter.tangViewHolder>
        implements Filterable {
    private Context mContext;
    private List<tangObj> mList;
    private List<tangObj> mListOld;
    private sendTang mListener;

    public tangAdapter(Context mContext, sendTang mListener) {
        this.mContext = mContext;
        this.mListener = mListener;
    }
    public void setmList(List<tangObj> mList){
        this.mList = mList;
        this.mListOld = this.mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public tangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tang
        ,parent,false);
        return new tangViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull tangViewHolder holder, int position) {
        tangObj items = mList.get(position);
        if (items == null){
            return;
        }
        //todo...........
        holder.mTextView.setText(items.getTenTang());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.sendTang(items);
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
    public final class tangViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTextView;
        public tangViewHolder(@NonNull View itemView) {
            super(itemView);
            //todo..........
            mImageView = itemView.findViewById(R.id.item_tang_img);
            mTextView = itemView.findViewById(R.id.item_tang_tenTang);
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString();
                if (query.isEmpty()){
                    mList = mListOld;
                }
                else{
                    List<tangObj> list = new ArrayList<>();
                    for (tangObj x: mListOld){
                        if (x.getMaTang().toLowerCase().contains(query.toLowerCase())||
                        x.getTenTang().toLowerCase().contains(query.toLowerCase())){
                            list.add(x);
                        }
                    }
                    mList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mList = (List<tangObj>) results.values;
                notifyDataSetChanged();

            }
        };
    }
}
