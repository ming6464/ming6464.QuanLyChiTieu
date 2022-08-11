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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ming6464.quanlychitieu.Adapter.Adapter_loai;
import com.ming6464.quanlychitieu.DAO.DAO_loai;
import com.ming6464.quanlychitieu.DTO.Khoan;
import com.ming6464.quanlychitieu.DTO.Loai;
import com.ming6464.quanlychitieu.DbHelper.Database;
import com.ming6464.quanlychitieu.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Fragment_LoaiChi extends Fragment implements Adapter_loai.OnActionSuaXoa, View.OnClickListener {
    private RecyclerView recyclerView;
    private FloatingActionButton floatSearch,floatAdd,floatAll;
    private EditText ten,ma,thanhTimKiem;
    private TextView title;
    private boolean check,checkFloat;
    private Button them,huy,timKiem;
    private Adapter_loai adapter;
    private String duLieuTim;
    private ArrayList<Loai> list,list_tam;
    private Loai obj;
    private DAO_loai db;
    private AlertDialog.Builder builder;


    public static Fragment_LoaiChi newInstance() {
        Fragment_LoaiChi fragment = new Fragment_LoaiChi();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment__chung, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        floatAll = view.findViewById(R.id.frag_chung_float);
        floatAdd = view.findViewById(R.id.frag_chung_floatAdd);
        floatSearch = view.findViewById(R.id.frag_chung_floatSearch);
        recyclerView = view.findViewById(R.id.frag_chung_recyclerView);
        list_tam = new ArrayList<>();
        db = new DAO_loai(getActivity(), Database.KH_CHI);
        list = new ArrayList<>();
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
        addAciton();
        addAdapter();
    }

    private void addAdapter() {
        list = db.read();
        adapter = new Adapter_loai(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
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

    private void addAciton() {
        floatSearch.setOnClickListener(this::onClick);
        floatAdd.setOnClickListener(this::onClick);
        floatAll.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.frag_chung_float:
                actionFloatAll();
                break;
            case R.id.frag_chung_floatAdd:
                actionFloatAdd();
                break;
            case R.id.frag_chung_floatSearch:
                actionFloatSearch();
                break;
        }
    }

    private void actionFloatSearch() {
        list_tam.clear();
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
            for(Loai x : list){
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

    private void actionFloatAdd() {
        checkFloat = false;
        show_hide_float(View.GONE);
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_loai);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //
        ten = dialog.findViewById(R.id.dialog_loai_et_ten);
        ma = dialog.findViewById(R.id.dialog_loai_et_ma);
        them = dialog.findViewById(R.id.dialog_loai_btn_them);
        huy = dialog.findViewById(R.id.dialog_loai_btn_huy);
        title = dialog.findViewById(R.id.dialog_loai_tv_title);
        //
        title.setText("Thêm Loại Chi");
        //
        huy.setOnClickListener(view -> dialog.cancel());
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = ten.getText().toString(),m = ma.getText().toString();
                if(check(t,m)){
                    Loai obj = new Loai(m,t);
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

            private boolean check(String t, String m) {
                DrawableCompat.setTint(ten.getBackground(), ContextCompat.getColor(getActivity(),R.color.purple_700));
                DrawableCompat.setTint(ma.getBackground(),ContextCompat.getColor(getActivity(),R.color.purple_700));
                check = true;
                if(t.isEmpty()){
                    DrawableCompat.setTint(ten.getBackground(),ContextCompat.getColor(getActivity(),R.color.red));
                    check = false;
                }
                if(m.isEmpty()){
                    DrawableCompat.setTint(ma.getBackground(),ContextCompat.getColor(getActivity(),R.color.red));
                    check = false;
                }
                if(!check){
                    Toast.makeText(getActivity(),"Thông tin không được bỏ trống",Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                    if(!m.matches("^[a-zA-Z0-9]+$")){
                        DrawableCompat.setTint(ma.getBackground(),ContextCompat.getColor(getActivity(),R.color.red));
                        Toast.makeText(getActivity(),"Mã không hợp lệ",Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
                return true;
            }
        });

        dialog.show();
    }

    @Override
    public void actionSua(int index) {
        obj = list.get(index);
        Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_loai);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //
        ten = dialog.findViewById(R.id.dialog_loai_et_ten);
        ma = dialog.findViewById(R.id.dialog_loai_et_ma);
        them = dialog.findViewById(R.id.dialog_loai_btn_them);
        huy = dialog.findViewById(R.id.dialog_loai_btn_huy);
        title = dialog.findViewById(R.id.dialog_loai_tv_title);
        //
        title.setText("Sửa thông tin");
        ten.setText(obj.getTenLoai());
        ma.setText(obj.getMaLoai());
        them.setText("Sửa");
        //
        huy.setOnClickListener(view -> dialog.cancel());
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = ten.getText().toString(),m = ma.getText().toString();
                if(check(t,m)){
                    Loai obj1 = new Loai(m,t);
                    db.update(obj.getMaLoai(),obj1);
                    list.set(index,obj1);
                    adapter.notifyItemChanged(index);
                    Toast.makeText(getActivity(), "Update !", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }

            private boolean check(String t, String m) {
                DrawableCompat.setTint(ten.getBackground(), ContextCompat.getColor(getActivity(),R.color.purple_700));
                DrawableCompat.setTint(ma.getBackground(),ContextCompat.getColor(getActivity(),R.color.purple_700));
                check = true;
                if(t.isEmpty()){
                    DrawableCompat.setTint(ten.getBackground(),ContextCompat.getColor(getActivity(),R.color.red));
                    check = false;
                }
                if(m.isEmpty()){
                    DrawableCompat.setTint(ma.getBackground(),ContextCompat.getColor(getActivity(),R.color.red));
                    check = false;
                }
                if(!check){
                    Toast.makeText(getActivity(),"Thông tin không được bỏ trống",Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                    if(!m.matches("^[a-zA-Z0-9]+$")){
                        DrawableCompat.setTint(ma.getBackground(),ContextCompat.getColor(getActivity(),R.color.red));
                        Toast.makeText(getActivity(),"Mã không hợp lệ",Toast.LENGTH_SHORT).show();
                        return false;
                    }
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
        builder.setPositiveButton("Bỏ", (dialogInterface, i) -> {
        });
        builder.setNegativeButton("Xoá", (dialogInterface, i) -> {
            db.delete(list.get(index).getMaLoai());
            list.remove(index);
            adapter.notifyItemRemoved(index);
            adapter.notifyItemRangeChanged(index,list.size() - index);
        });
        builder.show();
    }
}
