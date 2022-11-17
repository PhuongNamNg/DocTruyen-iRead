package com.example.doctruyen_iread.FragmentTrangChu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doctruyen_iread.MainActivity;
import com.example.doctruyen_iread.Module.Story;
import com.example.doctruyen_iread.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddStoryActivity extends AppCompatActivity {
    private EditText etTitle, etContent, etAuthorName;
    private Button btnSave, btnCancel;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final DocumentReference noteRef = db.collection("Story").document("Story 1");
    private final CollectionReference colRef = db.collection("Story");
    private String storyDatePost, datePost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_story);
        etTitle = findViewById(R.id.etAddTitle);
        etContent = findViewById(R.id.etAddContent);
        etAuthorName = findViewById(R.id.etAddAuthor);
        btnSave = findViewById(R.id.btnEditSave);
        btnCancel = findViewById(R.id.btnEditCancel);

        btnSave.setEnabled(false);

        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                btnSave.setEnabled(!etTitle.getText().toString().isEmpty());
            }
        });

        etAuthorName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                btnSave.setEnabled(!etTitle.getText().toString().isEmpty());
            }
        });

        btnSave.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddStoryActivity.this);
            builder.setTitle("THÔNG BÁO!");
            builder.setMessage("Bạn chắc chắn muốn lưu?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Story mStory = new Story();
                    mStory.setStoryTitle(etTitle.getText().toString().trim());
                    mStory.setAuthorsName(etAuthorName.getText().toString());
                    mStory.setStoryViews(0);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a, dd-MM-yyyy");
                    storyDatePost = dateFormat.format(Calendar.getInstance().getTime());

                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
                    datePost = dateFormat2.format(Calendar.getInstance().getTime());

                    mStory.setStoryId(etTitle.getText().toString().trim() + datePost);
                    mStory.setStoryDatePost(storyDatePost);
                    colRef.document(mStory.getStoryId()).set(mStory).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(AddStoryActivity.this, "Lưu thành công!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AddStoryActivity.this, MainActivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddStoryActivity.this, "Lỗi!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddStoryActivity.this, MainActivity.class));
            }
        });

    }
}