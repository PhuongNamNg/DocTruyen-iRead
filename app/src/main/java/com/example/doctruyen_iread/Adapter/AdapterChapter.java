package com.example.doctruyen_iread.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctruyen_iread.FragmentTrangChu.ReadChapterActivity;
import com.example.doctruyen_iread.Module.Chapter;
import com.example.doctruyen_iread.R;

import java.util.ArrayList;

public class AdapterChapter extends RecyclerView.Adapter<AdapterChapter.Holder>{
    Context mContext;
    ArrayList<Chapter> listChapter = new ArrayList<>();
    String storyId, authorsName, storyTitle;

    public AdapterChapter(Context mContext) {
        this.mContext = mContext;
    }

    public void getData(ArrayList<Chapter> listChapter) {
        this.listChapter = listChapter;
        notifyDataSetChanged();
    }

    public void getStoryId(String storyId) {
        this.storyId = storyId;
    }

    public void getAuthorsName(String authorsName) {this.authorsName = authorsName;}

    public void getTitleName(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.tvIndexChapter.setText(" " + (position + 1) + ": ");
        holder.tvTitleChapter.setText(listChapter.get(position).getChapterTitle());
        holder.tvDescripChapter.setText(listChapter.get(position).getChapterDescription());
        String chapterId = listChapter.get(position).getChapterId();
        holder.mCardView.setOnClickListener(v -> {
            Intent mIntent = new Intent(mContext, ReadChapterActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putString("chapterId", chapterId);
            mBundle.putString("storyId", storyId);
            mBundle.putString("authorsName", authorsName);
            mBundle.putString("storyTitle", storyTitle);
            mIntent.putExtra("chapter", mBundle);
            mContext.startActivity(mIntent);
        });
    }

    @Override
    public int getItemCount() {
        return listChapter.size();
    }



    public class Holder extends RecyclerView.ViewHolder {
        private final TextView tvTitleChapter;
        private final TextView tvDescripChapter;
        private final TextView tvIndexChapter;
        private final CardView mCardView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tvTitleChapter = itemView.findViewById(R.id.tvTitleChapter);
            tvDescripChapter = itemView.findViewById(R.id.tvDescripChapter);
            mCardView= itemView.findViewById(R.id.cardView);
            tvIndexChapter = itemView.findViewById(R.id.tvIndexChapter);
        }
    }
}
