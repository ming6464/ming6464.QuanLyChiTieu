package com.ming6464.quanlychitieu.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ming6464.quanlychitieu.Adapter.Adapter_thong_ke;
import com.ming6464.quanlychitieu.DAO.DAO_khoan;
import com.ming6464.quanlychitieu.DAO.DAO_loai;
import com.ming6464.quanlychitieu.DTO.Loai;
import com.ming6464.quanlychitieu.DbHelper.Database;
import com.ming6464.quanlychitieu.R;

import java.util.ArrayList;


public class Fragment_ThongKe extends Fragment implements View.OnClickListener {
    private TextView khoanThu,khoanChi;
    private RecyclerView recyclerView;
    private ArrayList<Integer> listTien;
    private ArrayList<String> listTen;
    private Adapter_thong_ke adapter;
    private DAO_khoan db_khoan;
    private DAO_loai db_loai;
    private ImageView bgKhoanChi,bgKhoanThu;

    public static Fragment_ThongKe newInstance() {
        Fragment_ThongKe fragment = new Fragment_ThongKe();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment__thong_ke, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        khoanThu = view.findViewById(R.id.frag_thong_ke_tv_khoanThu);
        khoanChi = view.findViewById(R.id.frag_thong_ke_tv_khoanChi);
        recyclerView = view.findViewById(R.id.frag_thong_ke_rv);
        bgKhoanThu = view.findViewById(R.id.frag_thong_ke_img_backgroundKhoanThu);
        bgKhoanChi = view.findViewById(R.id.frag_thong_ke_img_backgroundKhoanChi);
        listTen = new ArrayList<>();
        listTien = new ArrayList<>();
        db_khoan = new DAO_khoan(getActivity(), Database.KH_THU);
        db_loai = new DAO_loai(getActivity(),Database.KH_THU);
        bgKhoanChi.setSelected(false);
        bgKhoanThu.setSelected(true);
        init();
    }

    private void init() {
        addData();
        addList();
        addRecyclerView();
        addAction();
    }

    private void addData() {
        db_khoan.changeKH(Database.KH_CHI);
        khoanChi.setText(String.valueOf(db_khoan.readTongTien()));
        db_khoan.changeKH(Database.KH_THU);
        khoanThu.setText(String.valueOf(db_khoan.readTongTien()));
    }

    private void addAction() {
        bgKhoanChi.setOnClickListener(this::onClick);
        bgKhoanThu.setOnClickListener(this::onClick);
    }

    private void addRecyclerView() {
        adapter = new Adapter_thong_ke();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setData(listTen,listTien);
    }

    private void addList() {
        listTen = db_loai.readTen();
        listTien = new ArrayList<>();
        for(String x : db_loai.readMa()){
            listTien.add(db_khoan.readTongTienWithMa(x));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.frag_thong_ke_img_backgroundKhoanThu:
                actionKhoanThu();
                break;
            default:
                actionKhoanchi();
        }
    }

    private void changeKH(String kh){
        db_loai.changeKH(kh);
        db_khoan.changeKH(kh);
    }

    private void actionKhoanchi() {
        changeKH(Database.KH_CHI);
        addList();
        bgKhoanChi.setSelected(true);
        bgKhoanThu.setSelected(false);
        adapter.setData(listTen,listTien);

    }

    private void actionKhoanThu() {
        bgKhoanChi.setSelected(false);
        bgKhoanThu.setSelected(true);
        changeKH(Database.KH_THU);
        addList();
        adapter.setData(listTen,listTien);
    }

}