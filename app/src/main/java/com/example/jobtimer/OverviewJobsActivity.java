package com.example.jobtimer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class OverviewJobsActivity extends AppCompatActivity {

    //Scrollview for displaying all jobs
    ScrollView scrollViewJobs;

    //viewModel to get saved Jobs from roomdb
    private RoomJobViewModel roomJobViewModel;
    private TimingViewModel timingViewModel;


    //Create Linear Layout to wrap all jobs inside scroll view
    LinearLayout linearLayoutWrappAll;

    //List of timing to identify by jobId
    List<Timing> timingsFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_jobs);


        //get scrollview for displaying jobs and set backgroundcolor
        scrollViewJobs = (ScrollView) findViewById(R.id.scrollViewJobs);
        scrollViewJobs.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        //Create Linear Layout to wrap all jobs inside scroll view
        linearLayoutWrappAll = new LinearLayout(this);
        ////LayoutParams
        LinearLayout.LayoutParams linearParamsWrapAll = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearParamsWrapAll.setMargins(0, 0, 0, 0);

        //Set orentiation and params for wrapall linearlayout
        linearLayoutWrappAll.setOrientation(LinearLayout.VERTICAL);
        linearLayoutWrappAll.setLayoutParams(linearParamsWrapAll);

        //Add wraper linear layout to scrollview
        scrollViewJobs.addView(linearLayoutWrappAll);


        timingsFound = new ArrayList<Timing>();

        timingViewModel = new ViewModelProvider(this).get(TimingViewModel.class);
        timingViewModel.getAllTimings().observe(this, new Observer<List<Timing>>() {
            @Override
            public void onChanged(@Nullable final List<Timing> timings) {
                // Update the cached copy of the words in the adapter.
                System.out.println(timings + " job tttt");
                for (Timing timing : timings) {
                    timingsFound.add(timing);
                }
                getRJobs();
            }
        });


    }

    public void getRJobs() {
        //Viewmodel for saved jobs
        roomJobViewModel = new ViewModelProvider(this).get(RoomJobViewModel.class);
        roomJobViewModel.getAllJobs().observe(this, new Observer<List<RoomJob>>() {
            @Override
            public void onChanged(@Nullable final List<RoomJob> rJobs) {
                // Update the cached copy of the words in the adapter.
                System.out.println(" job t");
                for (RoomJob rJob : rJobs) {
                    System.out.println(rJob.getRid() + " job rididid");
                    //  System.out.println(rJob.getTimings() + " job t");
                    paintJobs(rJob);
                }
            }
        });
    }

    public void paintJobs(RoomJob rJob) {
        //float scale to handle dps
        final float scale = this.getResources().getDisplayMetrics().density;
        //set X and Y Value for placing Textviews
        int textX = (int) (5 * scale + 0.5f);
        int textY = (int) (5 * scale + 0.5f);


        //set Linear Layout as wrapper per job
        LinearLayout linearLayoutSingleJobWrapper = new LinearLayout(this);
        linearLayoutSingleJobWrapper.setOrientation(LinearLayout.VERTICAL);

        ////Set Params for single job
        LinearLayout.LayoutParams linearparamsSingleJob = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearparamsSingleJob.setMargins(0, 100, 0, 0);

        ////Assign params
        linearLayoutSingleJobWrapper.setLayoutParams(linearparamsSingleJob);


        //createTextView for JobTitle
        TextView textViewTitle = new TextView(this);

        ////setText for Jobtitle
        textViewTitle.setText(rJob.getTitle().toUpperCase());

        ////get Appearence from styles.xml
        textViewTitle.setTextAppearance(this, R.style.fontForJobOverviewTitle);

        ////setTextColor
        textViewTitle.setTextColor(getResources().getColor(R.color.black));

        ////SetX and Y for TitleText
        textViewTitle.setX(textX);
        textViewTitle.setY(textY);


        //Create Params for line seperators
        ConstraintLayout.LayoutParams constLineParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        constLineParams.setMargins(25, 10, 25, 0);

        //Create Line Seperators
        ConstraintLayout constline = new ConstraintLayout(this);
        constline.setLayoutParams(constLineParams);

        ConstraintLayout constline2 = new ConstraintLayout(this);
        constline2.setLayoutParams(constLineParams);

        //Set Seperatorcolors and height
        int constBottomlineHeight = (int) (5 * scale + 0.5f);
        constline.getLayoutParams().height = constBottomlineHeight;
        constline.setBackgroundColor(rJob.getColor());

        constline2.getLayoutParams().height = constBottomlineHeight;
        constline2.setBackgroundColor(rJob.getColor());


        //Create Text for output number Timings
        //Create TextView
        TextView timingsText = new TextView(this);


        int timingsCounter = 0;
        System.out.println(timingsFound + " job ts");
        for (Timing timing : timingsFound) {
            System.out.println(timing.getJobId() + " job id " + rJob.getRid());
            if (timing.getJobId() == rJob.getRid()) {
                timingsCounter++;
            }
        }

        //Set TextString output number Timings
        timingsText.setText("Timings for this job: " + timingsCounter);

        //Set styles.xml output number Timings
        timingsText.setTextAppearance(this, R.style.fontForJobOverview);

        //Set TextColor output number Timings
        timingsText.setTextColor(getResources().getColor(R.color.black));

        //Set Position output number Timings
        timingsText.setX(textX + (int) (8 * scale + 0.5f));
        timingsText.setY(textY + (int) (5 * scale + 0.5f));


        //Create Button to show single job timings
        //Set Params
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        buttonParams.setMargins(25, 25, 0, 0);

        ////Create of timingsbutton
        Button showTimings = new Button(this);

        //Set TextString of timingsbutton
        showTimings.setText("Show Timings");

        //Set styles.xml of timingsbutton
        showTimings.setTextAppearance(this, R.style.fontForJobOverviewButton);

        //Set Width of timingsbutton
        showTimings.setWidth((int) (314 * scale + 0.5f));

        //Set Backgroundcolor of timingsbutton
        showTimings.setBackgroundColor(getResources().getColor(R.color.lightestgrey));

        //Assign params to timingsbutton
        showTimings.setLayoutParams(buttonParams);

        //timingsbutton clicklistener
        showTimings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timingsJob(rJob.getRid());
            }
        });

        ////Create of notesbutton
        Button showNotes = new Button(this);

        //Set TextString of notesbutton
        showNotes.setText("Show Notes");

        //Set styles.xml of notesbutton
        showNotes.setTextAppearance(this, R.style.fontForJobOverviewButton);

        //Set Width of notesbutton
        showNotes.setWidth((int) (314 * scale + 0.5f));

        //Set Backgroundcolor of notesbutton
        showNotes.setBackgroundColor(getResources().getColor(R.color.lightestgrey));

        //Assign params to notesbutton
        showNotes.setLayoutParams(buttonParams);

        //notesbutton clicklistener
        showNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotes(rJob.getRid());
            }
        });


        //Create Layout for job main color and button for start timing
        //Set Params
        ConstraintLayout.LayoutParams constParams = new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        constParams.setMargins(25, 10, 25, 0);

        //Create Layout and Assing Params for Start timing/job color view
        ConstraintLayout constColor = new ConstraintLayout(this);
        constColor.setLayoutParams(constParams);

        //Set height and color for start/job color view
        int constHeight = (int) (55 * scale + 0.5f);
        constColor.getLayoutParams().height = constHeight;
        constColor.setBackgroundColor(rJob.getColor());

        constColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleJob(rJob.getRid());
            }
        });


        //TextView for Call to Action/Start Timing
        TextView clickHereToTimeText = new TextView(this);

        //set TextString Call to Action/Start Timing
        clickHereToTimeText.setText("Click here to start measuring time");

        //set Color according to job color for Call to Action/Start Timing
        if (rJob.getColor() == getResources().getColor(R.color.blue)) {
            clickHereToTimeText.setTextColor(getResources().getColor(R.color.lightyellow));
        }
        if (rJob.getColor() == getResources().getColor(R.color.green)) {
            clickHereToTimeText.setTextColor(getResources().getColor(R.color.lightyellow));
        }
        if (rJob.getColor() == getResources().getColor(R.color.red)) {
            clickHereToTimeText.setTextColor(getResources().getColor(R.color.lightgreen));
        }
        if (rJob.getColor() == getResources().getColor(R.color.yellow)) {
            clickHereToTimeText.setTextColor(getResources().getColor(R.color.blue));
        }
        if (rJob.getColor() == getResources().getColor(R.color.lightblue)) {
            clickHereToTimeText.setTextColor(getResources().getColor(R.color.lightyellow));
        }
        if (rJob.getColor() == getResources().getColor(R.color.lightyellow)) {
            clickHereToTimeText.setTextColor(getResources().getColor(R.color.blue));
        }
        if (rJob.getColor() == getResources().getColor(R.color.lightred)) {
            clickHereToTimeText.setTextColor(getResources().getColor(R.color.lightgreen));
        }
        if (rJob.getColor() == getResources().getColor(R.color.lightgreen)) {
            clickHereToTimeText.setTextColor(getResources().getColor(R.color.red));
        }

        //set styles.xml for Call to Action/Start Timing
        clickHereToTimeText.setTextAppearance(this, R.style.fontForJobOverview);

        //set Text Postion for Call to Action/Start Timing
        clickHereToTimeText.setY(textY);


        //Add TextViewTitle to single job wrapper
        linearLayoutSingleJobWrapper.addView(textViewTitle);

        //Add first Line Seperator
        linearLayoutSingleJobWrapper.addView(constline);

        //Add output number of timings
        linearLayoutSingleJobWrapper.addView(timingsText);

        //Add TimingsButton
        linearLayoutSingleJobWrapper.addView(showTimings);

        //Add notesbutton
        linearLayoutSingleJobWrapper.addView(showNotes);

        //Add JobColorView/Start timing
        linearLayoutSingleJobWrapper.addView(constColor);
        ////Add Text Call to Action
        constColor.addView(clickHereToTimeText);


        //Add singleJobWrapper to allJobsWrapper
        linearLayoutWrappAll.addView(linearLayoutSingleJobWrapper);
    }

    //function to open Notes for this job
    public void openNotes(int id){
        Intent intent = new Intent(OverviewJobsActivity.this, NotesActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    //function to see overview of timings for job
    public void timingsJob(int id) {
        Intent intent = new Intent(OverviewJobsActivity.this, TimingsActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    //function to start new timing for job
    public void singleJob(int id) {
        Intent intent = new Intent(OverviewJobsActivity.this, SingleJobUseActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }


    //On Back pressed always go to main activity
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OverviewJobsActivity.this, MainActivity.class);
        startActivity(intent);

    }
}
