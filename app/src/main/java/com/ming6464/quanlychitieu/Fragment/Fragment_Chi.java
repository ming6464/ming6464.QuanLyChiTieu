package com.ming6464.quanlychitieu.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ming6464.quanlychitieu.Adapter.Adapter_Fragment;
import com.ming6464.quanlychitieu.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Fragment_Chi extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private Adapter_Fragment adapter_fragment;


    public static Fragment_Chi newInstance() {
        Fragment_Chi fragment = new Fragment_Chi();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment__chi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.frag_chi_tabLayout);
        viewPager2 = view.findViewById(R.id.frag_chi_viewPager);
        init();
    }

    private void init() {
        addViewPager();
        addTabLayout();
    }

    private void addTabLayout() {
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if(position == 0){
                tab.setText("Loại Chi");
            }else
                tab.setText("Khoản Chi");
        }).attach();
    }

    private void addViewPager() {
        adapter_fragment = new Adapter_Fragment(getActivity(),new Fragment[]{Fragment_LoaiChi.newInstance(),Fragment_KhoanChi.newInstance()});
        viewPager2.setAdapter(adapter_fragment);
    }
}