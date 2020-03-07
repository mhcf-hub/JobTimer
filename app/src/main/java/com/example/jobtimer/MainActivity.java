package com.example.jobtimer;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.provider.Contacts.SettingsColumns.KEY;

public class MainActivity extends AppCompatActivity {
    //Buttons for Main Menu
    ////create New Job
    Button newJob;
    ////Show all Jobs
    Button jobOverview;

    //View Models for Job (main entity) and timings(Sub entity)
    private RoomJobViewModel roomJobViewModel;
    private TimingViewModel roomJobViewModelTimings;

    public static final int NEW_JOB_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //declare viewModel for counting jobs
        roomJobViewModel = new ViewModelProvider(this).get(RoomJobViewModel.class);
        roomJobViewModel.getAllJobs().observe(this, new Observer<List<RoomJob>>() {
            @Override
            public void onChanged(@Nullable final List<RoomJob> rJobs) {
                // Update the cached copy of the words in the adapter.
                System.out.println(rJobs + " jobs");
                for (RoomJob rJob : rJobs){
                    System.out.println(rJob.getTitle() + " job title");
                }
            }
        });

        roomJobViewModelTimings = new ViewModelProvider(this).get(TimingViewModel.class);
        roomJobViewModelTimings.getAllTimings().observe(this, new Observer<List<Timing>>() {
            @Override
            public void onChanged(@Nullable final List<Timing> timings) {
                // Update the cached copy of the words in the adapter.
                System.out.println(timings + " job timings");
                for (Timing timing : timings){
                    System.out.println(timing + " job timings");
                }
            }
        });

        //Declare buttons for Main menu
        ////create new Job
        newJob = (Button) findViewById(R.id.buttonNewJob);
        newJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNewJobActivity.class);
                startActivity(intent);
            }
        });

        //////Show all Jobs
        jobOverview= (Button) findViewById(R.id.buttonAllJobs);
        jobOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OverviewJobsActivity.class);
                startActivity(intent);
            }
        });
    }
}
