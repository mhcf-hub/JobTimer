package com.example.jobtimer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class TimingsActivity extends AppCompatActivity {

    //int for id to identify job
    int idIn;

    //Seperator line after TitleText
    ConstraintLayout constLayoutTitleSeperator;
    ConstraintLayout constLayoutTitleSeperatorLow;

    //Button to add timing
    Button addTimingButton;

    //DateFormat for printing out the start dates
    SimpleDateFormat timeStartFormat;



    //get Linear Layout Wrapper for all
    LinearLayout linearLayoutWrapperAll;


    //Strings for Timeconversion and total time
    String timeStringConverted;
    String totalTimeStringConverted;
    long secondsTotal = 0;
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

        //get Linear Layout Wrapper for all
        linearLayoutWrapperAll = findViewById(R.id.linearLayoutTimingWrapper);


        //Button to add timing
        addTimingButton = findViewById(R.id.buttonAddTiming);
        addTimingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTiming();
            }
        });

        //Seperator line after Title text:
        constLayoutTitleSeperator = findViewById(R.id.constLayoutEditTimingSeperator);
        constLayoutTitleSeperatorLow = findViewById(R.id.constLayoutEditTimingSeperatorLow);

        //get id passed on from Overview to identify job
        Intent intent = getIntent();
        idIn = intent.getIntExtra("id", 9);

        ViewModelStoreOwner vMSO = this;
        LifecycleOwner lCO = this;


        //define rJob as container for current job
        rJob = new RoomJob("new Job");
        //job view model to find job
        roomJobViewModel = new ViewModelProvider(vMSO).get(RoomJobViewModel.class);
        //load jobs
        roomJobViewModel.getAllJobs().observe(lCO, new Observer<List<RoomJob>>() {
            @Override
            public void onChanged(@Nullable final List<RoomJob> rJobs) {

                //Search through jobs in db
                for (RoomJob rIJob : rJobs){

                    //if jobs id is id passed from former activity:
                    if (rIJob.getRid() == idIn){

                        //get current Job
                        rJob = rIJob;
                        System.out.println(rJob.getColor() + " color job");
                        //setColor for Seperator
                        constLayoutTitleSeperator.setBackgroundColor(rJob.getColor());
                        constLayoutTitleSeperatorLow.setBackgroundColor(rJob.getColor());
                        //List extracted timings for later use
                        timingsCurrentJob = new ArrayList<Timing>();
                        //TimeViewModel
                        timingViewModel = new ViewModelProvider(vMSO).get(TimingViewModel.class);
                        ////Set Observer
                        timingViewModel.getAllTimings().observe(lCO, new Observer<List<Timing>>() {
                            @Override
                            public void onChanged(@Nullable final List<Timing> timings) {

                                for(Timing timing : timings){
                                    if(timing.getJobId() == idIn){

                                        //get Timings from currentJob
                                        timingsCurrentJob.add(timing);
                                        System.out.println("heya");
                                    }
                                }
                                paintTimings();
                            }
                        });
                    }
                }
            }
        });






    }


    public void addTiming(){
        //removeAllTimings before repainting
        timingsCurrentJob.clear();
        linearLayoutWrapperAll.removeAllViews();

        //Create new Timing ended of 1 (important so start timer function will pick right choice)
        Timing currentTiming = new Timing(idIn, 2);

        //insert New Timming
        timingViewModel.insertTiming(currentTiming);

        //reset secondstotal, otherwise they will be counted 2 times
        secondsTotal = 0;
    }

    public void paintTimings(){
        //float scale to handle dps
        final float scale = this.getResources().getDisplayMetrics().density;

        //get Views
        textViewTimeTotal = (TextView) findViewById(R.id.textViewTimeTotal);

        //setJobTitle
        title = (TextView) findViewById(R.id.textViewNotesTitle);
        title.setText(rJob.getTitle());

        //dateFormat
        timeStartFormat = new SimpleDateFormat("dd.MM.YYYY - HH:mm");

        //get Linear Layout Wrapper for all
        LinearLayout linearLayoutWrapperAll;
        linearLayoutWrapperAll = findViewById(R.id.linearLayoutTimingWrapper);
        linearLayoutWrapperAll.removeAllViews();


        for (int i = timingsCurrentJob.size() - 1; i >= 0; i--){
            System.out.println(i + " color");
            //get timecode for when timing started
            final long start = timingsCurrentJob.get(i).getStart();

            //get timecode for when timing ended
            final long end = timingsCurrentJob.get(i).getEnd();

            //calculate difference between ending and starting point
            Timestamp sqlTimestampDifference = new Timestamp(end - start);
            int hoursForTiming = (int)((((sqlTimestampDifference.getTime() / 1000) / 60) / 60));
            secondsTotal += end - start;

            //time difference formatted as hours, minutes, seconds
            SimpleDateFormat sDF = new SimpleDateFormat("mm:ss");
            sDF.setTimeZone(TimeZone.getTimeZone("GMT"));
            String formattedDateDifference = sDF.format(sqlTimestampDifference);

            //create wrapper for each timing
            LinearLayout linearLayoutEachTiming = new LinearLayout(this);
            LinearLayout.LayoutParams linearparams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            linearLayoutEachTiming.setOrientation(LinearLayout.VERTICAL);
            linearparams3.setMargins(0, 110, 0, 0);

            linearLayoutEachTiming.setLayoutParams(linearparams3);

            //create Textviews for each timing
            ///Header
            TextView textViewTimingHeader = new TextView(this);
            SimpleDateFormat headerFormat = new SimpleDateFormat("dd.MM.YYYY");
            String headerString = headerFormat.format(new Timestamp(start));
            if(headerString.equals("01.01.1970")){
                textViewTimingHeader.setText("unset");
            } else {
                textViewTimingHeader.setText(headerString);
            }
            textViewTimingHeader.setTextAppearance(this, R.style.fontForTimingHeader);

            ////Date
            SimpleDateFormat startEndDate = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");
            TextView textViewTimingStartDate = new TextView(this);
            String startString = startEndDate.format(new Timestamp(start));
            if(startString.equals("01.01.1970 00:00:00")){
                textViewTimingStartDate.setText("Start: unset");
            } else {
                textViewTimingStartDate.setText("Start: " + startString);
            }
            textViewTimingStartDate.setTextAppearance(this, R.style.fontForJobOverview);

            String endString = startEndDate.format(new Timestamp(end));
            TextView textViewTimingEndDate = new TextView(this);
            if(endString.equals("01.01.1970 00:00:00")){
                textViewTimingEndDate.setText("End: unset");
            } else {
                textViewTimingEndDate.setText("End: " + endString);
            }

            textViewTimingEndDate.setTextAppearance(this, R.style.fontForJobOverview);

            ////Time
            TextView textViewTimingTime = new TextView(this);
            textViewTimingTime.setText("Time passed: " + hoursForTiming + ":" + formattedDateDifference);
            textViewTimingTime.setTextAppearance(this, R.style.fontForJobOverview);

            //Add TextViews to timingwrapper
            linearLayoutEachTiming.addView(textViewTimingHeader);
            linearLayoutEachTiming.addView(textViewTimingStartDate);
            linearLayoutEachTiming.addView(textViewTimingEndDate);
            linearLayoutEachTiming.addView(textViewTimingTime);

            //create Button for Edit
            //Set Params
            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            buttonParams.setMargins(25, 25, 0, 0);

            //create Button
            Button editTimingButton = new Button(this);

            //Set TextString of Editbutton
            editTimingButton.setText(getResources().getString(R.string.editTiming));

            //Set styles.xml of Editbutton
            editTimingButton.setTextAppearance(this, R.style.fontForJobOverviewButton);

            //Set Width of Editbutton
            editTimingButton.setWidth((int) (314 * scale + 0.5f));

            //Set Backgroundcolor of Editbutton
            editTimingButton.setBackgroundColor(getResources().getColor(R.color.lightestgrey));

            //Assign params to Editbutton
            editTimingButton.setLayoutParams(buttonParams);

            //Editbutton clicklistener
            final int identifier = i;
            editTimingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editTiming(timingsCurrentJob.get(identifier).getId());
                }
            });

            //add editbutton to timingwrapper
            linearLayoutEachTiming.addView(editTimingButton);

            //Create Params for line seperator 1
            ConstraintLayout.LayoutParams constLineParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            constLineParams.setMargins(25, 10, 50, 0);

            //Create Line Seperator 1
            ConstraintLayout constline = new ConstraintLayout(this);
            constline.setLayoutParams(constLineParams);

            //Set Seperatorcolors and height
            int constBottomlineHeight = (int) (1 * scale + 0.5f);
            constline.getLayoutParams().height = constBottomlineHeight;
            constline.setBackgroundColor(rJob.getColor());

            //add Line Seperator
            linearLayoutEachTiming.addView(constline);



            //add Timingwrapper to alltimingswrapper
            linearLayoutEachTiming.setBackgroundColor(getResources().getColor(R.color.bgtiming));
            linearLayoutWrapperAll.addView(linearLayoutEachTiming);

        }

        //output total time spent on job (TextView already in layout)
        //calculate difference between ending and starting point
        Timestamp sqlTimestampTotalTime = new Timestamp(secondsTotal);
        int hoursForJob = (int)((((sqlTimestampTotalTime.getTime() / 1000) / 60) / 60));

        //time difference formatted as hours, minutes, seconds
        SimpleDateFormat sDF = new SimpleDateFormat("mm:ss");
        sDF.setTimeZone(TimeZone.getTimeZone("GMT"));
        String formattedDateTotalTime = sDF.format(sqlTimestampTotalTime);
        textViewTimeTotal.setText(getResources().getString(R.string.total_job_time) + " " + hoursForJob + ":" + formattedDateTotalTime);
    }

    public void editTiming(int timingId){
        Intent intent = new Intent(TimingsActivity.this, EditTimingActivity.class);
        intent.putExtra("jobTitle", rJob.getTitle());
        intent.putExtra("jobColor", rJob.getColor());
        intent.putExtra("timingId", timingId);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
            Intent intent = new Intent(TimingsActivity.this, OverviewJobsActivity.class);
            startActivity(intent);
    }

}
