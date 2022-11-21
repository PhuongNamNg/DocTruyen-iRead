package com.example.doctruyen_iread.FragmentTrangChu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doctruyen_iread.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class EditStoryActivity extends AppCompatActivity {
    private EditText etTitle, etContent;
    private Button btnSave, btnCancel;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference colRef = db.collection("Story");
    private DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_story);
        etTitle = findViewById(R.id.etAddTitle);
        etContent = findViewById(R.id.etAddDescription);
        btnSave = findViewById(R.id.btnAddNext);
        btnCancel = findViewById(R.id.btnAddCancel);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("story");
        etTitle.setText(bundle.getString("title"));
        etContent.setText(bundle.getString("content"));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditStoryActivity.this);
                builder.setTitle("THÔNG BÁO!");
                builder.setMessage("Bạn chắc chắn muốn lưu?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = bundle.getString("title");

                        colRef.whereEqualTo("storyTitle", title).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            String docId;
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                Log.e("list", "" + list.size());
                                for (DocumentSnapshot docSnap : list) {
                                    docId = docSnap.getId();
                                }
                                colRef.document(docId).update("storyTitle", etTitle.getText().toString(),
                                        "storyContent", etContent.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(EditStoryActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                                        Intent intent1 = new Intent(EditStoryActivity.this, ReadStoryActivity.class);
                                        intent1.putExtra("title", etTitle.getText().toString());
                                        setResult(RESULT_OK, intent1);
                                        finish();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(EditStoryActivity.this, "Sửa Lỗi", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EditStoryActivity.this, ReadStoryActivity.class);
                intent1.putExtra("title", etTitle.getText().toString());
                setResult(RESULT_OK, intent1);
                finish();
            }
        });
    }
}