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

import com.example.doctruyen_iread.FragmentTrangChu.StoryDetailActivity;
import com.example.doctruyen_iread.Module.Story;
import com.example.doctruyen_iread.R;

import java.util.ArrayList;

public class AdapterStoryViews extends RecyclerView.Adapter<AdapterStoryViews.Holder>{
    Context mContext;
    ArrayList<Story> stories = new ArrayList<>();

    public AdapterStoryViews(Context mContext) {
        this.mContext = mContext;
    }

    public void getData(ArrayList<Story> stories) {
        this.stories = stories;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story_views, parent, false);
        return new AdapterStoryViews.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.img.setImageResource(R.drawable.anh1);
        holder.name.setText(stories.get(position).getStoryTitle());

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
    }

    @Override
    public int getItemCount() {
        if (stories.size() >= 6){
            return 6;
        } else return stories.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        private final ImageView img;
        private final TextView name;

        public Holder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imvItemImgStoryViews);
            name = itemView.findViewById(R.id.tvItemTitleStoryViews);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
