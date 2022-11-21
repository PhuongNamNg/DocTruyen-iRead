package com.example.doctruyen_iread.FragmentTrangChu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doctruyen_iread.MainActivity;
import com.example.doctruyen_iread.Module.Chapter;
import com.example.doctruyen_iread.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.C;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddStoryActivityNext extends AppCompatActivity {
    Button btnChapterCancel, btnChapterSave;
    EditText etChapterTitle, etChapterDescript, etChapterContent;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colChapter;
    private DocumentReference docStory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_story_next);

        btnChapterCancel = findViewById(R.id.btnChapterCancel);
        btnChapterSave = findViewById(R.id.btnChapterSave);
        etChapterTitle = findViewById(R.id.etChapterTitle);
        etChapterDescript = findViewById(R.id.etChapterDescription);
        etChapterContent = findViewById(R.id.etChapterContent);

        btnChapterSave.setEnabled(false);

        etChapterTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etChapterTitle.getText().toString().length() > 10) {
                    btnChapterSave.setEnabled(true);
                } else {
                    btnChapterSave.setEnabled(false);
                }

            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("story");
        String storyId = bundle.getString("storyId");

        btnChapterCancel.setOnClickListener(v -> {
            finish();
        });

        btnChapterSave.setOnClickListener(v -> {
            Chapter mChapter = new Chapter();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String datePost = dateFormat.format(Calendar.getInstance().getTime());

            mChapter.setChapterTitle(etChapterTitle.getText().toString().trim());
            mChapter.setChapterId(etChapterTitle.getText().toString().trim() + datePost);
            mChapter.setChapterDescription(etChapterDescript.getText().toString().trim());
            mChapter.setChapterContent(etChapterContent.getText().toString().trim());

            colChapter = db.collection("Story").document(storyId).collection("Chapter");
            colChapter.document(mChapter.getChapterId()).set(mChapter).addOnSuccessListener(unused -> {
                Toast.makeText(this, "Lưu chuyện thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
            });
        });

    }
}