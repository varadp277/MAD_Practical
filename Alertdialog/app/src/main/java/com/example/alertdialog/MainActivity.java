package com.example.alertdialog;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);

        button.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Alert");
            builder.setMessage("Do you want to close this application?");
            builder.setCancelable(false);

            builder.setPositiveButton("Yes", (dialog, which) -> {
                finish(); // Close app
            });

            builder.setNegativeButton("No", (dialog, which) -> {
                dialog.cancel();
            });

            AlertDialog alert = builder.create();
            alert.show();
        });
    }
}