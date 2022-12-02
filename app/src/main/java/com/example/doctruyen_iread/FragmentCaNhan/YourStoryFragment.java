package com.example.doctruyen_iread.FragmentCaNhan;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.doctruyen_iread.Adapter.AdapterStoryCaNhan;
import com.example.doctruyen_iread.FragmentTrangChu.AddStoryActivity;
import com.example.doctruyen_iread.Module.Story;
import com.example.doctruyen_iread.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class YourStoryFragment extends Fragment {
    private TextView tvView;
    private RecyclerView recyclerView;
    private LinearLayout linearThemTruyen;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colStory = db.collection("Story");
    private CollectionReference colUser = db.collection("User");
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_your_story, container, false);
        tvView = view.findViewById(R.id.tvView);
        recyclerView = view.findViewById(R.id.recviTruyenCaNhan);
        linearThemTruyen = view.findViewById(R.id.linearThemTruyen);

        linearThemTruyen.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddStoryActivity.class));
        });

        String email = user.getEmail();

        colUser.whereEqualTo("userEmail", email).get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
            DocumentSnapshot docSnap = list.get(0);
            getStory(docSnap.getString("userName"));
        });
        return view;
    }

    private void getStory(String name) {
        colStory.whereEqualTo("authorsName", name).get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<Story> stories = new ArrayList<>();
            Integer count = 0;
            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
            for (DocumentSnapshot docSnap : list) {
                Story mStory = docSnap.toObject(Story.class);
                count += mStory.getStoryViews();
                stories.add(mStory);
            }
            tvView.setText("Tổng Lượt Xem: " + count);

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

            AdapterStoryCaNhan adapter = new AdapterStoryCaNhan(getActivity());
            adapter.getData(stories);

            recyclerView.setAdapter(adapter);
        });
    }
}