package com.example.ratingbar;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RatingBar ratingBar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ratingBar = findViewById(R.id.ratingBar);
        button = findViewById(R.id.button);

        button.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            Toast.makeText(MainActivity.this,
                    "Rating: " + rating,
                    Toast.LENGTH_SHORT).show();
        });
    }
}