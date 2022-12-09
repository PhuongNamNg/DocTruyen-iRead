package com.example.doctruyen_iread.FragmentTrangChu;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditStoryActivity extends AppCompatActivity {
    private TextView tvEditStoryTitle, tvEditStoryDescript;
    private EditText etEditStoryNewTitle, etEditStoryNewDescript;
    private Button btnCancel, btnEditStoryName, btnHuyEditStoryName, btnEditStoryDescript, btnHuyEditStoryDescript;
    private LinearLayout linearEditName, linearEditDescrpit;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference colRef = db.collection("Story");
    private DocumentReference docRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_story);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("story");
        String title = bundle.getString("title");
        String descript = bundle.getString("descript");
        String id = bundle.getString("id");
        showDialog1(title, descript, id);


//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent1 = new Intent(EditStoryActivity.this, StoryDetailActivity.class);
//                intent1.putExtra("title", etTitle.getText().toString());
//                setResult(RESULT_OK, intent1);
//                finish();
//            }
//        });
    }

    private void showDialog1(String title, String descript, String id) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        View mView = LayoutInflater.from(this).inflate(R.layout.dialog_sua_truyen, null, false);
        builder.setView(mView);
        linearEditName = mView.findViewById(R.id.linearEditStoryTitle);
        linearEditDescrpit = mView.findViewById(R.id.linearEditStoryDescript);
        btnCancel = mView.findViewById(R.id.btnHuyEdit);
        AlertDialog dialog = builder.show();
        dialog.setCancelable(false);

        linearEditName.setOnClickListener(v -> {
            View mView2 = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_sheet_story_title, null);
            BottomSheetDialog sheetDialog = new BottomSheetDialog(this);
            sheetDialog.setContentView(mView2);
            btnEditStoryName = mView2.findViewById(R.id.btnEditStoryName);
            btnHuyEditStoryName = mView2.findViewById(R.id.btnHuyEditStoryName);
            tvEditStoryTitle = mView2.findViewById(R.id.tvEditStoryTitle);
            etEditStoryNewTitle = mView2.findViewById(R.id.etEditStoryNewTitle);
            sheetDialog.show();
            sheetDialog.setCancelable(false);

            tvEditStoryTitle.setText(title);

            btnEditStoryName.setOnClickListener(v1 -> {
                docRef = colRef.document(id);
                docRef.update("storyTitle", etEditStoryNewTitle.getText().toString().trim()).addOnSuccessListener(unused -> {
                    Toast.makeText(EditStoryActivity.this, "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                    finish();
                });
            });

            btnHuyEditStoryName.setOnClickListener(v1 -> {
                sheetDialog.dismiss();
            });

        });

        linearEditDescrpit.setOnClickListener(v -> {
            View mView2 = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_sheet_story_descript, null);
            BottomSheetDialog sheetDialog = new BottomSheetDialog(this);
            sheetDialog.setContentView(mView2);
            btnEditStoryDescript = mView2.findViewById(R.id.btnEditStoryDescript);
            btnHuyEditStoryDescript = mView2.findViewById(R.id.btnHuyEditStoryDescript);
            tvEditStoryDescript = mView2.findViewById(R.id.tvEditStoryDescript);
            etEditStoryNewDescript = mView2.findViewById(R.id.etEditStoryNewDescript);
            sheetDialog.show();
            sheetDialog.setCancelable(false);

            BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from((View) mView2.getParent());
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

            tvEditStoryDescript.setText(descript);
            etEditStoryNewDescript.setText(descript);

            btnEditStoryDescript.setOnClickListener(v1 -> {
                docRef = colRef.document(id);
                docRef.update("storyDescription", etEditStoryNewDescript.getText().toString().trim()).addOnSuccessListener(unused -> {
                    Toast.makeText(EditStoryActivity.this, "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                    finish();
                });
            });

            btnHuyEditStoryDescript.setOnClickListener(v1 -> {
                sheetDialog.dismiss();
            });
        });

        btnCancel.setOnClickListener(v -> {
            finish();
        });
    }
}