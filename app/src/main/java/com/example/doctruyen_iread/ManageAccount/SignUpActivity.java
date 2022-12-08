package com.example.doctruyen_iread.ManageAccount;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.doctruyen_iread.FragmentThem.DoiMatKhau;
import com.example.doctruyen_iread.Module.UserObj;
import com.example.doctruyen_iread.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtEmail,edtPassword,edtcheckpass, edtUserName, edtTuoi;
    private Button btnSignUp;
    private Button btn_Cancel;
    private ProgressDialog progressDialog;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference colRef = db.collection("User");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initUi();
        initListener();
        cancel();

    }
    private void initUi(){
        progressDialog = new ProgressDialog(this);
        edtUserName = findViewById(R.id.edt_Username);
        edtTuoi = findViewById(R.id.edt_Tuoi);
        edtEmail = findViewById(R.id.edt_email1);
        edtPassword = findViewById(R.id.edt_password);
        btnSignUp = findViewById(R.id.btn_sign_up);
        btn_Cancel = findViewById(R.id.btn_Cancel);
        edtcheckpass = findViewById(R.id.edt_checkpass);
    }
    private void initListener(){
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        String name = bundle.getString("txtTen");
        Integer tuoi = bundle.getInt("txtTuoi");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSigUp(name,tuoi);
            }
        });
    }
    private boolean onClickSigUp(String strUserName,Integer intTuoi) {
        String strcheckpass = edtcheckpass.getText().toString().trim();
        String strEmail = edtEmail.getText().toString().trim();
        String strPassword = edtPassword.getText().toString().trim();

        if (strEmail.isEmpty() || strPassword.isEmpty() || strUserName.isEmpty() || intTuoi.toString().isEmpty()) {
            Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
        }
        else if(strPassword.length()<6){
            Toast.makeText(this, "Mật khẩu phải trên 6 kí tự", Toast.LENGTH_SHORT).show();
        }
        else if(strPassword.equals(strcheckpass)) {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            progressDialog.show();
            auth.createUserWithEmailAndPassword(strEmail, strPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                builder.setTitle("Đăng ký thành công");
                                builder.setMessage("Chúc mừng bạn đã nhận được 300 xu");
                                builder.setPositiveButton("Nhận", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                                        intent.putExtra("Email",strEmail);
                                        startActivity(intent);
                                        startActivity(intent);
                                        finishAffinity();
                                    }
                                });
                                builder.show();

                                addUser(strEmail, strUserName, intTuoi);
                            } else {
                                Toast.makeText(SignUpActivity.this, "Đăng ký thất bại",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else{
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    private void cancel(){
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addUser (String strEmail, String strUserName, int intTuoi) {
        UserObj user = new UserObj();
        user.setUserEmail(strEmail);
        user.setUserName(strUserName);
        user.setUserAge(intTuoi);
        user.setUserToken(300);
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy");
        String datePost = dateFormat2.format(Calendar.getInstance().getTime());
        String userID = strUserName + datePost;
        user.setUserID(userID);

        colRef.document(userID).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.i("Check add user", "Thành Công");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("Check add user", "Thất Bại");
            }
        });
    }
}