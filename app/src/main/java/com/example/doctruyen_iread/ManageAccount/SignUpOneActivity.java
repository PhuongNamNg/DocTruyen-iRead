package com.example.doctruyen_iread.ManageAccount;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doctruyen_iread.FragmentTrangChu.AddStoryActivity;
import com.example.doctruyen_iread.MainActivity;
import com.example.doctruyen_iread.Module.UserObj;
import com.example.doctruyen_iread.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpOneActivity extends AppCompatActivity {
    private TextInputEditText txtTuoi,txtName;
    private Button btnNextt,btncancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_one);

        txtName = findViewById(R.id.edt_Username);
        txtTuoi = findViewById(R.id.edt_Tuoi);
        btnNextt = findViewById(R.id.btn_next);
        btncancel = findViewById(R.id.btn_Cancell);

        btnNextt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtTen = txtName.getText().toString().trim();
                String txttuoi =  txtTuoi.getText().toString().trim();
                if (txtTen.isEmpty() || txttuoi.isEmpty()) {
                    Toast.makeText(SignUpOneActivity.this, "Không được để trống", Toast.LENGTH_SHORT).show();
                }else if (Integer.valueOf(txttuoi) < 1){
                    Toast.makeText(SignUpOneActivity.this, "Tuổi không nhỏ hơn 1", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(SignUpOneActivity.this,SignUpActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("txtTen",txtTen);
                    bundle.putInt("txtTuoi",Integer.valueOf(txttuoi));
                    intent.putExtra("bundle",bundle);
                    startActivity(intent);
                }
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpOneActivity.this, SignInActivity.class));
            }
        });

    }

}