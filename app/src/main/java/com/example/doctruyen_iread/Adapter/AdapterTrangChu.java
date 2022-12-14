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
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctruyen_iread.FragmentTrangChu.StoryDetailActivity;
import com.example.doctruyen_iread.Module.Story;
import com.example.doctruyen_iread.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterTrangChu extends RecyclerView.Adapter<AdapterTrangChu.Holder> implements Filterable {
    Context mContext;
    ArrayList<Story> stories = new ArrayList<>();
    ArrayList<Story> storiess = new ArrayList<>();

    public AdapterTrangChu(Context mContext) {
        this.mContext = mContext;
    }

    public void getData(ArrayList<Story> stories) {
        this.stories = stories;
        this.storiess = stories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_story, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.img.setImageResource(R.drawable.anh2);
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
        return stories.size();
    }



    public class Holder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        private final ImageView img;
        private final TextView name;

        public Holder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imvItem);
            name = itemView.findViewById(R.id.tvItem);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()) {
                    stories = storiess;
                }else {
                    List<Story> list = new ArrayList<>();
                    for (Story story: storiess){
                        if (story.getStoryTitle().toLowerCase().contains(strSearch.toLowerCase())){
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
