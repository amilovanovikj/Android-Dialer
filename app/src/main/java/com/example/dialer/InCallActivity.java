package com.example.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.*;

public class InCallActivity extends AppCompatActivity {
    private static final String PHONE_NUMBER = "phone_number";
    private FloatingActionButton fab;
    private boolean muted = false;
    private boolean onSpeaker = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_in_call);

        TextView callDuration = findViewById(R.id.callDuration);
        TextView contactNameInCall = findViewById(R.id.contactNameInCall);
        TextView contactNumberInCall = findViewById(R.id.contactNumberInCall);
        Timer timer = new Timer();
        fab = findViewById(R.id.fabEndCall);
        String phoneNumber = getIntent().getStringExtra(PHONE_NUMBER);
        Contact contact = MainActivity.DIALER.findContactInMap(phoneNumber);

        contactNameInCall.setText(contact.getName());
        contactNumberInCall.setText(phoneNumber);

        decideIfBusy(callDuration);
        setTimersAndListeners(timer, callDuration);
        //MainActivity.DIALER
    }

    public void speakerClicked(View view){
        ImageView ivSpeaker = findViewById(R.id.ivSpeaker);
        if(onSpeaker){
            ivSpeaker.setImageResource(R.drawable.ic_turn_speaker_on);
        }
        else {
            ivSpeaker.setImageResource(R.drawable.ic_turn_speaker_off);
        }
        onSpeaker = !onSpeaker;
    }

    public void muteClicked(View view){
        ImageView ivMic = findViewById(R.id.ivMic);
        if(muted){
            ivMic.setImageResource(R.drawable.ic_call_unmuted);
        }
        else {
            ivMic.setImageResource(R.drawable.ic_call_muted);
        }
        muted = !muted;
    }

    private void decideIfBusy(TextView callDuration) {
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
            finishCall("B");
            blinkTimer.start();
        }
    }

    public void setTimersAndListeners(Timer timer, TextView callDuration){
        setFabListener(timer, callDuration);
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

    private void setFabListener(Timer timer, TextView callDuration) {
        fab.setOnClickListener(v -> {
            if(callDuration.getText().toString().equals("DIALING")){
                finishCall("M");
                callDuration.setText(R.string.hanging_up);
            }
            else {
                finishCall("D");
                String timestamp = callDuration.getText().toString();
                callDuration.setText("HANGING UP\n" + timestamp);
                callDuration.setTextSize(20);
            }
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

    private void finishCall(String type){
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

        ImageView ivMic = findViewById(R.id.ivMic);
        ImageView ivSpeaker = findViewById(R.id.ivSpeaker);
        ivMic.setClickable(false);
        ivSpeaker.setClickable(false);
        fab.setEnabled(false);
    }
}
