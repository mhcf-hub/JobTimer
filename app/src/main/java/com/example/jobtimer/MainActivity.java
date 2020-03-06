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
    AllJobs allJobs;

    Button newJob;
    Button jobOverview;

    TextView jobNr;

    private RoomJobViewModel roomJobViewModel;

    RoomJobDB rJobDb;

    public static final int NEW_JOB_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        allJobs = new AllJobs();
//
//        //load();
//
//
//        List<Job> jobs = new ArrayList<Job>();
//        jobs = allJobs.getJobs();


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






        newJob = (Button) findViewById(R.id.buttonNewJob);
        newJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewJob.class);
                startActivity(intent);
            }
        });

        jobOverview= (Button) findViewById(R.id.buttonAllJobs);
        jobOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, JobsOverview.class);
                startActivity(intent);
            }
        });

//        jobNr = (TextView) findViewById(R.id.textViewJobNumber);
//        if (allJobs.getJobs().size() == 1){
//            jobOverview.setText("All Jobs - " + allJobs.getJobs().size() + " job");
//        } else {
//            jobOverview.setText("All Jobs - " + allJobs.getJobs().size() + " jobs");
//
//        }

        Intent intent = new Intent(MainActivity.this, RoomNewJobActivity.class);
        startActivityForResult(intent, NEW_JOB_ACTIVITY_REQUEST_CODE);


        RoomJob rJob = new RoomJob();
        rJob.setTitle("heyyyaaaa");
        roomJobViewModel.insert(rJob);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_JOB_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            RoomJob rJob = new RoomJob();
            roomJobViewModel.insert(rJob);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    "yoooo",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void load(){
        try {
            allJobs =  (AllJobs) InternalStorage.readObject(this, KEY);
        } catch (IOException e) {
            System.out.println(e + " e1");
        } catch (ClassNotFoundException e) {
            System.out.println(e + " e2");
        }


    }


    public void save(){

        try {
            // Save the list of entries to internal storage
            InternalStorage.writeObject(this, KEY, allJobs);
        } catch (IOException e) {
            System.out.println(e + " e2");
        }
    }


}
