package com.example.doctruyen_iread.FragmentCaNhan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctruyen_iread.FragmentCaNhan.InCaNhan.DangXuat;
import com.example.doctruyen_iread.FragmentCaNhan.InCaNhan.DoiMatKhau;
import com.example.doctruyen_iread.FragmentCaNhan.ThongKe;
import com.example.doctruyen_iread.FragmentCaNhan.YeuThich;
import com.example.doctruyen_iread.FragmentTrangChu.AddStoryActivity;
import com.example.doctruyen_iread.MainActivity;
import com.example.doctruyen_iread.ManageAccount.SignInActivity;

import com.example.doctruyen_iread.R;
import com.google.firebase.auth.FirebaseAuth;


public class CaNhanFragment extends Fragment {

    TextView tvThongKe,tvDangXuat,tvDoiMK,tvYeuThich, tvThemTruyen;
    public CaNhanFragment() {
    }

    public static CaNhanFragment newInstance(String param1, String param2) {
        CaNhanFragment fragment = new CaNhanFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ca_nhan, container, false);
        tvThongKe = view.findViewById(R.id.tvThongKe);
        tvYeuThich = view.findViewById(R.id.tvYeuThich);
        tvDoiMK = view.findViewById(R.id.tvDoiMK);
        tvDangXuat = view.findViewById(R.id.tvDangXuat);
        tvThemTruyen = view.findViewById(R.id.tvThemTruyen);

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

        return  view;
    }
}