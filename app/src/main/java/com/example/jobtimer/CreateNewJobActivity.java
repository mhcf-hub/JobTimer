package com.example.jobtimer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class CreateNewJobActivity extends AppCompatActivity {

    //Views for Colorpicker
    View viewColor0;
    View viewColor1;
    View viewColor2;
    View viewColor3;
    View viewColor4;
    View viewColor5;
    View viewColor6;
    View viewColor7;

    ////View for the color chosen in colorpicker
    View viewColorChosen;

    //EditText to save the job's title
    EditText title;

    //Button to save the Job and return
    Button newJobSave;

    //View Model to save job
    private RoomJobViewModel roomJobViewModel;
    private TimingViewModel timingViewModel;
    //int got get jobId;
    int jobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_job);

        //Viewmodel for jobsaving:
        roomJobViewModel = new ViewModelProvider(this).get(RoomJobViewModel.class);
        timingViewModel = new ViewModelProvider(this).get(TimingViewModel.class);

        //Set observer for datachange
        roomJobViewModel.getAllJobs().observe(this, new Observer<List<RoomJob>>() {
            @Override
            public void onChanged(@Nullable final List<RoomJob> rJobs) {
                // Update the cached copy of the words in the adapter.
                System.out.println(rJobs + " jobs");
                jobId = rJobs.size() + 1;
                for (RoomJob rJob : rJobs){
                    System.out.println(rJob.getTitle() + " job title");
                }
            }
        });

        //Set observer for datachange
        timingViewModel.getAllTimings().observe(this, new Observer<List<Timing>>() {
            @Override
            public void onChanged(@Nullable final List<Timing> timings) {
                // Update the cached copy of the words in the adapter.
                System.out.println(timings + " jobs timings");
                for (Timing timing : timings){
                    System.out.println(timing.getId() + " job title");
                }
            }
        });




        //View for colorpicker
        viewColor0  = (View) findViewById(R.id.viewColor0);
        viewColor1  = (View) findViewById(R.id.viewColor1);
        viewColor2  = (View) findViewById(R.id.viewColor2);
        viewColor3  = (View) findViewById(R.id.viewColor3);
        viewColor4  = (View) findViewById(R.id.viewColor4);
        viewColor5  = (View) findViewById(R.id.viewColor5);
        viewColor6  = (View) findViewById(R.id.viewColor6);
        viewColor7  = (View) findViewById(R.id.viewColor7);

        //Chosen color, by default is blue
        viewColorChosen = (View) findViewById(R.id.viewColorChosen);
        viewColorChosen.setBackgroundColor(getResources().getColor(R.color.blue));

        //set Colors to views for colorpicker
        viewColor0.setBackgroundColor(getResources().getColor(R.color.blue));
        viewColor1.setBackgroundColor(getResources().getColor(R.color.red));
        viewColor2.setBackgroundColor(getResources().getColor(R.color.green));
        viewColor3.setBackgroundColor(getResources().getColor(R.color.yellow));
        viewColor4.setBackgroundColor(getResources().getColor(R.color.lightblue));
        viewColor5.setBackgroundColor(getResources().getColor(R.color.lightred));
        viewColor6.setBackgroundColor(getResources().getColor(R.color.lightgreen));
        viewColor7.setBackgroundColor(getResources().getColor(R.color.lightyellow));

        //Click listeners for Colorpicker
        //Click on view to change the selected color
        //colorchosen view will get the picked color
        //Blue
        viewColor0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chosencolor set to picker color
                viewColorChosen.setBackgroundColor(getResources().getColor(R.color.blue));
            }
        });

        //Red
        viewColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chosencolor set to picker color
                viewColorChosen.setBackgroundColor(getResources().getColor(R.color.red));
            }
        });

        //Green
        viewColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chosencolor set to picker color
                viewColorChosen.setBackgroundColor(getResources().getColor(R.color.green));
            }
        });

        //Yellow
        viewColor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chosencolor set to picker color
                viewColorChosen.setBackgroundColor(getResources().getColor(R.color.yellow));
            }
        });

        //lightblue
        viewColor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chosencolor set to picker color
                viewColorChosen.setBackgroundColor(getResources().getColor(R.color.lightblue));
            }
        });


        //lightred
        viewColor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chosencolor set to picker color
                viewColorChosen.setBackgroundColor(getResources().getColor(R.color.lightred));
            }
        });


        //lightgreen
        viewColor6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chosencolor set to picker color
                viewColorChosen.setBackgroundColor(getResources().getColor(R.color.lightgreen));
            }
        });


        //lightyellow
        viewColor7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chosencolor set to picker color
                viewColorChosen.setBackgroundColor(getResources().getColor(R.color.lightyellow));
            }
        });


        //get the button for saving the job and set clikclistener:
        newJobSave = (Button) findViewById(R.id.buttonSingleSave);
        newJobSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveJob();
            }
        });

    }

    //method to save the new job
    public void saveJob(){
        //get Text from Title Input
        title = (EditText) findViewById(R.id.editTextTitle);
        String titleSet = title.getText().toString();

        //translate colorchoice for saving
        int color = Color.TRANSPARENT;
        Drawable background = viewColorChosen.getBackground();
        if (background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();

        //Save new Job to RoomDB
        RoomJob rJob = new RoomJob(titleSet);
        rJob.setColor(color);
        roomJobViewModel.insert(rJob);

//
//
//        Timing timing = new Timing(jobId, 1, "0", 1, "0");
//        timingViewModel.insertTiming(timing);

        Intent intent = new Intent(CreateNewJobActivity.this, MainActivity.class);
        startActivity(intent);

    }
}
