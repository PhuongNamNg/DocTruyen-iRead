package com.example.doctruyen_iread;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.doctruyen_iread.ManageAccount.SignInActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        },3000);
    }

    private void nextActivity() {
        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        if (user == null){
            //chua login
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        }else {
            //da login
            Intent intent = new Intent(this, com.example.doctruyen_iread.MainActivity.class);
            startActivity(intent);
        }
        finish();
    }
}