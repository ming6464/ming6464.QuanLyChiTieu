package com.ming6464.quanlychitieu.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ming6464.quanlychitieu.R;

public class Fragment_GioiThieu extends Fragment {

    public static Fragment_GioiThieu newInstance() {
        Fragment_GioiThieu fragment = new Fragment_GioiThieu();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment__gioi_thieu, container, false);
    }
}