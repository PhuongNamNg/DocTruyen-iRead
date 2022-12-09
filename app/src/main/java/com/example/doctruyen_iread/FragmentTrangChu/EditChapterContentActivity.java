package com.example.doctruyen_iread.FragmentTrangChu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doctruyen_iread.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditChapterContentActivity extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colStory = db.collection("Story");
    private EditText etEditChapterContent;
    private Button btnEditChapterContent, btnHuyEditChapterContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chapter_content);
        etEditChapterContent = findViewById(R.id.etEditChapterContent);
        btnEditChapterContent = findViewById(R.id.btnEditChapterContent);
        btnHuyEditChapterContent = findViewById(R.id.btnHuyEditChapterContent);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("chapter");
        String content = bundle.getString("content");
        String storyId = bundle.getString("storyId");
        String chapterId = bundle.getString("chapterId");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông Báo");
        builder.setMessage("Bạn muốn sửa nội dung cũ hay nhập nội dung mới?");
        builder.setPositiveButton("Mới", (dialog1, which) -> {
            dialog1.dismiss();
        });
        builder.setNegativeButton("Cũ", (dialog1, which) -> {
            etEditChapterContent.setText(content);
            dialog1.dismiss();
        });
        builder.show();

        btnEditChapterContent.setOnClickListener(v -> {
            colStory.document(storyId).collection("Chapter").document(chapterId).update("chapterContent", etEditChapterContent.getText().toString().trim()).addOnSuccessListener(unused -> {
                Toast.makeText(this, "Sửa nội dung chương thành công", Toast.LENGTH_SHORT).show();
                finish();
            });
        });

        btnHuyEditChapterContent.setOnClickListener(v -> {
            finish();
        });
    }
}