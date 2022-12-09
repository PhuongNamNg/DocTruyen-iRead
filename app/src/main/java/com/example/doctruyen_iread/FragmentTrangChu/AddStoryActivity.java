package com.example.doctruyen_iread.FragmentTrangChu;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doctruyen_iread.Adapter.AdapterDropDownTheLoai;
import com.example.doctruyen_iread.MainActivity;
import com.example.doctruyen_iread.Module.Story;
import com.example.doctruyen_iread.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddStoryActivity extends AppCompatActivity {
    private EditText etTitle, etDescription, etAuthorName;
    private Button btnNext, btnCancel;
    private AutoCompleteTextView autoCompleteTv;
    private AdapterDropDownTheLoai adapterDropDown;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference colStory = db.collection("Story");
    private final CollectionReference colTheLoai = db.collection("TheLoai");
    private final CollectionReference colUser = db.collection("User");
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String storyDatePost, datePost, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_story);

        etTitle = findViewById(R.id.etAddTitle);
        etDescription = findViewById(R.id.etAddDescription);
        btnNext = findViewById(R.id.btnAddNext);
        btnCancel = findViewById(R.id.btnAddCancel);
        autoCompleteTv = findViewById(R.id.autoCompleteTheLoai);

        colTheLoai.get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<String> listTheLoai = new ArrayList<>();
            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
            for (DocumentSnapshot docSnap : list) {
               listTheLoai.add(docSnap.getString("theloaiTen"));
            }
            adapterDropDown = new AdapterDropDownTheLoai(this, R.layout.item_dropdown_theloai, listTheLoai);
            autoCompleteTv.setAdapter(adapterDropDown);
        });

        etTitle.requestFocus();
        etTitle.postDelayed(() -> {
            //Hiện bàn phím tự động
            InputMethodManager keyboard = (InputMethodManager) getSystemService(AddStoryActivity.this.INPUT_METHOD_SERVICE);
            keyboard.showSoftInput(etTitle, 0);
        }, 200);

        btnNext.setEnabled(false);

        getUserName();

        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etTitle.getText().toString().length() > 10) {
                    btnNext.setEnabled(true);
                    btnNext.setTextColor(ContextCompat.getColor(AddStoryActivity.this, R.color.content_white));
                } else {
                    btnNext.setEnabled(false);
                }
            }
        });


        btnNext.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(AddStoryActivity.this);
            builder.setTitle("THÔNG BÁO!");
            builder.setMessage("Bạn chắc chắn muốn lưu?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Story mStory = new Story();
                    mStory.setStoryTitle(etTitle.getText().toString().trim());
                    mStory.setAuthorsName(userName);
                    mStory.setStoryDescription(etDescription.getText().toString());
                    mStory.setStoryViews(0);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a, dd-MM-yyyy");
                    storyDatePost = dateFormat.format(Calendar.getInstance().getTime());

                    SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
                    datePost = dateFormat2.format(Calendar.getInstance().getTime());

                    String id = etTitle.getText().toString().trim() + datePost;
                    mStory.setStoryId(id);

                    String theLoai = autoCompleteTv.getText().toString().trim();
                    mStory.setStoryCategory(theLoai);

                    mStory.setStoryDatePost(storyDatePost);
                    colStory.document(id).set(mStory).addOnSuccessListener(unused -> {
                        Toast.makeText(AddStoryActivity.this, "Lưu thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddStoryActivity.this, AddChapterActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("storyId",mStory.getStoryId());
                        bundle.putBoolean("check", false);
                        intent.putExtra("story", bundle);
                        startActivity(intent);
//                        startActivity(new Intent(AddStoryActivity.this, MainActivity.class));
                    }).addOnFailureListener(e ->
                            Toast.makeText(AddStoryActivity.this, "Lỗi!", Toast.LENGTH_SHORT).show());
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void getUserName () {
        if (user.getEmail() != null) {
            colUser.whereEqualTo("userEmail",user.getEmail()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                @Override
                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot doc:list) {
                        userName = doc.getString("userName");
                    }
                }
            });
        }
    }
}