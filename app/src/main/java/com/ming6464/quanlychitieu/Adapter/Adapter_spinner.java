package com.ming6464.quanlychitieu.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ming6464.quanlychitieu.R;

import java.util.ArrayList;

public class Adapter_spinner extends BaseAdapter {

    private ArrayList<String> list;
    private Context context;

    public Adapter_spinner(ArrayList<String> list,Context context){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = View.inflate(context, R.layout.item_spinner,null);
        TextView tv = view.findViewById(R.id.item_spinner_tv_title);
        tv.setText(list.get(i));
        return view;
    }
}
