package com.example.jobtimer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class EditTimingActivity extends AppCompatActivity {

    //identify Job and Timing
    int timingId;
    String jobTitle;
    int jobColor;
    Timing currentTiming;

    //TextView for Jobtitle
    TextView textViewJobTitle;

    //constLayout for seperator line
    ConstraintLayout constLayoutTimingSeperator;

    //viewModel to get saved Jobs from roomdb
    private TimingViewModel timingViewModel;

    //TextView StartDate
    TextView textViewStartDate;

    //TextView EndDate
    TextView textViewEndDate;

    //Buttons for start/endDate
    Button buttonStartDateNow;
    Button buttonStartDatePick;
    Button buttonEndDateNow;
    Button buttonEndDatePick;

    //STring for picking dateTime
    String dateTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_timing);

        //identify Job and Timing
        Intent intent = getIntent();
        jobTitle = intent.getStringExtra("jobTitle");
        jobColor = intent.getIntExtra("jobColor", 0);
        timingId = intent.getIntExtra("timingId", 9);


        //textview for JobTitle
        textViewJobTitle = findViewById(R.id.textViewTimingEditTitle);
        textViewJobTitle.setText(jobTitle);

        //constLayout for seperator line
        constLayoutTimingSeperator = findViewById(R.id.constLayoutEditTimingSeperator);
        constLayoutTimingSeperator.setBackgroundColor(jobColor);

        //TextView Startdate
        textViewStartDate = findViewById(R.id.textViewTimingEditStartDate);

        //TextView Enddate
        textViewEndDate = findViewById(R.id.textViewTimingEditEndDate);

        //Buttons for start/endDate
         buttonStartDateNow = findViewById(R.id.buttonSetStartDateNow);
         buttonStartDatePick = findViewById(R.id.buttonSetStartDatePick);
         buttonEndDateNow = findViewById(R.id.buttonSetEndDateNow);
         buttonEndDatePick = findViewById(R.id.buttonSetEndDatePick);
         /////set click listeners
        setClickListeners();





        System.out.println(jobTitle + " jobId " + timingId + " timingId");

        //TimeViewModel
        timingViewModel = new ViewModelProvider(this).get(TimingViewModel.class);
        ////Set Observer
        timingViewModel.getAllTimings().observe(this, new Observer<List<Timing>>() {
            @Override
            public void onChanged(@Nullable final List<Timing> timings) {

                for(Timing timing : timings){
                    if(timing.getId() == timingId){
                        //get timecode for when timing started
                        final long start = timing.getStart();

                        //get timecode for when timing ended
                        final long end = timing.getEnd();

                        //get identified timing
                        currentTiming = timing;

                        //set Text for StartDate
                        SimpleDateFormat startEndDate = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");
                        String startString = startEndDate.format(new Timestamp(start));
                        String endString = startEndDate.format(new Timestamp(end));

                        if(startString.equals("01.01.1970 00:00:00")){
                            textViewStartDate.setText("Start: unset");
                        } else {
                            textViewStartDate.setText("Start: " + startString);
                        }

                        if(endString.equals("01.01.1970 00:00:00")){
                            textViewEndDate.setText("End: unset");
                        } else {
                            textViewEndDate.setText("End: " + endString);
                        }
                    }
                }
            }
        });
    }

    private void datePicker(String startEnd){

        dateTime = "";

        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String zeroMonth = "";
                        if (monthOfYear < 9){
                            zeroMonth = "0";
                        }
                        String zeroDay = "";
                        if (dayOfMonth < 10){
                            zeroDay = "0";
                        }
                        dateTime += year + "-" + zeroMonth + (monthOfYear + 1) + "-" + zeroDay + dayOfMonth;
                        System.out.println(monthOfYear + " month" + dateTime);
                        timePicker(startEnd);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void timePicker(String startEnd){
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dateTime += " " + hourOfDay + ":" + minute + ":00";
                        Timestamp ts = Timestamp.valueOf(dateTime);
                        SimpleDateFormat startEndDate = new SimpleDateFormat("dd.MM.YYYY HH:mm:ss");
                        String dateTimeString = startEndDate.format(ts);
                        final long millis = ts.getTime();
                        if(startEnd.equals("Start: ")){
                                currentTiming.setStart(millis);
                                textViewStartDate.setText("Start: " + dateTimeString);
                        } else {
                                currentTiming.setEnd(millis);
                                textViewEndDate.setText("End: " + dateTimeString);
                        }
                        timingViewModel.update(currentTiming);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }


    public void setClickListeners(){
        buttonStartDateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDateNow();
            }
        });

        buttonStartDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDatePick();

            }
        });

        buttonEndDateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDateNow();

            }
        });

        buttonEndDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endDatePick();

            }
        });
    }

    public void startDateNow(){
        currentTiming.setStart(System.currentTimeMillis());
        timingViewModel.update(currentTiming);
    }

    public void startDatePick(){
        datePicker("Start: ");
    }

    public void endDateNow(){
        currentTiming.setEnd(System.currentTimeMillis());
        timingViewModel.update(currentTiming);
    }

    public void endDatePick(){
        datePicker("End: ");
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditTimingActivity.this, OverviewJobsActivity.class);
        startActivity(intent);
    }
}
