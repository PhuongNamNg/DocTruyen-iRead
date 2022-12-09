package com.example.doctruyen_iread.FragmentTrangChu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctruyen_iread.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ReadChapterActivity extends AppCompatActivity {
    private TextView tvContent, tvTitle;
    private EditText etTextSize;
    private ImageButton imbIncTextSize, imbDecTextSize;
    private LinearLayout lineDel, lineEdit;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colChapter = db.collection("Story");
    private CollectionReference colUser = db.collection("User");
    private DocumentReference docChapter;
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_chapter);

        findView();

        Intent mIntent = getIntent();
        Bundle mBundle = mIntent.getBundleExtra("chapter");
        String chapterId = mBundle.getString("chapterId");
        String storyId = mBundle.getString("storyId");
        String auhthorsName = mBundle.getString("authorsName");
        String storyTitle = mBundle.getString("storyTitle");


        checkAuthors(auhthorsName);

        getChapter(chapterId, storyId);

//        DisplayMetrics metrics;
//        metrics = getApplicationContext().getResources().getDisplayMetrics();
//        px / 2.375 -> sp. px / density -> dp
        etTextSize.setText(String.valueOf((int) (tvContent.getTextSize() / 2.375)));

        etTextSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String etSize = etTextSize.getText().toString();
                if (etSize.isEmpty()) {
                    Toast.makeText(ReadChapterActivity.this, "Cỡ chữ không được để trống ", Toast.LENGTH_SHORT).show();
                    changeTextSize("16");
                } else if (Float.parseFloat(etSize) < 1f) {
//                    changeTextSize(etTextSize.getText().toString());
                    Toast.makeText(ReadChapterActivity.this, "Cỡ chữ phải lớn hơn 1", Toast.LENGTH_SHORT).show();
                    changeTextSize("16");
                } else {
                    changeTextSize(etSize);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String etSize = etTextSize.getText().toString();
                if (etSize.isEmpty()) {
                    Toast.makeText(ReadChapterActivity.this, "Cỡ chữ không được để trống ", Toast.LENGTH_SHORT).show();
                    changeTextSize("16");
                } else {
                    changeTextSize(etSize);
                }
            }
        });

        imbIncTextSize.setOnClickListener(v -> {
            Integer size = Integer.parseInt(etTextSize.getText().toString()) + 1;
            String etSize = String.valueOf(size);
            changeEditText(etSize);
        });

        imbDecTextSize.setOnClickListener(v -> {
            Integer size = Integer.parseInt(etTextSize.getText().toString()) - 1;
            String etSize = String.valueOf(size);
            changeEditText(etSize);
        });

        lineDel.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("THÔNG BÁO");
            builder.setMessage("Bạn chắc chắn muốn xóa?");
            builder.setPositiveButton("OK", (dialog, which) -> {
                docChapter = db.collection("Story").document(storyId).collection("Chapter").document(chapterId);
                docChapter.delete().addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Xóa", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, StoryDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title", storyTitle);
                    bundle.putBoolean("check", false);
                    intent.putExtra("story", bundle);
                    startActivity(intent);
                }).addOnFailureListener(e -> Toast.makeText(this, "Lỗi", Toast.LENGTH_SHORT).show());
            });
            builder.setNegativeButton("No", null);
            builder.show();
        });

        lineEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditChapterActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("storyId", storyId);
            bundle.putString("chapterId", chapterId);
            intent.putExtra("chapter", bundle);
            startActivity(intent);
        });

    }

    private void checkAuthors(String auhthorsName) {
        String email = user.getEmail();

        colUser.whereEqualTo("userEmail", email).get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
            DocumentSnapshot docSnap = list.get(0);
            String authors = docSnap.getString("userName");

            if (auhthorsName.equals(authors)) {
                lineDel.setVisibility(View.VISIBLE);
                lineEdit.setVisibility(View.VISIBLE);
            } else {
                lineDel.setVisibility(View.INVISIBLE);
                lineEdit.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void getChapter(String chapterId, String storyId) {
        colChapter.document(storyId).collection("Chapter").document(chapterId).get().addOnSuccessListener(documentSnapshot -> {
            tvTitle.setText(documentSnapshot.getString("chapterTitle"));
            tvContent.setText(documentSnapshot.getString("chapterContent"));
        });
    }

    private void changeEditText (String etSize) {
        if (etSize.isEmpty()) {
            Toast.makeText(ReadChapterActivity.this, "Cỡ chữ không được để trống ", Toast.LENGTH_SHORT).show();
            etTextSize.setText("16");
        } else if (Float.parseFloat(etSize) < 1f) {
//                    changeTextSize(etTextSize.getText().toString());
            Toast.makeText(ReadChapterActivity.this, "Cỡ chữ phải lớn hơn 1", Toast.LENGTH_SHORT).show();
            etTextSize.setText("16");
        } else {
            etTextSize.setText(etSize);
        }
    }

    private void changeTextSize(String size) {
        tvContent.setTextSize(Float.parseFloat(size));
    }

    private void findView() {
        tvTitle = findViewById(R.id.tvChapterTitle);
        tvContent = findViewById(R.id.tvChapterContent);
        etTextSize = findViewById(R.id.etTextSize);
        imbIncTextSize = findViewById(R.id.imbIncTextSize);
        imbDecTextSize = findViewById(R.id.imbDecTextSize);
        lineDel = findViewById(R.id.linearChapterDel);
        lineEdit = findViewById(R.id.linearChapterEdit);
    }
}