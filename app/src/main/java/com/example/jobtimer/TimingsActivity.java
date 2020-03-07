package com.example.jobtimer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TimingsActivity extends AppCompatActivity {

    //int for id to identify job
    int idIn;




    //DateFormat for printing out the start dates
    SimpleDateFormat timeStartFormat;

    //Scrollview for displaying timings:
    ScrollView scrollViewTimings;


    //Strings for Timeconversion and total time
    String timeStringConverted;
    String totalTimeStringConverted;
    int secondsTotal = 0;
    TextView textViewTimeTotal;

    //TextView for Title of Job
    TextView title;

    //viewModel to get saved Jobs from roomdb
    private RoomJobViewModel roomJobViewModel;
    private TimingViewModel timingViewModel;
    //RoomJob to work with
    RoomJob rJob;
    //ArrayList of Timings from currentJob
    List<Timing> timingsCurrentJob;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timings_job);




        //get id passed on from Overview to identify job
        Intent intent = getIntent();
        idIn = intent.getIntExtra("id", 9);


        //define rJob as container for current job
        rJob = new RoomJob("new Job");
        //job view model to find job
        roomJobViewModel = new ViewModelProvider(this).get(RoomJobViewModel.class);
        //load jobs
        roomJobViewModel.getAllJobs().observe(this, new Observer<List<RoomJob>>() {
            @Override
            public void onChanged(@Nullable final List<RoomJob> rJobs) {

                //Search through jobs in db
                for (RoomJob rIJob : rJobs){

                    //if jobs id is id passed from former activity:
                    if (rIJob.getRid() == idIn){

                        //get current Job
                        rJob = rIJob;
                        System.out.println(rJob.getTitle() + " jobTitle");
                    }
                }
            }
        });

        //List extracted timings for later use
        timingsCurrentJob = new ArrayList<Timing>();
        //TimeViewModel
        timingViewModel = new ViewModelProvider(this).get(TimingViewModel.class);
        ////Set Observer
        timingViewModel.getAllTimings().observe(this, new Observer<List<Timing>>() {
            @Override
            public void onChanged(@Nullable final List<Timing> timings) {

                for(Timing timing : timings){
                    if(timing.getJobId() == idIn){

                        //get Timings from currentJob
                        timingsCurrentJob.add(timing);
                    }
                }
                paintTimings();


            }
        });


    }

    public void paintTimings(){
        //get Views
        textViewTimeTotal = (TextView) findViewById(R.id.textViewTimeTotal);

        //setJobTitle
        title = (TextView) findViewById(R.id.textViewNotesTitle);
        title.setText(rJob.getTitle());

        //dateFormat
        timeStartFormat = new SimpleDateFormat("dd.MM.YYYY - HH:mm");

        //print all timings
        scrollViewTimings = (ScrollView) findViewById(R.id.scrollViewTimings);

        //get Linear Layout Wrapper for all
        LinearLayout linearLayoutWrapperAll = new LinearLayout(this);
        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutWrapperAll.setOrientation(LinearLayout.VERTICAL);
        linearParams.setMargins(0, 0, 0, 0);
        linearLayoutWrapperAll.setLayoutParams(linearParams);
        ////Add to Scrollview
        scrollViewTimings.addView(linearLayoutWrapperAll);


        for (Timing timing : timingsCurrentJob) {
            //put formatted Date in a String
            String startString = timing.getDate();
            String endString = timing.getTimeLapsed();

            //get Seconds per timing and convert to timestring and add to total time (seconds)
            int seconds = timing.getSeconds();
            secondsTotal += timing.getSeconds();
            timeStringConverted = minutesHours(seconds);

            //create wrapper for each timing
            LinearLayout linearLayoutEachTiming = new LinearLayout(this);
            LinearLayout.LayoutParams linearparams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayoutEachTiming.setOrientation(LinearLayout.VERTICAL);
            linearparams3.setMargins(0, 110, 0, 0);

            linearLayoutEachTiming.setLayoutParams(linearparams3);

            //create Textviews for each timing
            ////Date
            TextView textViewTimingStartDate = new TextView(this);
            textViewTimingStartDate.setText("Start: " + startString);
            textViewTimingStartDate.setTextAppearance(this, R.style.fontForJobOverview);

            TextView textViewTimingEndDate = new TextView(this);
            textViewTimingEndDate.setText("End: " + endString);
            textViewTimingEndDate.setTextAppearance(this, R.style.fontForJobOverview);

            ////Time
            TextView textViewTimingTime = new TextView(this);
            textViewTimingTime.setText(timeStringConverted);
            textViewTimingTime.setTextAppearance(this, R.style.fontForJobOverview);

            //Add TextViews to timingwrapper
            linearLayoutEachTiming.addView(textViewTimingStartDate);
            linearLayoutEachTiming.addView(textViewTimingEndDate);
            linearLayoutEachTiming.addView(textViewTimingTime);
            linearLayoutWrapperAll.addView(linearLayoutEachTiming);
        }

        //output total time spent on job (TextView already in layout)
        totalTimeStringConverted = minutesHours(secondsTotal);
        textViewTimeTotal.setText("Total Job " + totalTimeStringConverted);
        System.out.println(totalTimeStringConverted);
    }


    public String minutesHours(int seconds) {
        //Create String to return
        String timeConverted;

        //set minutes and hours to 0, to count up
        int minutes = 0;
        int hours = 0;

        //counter = seconds so we can count how many minutes and subtract 60 seconds each minute,
        //until last started minute
        int counter = seconds;
        for (int i = counter; i > 0; i--) {
            if (i % 60 == 0) {
                minutes++;
                seconds -= 60;
            }
        }
        //counter = minutes so wen count how many hours and subtract 60 minutes each hour,
        //until last started hour
        counter = minutes;
        for (int i = counter; i > 0; i--) {
            if (i % 60 == 0) {
                hours++;
                minutes -= 60;
            }
        }

        //Convert to a readable String, depending on seconds/minutes/hours > 0 and < 10
        if (minutes == 0 && hours == 0) {
            if (seconds < 10) {
                timeConverted = ("Time: 0" + seconds + " seconds");

            } else {
                timeConverted = ("Time: " + seconds + " seconds");

            }
        } else if (hours == 0) {
            if (minutes < 10) {
                if (seconds < 10) {
                    timeConverted = ("Time: 0" + minutes + ":0" + seconds);

                } else {
                    timeConverted = ("Time: 0" + minutes + ":" + seconds);

                }
            } else {
                if (seconds < 10) {
                    timeConverted = ("Time: " + minutes + ":0" + seconds);


                } else {
                    timeConverted = ("Time: " + minutes + ":" + seconds);

                }
            }
        } else {
            if (hours < 10) {
                if (minutes < 10) {
                    if (seconds < 10) {
                        timeConverted = ("Time: 0" + hours + ":0" + minutes + ":0" + seconds);

                    } else {
                        timeConverted = ("Time: 0" + hours + ":0" + minutes + ":" + seconds);

                    }
                } else {
                    if (seconds < 10) {
                        timeConverted = ("Time: 0" + hours + ":" + minutes + ":0" + seconds);

                    } else {
                        timeConverted = ("Time: 0" + hours + ":" + minutes + ":" + seconds);

                    }

                }
            } else {
                if (minutes < 10) {
                    if (seconds < 10) {
                        timeConverted = ("Time: " + hours + ":0" + minutes + ":0" + seconds);

                    } else {
                        timeConverted = ("Time: " + hours + ":0" + minutes + ":" + seconds);

                    }
                } else {
                    if (seconds < 10) {
                        timeConverted = ("Time: " + hours + ":" + minutes + ":0" + seconds);

                    } else {
                        timeConverted = ("Time: " + hours + ":" + minutes + ":" + seconds);

                    }
                }
            }

        }
        //return the string, so we can output it
        return timeConverted;
    }



}
