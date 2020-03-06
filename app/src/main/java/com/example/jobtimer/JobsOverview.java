package com.example.jobtimer;

import android.content.Intent;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.provider.Contacts.SettingsColumns.KEY;

public class JobsOverview extends AppCompatActivity {

    AllJobs allJobs;

    ScrollView scrollViewJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_overview);


        final float scale = this.getResources().getDisplayMetrics().density;

        allJobs = new AllJobs();
        load();
        scrollViewJobs = (ScrollView) findViewById(R.id.scrollViewJobs);
        scrollViewJobs.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        LinearLayout linearLayout = new LinearLayout(this);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearParams.setMargins(0, 0, 0, 0);
        linearLayout.setLayoutParams(linearParams);

        scrollViewJobs.addView(linearLayout);

        List<Job> jobs = new ArrayList<Job>();
        jobs = allJobs.getJobs();

        for (final Job job : jobs){

            int textX = (int) (5 * scale + 0.5f);
            int textY = (int) (5 * scale + 0.5f);

            LinearLayout linearLayout2 = new LinearLayout(this);
            LinearLayout.LayoutParams linearparams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            linearLayout2.setOrientation(LinearLayout.VERTICAL);
            linearparams3.setMargins(0, 100, 0, 0);

            linearLayout2.setLayoutParams(linearparams3);

            ConstraintLayout.LayoutParams constParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            constParams.setMargins(25, 10, 25, 0);

            ConstraintLayout constColor = new ConstraintLayout(this);
            constColor.setLayoutParams(constParams);

            int constHeight = (int) (55 * scale + 0.5f);
            constColor.getLayoutParams().height = constHeight;
            constColor.setBackgroundColor(job.getColor());

            TextView textViewTitle = new TextView (this);
            textViewTitle.setText(job.getTitle().toUpperCase());
            textViewTitle.setTextAppearance(this, R.style.fontForJobOverviewTitle);
            textViewTitle.setTextColor(getResources().getColor(R.color.black));
            textViewTitle.setX(textX);
            textViewTitle.setY(textY);

            TextView timingsText = new TextView(this);
            timingsText.setText("Timings for this job: " + job.getTimings().size());
            timingsText.setTextAppearance(this, R.style.fontForJobOverview);
            timingsText.setTextColor(getResources().getColor(R.color.black));
            timingsText.setX(textX +(int) (8 * scale + 0.5f));
            timingsText.setY(textY +(int) (5 * scale + 0.5f));

            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            buttonParams.setMargins(25, 25, 0, 0);

            Button showTimings = new Button(this);
            showTimings.setText("Show Timings");
            showTimings.setTextAppearance(this, R.style.fontForJobOverviewButton);

            //showTimings.setBackgroundColor(getResources().getColor(R.color.white));
//            showTimings.setY(textY);
//            showTimings.setX(textX + (int) (20 * scale + 0.5f));
            showTimings.setWidth((int) (314 * scale + 0.5f));
            showTimings.setBackgroundColor(getResources().getColor(R.color.lightestgrey));
            showTimings.setLayoutParams(buttonParams);

            showTimings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timingsJob(job.getId());
                }
            });





            for (Timing timing : job.getTimings()){

            }

            ConstraintLayout.LayoutParams constBottomParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            constBottomParams.setMargins(25, 10, 25, 0);

            ConstraintLayout constBottomline = new ConstraintLayout(this);
            constBottomline.setLayoutParams(constBottomParams);

            ConstraintLayout constBottomline2 = new ConstraintLayout(this);
            constBottomline2.setLayoutParams(constBottomParams);

            int constBottomlineHeight = (int) (5 * scale + 0.5f);
            constBottomline.getLayoutParams().height = constBottomlineHeight;
            constBottomline.setBackgroundColor(job.getColor());

            constBottomline2.getLayoutParams().height = constBottomlineHeight;
            constBottomline2.setBackgroundColor(job.getColor());

            TextView clickHereToTimeText = new TextView(this);
            clickHereToTimeText.setText("Click here to start measuring time");
            if (job.getColor() == getResources().getColor(R.color.blue)){
                clickHereToTimeText.setTextColor(getResources().getColor(R.color.yellow));
            }
            if (job.getColor() == getResources().getColor(R.color.green)){
                clickHereToTimeText.setTextColor(getResources().getColor(R.color.red));
            }
            if (job.getColor() == getResources().getColor(R.color.red)){
                clickHereToTimeText.setTextColor(getResources().getColor(R.color.green));
            }
            if (job.getColor() == getResources().getColor(R.color.yellow)){
                clickHereToTimeText.setTextColor(getResources().getColor(R.color.blue));
            }


            clickHereToTimeText.setTextAppearance(this, R.style.fontForJobOverview);


            //clickHereToTimeText.setX(textX);
            clickHereToTimeText.setY(textY);


            constColor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    singleJob(job.getId());
                }
            });
            linearLayout2.addView(textViewTitle);

            linearLayout2.addView(constBottomline);

            linearLayout2.addView(timingsText);
            linearLayout2.addView(showTimings);

            linearLayout2.addView(constColor);
            constColor.addView(clickHereToTimeText);


            linearLayout2.addView(constBottomline2);
            linearLayout.addView(linearLayout2);
        }


    }

    public void timingsJob(int id){
        Intent intent = new Intent(JobsOverview.this, TimingsJob.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void singleJob(int id){
        Intent intent = new Intent(JobsOverview.this, SingleJob.class);
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
