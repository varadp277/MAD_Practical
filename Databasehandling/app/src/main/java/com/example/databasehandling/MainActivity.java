package com.example.databasehandling;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Button b1, b2;
    EditText eid, ename;
    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking UI
        eid = findViewById(R.id.editText1);
        ename = findViewById(R.id.editText2);
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        t1 = findViewById(R.id.textView3);

        // Create/Open Database
        db = openOrCreateDatabase("StudentDB", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Temp(id INTEGER, name TEXT)");

        // INSERT BUTTON
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ContentValues values = new ContentValues();
                values.put("id", eid.getText().toString());
                values.put("name", ename.getText().toString());

                long result = db.insert("Temp", null, values);

                if (result != -1) {
                    Toast.makeText(MainActivity.this, "Record Inserted", Toast.LENGTH_SHORT).show();
                    eid.setText("");
                    ename.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Insert Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // DISPLAY BUTTON
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String data = "";
                Cursor c = db.rawQuery("SELECT * FROM Temp", null);

                if (c.moveToFirst()) {
                    do {
                        data += "ID: " + c.getString(0) +
                                " Name: " + c.getString(1) + "\n";
                    } while (c.moveToNext());
                } else {
                    data = "No Records Found";
                }

                t1.setText(data);
                c.close();
            }
        });
    }
}