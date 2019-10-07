package com.example.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.*;

public class InCallActivity extends AppCompatActivity {
    private static final String PHONE_NUMBER = "phone_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_in_call);

        TextView callDuration = findViewById(R.id.callDuration);
        TextView contactNameInCall = findViewById(R.id.contactNameInCall);
        TextView contactNumberInCall = findViewById(R.id.contactNumberInCall);
        Timer timer = new Timer();
        FloatingActionButton fab = findViewById(R.id.fabEndCall);
        String phoneNumber = getIntent().getStringExtra(PHONE_NUMBER);
        Contact contact = MainActivity.DIALER.findContactInMap(phoneNumber);

        contactNameInCall.setText(contact.getName());
        contactNumberInCall.setText(phoneNumber);

        decideIfBusy(callDuration, fab);
        setTimersAndListeners(timer,fab, callDuration);
        //MainActivity.DIALER
    }

    private void decideIfBusy(TextView callDuration, FloatingActionButton fab) {
        Random rand = new Random();
        int r = rand.nextInt(10);
        if(r < 2){
            callDuration.setText(R.string.busy);
            CountDownTimer blinkTimer = new CountDownTimer(1450, 350) {
                boolean f = true;

                @Override
                public void onTick(long millisUntilFinished) {
                    if(f) {
                        runOnUiThread(() -> callDuration.setTextColor(getResources().getColor(R.color.blinkingTextColor, null)));
                        f = false;
                    }
                    else {
                        runOnUiThread(() -> callDuration.setTextColor(getResources().getColor(R.color.textColor, null)));
                        f = true;
                    }
                }

                @Override
                public void onFinish() {
                    finish();
                }
            };
            fab.setEnabled(false);
            makeCall("B");
            blinkTimer.start();
        }
    }

    public void setTimersAndListeners(Timer timer, FloatingActionButton fab, TextView callDuration){
        setFabListener(timer, fab, callDuration);
        scheduleTimer(timer, callDuration);
    }

    private void scheduleTimer(Timer timer, TextView callDuration) {
        timer.scheduleAtFixedRate(new TimerTask() {
            boolean f = true;
            @Override
            public void run() {
                int hours, minutes, seconds;
                if(f) {
                    hours = minutes = seconds = 0;
                    f = false;
                }
                else{
                    String[] parts = callDuration.getText().toString().split(":");
                    hours = Integer.parseInt(parts[0]);
                    minutes = Integer.parseInt(parts[1]);
                    seconds = Integer.parseInt(parts[2]);
                }
                ++seconds;
                minutes += seconds / 60;
                hours += minutes / 60;
                seconds = seconds % 60;
                minutes = minutes % 60;
                String temp = String.format("%d:%02d:%02d", hours, minutes, seconds);
                runOnUiThread(() -> callDuration.setText(temp));
            }
        }, 5000, 1000);
    }

    private void setFabListener(Timer timer, FloatingActionButton fab, TextView callDuration) {
        fab.setOnClickListener(v -> {
            if(callDuration.getText().toString().equals("DIALING")){
                makeCall("M");
                callDuration.setText(R.string.hanging_up);
            }
            else {
                makeCall("D");
                String timestamp = callDuration.getText().toString();
                callDuration.setText("HANGING UP\n" + timestamp);
                callDuration.setTextSize(20);
            }
            fab.setEnabled(false);
            pauseBeforeHangUp(timer, callDuration);
        });
    }

    private void pauseBeforeHangUp(Timer timer, TextView callDuration) {
        timer.cancel();
        CountDownTimer blinkTimer = new CountDownTimer(1450, 350) {
            boolean f = true;

            @Override
            public void onTick(long millisUntilFinished) {
                if(f) {
                    runOnUiThread(() -> callDuration.setTextColor(getResources().getColor(R.color.blinkingTextColor, null)));
                    f = false;
                }
                else {
                    runOnUiThread(() -> callDuration.setTextColor(getResources().getColor(R.color.textColor, null)));
                    f = true;
                }
            }

            @Override
            public void onFinish() {
                finish();
            }
        };
        blinkTimer.start();
    }

    private void makeCall(String type){
        TextView contactNumberInCall = findViewById(R.id.contactNumberInCall);
        TextView callDuration = findViewById(R.id.callDuration);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String currentDateTime = sdf.format(new Date());
        String number = contactNumberInCall.getText().toString();
        String duration;
        if(callDuration.getText().toString().split(":").length != 3)
            duration = "0:00:00";
        else duration = callDuration.getText().toString();
        MainActivity.DIALER.makeCall(number,currentDateTime,duration,type);
    }
}
