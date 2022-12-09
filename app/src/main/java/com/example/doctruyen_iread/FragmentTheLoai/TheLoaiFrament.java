package com.example.doctruyen_iread.FragmentTheLoai;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doctruyen_iread.Adapter.AdapterTheLoai;
import com.example.doctruyen_iread.Adapter.PhotoAdapter;
import com.example.doctruyen_iread.Module.Photo;
import com.example.doctruyen_iread.Module.Story;
import com.example.doctruyen_iread.Module.TheLoai;
import com.example.doctruyen_iread.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class TheLoaiFrament extends Fragment {

    private AdapterTheLoai adapterTheLoai;
    RecyclerView recyclerView;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference docRef;
    private final CollectionReference colTheLoai = db.collection("TheLoai");
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final ArrayList<String> list = new ArrayList<>();
    private final CollectionReference colRefStory = db.collection("Story");

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter;
    private List<Photo> mListPhoto;
    private Timer mTimer;

    public static TheLoaiFrament newInstance() {
        TheLoaiFrament fragament = new TheLoaiFrament();
        return fragament;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_the_loai, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcv_category);

        viewPager = view.findViewById(R.id.viewpager);
        circleIndicator = view.findViewById(R.id.circle_indicator);

        mListPhoto = getListPhoto();
        photoAdapter = new PhotoAdapter(getActivity(), getListPhoto());
        viewPager.setAdapter(photoAdapter);

        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlideImages();

        updateTheLoai();

    }

    //    private List<com.example.doctruyen_iread.Module.TheLoai> Getlist(){
//        List<com.example.doctruyen_iread.Module.TheLoai> theLoaiList = new ArrayList<>();
//        List<Story> stories = new ArrayList<>();
//        CollectionReference collectionReference = db.collection("TheLoai").document().collection("StoryId");
//        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//            @Override
//            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                for (DocumentSnapshot docNap : queryDocumentSnapshots.getDocuments()){
//                   Story story = docNap.toObject(Story.class);
//                   theLoaiList.add(story);
//                }
//            }
//        });
//        return   theLoaiList;
//    }
    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.anh2));
        list.add(new Photo(R.drawable.anh3));
        list.add(new Photo(R.drawable.anh2));
        list.add(new Photo(R.drawable.anh3));

        return list;
    }

    private void autoSlideImages() {
        if (mListPhoto == null || mListPhoto.isEmpty() || viewPager == null) {
            return;
        }

        if (mTimer == null) {
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
                        if (currentItem < totalItem) {
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 500, 2000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private void updateTheLoai() {
//        if (list.size() > 0) {
//            list.clear();
//        }
//        if (theLoais.size() > 0) {
//            theLoais.clear();
//        }
        colTheLoai.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            String tenTheLoai;
            ArrayList<TheLoai> theLoais = new ArrayList<TheLoai>();
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot nap : list) {
                    TheLoai theLoai = nap.toObject(TheLoai.class);
                    tenTheLoai = theLoai.getTheloaiTen();
                    theLoais.add(theLoai);
                }

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);

                adapterTheLoai = new AdapterTheLoai(getActivity());
                adapterTheLoai.setData(theLoais);
                recyclerView.setAdapter(adapterTheLoai);
            }
        });
    }

    private void setDulieuTheLoai(String name) {

//        colTheLoai.document(name).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            String idTruyen;
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();
//                for (DocumentSnapshot nap : list1) {
//                    idTruyen = nap.getString("ID");
//
//                }
//
//            }
//        });
    }

}
