package com.example.doctruyen_iread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.doctruyen_iread.FragmentCaNhan.CaNhanFragment;
import com.example.doctruyen_iread.FragmentTheLoai.TheLoaiFrament;
import com.example.doctruyen_iread.FragmentThem.Them;
import com.example.doctruyen_iread.FragmentTrangChu.TrangChuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mBottomNavigationView;
    DrawerLayout drawerLayout;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomNavigationView = findViewById(R.id.bottom_nav);

        drawerLayout = findViewById(R.id.Activity);
        frameLayout = findViewById(R.id.frameContent);

        TrangChuFragment trangChuFragment = new TrangChuFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameContent, trangChuFragment).commit();

        mBottomNavigationView.setSelectedItemId(R.id.action_Truyen);

        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_Truyen:
                        mBottomNavigationView.getMenu().findItem(R.id.action_Truyen).setChecked(true);
                        fragmentManager.beginTransaction().replace(R.id.frameContent,trangChuFragment).commit();
                        break;
                    case R.id.action_TheLoai:
                        mBottomNavigationView.getMenu().findItem(R.id.action_TheLoai).setChecked(true);
                        TheLoaiFrament theloai = new TheLoaiFrament();
                        fragmentManager.beginTransaction().replace(R.id.frameContent,theloai).commit();
                        break;
                    case R.id.action_CaNhan:
                        mBottomNavigationView.getMenu().findItem(R.id.action_CaNhan).setChecked(true);
                        CaNhanFragment canhan = new CaNhanFragment();
                        fragmentManager.beginTransaction().replace(R.id.frameContent,canhan).commit();
                        break;
                    case R.id.action_Them:
                        mBottomNavigationView.getMenu().findItem(R.id.action_Them).setChecked(true);
                        Them them = new Them();
                        fragmentManager.beginTransaction().replace(R.id.frameContent,them).commit();
                        break;
                }
                return true;
            }
        });
    }
}