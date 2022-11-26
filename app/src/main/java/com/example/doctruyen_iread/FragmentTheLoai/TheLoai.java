package com.example.doctruyen_iread.FragmentTheLoai;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.doctruyen_iread.Adapter.AdapterTheLoai;
import com.example.doctruyen_iread.Adapter.AdapterTruyenTheLoai;
import com.example.doctruyen_iread.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TheLoai extends Fragment {

    private AdapterTheLoai adapterTheLoai;
    RecyclerView recyclerView;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference docRef;
    private final CollectionReference colRef = db.collection("TheLoai");
    private final ArrayList<com.example.doctruyen_iread.Module.TheLoai> theLoais = new ArrayList<>();
    private final  ArrayList<String> list = new ArrayList<>();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public  static  TheLoai newInstance(){
        TheLoai fragament = new TheLoai();
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

    private  void updateTheLoai(){
        if (list.size()>0){
            list.clear();
        }
        if (theLoais.size()> 0){
            theLoais.clear();
        }
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()){
                    list.add(document.getId());
                }
                for (int i = 0 ; i <list.size(); i ++ ){
                    docRef = db.collection("TheLoai").document(list.get(i));
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            com.example.doctruyen_iread.Module.TheLoai theLoai = documentSnapshot.toObject(com.example.doctruyen_iread.Module.TheLoai.class);
                            theLoais.add(theLoai);

                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
                            recyclerView.setLayoutManager(linearLayoutManager);

                            adapterTheLoai = new AdapterTheLoai(getActivity());
                            adapterTheLoai.setData(theLoais);
                            recyclerView.setAdapter(adapterTheLoai);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

}
