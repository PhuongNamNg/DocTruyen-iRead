package com.example.doctruyen_iread.FragmentThem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.doctruyen_iread.Adapter.AdapterDuyetTruyen;
import com.example.doctruyen_iread.Module.Story;
import com.example.doctruyen_iread.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DuyetTruyenActivity extends AppCompatActivity {
    private SearchView searchView2;
    private RecyclerView recyViewDuyetTruyen;
    private AdapterDuyetTruyen adapter;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colStory = db.collection("Story");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duyet_truyen);
        Toolbar toolbar ;
        recyViewDuyetTruyen = findViewById(R.id.revieDuyetTruyen);
        toolbar = findViewById(R.id.toobar_ve);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        colStory.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            ArrayList<Story> stories = new ArrayList<>();
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for (DocumentSnapshot docSnap : list) {
                    Story mstory = docSnap.toObject(Story.class);
                    stories.add(mstory);
                    Log.e("checkList", String.valueOf(stories.size()));

                }

                GridLayoutManager gridLayoutManager = new GridLayoutManager(DuyetTruyenActivity.this, 2);
                recyViewDuyetTruyen.setLayoutManager(gridLayoutManager);

                adapter = new AdapterDuyetTruyen(DuyetTruyenActivity.this);
                adapter.getData(stories);

                recyViewDuyetTruyen.setAdapter(adapter);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView2 = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView2.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView2.setMaxWidth(Integer.MAX_VALUE);

        searchView2.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}