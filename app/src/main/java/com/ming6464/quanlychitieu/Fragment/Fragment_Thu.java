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


public class Fragment_Thu extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private Adapter_Fragment adapter_fragment;

    public static Fragment_Thu newInstance() {
        Fragment_Thu fragment = new Fragment_Thu();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment__thu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tabLayout = view.findViewById(R.id.frag_thu_tabLayout);
        viewPager2 = view.findViewById(R.id.frag_thu_viewPager);
        init();
    }

    private void init() {
        addViewPager();
        addTabLayout();
    }

    private void addTabLayout() {
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            if(position == 0){
                tab.setText("Loại Thu");
            }else
                tab.setText("Khoản Thu");
        }).attach();
    }

    private void addViewPager() {
        adapter_fragment = new Adapter_Fragment(getActivity(),new Fragment[]{Fragment_LoaiThu.newInstance(),Fragment_KhoanThu.newInstance()});
        viewPager2.setAdapter(adapter_fragment);
    }
}