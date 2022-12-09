package com.example.doctruyen_iread.FragmentTrangChu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctruyen_iread.Adapter.AdapterChapter;
import com.example.doctruyen_iread.Module.Chapter;
import com.example.doctruyen_iread.Module.UserObj;
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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class StoryDetailActivity extends AppCompatActivity {
    private TextView tvContent, tvDescript, tvAuthorsName, tvDatePost, tvView, tvRead, tvNoti;
    private Toolbar toolbar;
    private LinearLayout lineaShare, linearCheck, linearAddChapter;
    private RecyclerView reviChapter;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference docRef;
    private final CollectionReference colRefStory = db.collection("Story");
    private final CollectionReference colRefFav = db.collection("Favorite");
    private final CollectionReference colRefUser = db.collection("User");
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private ArrayList<Chapter> listChapter = new ArrayList<Chapter>();

//    final private ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
//        Intent intent = result.getData();
//        String title = intent.getStringExtra("title");
//        getStory(title, true);
//    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_detail);
        findView();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("story");
        String title = bundle.getString("title");
        String storyId = bundle.getString("id");
        Boolean check = bundle.getBoolean("check");

        if (check == true) {
            linearCheck.setVisibility(View.VISIBLE);
        } else if (check == false) {
            linearCheck.setVisibility(View.GONE);
        }

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        getStory(title, check);

//        imbEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(StoryDetailActivity.this, EditStoryActivity.class);
//                Bundle bundle1 = new Bundle();
//                bundle1.putInt("id",id);
//                bundle1.putString("content", tvContent.getText().toString());
//                intent1.putExtra("story", bundle1);
//                launcher.launch(intent1);
//            }
//        });

        linearCheck.setOnClickListener(v -> {
            docRef = colRefStory.document(storyId);
            docRef.update("storyCheck", true).addOnSuccessListener(unused -> {
                Toast.makeText(this, "Duyệt truyện thành công", Toast.LENGTH_SHORT).show();
            });
        });

        linearAddChapter.setOnClickListener(v -> {
            Intent mIntent = new Intent(this, AddChapterActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putString("title", title);
            mBundle.putString("storyId", storyId);
            mBundle.putBoolean("check", true);
            mIntent.putExtra("story", mBundle);
            startActivity(mIntent);
        });

        tvRead.setOnClickListener(v -> {
            ViewGroup.LayoutParams layoutParams = tvDescript.getLayoutParams();
            if (layoutParams.height == layoutParams.WRAP_CONTENT) {
                layoutParams.height = 75;
                tvDescript.setLayoutParams(layoutParams);

            } else {
                layoutParams.height = layoutParams.WRAP_CONTENT;
                tvDescript.setLayoutParams(layoutParams);
            }

        });
    }

//    private void createDBFav(String docID) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("THÔNG BÁO");
//        View view = LayoutInflater.from(this).inflate(R.layout.dialog_new_list, null);
//        EditText etNameList = view.findViewById(R.id.etDiaNameLst);
//        builder.setView(view);
//        builder.setPositiveButton("Thêm", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Favorite fav = new Favorite();
//                fav.setFavoriteName(etNameList.getText().toString().trim());
//                fav.setFavListStoryID(listStoryName);
//                colRefFav.document().set(fav).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        updateFav(etNameList.getText().toString().trim());
//                        updateUserFav(docID, etNameList.getText().toString().trim());
//                    }
//                });
//
//            }
//        });
//        builder.setNegativeButton("Hủy", null);
//        builder.show();
//    }

    public void updateFav(String favName) {
//        listStoryName.add(tvTitle.getText().toString());
        colRefFav.whereEqualTo("favoriteName", favName).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            String docID;

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot docSnap : task.getResult()) {
                    DocumentSnapshot doc = docSnap;
                    docID = doc.getId();
                }
                colRefFav.document(docID).update("favListStoryID", FieldValue.arrayUnion(getSupportActionBar().getTitle())).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(StoryDetailActivity.this, "Thêm truyện " + getSupportActionBar().getTitle() + " thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void getUser() {
        Log.e("user", String.valueOf(user));
        colRefUser.whereEqualTo("userEmail", user.getEmail()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            UserObj user;
            String docID;

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot docSnap : task.getResult()) {
                    DocumentSnapshot doc = docSnap;
                    user = doc.toObject(UserObj.class);
                    docID = doc.getId();
                }
                if (user.getUsersFavorite() == null) {
//                    createDBFav(docID);
                } else {
                    updateFav(user.getUsersFavorite());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StoryDetailActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUserFav(String docID, String favName) {
        colRefUser.document(docID).update("usersFavorite", favName).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
//                            Toast.makeText(ReadStoryActivity.this, "Thêm truyện " + tvTitle.getText().toString() + " thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getStory(String title, boolean check) {
        colRefStory.whereEqualTo("storyTitle", title).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot docSnap : list) {
                    String authorsName = docSnap.getString("authorsName");
                    tvAuthorsName.setText("Tác Giả: " + authorsName);
                    tvDatePost.setText(docSnap.getString("storyDatePost"));
                    tvView.setText((Integer.parseInt(docSnap.get("storyViews").toString()) + 1 + " lượt xem"));
                    String descript = docSnap.getString("storyDescription");
                    if (descript.equals("") || descript == null) {
                        tvRead.setVisibility(View.GONE);
                        tvDescript.setText("Truyện này không có miêu tả");
                    } else {
                        tvDescript.setText(descript);
                    }
                    checkAuthors(authorsName);
                    String storyId = docSnap.getString("storyId");
                    setRecycleViewChapter(storyId, authorsName, title);
                    updateView(docSnap.getString("storyId"), check);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(StoryDetailActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkAuthors(String authorsName) {
        String email = user.getEmail();

        colRefUser.whereEqualTo("userEmail", email).get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
            DocumentSnapshot docSnap = list.get(0);
            if (authorsName.equals(docSnap.getString("userName"))) {
                linearAddChapter.setVisibility(View.VISIBLE);
            } else {
                linearAddChapter.setVisibility(View.GONE);
            }
        });



    }

    private void setRecycleViewChapter(String id, String authorsName, String title) {
        CollectionReference colChapter = db.collection("Story").document(id).collection("Chapter");

        colChapter.get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (DocumentSnapshot docSnap : queryDocumentSnapshots.getDocuments()) {
                Chapter mChapter = docSnap.toObject(Chapter.class);
                listChapter.add(mChapter);
//                Collections.sort(listChapter, (o1, o2) -> (Integer) (o1.getChapterIndex() - o2.getChapterIndex()));
            }

            if (listChapter.size() > 0) {
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                reviChapter.setLayoutManager(linearLayoutManager);

                AdapterChapter adapterChapter = new AdapterChapter(this);
                adapterChapter.getData(listChapter);
                adapterChapter.getStoryId(id);
                adapterChapter.getAuthorsName(authorsName);
                adapterChapter.getTitleName(title);

                reviChapter.setAdapter(adapterChapter);
            } else {
                tvNoti.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tvNoti.setText("Chưa có chương nào :(");
            }

        });
    }

    private void updateView(String id, boolean check) {
        if (check == false) {
            docRef = db.collection("Story").document(id);
            docRef.update("storyViews", FieldValue.increment(1));
        }
    }

//    public void checkAdminorUser() {
//        String email = user.getEmail();
//        Log.e("user", email);
//        if (email.equalsIgnoreCase("namsg19@gmail.com") || email.equalsIgnoreCase("sangnnph14292@gmail.com")) {
//            lineDel.setVisibility(View.VISIBLE);
//            lineEdit.setVisibility(View.VISIBLE);
//        } else {
//            lineDel.setVisibility(View.GONE);
//            lineEdit.setVisibility(View.GONE);
//        }
//    }

    public void findView() {
        toolbar = findViewById(R.id.toolbar);
        tvAuthorsName = findViewById(R.id.tvAuthorsNameRead);
        tvDatePost = findViewById(R.id.tvDatePostRead);
        tvView = findViewById(R.id.textView2);
        tvDescript = findViewById(R.id.tvStoryDescript);
        tvRead = findViewById(R.id.tvRead);
        tvNoti = findViewById(R.id.tvNotiListStory);
        reviChapter = findViewById(R.id.revieChapter);
        linearCheck = findViewById(R.id.linearDuyetTruyen);
        linearAddChapter = findViewById(R.id.linearAddChapter);
    }
}