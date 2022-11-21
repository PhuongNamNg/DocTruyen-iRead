package com.example.doctruyen_iread.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctruyen_iread.Module.Chapter;
import com.example.doctruyen_iread.R;

import java.util.ArrayList;

public class AdapterChapter extends RecyclerView.Adapter<AdapterChapter.Holder>{
    Context mContext;
    ArrayList<Chapter> listChapter = new ArrayList<>();

    public AdapterChapter(Context mContext) {
        this.mContext = mContext;
    }

    public void getData(ArrayList<Chapter> listChapter) {
        this.listChapter = listChapter;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.tvTitleChapter.setText("Chapter " + (position + 1) + ": " + listChapter.get(position).getChapterTitle());
    }

    @Override
    public int getItemCount() {
        return listChapter.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView tvTitleChapter;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tvTitleChapter = itemView.findViewById(R.id.tvTitleChapter);
        }
    }
}
