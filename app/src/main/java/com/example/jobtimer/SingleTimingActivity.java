package com.example.jobtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SingleTimingActivity extends AppCompatActivity {

    //Button for stopping a timing
    Button buttonStopTiming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_timing);

        //find view for Button for stopping a timing
        buttonStopTiming = findViewById(R.id.buttonEndTiming);
    }

    public void startTimer(View view){
        ObjectAnimator animation = ObjectAnimator.ofFloat(buttonStopTiming,
                                "translationY",
                                buttonStopTiming.getTranslationY(),
                                buttonStopTiming.getTranslationY() + 162);

                        animation.setDuration(600);
                        animation.start();
    }
}
