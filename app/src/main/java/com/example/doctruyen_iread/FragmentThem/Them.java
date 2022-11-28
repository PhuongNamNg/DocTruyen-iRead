package com.example.doctruyen_iread.FragmentThem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctruyen_iread.FragmentTrangChu.AddStoryActivity;
import com.example.doctruyen_iread.ManageAccount.SignInActivity;
import com.example.doctruyen_iread.Module.TheLoai;
import com.example.doctruyen_iread.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Them extends Fragment {
    TextView tvThongKe,tvDangXuat,tvDoiMK,tvYeuThich, tvThemTruyen, tvDuyetTruyen, tvThemTheLoai;
    EditText etTen, etMieuTa;
    Button btnThem, btnHuy;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colTheLoai = db.collection("TheLoai");
    public Them() {}

    public static Them newInstance(String param1, String param2) {
        Them fragment = new Them();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_them, container, false);
        tvThongKe = view.findViewById(R.id.tvThongKe);
        tvYeuThich = view.findViewById(R.id.tvYeuThich);
        tvDoiMK = view.findViewById(R.id.tvDoiMK);
        tvDangXuat = view.findViewById(R.id.tvDangXuat);
        tvThemTruyen = view.findViewById(R.id.tvThemTruyen);
        tvThemTheLoai = view.findViewById(R.id.tvThemTheLoai);
        tvDuyetTruyen = view.findViewById(R.id.tvDuyetTruyen);

        tvThemTruyen.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddStoryActivity.class));
        });

        tvThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ThongKe.class));
            }
        });
        tvYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), YeuThich.class));
            }
        });
        tvDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Thông Báo");
                builder.setMessage("Bạn Có Muốn Đăng Xuất?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        Toast.makeText(getActivity(), "Bạn Đã Đăng Xuất", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), SignInActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("NO", null);
                builder.show();
            }
        });
        tvDoiMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DoiMatKhau.class));
            }
        });

        tvThemTheLoai.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View mView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_them_theloai, null, false);
            etTen = mView.findViewById(R.id.etTenTheLoai);
            etMieuTa = mView.findViewById(R.id.etMieuTaTheLoai);
            btnThem = mView.findViewById(R.id.btnThemTheLoai);
            btnHuy = mView.findViewById(R.id.btnHuyTheLoai);
            builder.setView(mView);
            AlertDialog dialog = builder.show();

            btnThem.setOnClickListener(v1 -> {
                TheLoai theLoai = new TheLoai();
                theLoai.setTheloaiTen(etTen.getText().toString().trim());
                theLoai.setTheloaiMieuTa(etMieuTa.getText().toString().trim());
                colTheLoai.document(etTen.getText().toString().trim()).set(theLoai).addOnSuccessListener(unused -> {
                    Toast.makeText(getActivity(), "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                });
            });

            btnHuy.setOnClickListener(v1 -> {
                dialog.dismiss();
            });
        });

        tvDuyetTruyen.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), DuyetTruyenActivity.class));
        });

        return  view;
    }
}