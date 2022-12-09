package com.example.doctruyen_iread.FragmentTrangChu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctruyen_iread.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditChapterActivity extends AppCompatActivity {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colStory = db.collection("Story");
    private LinearLayout linearEditChapterTitle, linearEditChapterDescript, linearEditChapterContent;
    private Button btnHuyEditChapter, btnEditChapterName, btnHuyEditChapterName, btnEditChapterDescript, btnHuyEditChapterDescript;
    private TextView tvEditChapterTitle, tvEditChapterDescript;
    private EditText etEditChapterNewTitle, etEditChapterNewDescript;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_chapter);

        Intent mIntent = getIntent();
        Bundle mBundle = mIntent.getBundleExtra("chapter");
        String storyId = mBundle.getString("storyId");
        String chapterId = mBundle.getString("chapterId");

        getChapter(storyId, chapterId);

    }

    private void getChapter (String storyId, String chapterId) {
        colStory.document(storyId).collection("Chapter").document(chapterId).get().addOnSuccessListener(documentSnapshot -> {
           String title = documentSnapshot.getString("chapterTitle");
           String descript = documentSnapshot.getString("chapterDescription");
           String content = documentSnapshot.getString("chapterContent");

           showDialog2(title, descript, content, storyId, chapterId);
        });
    }

    private void showDialog2(String title, String descript, String content, String storyId, String chapterId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View mView = LayoutInflater.from(this).inflate(R.layout.dialog_sua_chapter, null, false);
        builder.setView(mView);
        linearEditChapterTitle= mView.findViewById(R.id.linearEditChapterTitle);
        linearEditChapterDescript = mView.findViewById(R.id.linearEditChapterDescript);
        linearEditChapterContent = mView.findViewById(R.id.linearEditChapterContent);
        btnHuyEditChapter = mView.findViewById(R.id.btnHuyEditChapter);
        AlertDialog dialog = builder.show();
        dialog.setCancelable(false);

        linearEditChapterTitle.setOnClickListener(v -> {
            View mView2 = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_sheet_chapter_title, null, false);
            BottomSheetDialog sheetDialog = new BottomSheetDialog(this);
            sheetDialog.setContentView(mView2);
            tvEditChapterTitle = mView2.findViewById(R.id.tvEditChapterTitle);
            etEditChapterNewTitle = mView2.findViewById(R.id.etEditChapterNewTitle);
            btnEditChapterName = mView2.findViewById(R.id.btnEditChapterName);
            btnHuyEditChapterName = mView2.findViewById(R.id.btnHuyEditChapterName);
            sheetDialog.show();
            sheetDialog.setCancelable(false);

            tvEditChapterTitle.setText(title);

            btnEditChapterName.setOnClickListener(v1 -> {
                colStory.document(storyId).collection("Chapter").document(chapterId).update("chapterTitle", etEditChapterNewTitle.getText().toString().trim()).addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Sửa tên chương thành công", Toast.LENGTH_SHORT).show();
                    finish();
                });
            });

            btnHuyEditChapterName.setOnClickListener(v1 -> {
                sheetDialog.dismiss();
            });

        });

        linearEditChapterDescript.setOnClickListener(v -> {
            View mView2 = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_sheet_chapter_descript, null, false);
            BottomSheetDialog sheetDialog = new BottomSheetDialog(this);
            sheetDialog.setContentView(mView2);
            tvEditChapterDescript = mView2.findViewById(R.id.tvEditChapterDescript);
            etEditChapterNewDescript = mView2.findViewById(R.id.etEditChapterNewDescript);
            btnEditChapterDescript = mView2.findViewById(R.id.btnEditChapterDescript);
            btnHuyEditChapterDescript = mView2.findViewById(R.id.btnHuyEditChapterDescript);
            sheetDialog.show();
            sheetDialog.setCancelable(false);

            tvEditChapterDescript.setText(descript);

            btnEditChapterDescript.setOnClickListener(v1 -> {
                colStory.document(storyId).collection("Chapter").document(chapterId).update("chapterDescription", etEditChapterNewDescript.getText().toString().trim()).addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Sửa mô tả chương thành công", Toast.LENGTH_SHORT).show();
                    finish();
                });
            });

            btnHuyEditChapterDescript.setOnClickListener(v1 -> {
                sheetDialog.dismiss();
            });
        });

        linearEditChapterContent.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditChapterContentActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("content", content);
            bundle.putString("storyId", storyId);
            bundle.putString("chapterId", chapterId);
            intent.putExtra("chapter", bundle);
            startActivity(intent);
        });

        btnHuyEditChapter.setOnClickListener(v -> {
            finish();
        });

    }
}