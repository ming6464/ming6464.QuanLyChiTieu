package com.ming6464.quanlychitieu.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ming6464.quanlychitieu.DTO.Khoan;
import com.ming6464.quanlychitieu.R;

import java.util.ArrayList;

public class Adapter_khoan extends RecyclerView.Adapter<Adapter_khoan.MyViewHolder> {
    private ArrayList<Khoan> list;
    private OnActionSuaXoa aciton;
    public Adapter_khoan(OnActionSuaXoa aciton){
        this.aciton = aciton;
    }

    public void setData(ArrayList<Khoan> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_khoan_thu_chi,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Khoan obj = list.get(position);
        holder.STT.setText(position + ": ");
        holder.ma.setText(obj.getMaLoai());
        holder.giaTien.setText(String.valueOf(obj.getKhoanTien()));
        holder.xoa.setOnClickListener(view -> aciton.actionXoa(position));
        holder.sua.setOnClickListener(view -> aciton.actionSua(position));
    }

    @Override
    public int getItemCount() {
        if(list == null)
            return 0;
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageButton xoa,sua;
        private TextView ma,giaTien,STT;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ma = itemView.findViewById(R.id.item_khoan_thu_chi_tv_maThuChi);
            giaTien = itemView.findViewById(R.id.item_khoan_thu_chi_tv_giaTien);
            STT = itemView.findViewById(R.id.item_khoan_thu_chi_tv_STT);
            xoa = itemView.findViewById(R.id.item_khoan_thu_chi_imgBtn_remove);
            sua = itemView.findViewById(R.id.item_khoan_thu_chi_imgBtn_edit);
        }
    }

    public interface OnActionSuaXoa{
        void actionSua(int index);
        void actionXoa(int index);
    }
}
