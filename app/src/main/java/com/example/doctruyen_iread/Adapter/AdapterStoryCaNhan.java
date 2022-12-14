package com.example.doctruyen_iread.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctruyen_iread.FragmentTrangChu.EditStoryActivity;
import com.example.doctruyen_iread.FragmentTrangChu.StoryDetailActivity;
import com.example.doctruyen_iread.Module.Story;
import com.example.doctruyen_iread.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdapterStoryCaNhan extends RecyclerView.Adapter<AdapterStoryCaNhan.Holder> {
    Context mContext;
    ArrayList<Story> stories = new ArrayList<>();
    private DocumentReference docRef;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public AdapterStoryCaNhan(Context mContext) {
        this.mContext = mContext;
    }

    public void getData(ArrayList<Story> stories) {
        this.stories = stories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ca_nhan, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.img.setImageResource(R.drawable.anh3);
        holder.name.setText(stories.get(position).getStoryTitle());
        holder.index.setText((position + 1) + ".");
        holder.views.setText("Lượt xem: " + stories.get(position).getStoryViews());
        Boolean check = stories.get(position).isStoryCheck();

        if (check == true) {
            holder.check.setText("Đã Duyệt");
            holder.check.setTextColor(ContextCompat.getColor(mContext, R.color.light_green));
        } else if (check == false) {
            holder.check.setText("Chưa Duyệt");
            holder.check.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        } else {
            return;
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StoryDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", stories.get(holder.getAdapterPosition()).getStoryTitle());
                bundle.putString("id", stories.get(holder.getAdapterPosition()).getStoryId());
                bundle.putString("author", stories.get(holder.getAdapterPosition()).getAuthorsName());
                bundle.putBoolean("check", false);
                intent.putExtra("story", bundle);
                mContext.startActivity(intent);
            }
        });

        holder.imbEdit.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, EditStoryActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("title", stories.get(position).getStoryTitle());
            bundle.putString("descript", stories.get(position).getStoryDescription());
            bundle.putString("id", stories.get(position).getStoryId());
            intent.putExtra("story", bundle);
            mContext.startActivity(intent);
        });

        holder.cardView.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("THÔNG BÁO");
            builder.setMessage("Bạn chắc chắn muốn xóa?");
            builder.setPositiveButton("OK", (dialog, which) -> {
                docRef = db.collection("Story").document(stories.get(holder.getAdapterPosition()).getStoryId());
                docRef.delete().addOnSuccessListener(unused -> {
                    Toast.makeText(mContext, "Xóa", Toast.LENGTH_SHORT).show();
                    stories.remove(holder.getAdapterPosition());
                    getData(stories);
                }).addOnFailureListener(e -> Toast.makeText(mContext, "Lỗi", Toast.LENGTH_SHORT).show());
            });
            builder.setNegativeButton("No", null);
            builder.show();
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        private final ImageView img;
        private final TextView name;
        private final TextView index;
        private final TextView views;
        private final TextView check;
        private final LinearLayout imbEdit;

        public Holder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imvStoryCaNhan);
            name = itemView.findViewById(R.id.tvTitleCaNhan);
            cardView = itemView.findViewById(R.id.cardView);
            views = itemView.findViewById(R.id.tvViewCaNhan);
            imbEdit = itemView.findViewById(R.id.linearEditCaNhan);
            index = itemView.findViewById(R.id.tvIndex);
            check = itemView.findViewById(R.id.tvCheckCaNhan);
        }
    }
}
