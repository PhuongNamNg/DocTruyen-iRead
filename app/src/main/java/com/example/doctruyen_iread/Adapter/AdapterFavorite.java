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
import com.example.doctruyen_iread.Module.UserObj;
import com.example.doctruyen_iread.R;

import java.util.ArrayList;

public class
AdapterFavorite extends RecyclerView.Adapter<AdapterFavorite.Holder> {

    private final Context mContext;
    private ArrayList<Story> stories;

    public AdapterFavorite(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(ArrayList<Story> stories){
        this.stories = stories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yeu_thich,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.imgUser.setImageResource(R.drawable.anh1);
        holder.tvName.setText(stories.get(position).getStoryTitle());
        holder.tvview.setText("Lượt xem: " +stories.get(position).getStoryViews().toString());
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
        if (stories != null){
            return stories.size();
        }
        return 0;
    }

    public class Holder extends RecyclerView.ViewHolder{

        private final ImageView imgUser;
        private final TextView tvName,tvview;
        private final CardView cardView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView_item);
            imgUser = itemView.findViewById(R.id.item_img_yeuthich);
            tvName = itemView.findViewById(R.id.item_tv_yeu_thich);
            tvview = itemView.findViewById(R.id.item_tv_yeu);
        }
    }
}