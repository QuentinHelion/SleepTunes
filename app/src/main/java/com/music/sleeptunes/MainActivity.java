package com.music.sleeptunes;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etTimer;
    private Button btnStart;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etTimer = findViewById(R.id.etTimer);
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(v -> {
            String timeString = etTimer.getText().toString();
            int timeInSeconds;

            try {
                timeInSeconds = Integer.parseInt(timeString);
                if (timeInSeconds > 0) {
                    startTimer(timeInSeconds);
                } else {
                    Toast.makeText(MainActivity.this, "Please enter a valid time", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Please enter a valid time", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startTimer(int timeInSeconds) {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(timeInSeconds * 1000L, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the UI if needed
            }

            @Override
            public void onFinish() {
                stopMusic();
            }
        }.start();
    }

    private void stopMusic() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            // Pause music by sending a media button event
            audioManager.dispatchMediaKeyEvent(new android.view.KeyEvent(android.view.KeyEvent.ACTION_DOWN, android.view.KeyEvent.KEYCODE_MEDIA_PAUSE));
            audioManager.dispatchMediaKeyEvent(new android.view.KeyEvent(android.view.KeyEvent.ACTION_UP, android.view.KeyEvent.KEYCODE_MEDIA_PAUSE));
            Toast.makeText(MainActivity.this, "Music stopped", Toast.LENGTH_SHORT).show();
        }
    }
}
