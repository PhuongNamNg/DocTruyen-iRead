package com.example.doctruyen_iread;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.doctruyen_iread.FragmentCaNhan.CaNhanFragment;
import com.example.doctruyen_iread.FragmentTheLoai.TheLoai;
import com.example.doctruyen_iread.FragmentThem.Them;
import com.example.doctruyen_iread.FragmentTrangChu.TrangChuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

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
                        Toast.makeText(MainActivity.this, "Truyện", Toast.LENGTH_SHORT).show();
                        mBottomNavigationView.getMenu().findItem(R.id.action_Truyen).setChecked(true);
                        fragmentManager.beginTransaction().replace(R.id.frameContent,trangChuFragment).commit();
                        break;
                    case R.id.action_TheLoai:
                        Toast.makeText(MainActivity.this, "Thể Loại", Toast.LENGTH_SHORT).show();
                        mBottomNavigationView.getMenu().findItem(R.id.action_TheLoai).setChecked(true);
                        TheLoai theloai = new TheLoai();
                        fragmentManager.beginTransaction().replace(R.id.frameContent,theloai).commit();
                        break;
                    case R.id.action_CaNhan:
                        Toast.makeText(MainActivity.this, "Cá Nhân", Toast.LENGTH_SHORT).show();
                        mBottomNavigationView.getMenu().findItem(R.id.action_CaNhan).setChecked(true);
                        CaNhanFragment canhan = new CaNhanFragment();
                        fragmentManager.beginTransaction().replace(R.id.frameContent,canhan).commit();
                        break;
                    case R.id.action_Them:
                        Toast.makeText(MainActivity.this, "Thêm", Toast.LENGTH_SHORT).show();
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