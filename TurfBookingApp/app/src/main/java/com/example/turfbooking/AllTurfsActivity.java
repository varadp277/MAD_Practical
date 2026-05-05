package com.example.turfbooking;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AllTurfsActivity extends AppCompatActivity {

    private RecyclerView rvAllTurfs;
    private TurfAdapter turfAdapter;
    private DatabaseHelper databaseHelper;
    private List<Turf> allTurfs;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_turfs);

        userId = getIntent().getIntExtra("USER_ID", -1);
        databaseHelper = new DatabaseHelper(this);

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> finish());

        rvAllTurfs = findViewById(R.id.rvAllTurfs);
        rvAllTurfs.setLayoutManager(new LinearLayoutManager(this));

        this.allTurfs = databaseHelper.getAllTurfs();
        
        for (Turf t : allTurfs) {
            float rating = databaseHelper.getAverageRatingForTurf(t.getId());
            t.setRating(rating);
        }

        turfAdapter = new TurfAdapter(this, allTurfs, userId);
        rvAllTurfs.setAdapter(turfAdapter);

        EditText etSearchAllTurfs = findViewById(R.id.etSearchAllTurfs);
        etSearchAllTurfs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterTurfs(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterTurfs(String query) {
        List<Turf> filteredTurfs = new ArrayList<>();
        for (Turf turf : allTurfs) {
            if (turf.getName().toLowerCase().contains(query.toLowerCase()) ||
                turf.getLocation().toLowerCase().contains(query.toLowerCase())) {
                filteredTurfs.add(turf);
            }
        }
        turfAdapter.updateTurfs(filteredTurfs);
    }
}
