package com.ming6464.quanlychitieu.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ming6464.quanlychitieu.Adapter.Adapter_khoan;
import com.ming6464.quanlychitieu.Adapter.Adapter_spinner;
import com.ming6464.quanlychitieu.DAO.DAO_khoan;
import com.ming6464.quanlychitieu.DAO.DAO_loai;
import com.ming6464.quanlychitieu.DTO.Khoan;
import com.ming6464.quanlychitieu.DbHelper.Database;
import com.ming6464.quanlychitieu.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class Fragment_KhoanChi extends Fragment implements View.OnClickListener,Adapter_khoan.OnActionSuaXoa {
    private RecyclerView recyclerView;
    private FloatingActionButton floatSearch,floatAdd,floatAll;
    private EditText khoanTien,thanhTimKiem;
    private TextView title;
    private Spinner ma;
    private Button them,huy,timKiem;
    private String ma_loai,tien,duLieuTim;
    private int position;
    private boolean checkFloat;
    private ArrayList<Khoan> list;
    private ArrayList<Khoan> list_tam;
    private ArrayList<String> list_ma;
    private Adapter_spinner adapter_spinner;
    private Adapter_khoan adapter;
    private DAO_khoan db;
    private AlertDialog.Builder builder;
    private DAO_loai db_loaiChi;

    public static Fragment_KhoanChi newInstance() {
        Fragment_KhoanChi fragment = new Fragment_KhoanChi();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment__chung, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onViewCreated(view, savedInstanceState);
        floatAll = view.findViewById(R.id.frag_chung_float);
        floatAdd = view.findViewById(R.id.frag_chung_floatAdd);
        floatSearch = view.findViewById(R.id.frag_chung_floatSearch);
        list = new ArrayList<>();
        list_ma = new ArrayList<>();
        list_tam = new ArrayList<>();
        recyclerView = view.findViewById(R.id.frag_chung_recyclerView);
        db = new DAO_khoan(getActivity(), Database.KH_CHI);
        db_loaiChi = new DAO_loai(getActivity(), Database.KH_CHI);
    }

    @Override
    public void onResume() {
        super.onResume();
        reLoad();
    }

    private void reLoad() {
        floatAll.setVisibility(View.VISIBLE);
        checkFloat = false;
        show_hide_float(View.GONE);
        addRecyclerView();
        addAdapterSpinner();
        addAction();
    }

    private void addRecyclerView() {
        list = db.read();
        adapter = new Adapter_khoan(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setData(list);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0){
                    floatAll.setVisibility(View.GONE);
                    show_hide_float(View.GONE);
                }
                else{
                    floatAll.setVisibility(View.VISIBLE);
                    if(checkFloat){
                        show_hide_float(View.VISIBLE);
                    }
                }
            }
        });
    }

    private void addAdapterSpinner() {
        list_ma = db_loaiChi.readMaVsLoai();
        adapter_spinner = new Adapter_spinner(list_ma,getActivity());
    }

    private void addAction() {
        floatAll.setOnClickListener(this);
        floatAdd.setOnClickListener(this::onClick);
        floatSearch.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.frag_chung_float:
                actionFloatAll();
                break;
            case R.id.frag_chung_floatSearch:
                actionFloatSearch();
                break;
            case R.id.frag_chung_floatAdd:
                acitonFloatAdd();
        }
    }

    private void acitonFloatAdd() {
        show_hide_float(View.GONE);
        checkFloat = false;
        if(list_ma.size() == 0){
            Toast.makeText(getActivity(), "Hãy thêm tạo thêm loại chi", Toast.LENGTH_SHORT).show();
            return;
        }
        ma_loai = list_ma.get(0);
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_khoan);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //
        khoanTien = dialog.findViewById(R.id.dialog_khoan_et_ten);
        ma = dialog.findViewById(R.id.dialog_khoan_sp_ma);
        them = dialog.findViewById(R.id.dialog_khoan_btn_them);
        huy = dialog.findViewById(R.id.dialog_khoan_btn_huy);
        //
        ma.setAdapter(adapter_spinner);
        ma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ma_loai = list_ma.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //
        huy.setOnClickListener(view -> dialog.cancel());
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tien = khoanTien.getText().toString();
                if(check(tien)){
                    ma_loai = ma_loai.substring(0,ma_loai.lastIndexOf("-"));
                    Khoan obj = new Khoan(db.getSTT(),Integer.parseInt(tien),ma_loai);
                    db.create(obj);
                    list.add(obj);
                    if(list_tam.size() > 0){
                        adapter.setData(list);
                    }
                    else
                        adapter.notifyItemInserted(list.size() - 1);
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }

            private boolean check(String t) {
                DrawableCompat.setTint(khoanTien.getBackground(), ContextCompat.getColor(getActivity(),R.color.purple_700));
                if(t.isEmpty()){
                    DrawableCompat.setTint(khoanTien.getBackground(),ContextCompat.getColor(getActivity(),R.color.red));
                    Toast.makeText(getActivity(),"Thông tin không được bỏ trống",Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }
        });
        dialog.show();
    }

    private void actionFloatSearch() {
        show_hide_float(View.GONE);
        checkFloat = false;
        if(list.size() == 0){
            Toast.makeText(getActivity(), "Dữ liệu trống", Toast.LENGTH_SHORT).show();
            return;
        }
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.item_search);
        Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        //
        thanhTimKiem = dialog.findViewById(R.id.item_search_et_thanhTimKiem);
        timKiem = dialog.findViewById(R.id.item_search_btn_timKiem);
        //
        timKiem.setOnClickListener(view -> {
            list_tam = new ArrayList<>();
            duLieuTim = thanhTimKiem.getText().toString();
            for(Khoan x : list){
                if(x.getMaLoai().equals(duLieuTim))
                    list_tam.add(x);
            }
            if(duLieuTim.length() == 0){
                adapter.setData(list);
                dialog.cancel();
                return;
            }
            adapter.setData(list_tam);
            dialog.cancel();
        });
        dialog.show();
    }

    private void actionFloatAll() {
        if(checkFloat){
            checkFloat = false;
            show_hide_float(View.GONE);
            return;
        }
        checkFloat = true;
        show_hide_float(View.VISIBLE);
    }

    private void show_hide_float(int visible){
        floatAdd.setVisibility(visible);
        floatSearch.setVisibility(visible);
    }

    @Override
    public void actionSua(int index) {
        Khoan obj = list.get(index);
        position = db_loaiChi.getIndex(obj.getMaLoai());
        ma_loai = list_ma.get(position);
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_khoan);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //
        khoanTien = dialog.findViewById(R.id.dialog_khoan_et_ten);
        ma = dialog.findViewById(R.id.dialog_khoan_sp_ma);
        them = dialog.findViewById(R.id.dialog_khoan_btn_them);
        huy = dialog.findViewById(R.id.dialog_khoan_btn_huy);
        title = dialog.findViewById(R.id.dialog_khoan_title);
        //
        them.setText("Update");
        ma.setAdapter(adapter_spinner);
        title.setText("Sửa Thông tin");
        khoanTien.setText(String.valueOf(obj.getSTT()));
        //
        ma.setSelection(position);
        ma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ma_loai = list_ma.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //
        huy.setOnClickListener(view -> dialog.cancel());
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tien = khoanTien.getText().toString();
                if(check(tien)){
                    ma_loai = ma_loai.substring(0,ma_loai.lastIndexOf("-"));
                    Khoan obj1 = new Khoan(db.getSTT(),Integer.parseInt(tien),ma_loai);
                    list.set(index,obj1);
                    db.update(obj.getSTT(),obj1);
                    adapter.notifyItemChanged(index);
                    Toast.makeText(getActivity(), "Update !", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }

            private boolean check(String t) {
                DrawableCompat.setTint(khoanTien.getBackground(), ContextCompat.getColor(getActivity(),R.color.purple_700));
                if(t.isEmpty()){
                    DrawableCompat.setTint(khoanTien.getBackground(),ContextCompat.getColor(getActivity(),R.color.red));
                    Toast.makeText(getActivity(),"Thông tin không được bỏ trống",Toast.LENGTH_SHORT).show();
                    return false;
                }
                return true;
            }
        });

        dialog.show();
    }

    @Override
    public void actionXoa(int index) {
        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Bạn có chắc chắn xoá không ?");
        builder.setNegativeButton("Xoá", (dialogInterface, i) -> {
            db.delete(list.get(index).getSTT());
            list.remove(index);
            adapter.notifyItemRemoved(index);
            adapter.notifyItemRangeChanged(index,list.size() - index);
        });
        builder.setPositiveButton("Bỏ", (dialogInterface, i) -> {
        });
        builder.show();
    }
}