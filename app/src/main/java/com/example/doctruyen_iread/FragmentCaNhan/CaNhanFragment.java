package com.example.doctruyen_iread.FragmentCaNhan;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doctruyen_iread.Adapter.AdapterViewPagerCaNhan;

import com.example.doctruyen_iread.Module.Story;
import com.example.doctruyen_iread.Module.UserObj;
import com.example.doctruyen_iread.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class CaNhanFragment extends Fragment {
    TextView tvFollows, tvNumbStory, tvNameUser, tvFollowed;
    private TabLayout tab;
    private ViewPager2 vp;
    private AdapterViewPagerCaNhan adapter;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colStory = db.collection("Story");
    private CollectionReference colUser = db.collection("User");
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public CaNhanFragment() {
    }

    public static CaNhanFragment newInstance(String param1, String param2) {
        CaNhanFragment fragment = new CaNhanFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ca_nhan, container, false);
        tab = view.findViewById(R.id.tablayoutCaNhan);
        vp = view.findViewById(R.id.vpCaNhan);
        tvNumbStory = view.findViewById(R.id.tvNumOfStory);
        tvNameUser = view.findViewById(R.id.tvNameUser);
        tvFollows = view.findViewById(R.id.tvFollows);
        tvFollowed = view.findViewById(R.id.tvFollowed);

        String email = user.getEmail();

        colUser.whereEqualTo("userEmail", email).get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
            DocumentSnapshot docSnap = list.get(0);
            UserObj user = docSnap.toObject(UserObj.class);
            String name = user.getUserName();
            Integer followed = user.getUserFollowed();

            if (followed == null) {
                tvFollowed.setText("0");
            } else if (followed == 0) {
                tvFollowed.setText("0");
            } else tvFollowed.setText(String.valueOf(user.getUserFollowed()));

            ArrayList<String> listFollows = user.getUserFollow();
            if (listFollows == null || listFollows.size() == 0) {
                tvFollows.setText("0");
            } else {
                tvFollows.setText(String.valueOf(listFollows.size()));
            }

            getStory(name);
            tvNameUser.setText(name);
        });

        adapter = new AdapterViewPagerCaNhan(getActivity());
        vp.setAdapter(adapter);

        new TabLayoutMediator(tab, vp, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Danh Sách Truyện");
                    break;
                case 1:
                    tab.setText("Yêu Thích");
                    break;
                case 2:
                    tab.setText("Sửa Thông Tin");
                    break;
            }
        }).attach();
        return view;
    }

    private void getStory(String name) {
        colStory.whereEqualTo("authorsName", name).get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<Story> stories = new ArrayList<>();
            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
            for (DocumentSnapshot docSnap : list) {
                Story mStory = docSnap.toObject(Story.class);
                stories.add(mStory);
            }
            if (stories.size() == 0) {
                tvNumbStory.setText("0");
            } else {
                tvNumbStory.setText(String.valueOf(stories.size()));
            }
        });
    }
}