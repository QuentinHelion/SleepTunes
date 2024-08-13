package com.music.sleeptunes;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private NumberPicker npHours;
    private NumberPicker npMinutes;
    private NumberPicker npSeconds;
    private TextView tvRemainingTime;
    private Button btnStart;
    private Button btnStop;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize NumberPickers
        npHours = findViewById(R.id.npHours);
        npMinutes = findViewById(R.id.npMinutes);
        npSeconds = findViewById(R.id.npSeconds);
        tvRemainingTime = findViewById(R.id.tvRemainingTime);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

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
                startTimer(totalSeconds);
                btnStart.setEnabled(false); // Disable Start button
                btnStop.setVisibility(Button.VISIBLE); // Show Stop button
            } else {
                Toast.makeText(MainActivity.this, "Please enter a valid time", Toast.LENGTH_SHORT).show();
            }
        });

        btnStop.setOnClickListener(v -> stopTimer());
    }

    private void startTimer(int totalSeconds) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(totalSeconds * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long hours = (millisUntilFinished / (1000 * 3600));
                long minutes = (millisUntilFinished / (1000 * 60)) % 60;
                long seconds = (millisUntilFinished / 1000) % 60;

                String timeRemaining = String.format("Time Remaining: %02d:%02d:%02d", hours, minutes, seconds);
                tvRemainingTime.setText(timeRemaining);
            }

            @Override
            public void onFinish() {
                stopMusic();
                resetUI();
            }
        }.start();
    }

    private void stopMusic() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.dispatchMediaKeyEvent(new android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_MEDIA_PAUSE));
            audioManager.dispatchMediaKeyEvent(new android.view.KeyEvent(android.view.KeyEvent.ACTION_UP, android.view.KeyEvent.KEYCODE_MEDIA_PAUSE));
            Toast.makeText(MainActivity.this, "Music stopped", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            resetUI();
            Toast.makeText(MainActivity.this, "Timer stopped", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetUI() {
        btnStart.setEnabled(true);
        btnStop.setVisibility(Button.GONE);
        tvRemainingTime.setText("Time Remaining: ");
    }
}
