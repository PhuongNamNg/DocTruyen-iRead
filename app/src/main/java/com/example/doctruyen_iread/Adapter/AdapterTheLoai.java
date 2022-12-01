package com.example.doctruyen_iread.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctruyen_iread.Module.Story;
import com.example.doctruyen_iread.Module.TheLoai;
import com.example.doctruyen_iread.R;
import com.example.doctruyen_iread.Adapter.AdapterTruyenTheLoai;

import java.util.ArrayList;

public class AdapterTheLoai extends RecyclerView.Adapter<AdapterTheLoai.Holder> {
    Context mcontext;
    ArrayList<TheLoai> theLoais = new ArrayList<>();
    ArrayList<Story> stories = new ArrayList<>();
    public AdapterTheLoai(Context context) {
        this.mcontext = context;
    }
    public void setData(ArrayList<TheLoai> theLoais){
        this.theLoais = theLoais;
        notifyDataSetChanged();
    }
    public  void setDatatruyen(ArrayList<Story> stories){
        this.stories = stories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theloai,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.textViewTl.setText(theLoais.get(position).getTheloaiTen());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mcontext,RecyclerView.HORIZONTAL,false);
        holder.recyclerViewTl.setLayoutManager(linearLayoutManager);

        AdapterTruyenTheLoai adapterTruyenTheLoai = new AdapterTruyenTheLoai();
        adapterTruyenTheLoai.setData(stories);
        holder.recyclerViewTl.setAdapter(adapterTruyenTheLoai);
    }

    @Override
    public int getItemCount() {
        return theLoais.size();
    }


    public class Holder extends RecyclerView.ViewHolder {
    private final TextView textViewTl;
    private final RecyclerView recyclerViewTl;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textViewTl = itemView.findViewById(R.id.item_textView);
            recyclerViewTl = itemView.findViewById(R.id.item_recvi);
        }
    }

}
