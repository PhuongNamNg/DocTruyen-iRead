package com.example.doctruyen_iread.FragmentCaNhan;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctruyen_iread.Adapter.AdapterFavorite;
import com.example.doctruyen_iread.Module.Favorite;
import com.example.doctruyen_iread.Module.Story;
import com.example.doctruyen_iread.Module.UserObj;
import com.example.doctruyen_iread.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class YeuThichFragment extends Fragment {
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yeu_thich, container, false);
        recyclerView = view.findViewById(R.id.rcv_list);
        tvTrong = view.findViewById(R.id.tv_trong);

        yeuthich();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapterFavorite = new AdapterFavorite(getContext());

        recyclerView.setAdapter(adapterFavorite);
        return view;
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
                                Toast.makeText(getContext(), "loi", Toast.LENGTH_SHORT).show();
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