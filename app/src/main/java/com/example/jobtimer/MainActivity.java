package com.example.jobtimer;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.provider.Contacts.SettingsColumns.KEY;

public class MainActivity extends AppCompatActivity {
    AllJobs allJobs;

    Button newJob;
    Button jobOverview;

    TextView jobNr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allJobs = new AllJobs();

        load();


        List<Job> jobs = new ArrayList<Job>();
        jobs = allJobs.getJobs();




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

        jobNr = (TextView) findViewById(R.id.textViewJobNumber);
        if (allJobs.getJobs().size() == 1){
            jobOverview.setText("All Jobs - " + allJobs.getJobs().size() + " job");
        } else {
            jobOverview.setText("All Jobs - " + allJobs.getJobs().size() + " jobs");

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
