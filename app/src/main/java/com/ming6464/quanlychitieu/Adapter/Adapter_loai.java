package com.ming6464.quanlychitieu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.quanlychitieu.DTO.Loai;
import com.ming6464.quanlychitieu.R;

import java.util.ArrayList;

public class Adapter_loai extends RecyclerView.Adapter<Adapter_loai.MyViewHolder>{

    private ArrayList<Loai> list;
    private OnActionSuaXoa action;
    public Adapter_loai(OnActionSuaXoa action){
        this.action = action;
    }

    public void setData(ArrayList<Loai> list){
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loai_thu_chi,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Loai obj = list.get(position);
        holder.STT.setText(String.valueOf(position));
        holder.tenLoai.setText(obj.getTenLoai());
        holder.ma.setText(obj.getMaLoai());
        holder.sua.setOnClickListener(view -> action.actionSua(holder.getAdapterPosition()));
        holder.xoa.setOnClickListener(view -> action.actionXoa(holder.getAdapterPosition()));
    }

    public interface OnActionSuaXoa{
        void actionSua(int index);
        void actionXoa(int index);
    }

    @Override
    public int getItemCount() {
        if(list == null)
            return 0;
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageButton xoa,sua;
        private TextView ma,tenLoai,STT;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ma = itemView.findViewById(R.id.item_loai_thu_chi_tv_maThuChi);
            tenLoai = itemView.findViewById(R.id.item_loai_thu_chi_tv_tenLoai);
            STT = itemView.findViewById(R.id.item_loai_thu_chi_tv_STT);
            xoa = itemView.findViewById(R.id.item_loai_thu_chi_imgBtn_remove);
            sua = itemView.findViewById(R.id.item_loai_thu_chi_imgBtn_edit);
        }
    }
}
