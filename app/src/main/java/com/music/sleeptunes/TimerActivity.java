package com.music.sleeptunes;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TimerActivity extends AppCompatActivity {

    private TextView tvRemainingTime;
    private Button btnStop;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        tvRemainingTime = findViewById(R.id.tvRemainingTime);
        btnStop = findViewById(R.id.btnStop);

        int totalSeconds = getIntent().getIntExtra("totalSeconds", 0);

        startTimer(totalSeconds);

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
                returnToMainActivity();
            }
        }.start();
    }

    private void stopMusic() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            audioManager.dispatchMediaKeyEvent(new android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_MEDIA_PAUSE));
            audioManager.dispatchMediaKeyEvent(new android.view.KeyEvent(android.view.KeyEvent.ACTION_UP, android.view.KeyEvent.KEYCODE_MEDIA_PAUSE));
            Toast.makeText(TimerActivity.this, "Music stopped", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            returnToMainActivity();
            Toast.makeText(TimerActivity.this, "Timer stopped", Toast.LENGTH_SHORT).show();
        }
    }

    private void returnToMainActivity() {
        Intent intent = new Intent(TimerActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Close the current activity
    }
}
