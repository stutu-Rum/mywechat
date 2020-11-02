package com.example.mywechat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//创建Adapter
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {
    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item,parent,false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.title.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder{
        public final TextView title;
        public VH( View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.friendText);
        }
    }

    private List<String> mDatas;
    public MyAdapter(List<String> data){
        this.mDatas = data;
    }
}
