package com.example.doctruyen_iread.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctruyen_iread.Module.Story;
import com.example.doctruyen_iread.Module.TheLoai;
import com.example.doctruyen_iread.R;
import com.example.doctruyen_iread.Adapter.AdapterTruyenTheLoai;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdapterTheLoai extends RecyclerView.Adapter<AdapterTheLoai.Holder> {
    Context mcontext;
    private ArrayList<TheLoai> theLoais = new ArrayList<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colRefStory = db.collection("Story");

    public AdapterTheLoai(Context context) {
        this.mcontext = context;
    }

    public void setData(ArrayList<TheLoai> theLoais) {
        this.theLoais = theLoais;
        notifyDataSetChanged();
    }

//    public void setDatatruyen(ArrayList<Story> stories) {
//        this.stories = stories;
//        notifyDataSetChanged();
//    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theloai, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String tenTheLoai = theLoais.get(position).getTheloaiTen();
        holder.textViewTl.setText(tenTheLoai);

        colRefStory.whereEqualTo("storyCategory", tenTheLoai).get().addOnSuccessListener(queryDocumentSnapshots -> {
            ArrayList<Story> stories = new ArrayList<>();
            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
            for (DocumentSnapshot docSnap : list) {
                if (docSnap.getBoolean("storyCheck") == true) {
                    Story mstory = docSnap.toObject(Story.class);
                    stories.add(mstory);
                }
            }
            if (stories != null) {
                if (stories.size() > 0) {
                    holder.tvTrong.setVisibility(View.GONE);
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(mcontext, 2);
                    holder.recyclerViewTl.setLayoutManager(gridLayoutManager);

                    AdapterTruyenTheLoai adapterTruyenTheLoai = new AdapterTruyenTheLoai(mcontext);
                    adapterTruyenTheLoai.setData(stories);
                    holder.recyclerViewTl.setAdapter(adapterTruyenTheLoai);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return theLoais.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private final TextView textViewTl;
        private final TextView tvTrong;
        private final RecyclerView recyclerViewTl;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textViewTl = itemView.findViewById(R.id.item_textView);
            tvTrong = itemView.findViewById(R.id.tvTrong);
            recyclerViewTl = itemView.findViewById(R.id.item_recvi);
        }
    }

}
