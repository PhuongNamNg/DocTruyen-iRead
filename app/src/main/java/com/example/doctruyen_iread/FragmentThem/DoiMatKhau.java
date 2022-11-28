package com.example.doctruyen_iread.FragmentThem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctruyen_iread.MainActivity;
import com.example.doctruyen_iread.ManageAccount.SignInActivity;
import  com.example.doctruyen_iread.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DoiMatKhau extends AppCompatActivity {
    private EditText doipasss,xacnhanpasss;
    private Button doiMK,quaylai;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);

        progressDialog = new ProgressDialog(this);
        doipasss = findViewById(R.id.doipass);
        xacnhanpasss = findViewById(R.id.xacnhanpass);
        doiMK = findViewById(R.id.btn_doimatkhau);
        quaylai = findViewById(R.id.btn_quaylai);

        doiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDoiMk();
            }
        });

        quaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DoiMatKhau.this);
                builder.setTitle("Thông Báo");
                builder.setMessage("Bạn có muốn thoát không");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.setNegativeButton("NO",null);
                builder.show();

            }
        });
    }
    private void onClickDoiMk(){
        String strDoiMatKhau = doipasss.getText().toString().trim();
        String strxacnhan = xacnhanpasss.getText().toString().trim();
        if(strDoiMatKhau.isEmpty()||strxacnhan.isEmpty()){
            Toast.makeText(DoiMatKhau.this, "Không được để trống", Toast.LENGTH_SHORT).show();
        }
        else if(strDoiMatKhau.length()<6){
            Toast.makeText(DoiMatKhau.this, "Mật khẩu phải trên 6 kí tự", Toast.LENGTH_SHORT).show();
        }
        else if(strDoiMatKhau.equals(strxacnhan)){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            progressDialog.show();
            user.updatePassword(strDoiMatKhau)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(DoiMatKhau.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(DoiMatKhau.this, MainActivity.class));
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(DoiMatKhau.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
                            FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(DoiMatKhau.this, SignInActivity.class);
                            Toast.makeText(DoiMatKhau.this, "Mời bạn đăng nhập", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    });

        }
        else{
            Toast.makeText(DoiMatKhau.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
        }
    }
}