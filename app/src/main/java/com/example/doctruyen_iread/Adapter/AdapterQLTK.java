package com.example.doctruyen_iread.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctruyen_iread.Module.UserObj;
import com.example.doctruyen_iread.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterQLTK extends RecyclerView.Adapter<AdapterQLTK.Holder>{
    private Context mContext;
    private ArrayList<UserObj> userObjs = new ArrayList<>();

    public AdapterQLTK(Context mContext) {
        this.mContext = mContext;
    }

    public void getData(ArrayList<UserObj> userObjs) {
        this.userObjs = userObjs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_qltk, parent, false);
        return new AdapterQLTK.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.tvIndexQLTK.setText((position + 1) + ".");
        holder.tvNameUserQLTK.setText(userObjs.get(position).getUserName());

        String email = userObjs.get(position).getUserEmail();
        if (email.equalsIgnoreCase("namsg19@gmail.com") || email.equalsIgnoreCase("sangnnph14292@gmail.com")) {
            holder.tvCheckUserQLTK.setText("Admin");
        } else holder.tvCheckUserQLTK.setText("User");

        holder.imbDelQLTK.setOnClickListener(v -> {
            Toast.makeText(mContext, "Tính năng xóa đang cập nhật", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return userObjs.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private final CircleImageView imv;
        private final TextView tvIndexQLTK;
        private final TextView tvNameUserQLTK;
        private final TextView tvCheckUserQLTK;
        private final ImageButton imbDelQLTK;
        public Holder(@NonNull View itemView) {
            super(itemView);
            imv = itemView.findViewById(R.id.imvQLTK);
            tvIndexQLTK = itemView.findViewById(R.id.tvIndexQLTK);
            tvNameUserQLTK = itemView.findViewById(R.id.tvNameUserQLTK);
            tvCheckUserQLTK = itemView.findViewById(R.id.tvCheckUserQLTK);
            imbDelQLTK = itemView.findViewById(R.id.imbDelQLTK);
        }
    }
}
