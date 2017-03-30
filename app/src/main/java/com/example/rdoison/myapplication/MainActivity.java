package com.example.rdoison.myapplication;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";
    TextView countdown;
    CountDownTimer countDownTimer;
    EditText runEdit;
    EditText walkEdit;
    EditText durationEdit;
    final static int MS_IN_S = 1000;
    final static int S_IN_M = 60;
    final static long INTERVAL = 1000;
    long runDuration;
    long walkDuration;
    long totalDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        countdown = (TextView) findViewById(R.id.countdown);
        runEdit = (EditText) findViewById(R.id.run);
        walkEdit = (EditText) findViewById(R.id.walk);
        durationEdit = (EditText) findViewById(R.id.duration);
    }

    public void startProgram(View view) {
        Intent intent = new Intent(this, ProgramActivity.class);
        String tag = view.getTag().toString();
        intent.putExtra("tag", tag);
        startActivity(intent);
    }

    public void start(View view) {
        runDuration = Long.valueOf(runEdit.getText().toString());
        walkDuration = Long.valueOf(walkEdit.getText().toString());
        totalDuration = Long.valueOf(durationEdit.getText().toString()) * S_IN_M;
        Log.d(TAG, "runDuration :"+runDuration);
        Log.d(TAG, "walkDuration :"+walkDuration);
        Log.d(TAG, "totalDuration :"+totalDuration);
        countDownTimer = new CountDownTimer(runDuration * MS_IN_S, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / MS_IN_S;
                long minutes = seconds / S_IN_M;
                seconds = seconds % S_IN_M;
                countdown.setText(String.format(Locale.getDefault(), "Run : %1$02d:%2$02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                totalDuration = totalDuration - runDuration;
                Log.i(TAG, "left :"+totalDuration);
                if (totalDuration > 0) {
                    startWalk();
                }
            }
        };
        countDownTimer.start();
    }

    public void startWalk() {
        countDownTimer = new CountDownTimer(walkDuration * MS_IN_S, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / MS_IN_S;
                long minutes = seconds / S_IN_M;
                seconds = seconds % S_IN_M;
                countdown.setText(String.format(Locale.getDefault(), "Walk : %1$02d:%2$02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                totalDuration = totalDuration - walkDuration;
                Log.i(TAG, "left :"+totalDuration);
                if (totalDuration >= runDuration) {
                    startRun();
                }
            }
        };
        countDownTimer.start();
    }

    public void startRun() {
        countDownTimer = new CountDownTimer(runDuration * MS_IN_S, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / MS_IN_S;
                long minutes = seconds / S_IN_M;
                seconds = seconds % S_IN_M;
                countdown.setText(String.format(Locale.getDefault(), "Run : %1$02d:%2$02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                totalDuration = totalDuration - runDuration;
                Log.i(TAG, "left :"+totalDuration);
                if (totalDuration > 0) {
                    startWalk();
                }
            }
        };
        countDownTimer.start();
    }
}
