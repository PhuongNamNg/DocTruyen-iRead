package com.example.doctruyen_iread.FragmentThem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctruyen_iread.MainActivity;
import  com.example.doctruyen_iread.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

public class ThongKe extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        toolbar = findViewById(R.id.toobar_ve2);

    }
}