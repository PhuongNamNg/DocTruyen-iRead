package com.example.doctruyen_iread.FragmentTrangChu;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctruyen_iread.MainActivity;
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

import java.util.ArrayList;
import java.util.List;

public class ReadStoryActivity extends AppCompatActivity {
    private TextView tvContent, tvAuthorsName, tvDatePost, tvView;
    private ImageButton imbEdit, imbDelete, imbFav, imbCate;
    private Toolbar toolbar;
    private ListView lvChapter;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference docRef;
    private final CollectionReference colRefStory = db.collection("Story");
    private final CollectionReference colRefFav = db.collection("Favorite");
    private final CollectionReference colRefUser = db.collection("User");
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final ArrayList<String> listStoryName = new ArrayList<>();

    final private ActivityResultLauncher launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent intent = result.getData();
            String title = intent.getStringExtra("title");
            getStory(title);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_story);
        findView();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("story");
        String title = bundle.getString("title");

        setSupportActionBar(toolbar);
//        toolbar.setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        checkAdminorUser();
        getStory(title);
//        ArrayList<String> listChapter = new ArrayList<String>();
//        for (int i = 0; i < 6; i++) {
//            listChapter.add("Chapter " + (i+1));
//        }
//        Log.e("list", listChapter.toString());
//        AdapterLvChapter adapter = new AdapterLvChapter(this, listChapter);
//        lvChapter.setAdapter(adapter);


        imbEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ReadStoryActivity.this, EditStoryActivity.class);
                Bundle bundle1 = new Bundle();
//                bundle1.putInt("id",id);
                bundle1.putString("content", tvContent.getText().toString());
                intent1.putExtra("story", bundle1);
                launcher.launch(intent1);
            }
        });

        imbDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ReadStoryActivity.this);
                builder.setTitle("THÔNG BÁO");
                builder.setMessage("Bạn chắc chắn muốn xóa?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        colRefStory.whereEqualTo("storyTitle", title).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            String docID;

                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    docID = doc.getId();
                                    Log.e("Doc ID", "" + docID);
                                }
                                docRef = db.collection("Story").document(docID);
                                docRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(ReadStoryActivity.this, "Xóa", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ReadStoryActivity.this, MainActivity.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ReadStoryActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
                builder.setNegativeButton("No", null);
                builder.show();
            }
        });

        imbFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUser();
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
                    Log.e("check", docID);
                }
                colRefFav.document(docID).update("favListStoryID", FieldValue.arrayUnion(getSupportActionBar().getTitle())).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ReadStoryActivity.this, "Thêm truyện " + getSupportActionBar().getTitle() + " thành công", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ReadStoryActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
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

    private void getStory(String title) {
        colRefStory.whereEqualTo("storyTitle", title).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot docSnap : list) {
                    tvContent.setText(docSnap.getString("storyContent"));
                    tvAuthorsName.setText("Tác Giả: " + docSnap.getString("authorsName"));
                    tvDatePost.setText(docSnap.getString("storyDatePost"));
                    tvView.setText((Integer.parseInt(docSnap.get("storyView").toString()) + 1 + " lượt xem"));
                    updateView(title);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ReadStoryActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateView(String title) {
        colRefStory.whereEqualTo("storyTitle", title).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            String docID;

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    docID = doc.getId();
                    Log.e("Doc ID", "" + docID);
                }
                docRef = db.collection("Story").document(docID);
                docRef.update("storyView", FieldValue.increment(1));
            }
        });
    }

    public void checkAdminorUser() {
        String email = user.getEmail();
        Log.e("user", email);
        if (email.equalsIgnoreCase("namsg19@gmail.com") || email.equalsIgnoreCase("sangnnph14292@gmail.com")) {
            imbDelete.setVisibility(View.VISIBLE);
            imbCate.setVisibility(View.VISIBLE);
            imbEdit.setVisibility(View.VISIBLE);
        } else {
            imbDelete.setVisibility(View.INVISIBLE);
            imbCate.setVisibility(View.INVISIBLE);
            imbEdit.setVisibility(View.INVISIBLE);
        }
    }

    public void findView() {
        toolbar = findViewById(R.id.toolbar);
        tvContent = findViewById(R.id.tvContentRead);
        tvAuthorsName = findViewById(R.id.tvAuthorsNameRead);
        tvDatePost = findViewById(R.id.tvDatePostRead);
        tvView = findViewById(R.id.textView2);
        imbEdit = findViewById(R.id.imbEdit);
        imbDelete = findViewById(R.id.imbDelete);
        imbFav = findViewById(R.id.imbFav);
        imbCate = findViewById(R.id.imbCategory);
        lvChapter = findViewById(R.id.lvChapter);

    }
}