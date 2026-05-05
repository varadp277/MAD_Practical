package com.example.turfbooking;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddTurfActivity extends AppCompatActivity {

    private EditText etTurfName, etTurfLocation, etTurfPrice;
    private Button btnSaveTurf;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_turf);

        Toolbar toolbar = findViewById(R.id.toolbarAddTurf);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Add Turf");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        databaseHelper = new DatabaseHelper(this);

        etTurfName = findViewById(R.id.etTurfName);
        etTurfLocation = findViewById(R.id.etTurfLocation);
        etTurfPrice = findViewById(R.id.etTurfPrice);
        btnSaveTurf = findViewById(R.id.btnSaveTurf);

        btnSaveTurf.setOnClickListener(v -> {
            String name = etTurfName.getText().toString().trim();
            String location = etTurfLocation.getText().toString().trim();
            String priceStr = etTurfPrice.getText().toString().trim();

            if (name.isEmpty() || location.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceStr);
            String mockImageName = "turf_placeholder"; // We would handle real images in a complex app
            String defaultCategory = "Football";

            boolean isInserted = databaseHelper.addTurf(name, location, price, mockImageName, defaultCategory);
            if (isInserted) {
                Toast.makeText(this, "Turf added successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add Turf", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
