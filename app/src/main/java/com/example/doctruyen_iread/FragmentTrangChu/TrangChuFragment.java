package com.example.doctruyen_iread.FragmentTrangChu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doctruyen_iread.Adapter.AdapterTrangChu;
import com.example.doctruyen_iread.Module.Story;
import com.example.doctruyen_iread.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TrangChuFragment extends Fragment {

    private AdapterTrangChu adapter;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference docRef;
    private final CollectionReference colRef = db.collection("Story");
    private final ArrayList<Story> stories = new ArrayList<>();
    private final ArrayList<String> list = new ArrayList<>();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public TrangChuFragment() {
        // Required empty public constructor
    }

    public static TrangChuFragment newInstance() {
        TrangChuFragment fragment = new TrangChuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trang_chu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recvi);
        fab = view.findViewById(R.id.fab);

//        checkAdminorUser();
        updateLVStories();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddStoryActivity.class));
            }
        });
    }

    private void updateLVStories() {
        if (list.size() > 0)
            list.clear();

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
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);

            adapter = new AdapterTrangChu(getActivity());
            adapter.getData(stories);
            recyclerView.setAdapter(adapter);
        });
    }


    public void checkAdminorUser() {
        String email = user.getEmail();
        Log.e("user", email);
        if (email.equalsIgnoreCase("namsg19@gmail.com") || email.equalsIgnoreCase("sangnnph14292@gmail.com")) {
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.INVISIBLE);
        }
    }
}