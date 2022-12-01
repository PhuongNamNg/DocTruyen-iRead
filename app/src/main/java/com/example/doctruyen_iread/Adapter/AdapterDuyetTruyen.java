package com.example.doctruyen_iread.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctruyen_iread.FragmentTrangChu.StoryDetailActivity;
import com.example.doctruyen_iread.Module.Story;
import com.example.doctruyen_iread.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterDuyetTruyen extends RecyclerView.Adapter<AdapterDuyetTruyen.Holder> implements Filterable {
    Context mContext;
    ArrayList<Story> stories = new ArrayList<>();
    ArrayList<Story> storiesss = new ArrayList<>();

    public AdapterDuyetTruyen(Context mContext) {
        this.mContext = mContext;
    }

    public void getData(ArrayList<Story> stories) {
        this.stories = stories;
        this.storiesss = stories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_duyet_truyen, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.img.setImageResource(R.drawable.anh3);
        holder.name.setText(stories.get(position).getStoryTitle());
        Boolean check = stories.get(position).isStoryCheck();

        if (check == false) {
            holder.status.setText("Chưa Duyệt");
            holder.status.setTextColor(ContextCompat.getColor(mContext, R.color.red));
        } else if (check == true) {
            holder.status.setText("Đã Duyệt");
            holder.status.setTextColor(ContextCompat.getColor(mContext, R.color.yellow));
        }

        holder.cardView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, StoryDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("title", stories.get(holder.getAdapterPosition()).getStoryTitle());
            bundle.putString("id", stories.get(holder.getAdapterPosition()).getStoryId());
            bundle.putBoolean("check", true);
            intent.putExtra("story", bundle);
            mContext.startActivity(intent);
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
        private final TextView status;

        public Holder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imvItemDuyetTruyen);
            name = itemView.findViewById(R.id.tvItemDuyetTruyen);
            cardView = itemView.findViewById(R.id.cardView);
            status = itemView.findViewById(R.id.tvItemDuyetTruyenStatus);
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    stories = storiesss;
                } else {
                    List<Story> list = new ArrayList<>();
                    for (Story story : storiesss) {
                        if (story.getStoryTitle().toLowerCase().contains(strSearch.toLowerCase())) {
                            list.add(story);
                        }
                    }
                    stories = (ArrayList<Story>) list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = stories;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                stories = (ArrayList<Story>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
