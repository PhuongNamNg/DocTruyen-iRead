package com.example.doctruyen_iread.FragmentTheLoai;

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

import com.example.doctruyen_iread.Adapter.AdapterTheLoai;
import com.example.doctruyen_iread.FragmentTrangChu.StoryDetailActivity;
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

public class TheLoaiFrament extends Fragment {

    private AdapterTheLoai adapterTheLoai;
    RecyclerView recyclerView;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference docRef;
    private final CollectionReference colRef = db.collection("TheLoai");
    private final ArrayList<TheLoai> theLoais = new ArrayList<TheLoai>();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final ArrayList<String> list = new ArrayList<>();
    private final CollectionReference colRefStory = db.collection("Story");

    public  static TheLoaiFrament newInstance(){
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

    private  void updateTheLoai(){
        if (list.size()>0){
            list.clear();
        }
        if (theLoais.size()> 0){
            theLoais.clear();
        }
       colRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
           String tenTheLoai;
           @Override
           public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
               List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
               for (DocumentSnapshot nap: list){
                   TheLoai theLoai = nap.toObject(TheLoai.class);
                   tenTheLoai = theLoai.getTheloaiTen();
                   Log.e("loi",tenTheLoai);
                   theLoais.add(theLoai);
               }
               LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
               recyclerView.setLayoutManager(linearLayoutManager);

               adapterTheLoai = new AdapterTheLoai(getActivity());
               adapterTheLoai.setData(theLoais);
               recyclerView.setAdapter(adapterTheLoai);
               setDulieuTheLoai(tenTheLoai);
           }
       });
    }
    private  void setDulieuTheLoai(String name){
        CollectionReference collectionReference = db.collection("TheLoai").document(name).collection("StoryId");
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            String idtruyen;
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list1 = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot nap: list1){
                    idtruyen = nap.getString("ID");

                }
                colRefStory.whereEqualTo("StoryId", idtruyen).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                     ArrayList<Story> stories = new ArrayList<>();
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot docSnap : list) {
                            Story mstory = docSnap.toObject(Story.class);
                            stories.add(mstory);
                        }
                        adapterTheLoai.setDatatruyen(stories);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

}