package com.example.doctruyen_iread.FragmentCaNhan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.doctruyen_iread.FragmentThem.DoiMatKhau;
import com.example.doctruyen_iread.FragmentTrangChu.EditStoryActivity;
import com.example.doctruyen_iread.MainActivity;
import com.example.doctruyen_iread.ManageAccount.SignInActivity;
import com.example.doctruyen_iread.Module.UserObj;
import com.example.doctruyen_iread.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EditProfileFragment extends Fragment {

    private TextInputEditText edten, edTuoi;
    private Button btnSua, btnCanel;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference colRefUser = db.collection("User");
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DocumentReference docRef;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        edten = view.findViewById(R.id.ed_ten_thongtin);
        edTuoi = view.findViewById(R.id.ed_tuoi_thongtin);
        btnSua = view.findViewById(R.id.btn_thongtin_sua);
        UserObj obj = new UserObj();
        progressDialog = new ProgressDialog(getActivity());

        btnSua.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sua();
            }
        });
        return view;
    }
    private void sua() {
        String idusert = user.getEmail().toString().trim();
        String edten = edTuoi.getText().toString().trim();
        String edtuoi = edTuoi.getText().toString().trim();
        DocumentReference documentReference = db.collection("User").document();
        documentReference.update("userName",idusert).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getActivity(), "thanh cong", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
