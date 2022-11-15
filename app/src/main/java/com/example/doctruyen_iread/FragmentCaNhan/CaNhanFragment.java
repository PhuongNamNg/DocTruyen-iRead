package com.example.doctruyen_iread.FragmentCaNhan;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.doctruyen_iread.FragmentCaNhan.InCaNhan.DangXuat;
import com.example.doctruyen_iread.FragmentCaNhan.InCaNhan.DoiMatKhau;
import com.example.doctruyen_iread.FragmentCaNhan.InCaNhan.ThongKe;
import com.example.doctruyen_iread.FragmentCaNhan.InCaNhan.YeuThich;
import com.example.doctruyen_iread.R;


public class CaNhanFragment extends Fragment {

    TextView tvThongKe,tvDangXuat,tvDoiMK,tvYeuThich;
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
                startActivity(new Intent(getActivity(), DangXuat.class));
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