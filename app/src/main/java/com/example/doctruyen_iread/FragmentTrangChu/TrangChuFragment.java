package com.example.doctruyen_iread.FragmentTrangChu;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.doctruyen_iread.Adapter.AdapterStoryViews;
import com.example.doctruyen_iread.Adapter.AdapterTrangChu;
import com.example.doctruyen_iread.Adapter.PhotoAdapter;
import com.example.doctruyen_iread.Module.Photo;
import com.example.doctruyen_iread.Module.Story;
import com.example.doctruyen_iread.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class TrangChuFragment extends Fragment {

    private AdapterTrangChu adapterTrangChu;
    private AdapterStoryViews adapterStoryViews;
    private RecyclerView recyclerView1, recyclerView2;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference docRef;
    private final CollectionReference colRef = db.collection("Story");
    private final ArrayList<Story> stories = new ArrayList<>();
    private final ArrayList<String> list = new ArrayList<>();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private LinearLayout search;

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private List<Photo> mListPhoto;
    private Timer mTimer;

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
        recyclerView1 = view.findViewById(R.id.recvi1);
        recyclerView2 = view.findViewById(R.id.recvi2);

        viewPager = view.findViewById(R.id.viewpager);
        circleIndicator = view.findViewById(R.id.circle_indicator);

        mListPhoto = getListPhoto();
        photoAdapter = new PhotoAdapter(getActivity(),mListPhoto);
        viewPager.setAdapter(photoAdapter);

        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        autoSlideImages();
        search = view.findViewById(R.id.btnsearch);
        updateLVStories();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),SearchActivity.class));
            }
        });
    }

    private List<Photo> getListPhoto(){
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.anh2));
        list.add(new Photo(R.drawable.anh3));
        list.add(new Photo(R.drawable.anh2));
        list.add(new Photo(R.drawable.anh3));

        return list;
    }
    private void autoSlideImages(){
        if (mListPhoto == null || mListPhoto.isEmpty() || viewPager == null){
            return;
        }

        if (mTimer == null){
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = mListPhoto.size() - 1;
                        if (currentItem < totalItem){
                            currentItem ++;
                            viewPager.setCurrentItem(currentItem);
                        }else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        },500,2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null){
            mTimer.cancel();
            mTimer = null;
        }
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

            setRecycleView1(stories);
            setRecycleView2(stories);


        });
    }

    private void setRecycleView2(ArrayList<Story> stories) {
        Collections.sort(stories, (o1, o2) -> (Integer) (o2.getStoryViews() - o1.getStoryViews()));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView2.setLayoutManager(gridLayoutManager);

        adapterStoryViews = new AdapterStoryViews(getActivity());
        adapterStoryViews.getData(stories);
        recyclerView2.setAdapter(adapterStoryViews);
    }

    private void setRecycleView1(ArrayList<Story> stories) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(linearLayoutManager);

        adapterTrangChu = new AdapterTrangChu(getActivity());
        adapterTrangChu.getData(stories);
        recyclerView1.setAdapter(adapterTrangChu);
    }
}