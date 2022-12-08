package com.example.doctruyen_iread.FragmentThem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctruyen_iread.Adapter.AdapterThongKe;
import com.example.doctruyen_iread.Adapter.AdapterTrangChu;
import com.example.doctruyen_iread.MainActivity;
import com.example.doctruyen_iread.Module.Story;
import  com.example.doctruyen_iread.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ThongKe extends AppCompatActivity {
    Toolbar toolbar ;
    private RecyclerView recyclerView;
    private final ArrayList<Story> stories = new ArrayList<>();
    private final AdapterThongKe adapter = new AdapterThongKe(this);
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference colRef = db.collection("Story");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);

        toolbar = findViewById(R.id.toobar_ve);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Thống kê");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.rvThongKe);

        updateLVStories();
    }
    private void updateLVStories() {
        if (stories.size() > 0)
            stories.clear();

        colRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
            for (DocumentSnapshot docSnap : list) {
                if (docSnap.getBoolean("storyCheck") == true) {
                    Story mstory = docSnap.toObject(Story.class);
                    stories.add(mstory);
                }
            }
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ThongKe.this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);

            adapter.getData(stories);
            recyclerView.setAdapter(adapter);
        }).addOnFailureListener(e -> Toast.makeText(ThongKe.this, "Lỗi", Toast.LENGTH_SHORT).show());
    }

}