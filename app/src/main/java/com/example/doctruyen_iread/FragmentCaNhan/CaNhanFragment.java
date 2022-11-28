package com.example.doctruyen_iread.FragmentCaNhan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctruyen_iread.Adapter.AdapterViewPagerCaNhan;
import com.example.doctruyen_iread.FragmentThem.DoiMatKhau;
import com.example.doctruyen_iread.FragmentThem.ThongKe;
import com.example.doctruyen_iread.FragmentThem.YeuThich;
import com.example.doctruyen_iread.FragmentTrangChu.AddStoryActivity;
import com.example.doctruyen_iread.ManageAccount.SignInActivity;

import com.example.doctruyen_iread.Module.Story;
import com.example.doctruyen_iread.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class CaNhanFragment extends Fragment {
    TextView tvThongKe, tvDangXuat, tvDoiMK, tvYeuThich, tvThemTruyen, tvNumb, tvNameUser;
    private TabLayout tab;
    private ViewPager2 vp;
    private AdapterViewPagerCaNhan adapter;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colStory = db.collection("Story");
    private CollectionReference colUser = db.collection("User");
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

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
        tab = view.findViewById(R.id.tablayoutCaNhan);
        vp = view.findViewById(R.id.vpCaNhan);
        tvNumb = view.findViewById(R.id.tvNumOfStory);
        tvNameUser = view.findViewById(R.id.tvNameUser);

        String email = user.getEmail();

        colUser.whereEqualTo("userEmail", email).get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
            DocumentSnapshot docSnap = list.get(0);
            String name = docSnap.getString("userName");
            getStory(name);
            tvNameUser.setText(name);
        });


        adapter = new AdapterViewPagerCaNhan(getActivity());
        vp.setAdapter(adapter);

        new TabLayoutMediator(tab, vp, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Danh Sách Truyện");
                    break;
                case 1:
                    tab.setText("Sửa Thông Tin");
                    break;
                case 2:
                    tab.setText("Lịch Sử");
                    break;
            }
        }).attach();
        return view;
    }

    private void getStory(String name) {
        colStory.whereEqualTo("authorsName", name).get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<Story> stories = new ArrayList<>();
            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
            for (DocumentSnapshot docSnap : list) {
                Story mStory = docSnap.toObject(Story.class);
                stories.add(mStory);
            }
            tvNumb.setText(String.valueOf(stories.size()));
        });
    }
}