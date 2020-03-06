package com.example.jobtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static android.provider.Contacts.SettingsColumns.KEY;

public class TimingsJob extends AppCompatActivity {

//    //int for id to identify job
//    int idIn;
//
//    //AllJobs Object to find current job
//    AllJobs allJobs;
//
//    //Job Object for extracting searched Job
//    Job currentJob;
//
//    //ArrayList of Timings from currentJob
//    List<Timing> timingsCurrentJob;
//
//    //DateFormat for printing out the start dates
//    SimpleDateFormat timeStart;
//
//    //Scrollview for displaying timings:
//    ScrollView scrollViewTimings;
//
//
//    //Strings for Timeconversion and total time
//    String timeStringConverted;
//    String totalTimeStringConverted;
//    int secondsTotal = 0;
//    TextView textViewTimeTotal;
//
//    //TextView for Title of Job
//    TextView title;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_timings_job);
//
//
//
//        //get id passed on from Overview to identify job
//        Intent intent = getIntent();
//        idIn = intent.getIntExtra("id", 9);
//
//        //get Views
//        textViewTimeTotal = (TextView) findViewById(R.id.textViewTimeTotal);
//
//        //get allJobs from Savegame
//        allJobs = new AllJobs();
//        load();
//
//
//
//        //get Current job by idIn
//        currentJob = allJobs.getJobs().get(idIn);
//
//        //setJobTitle
//        title = (TextView) findViewById(R.id.textViewTitleTimings);
//        title.setText(currentJob.getTitle());
//
//        //get Timings from currentJob
//        timingsCurrentJob = currentJob.getTimings();
//
//        //dateFormat
//        timeStart = new SimpleDateFormat("dd.MM.YYYY - HH:mm");
//
//        //print all timings
//        scrollViewTimings = (ScrollView) findViewById(R.id.scrollViewTimings);
//
//        //get Linear Layout Wrapper for all
//        LinearLayout linearLayout = new LinearLayout(this);
//        LinearLayout.LayoutParams linearParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        linearLayout.setOrientation(LinearLayout.VERTICAL);
//        linearParams.setMargins(0, 0, 0, 0);
//        linearLayout.setLayoutParams(linearParams);
//        ////Add to Scrollview
//        scrollViewTimings.addView(linearLayout);
//
//
//        for (Timing timing : timingsCurrentJob){
//            //put formatted Date in a String
//            String nowString = timeStart.format(timing.getDateStart());
//
//            //get Seconds per timing and convert to timestring and add to total time (seconds)
//            int seconds = timing.getSeconds();
//            secondsTotal += timing.getSeconds();
//            timeStringConverted = minutesHours(seconds);
//
//            //create wrapper for each timing
//            LinearLayout linearLayout2 = new LinearLayout(this);
//            LinearLayout.LayoutParams linearparams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            linearLayout2.setOrientation(LinearLayout.VERTICAL);
//            linearparams3.setMargins(0, 110, 0, 0);
//
//            linearLayout2.setLayoutParams(linearparams3);
//
//            //create Textviews for each timing
//            ////Date
//            TextView textViewTimingStartDate = new TextView (this);
//            textViewTimingStartDate.setText("Date: " + nowString);
//            textViewTimingStartDate.setTextAppearance(this, R.style.fontForJobOverview);
//
//            ////Time
//            TextView textViewTimingTime = new TextView (this);
//            textViewTimingTime.setText(timeStringConverted);
//            textViewTimingTime.setTextAppearance(this, R.style.fontForJobOverview);
//
//            //Add TextViews to timingwrapper
//            linearLayout2.addView(textViewTimingStartDate);
//            linearLayout2.addView(textViewTimingTime);
//            linearLayout.addView(linearLayout2);
//        }
//
//        //output total time spent on job (TextView already in layout)
//        totalTimeStringConverted = minutesHours(secondsTotal);
//        textViewTimeTotal.setText("Total Job " + totalTimeStringConverted);
//        System.out.println(totalTimeStringConverted);
//    }
//
//
//
//    public String minutesHours(int seconds){
//        //Create String to return
//        String timeConverted;
//
//        //set minutes and hours to 0, to count up
//        int minutes = 0;
//        int hours = 0;
//
//        //counter = seconds so we can count how many minutes and subtract 60 seconds each minute,
//        //until last started minute
//        int counter = seconds;
//        for(int i = counter; i > 0; i--){
//            if (i % 60 == 0) {
//                minutes++;
//                seconds-=60;
//            }
//        }
//        //counter = minutes so wen count how many hours and subtract 60 minutes each hour,
//        //until last started hour
//        counter = minutes;
//        for(int i = counter; i > 0; i--){
//            if(i % 60 == 0){
//                hours++;
//                minutes-=60;
//            }
//        }
//
//        //Convert to a readable String, depending on seconds/minutes/hours > 0 and < 10
//        if(minutes == 0 && hours == 0){
//            if(seconds < 10){
//                timeConverted = ("Time: 0" + seconds + " seconds");
//
//            } else {
//                timeConverted = ("Time: " + seconds + " seconds");
//
//            }
//        } else if(hours == 0){
//            if (minutes < 10){
//                if(seconds < 10){
//                    timeConverted = ("Time: 0" + minutes + ":0" + seconds);
//
//                } else {
//                    timeConverted = ("Time: 0" + minutes + ":" + seconds);
//
//                }
//            } else {
//                if(seconds < 10){
//                    timeConverted = ("Time: " + minutes + ":0" + seconds);
//
//
//                } else {
//                    timeConverted = ("Time: " + minutes + ":" + seconds);
//
//                }
//            }
//        } else {
//            if(hours < 10){
//                if (minutes < 10){
//                    if(seconds < 10){
//                        timeConverted = ("Time: 0" + hours + ":0" + minutes + ":0" + seconds);
//
//                    } else {
//                        timeConverted = ("Time: 0" + hours + ":0" + minutes + ":" + seconds);
//
//                    }
//                } else {
//                    if(seconds < 10){
//                        timeConverted = ("Time: 0" + hours + ":" + minutes + ":0" + seconds);
//
//                    } else {
//                        timeConverted = ("Time: 0" + hours + ":" + minutes + ":" + seconds);
//
//                    }
//
//                }
//            } else {
//                if (minutes < 10){
//                    if(seconds < 10){
//                        timeConverted = ("Time: " + hours + ":0" + minutes + ":0" + seconds);
//
//                    } else {
//                        timeConverted = ("Time: " + hours + ":0" + minutes + ":" + seconds);
//
//                    }
//                } else {
//                    if(seconds < 10){
//                        timeConverted = ("Time: " + hours + ":" + minutes + ":0" + seconds);
//
//                    } else {
//                        timeConverted = ("Time: " + hours + ":" + minutes + ":" + seconds);
//
//                    }
//                }
//            }
//
//        }
//        //return the string, so we can output it
//        return timeConverted;
//    }
//
//
//    public void load(){
//        try {
//            allJobs =  (AllJobs) InternalStorage.readObject(this, KEY);
//        } catch (IOException e) {
//            System.out.println(e + " e1");
//        } catch (ClassNotFoundException e) {
//            System.out.println(e + " e2");
//        }
//
//
//    }
//
//
//    public void save(){
//
//        try {
//            // Save the list of entries to internal storage
//            InternalStorage.writeObject(this, KEY, allJobs);
//        } catch (IOException e) {
//            System.out.println(e + " e2");
//        }
//    }
}
