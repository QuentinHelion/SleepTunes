package com.music.sleeptunes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private NumberPicker npHours;
    private NumberPicker npMinutes;
    private NumberPicker npSeconds;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize NumberPickers
        npHours = findViewById(R.id.npHours);
        npMinutes = findViewById(R.id.npMinutes);
        npSeconds = findViewById(R.id.npSeconds);
        btnStart = findViewById(R.id.btnStart);

        // Set NumberPicker ranges
        npHours.setMinValue(0);
        npHours.setMaxValue(23);

        npMinutes.setMinValue(0);
        npMinutes.setMaxValue(59);

        npSeconds.setMinValue(0);
        npSeconds.setMaxValue(59);

        btnStart.setOnClickListener(v -> {
            int hours = npHours.getValue();
            int minutes = npMinutes.getValue();
            int seconds = npSeconds.getValue();

            int totalSeconds = hours * 3600 + minutes * 60 + seconds;

            if (totalSeconds > 0) {
                Intent intent = new Intent(MainActivity.this, TimerActivity.class);
                intent.putExtra("totalSeconds", totalSeconds);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Please enter a valid time", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
