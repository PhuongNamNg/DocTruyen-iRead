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

import com.example.doctruyen_iread.FragmentThem.DoiMatKhau;
import com.example.doctruyen_iread.FragmentThem.ThongKe;
import com.example.doctruyen_iread.FragmentThem.YeuThich;
import com.example.doctruyen_iread.FragmentTrangChu.AddStoryActivity;
import com.example.doctruyen_iread.ManageAccount.SignInActivity;

import com.example.doctruyen_iread.R;
import com.google.firebase.auth.FirebaseAuth;


public class CaNhanFragment extends Fragment {
    TextView tvThongKe,tvDangXuat,tvDoiMK,tvYeuThich, tvThemTruyen;
    public CaNhanFragment() {}

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

        return  view;
    }
}