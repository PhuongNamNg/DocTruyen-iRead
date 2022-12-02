package com.example.doctruyen_iread.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctruyen_iread.FragmentTrangChu.ReadChapterActivity;
import com.example.doctruyen_iread.FragmentTrangChu.StoryDetailActivity;
import com.example.doctruyen_iread.Module.Story;
import com.example.doctruyen_iread.R;

import java.util.ArrayList;

public class AdapterThongKe extends RecyclerView.Adapter<AdapterThongKe.Holder> {
    Context mContext;
    ArrayList<Story> stories = new ArrayList<>();

    public AdapterThongKe(Context mContext) {
        this.mContext = mContext;
    }


    public void getData (ArrayList<Story> stories) {
        this.stories = stories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thong_ke, parent, false);
        return new Holder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.image.setImageResource(R.drawable.anh3);
        holder.title.setText(stories.get(position).getStoryTitle());
        holder.readTime.setText("Lượt Xem: " + stories.get(position).getStoryViews());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StoryDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("chapter", stories.get(holder.getAdapterPosition()).getStoryTitle());
                intent.putExtra("story", bundle);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final TextView title;
        private final TextView readTime;
        private final CardView cardView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imvStoryThongKe);
            title = itemView.findViewById(R.id.tvTitleThongKe);
            readTime = itemView.findViewById(R.id.tvReadTime);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
