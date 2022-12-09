package com.example.doctruyen_iread.FragmentThem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.doctruyen_iread.Adapter.AdapterFavorite;
import com.example.doctruyen_iread.Module.Favorite;
import com.example.doctruyen_iread.Module.Story;
import com.example.doctruyen_iread.Module.UserObj;
import  com.example.doctruyen_iread.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import android.os.Bundle;

import android.view.View;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class YeuThich extends AppCompatActivity {

    Toolbar toolbar;
    TextView tvTrong;
    RecyclerView recyclerView;
    private AdapterFavorite adapterFavorite;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference colRefFar = db.collection("Favorite");
    private final CollectionReference colRefStory = db.collection("Story");
    private final CollectionReference colRefUser = db.collection("User");
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String storyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeu_thich);

        toolbar = findViewById(R.id.toobar_ve);
        recyclerView = findViewById(R.id.rcv_list);
        tvTrong = findViewById(R.id.tv_trong);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        yeuthich();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(YeuThich.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapterFavorite = new AdapterFavorite(YeuThich.this);

        recyclerView.setAdapter(adapterFavorite);
    }

    private void hienthi(String name) {
        ArrayList<Story> stories = new ArrayList<>();
        colRefFar.whereEqualTo("favoriteName", name).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            Favorite fav = new Favorite();

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot docnap : task.getResult()) {
                    DocumentSnapshot doc = docnap;
                    fav = doc.toObject(Favorite.class);
                }
                ArrayList<String> list = fav.getFavListStoryID();
                if (list != null) {
                    tvTrong.setVisibility(View.GONE);
                    for (int i = 0; i < list.size(); i++) {
                        colRefStory.whereEqualTo("storyId", list.get(i)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {

                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();
                                for (DocumentSnapshot docnap : list1) {
                                    Story mstory = docnap.toObject(Story.class);
                                    storyId = mstory.getStoryId();
                                    stories.add(mstory);
                                    adapterFavorite.setData(stories);
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(YeuThich.this, "loi", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    tvTrong.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void yeuthich() {
        colRefUser.whereEqualTo("userEmail", user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            UserObj userObj;

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot docnap : task.getResult()) {
                    DocumentSnapshot doc = docnap;
                    userObj = doc.toObject(UserObj.class);
                }
                if (userObj.getUsersFavorite() == null) {
                    return;
                } else {
                    hienthi(userObj.getUsersFavorite().toString());
                }
            }
        });
    }


}