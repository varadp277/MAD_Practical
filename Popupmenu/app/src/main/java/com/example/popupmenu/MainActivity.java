package com.example.popupmenu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.clickBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(MainActivity.this, button);

                popupMenu.getMenuInflater().inflate(R.menu.popupmenu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();

                        if (id == R.id.android) {
                            Toast.makeText(MainActivity.this, "You Clicked Android", Toast.LENGTH_SHORT).show();
                        }
                        else if (id == R.id.java) {
                            Toast.makeText(MainActivity.this, "You Clicked JAVA", Toast.LENGTH_SHORT).show();
                        }
                        else if (id == R.id.kotlin) {
                            Toast.makeText(MainActivity.this, "You Clicked Kotlin", Toast.LENGTH_SHORT).show();
                        }

                        return true;
                    }
                });

                popupMenu.show();
            }
        });
    }
}