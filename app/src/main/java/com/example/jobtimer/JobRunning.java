package com.example.jobtimer;

import android.content.Intent;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.provider.Contacts.SettingsColumns.KEY;

public class JobRunning extends AppCompatActivity {

    AllJobs allJobs;

    LinearLayout runningLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_running);

        runningLayout = (LinearLayout) findViewById(R.id.linearLayoutRunning);

        allJobs = new AllJobs();
        load();
        List<Job> jobs = new ArrayList<Job>();
        jobs = allJobs.getJobs();

        final float scale = this.getResources().getDisplayMetrics().density;


        for (final Job job : jobs){
                LinearLayout linearLayout2 = new LinearLayout(this);
                LinearLayout.LayoutParams linearparams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                linearLayout2.setOrientation(LinearLayout.VERTICAL);
                linearparams3.setMargins(0, 10, 0, 60);

                linearLayout2.setLayoutParams(linearparams3);

                ConstraintLayout.LayoutParams constParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                constParams.setMargins(0, 0, 0, 0);

                ConstraintLayout constColor = new ConstraintLayout(this);
                constColor.setLayoutParams(constParams);

                int constHeight = (int) (55 * scale + 0.5f);
                constColor.getLayoutParams().height = constHeight;

                constColor.setBackgroundColor(job.getColor());


                TextView textView = new TextView (this);
                textView.setText("Title: " + job.getTitle().toUpperCase());

                linearLayout2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        singleJob(job.getId());
                    }
                });

                linearLayout2.addView(constColor);
                linearLayout2.addView(textView);
                runningLayout.addView(linearLayout2);

        }
    }

    public void singleJob(int id){
        Intent intent = new Intent(JobRunning.this, SingleJob.class);
        intent.putExtra("id", id);
        startActivity(intent);
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
