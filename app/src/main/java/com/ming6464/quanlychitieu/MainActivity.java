package com.ming6464.quanlychitieu;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.ming6464.quanlychitieu.Fragment.Fragment_Chi;
import com.ming6464.quanlychitieu.Fragment.Fragment_GioiThieu;
import com.ming6464.quanlychitieu.Fragment.Fragment_ThongKe;
import com.ming6464.quanlychitieu.Fragment.Fragment_Thu;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        anhXa();
        init();
    }
    private void init() {
        addToolBar();
        chuyenFragment(Fragment_ThongKe.newInstance());
        addNavigation();
    }

    private void addNavigation() {
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.main_menu_navigation_fragmentThu:
                    chuyenFragment(Fragment_Thu.newInstance());
                    break;
                case R.id.main_menu_navigation_fragmentChi:
                    chuyenFragment(Fragment_Chi.newInstance());
                    break;
                case R.id.main_menu_navigation_fragmentThongKe:
                    chuyenFragment(Fragment_ThongKe.newInstance());
                    break;
                case R.id.main_menu_navigation_gioiThieu:
                    chuyenFragment(Fragment_GioiThieu.newInstance());
                    break;
                default:
                    this.finish();
                    System.exit(0);
            }
            item.setCheckable(true);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    private void chuyenFragment(Fragment fragment){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.m_linearLayout,fragment);
        fragmentTransaction.commit();
    }



    private void addToolBar() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.open_menu_main,R.string.close_menu_main);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }



    private void anhXa() {
        drawerLayout = findViewById(R.id.m_drawerLayout);
        navigationView = findViewById(R.id.m_navigation);
        toolbar = findViewById(R.id.m_toolBar);
    }
}