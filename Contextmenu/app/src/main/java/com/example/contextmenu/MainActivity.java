package com.example.contextmenu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text);
        layout = findViewById(R.id.rel);

        // Register TextView for Context Menu
        registerForContextMenu(textView);
    }

    // Create Context Menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Choose a color");

        menu.add(0, 1, 0, "Yellow");
        menu.add(0, 2, 0, "Gray");
        menu.add(0, 3, 0, "Cyan");
    }

    // Handle Menu Click
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == 1) {
            layout.setBackgroundColor(Color.YELLOW);
        }
        else if (id == 2) {
            layout.setBackgroundColor(Color.GRAY);
        }
        else if (id == 3) {
            layout.setBackgroundColor(Color.CYAN);
        }

        return true;
    }
}