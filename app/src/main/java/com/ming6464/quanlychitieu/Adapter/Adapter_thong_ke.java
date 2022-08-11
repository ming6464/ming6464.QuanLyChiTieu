package com.ming6464.quanlychitieu.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.quanlychitieu.R;

import java.util.ArrayList;

public class Adapter_thong_ke  extends RecyclerView.Adapter<Adapter_thong_ke.MyViewHolder>{
    private ArrayList<String> listTen;
    private ArrayList<Integer> listTien;
    public Adapter_thong_ke(){
    }
    public void setData(ArrayList<String> listTen,ArrayList<Integer> listTien){
        this.listTen = listTen;
        this.listTien = listTien;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thong_ke,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.ten.setText(listTen.get(position));
        holder.gia.setText(String.valueOf(listTien.get(position)));
    }

    @Override
    public int getItemCount() {
        if(listTen == null)
            return 0;
        return listTen.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView ten,gia;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ten = itemView.findViewById(R.id.item_thong_ke_tv_ten);
            gia = itemView.findViewById(R.id.item_thong_ke_tv_gia);
        }
    }
}
