package com.example.doctruyen_iread.FragmentThem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.doctruyen_iread.Adapter.AdapterQLTK;
import com.example.doctruyen_iread.Module.UserObj;
import com.example.doctruyen_iread.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class QuanLyTaiKhoanActivity extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colUser = db.collection("User");
    private RecyclerView recyclerView;
    private AdapterQLTK adapter;
    private TextView tvTrongQLTK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_tai_khoan);
        recyclerView = findViewById(R.id.rvQLTK);
        tvTrongQLTK = findViewById(R.id.tvTrongQLTK);

        colUser.get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<UserObj> userObjs = new ArrayList<>();
            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
            for (DocumentSnapshot docSnap : list) {
                UserObj userObj = docSnap.toObject(UserObj.class);
                userObjs.add(userObj);
            }

            if (userObjs != null || userObjs.size() > 0) {
                tvTrongQLTK.setVisibility(View.GONE);
            }

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);

            adapter = new AdapterQLTK(this);
            adapter.getData(userObjs);
            recyclerView.setAdapter(adapter);
        });
    }
}