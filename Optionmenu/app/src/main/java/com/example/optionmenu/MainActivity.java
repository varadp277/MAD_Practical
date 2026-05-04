package com.example.optionmenu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    RelativeLayout currentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentLayout = findViewById(R.id.a);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.android:
                currentLayout.setBackgroundColor(Color.RED);
                return true;

            case R.id.java:
                currentLayout.setBackgroundColor(Color.GREEN);
                return true;

            case R.id.kotlin:
                currentLayout.setBackgroundColor(Color.BLUE);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}