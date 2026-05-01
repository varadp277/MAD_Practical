package com.example.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    String channel_id = "01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Permission for Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
        }

        Button b = findViewById(R.id.button);

        b.setOnClickListener(v -> {

            NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        channel_id,
                        "My Channel",
                        NotificationManager.IMPORTANCE_HIGH
                );
                nm.createNotificationChannel(channel);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channel_id)
                    .setContentTitle("Hello")
                    .setContentText("This is notification")
                    .setSmallIcon(android.R.drawable.ic_dialog_info);

            nm.notify(1, builder.build());
        });
    }
}