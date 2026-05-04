package com.example.radiobutton;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId == -1) {
                    Toast.makeText(MainActivity.this, "No option selected", Toast.LENGTH_SHORT).show();
                } else if (selectedId == R.id.radioJava) {
                    Toast.makeText(MainActivity.this, "JAVA Selected", Toast.LENGTH_SHORT).show();
                } else if (selectedId == R.id.radioHTML) {
                    Toast.makeText(MainActivity.this, "HTML Selected", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}