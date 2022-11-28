package com.example.doctruyen_iread.FragmentThem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.doctruyen_iread.Adapter.AdapterDuyetTruyen;
import com.example.doctruyen_iread.Module.Story;
import com.example.doctruyen_iread.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DuyetTruyenActivity extends AppCompatActivity {
    private RecyclerView recyViewDuyetTruyen;
    private AdapterDuyetTruyen adapter;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colStory = db.collection("Story");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duyet_truyen);

        recyViewDuyetTruyen = findViewById(R.id.revieDuyetTruyen);

        colStory.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            ArrayList<Story> stories = new ArrayList<>();
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot docSnap : list) {
                    Story mstory = docSnap.toObject(Story.class);
                    stories.add(mstory);
                    Log.e("checkList", String.valueOf(stories.size()));

                }

                GridLayoutManager gridLayoutManager = new GridLayoutManager(DuyetTruyenActivity.this, 2);
                recyViewDuyetTruyen.setLayoutManager(gridLayoutManager);

                adapter = new AdapterDuyetTruyen(DuyetTruyenActivity.this);
                adapter.getData(stories);

                recyViewDuyetTruyen.setAdapter(adapter);
            }
        });
    }
}