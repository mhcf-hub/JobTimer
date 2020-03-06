package com.example.jobtimer;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.provider.Contacts.SettingsColumns.KEY;

public class SingleJob extends AppCompatActivity {

    //Main Constraint Layout containing main functions of single job
    ConstraintLayout constMain;

    //Class "AllJobs" contains a List of all jobs the user creates.
    //As of now for Accessing it has to be loaded in from the Internal Storage on each Activity
    AllJobs allJobs;

    //TextView and Edit for the Single Job, written into the Internal Storage on "NewJob" Activity
    TextView title;
    EditText editTitle;

    //Views for the Colorpicker. Written into Internal Storage on "NewJob" Activity
    View colorChcosen;

    View viewColor0;
    View viewColor1;
    View viewColor2;
    View viewColor3;

    //Button for saving edits, like Color of the Job
    Button singleJobSave;

    //Buttons to start and stop the timer on a single job
    Button singleJobStart;
    Button singleJobStop;

    //On Returning to the singlejobactivity (from homescreen), decide weather to save and end,
    // pause, or continue the timer
    //All hidden on start:
    ////Buttons
    Button continueButton;
    Button continueEndButton;
    Button continuePauseButton;
    ////Layout
    ConstraintLayout constTimingActual;
    ////TextView
    TextView textTimingActual;

    //DateFormat, Calendar and Date to store the Date on which the timer is used (pracitcal
    // for writing bills)
    SimpleDateFormat timeStart;
    Calendar cal;
    Date now;

    //TextViews to show the Date on which a timer is running and the actual time elapsed
    TextView startDate;
    TextView timeLapsed;


    //integer to identify the job the user wants to use
    int idIn;


    //Timer to use for ticking seconds
    private Timer myTimer;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_job);

        constMain = (ConstraintLayout) findViewById(R.id.constLayoutMain);

        continueButton = (Button) findViewById(R.id.buttonTimingContinue);
        continuePauseButton = (Button) findViewById(R.id.buttonTimingPause);
        continueEndButton = (Button) findViewById(R.id.buttonTimingEnd);

        constTimingActual = (ConstraintLayout) findViewById(R.id.constraintLayoutTimingActual);
        textTimingActual = (TextView)findViewById(R.id.textViewTimingActual);

        startDate = (TextView) findViewById(R.id.textViewStartDate);
        startDate.setText("Start Date:");

        timeLapsed = (TextView) findViewById(R.id.textViewTimeLapsed);
        timeLapsed.setText("Time: ");

        myTimer = new Timer();

        allJobs = new AllJobs();

        title = (TextView) findViewById(R.id.textSingleJobTitle);
        editTitle = (EditText) findViewById(R.id.editTextTitle);

        colorChcosen = (View) findViewById(R.id.viewColorChosen);

        viewColor0  = (View) findViewById(R.id.viewColor0);
        viewColor1  = (View) findViewById(R.id.viewColor1);
        viewColor2  = (View) findViewById(R.id.viewColor2);
        viewColor3  = (View) findViewById(R.id.viewColor3);

        singleJobSave = (Button) findViewById(R.id.buttonSingleSave);
        singleJobStart = (Button) findViewById(R.id.buttonSingleStart);
        singleJobStop = (Button) findViewById(R.id.buttonSingleStop);


        load();

        //Identify the job, the user has selected:
        Intent intent = getIntent();
        final int id = intent.getIntExtra("id", 9);
        idIn = id;

        //Get the Job's title and Color
        final String jobTitle = allJobs.getJobs().get(id).getTitle();
        final int jobColor = allJobs.getJobs().get(id).getColor();


        //Display Job Title:
        title.setText(jobTitle + "");
        editTitle.setText(jobTitle + "");


        //Views and Clicklisteners for the Colorpicker. has to be saved by clicking the
        // 'singleJobSave' button

        /////display Job Color:
        colorChcosen.setBackgroundColor(jobColor);


        /////Paint Colorpickers:
        viewColor0.setBackgroundColor(getResources().getColor(R.color.blue));
        viewColor1.setBackgroundColor(getResources().getColor(R.color.red));
        viewColor2.setBackgroundColor(getResources().getColor(R.color.green));
        viewColor3.setBackgroundColor(getResources().getColor(R.color.yellow));

        ////Click listeners: paint the 'colorChosen' view
        //////Blue
        viewColor0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChcosen.setBackgroundColor(getResources().getColor(R.color.blue));
            }
        });
        //////Red
        viewColor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChcosen.setBackgroundColor(getResources().getColor(R.color.red));
            }
        });
        //////Green
        viewColor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChcosen.setBackgroundColor(getResources().getColor(R.color.green));
            }
        });
        //////Yellow
        viewColor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorChcosen.setBackgroundColor(getResources().getColor(R.color.yellow));
            }
        });


        //After returning to the Activity, user will have to chose
        ////Continue, Pause or End actual Timing of a job:
        //////Continue
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondsCounted = allJobs.getJobs().get(idIn).getSeconds();
                seconds = secondsCounted;
                try{
                    //Start job timer WITHOUT adding a new timing
                    //not required, since choice is only made AFTER timing is added and user
                    //is still using the same job and timing
                    startTimer();
                    allJobs.getJobs()
                            .get(idIn)
                            .getTimings()
                            .get(allJobs
                                    .getJobs()
                                    .get(idIn)
                                    .getTimings()
                                    .size() - 1).setEnded(1);
                    constTimingActual.setVisibility(View.GONE);
                    constMain.setVisibility(View.VISIBLE);
                } catch (Exception e){
                    System.out.println(e + " eeer");
                }

            }
        });
        /////End
        continueEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondsCounted = allJobs.getJobs().get(idIn).getSeconds();
                //get the current timing
                Timing currentTiming = allJobs
                        .getJobs()
                        .get(idIn)
                        .getTimings()
                        .get(allJobs
                                .getJobs()
                                .get(idIn)
                                .getTimings()
                                .size() - 1);

                //set current Timing to have ended
                currentTiming.setEnded(0);

                //set seconds to current Timing, will later be converted to minutes and hours
                currentTiming.setSeconds(secondsCounted);
                save();
                secondsCounted = 0;
                seconds = 0;
                constTimingActual.setVisibility(View.GONE);
                constMain.setVisibility(View.VISIBLE);


            }
        });
        /////Pause
        continuePauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //Click listener: Save Edits like Jobcolor
        singleJobSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allJobs.getJobs().get(id).setTitle(editTitle.getText().toString());
                int color = Color.TRANSPARENT;
                Drawable background = colorChcosen.getBackground();
                if (background instanceof ColorDrawable)
                    color = ((ColorDrawable) background).getColor();
                allJobs.getJobs().get(id).setColor(color);
                title.setText(editTitle.getText());
                save();
            }
        });


        //Click listener to start the timing of a job, when no timing is running
        singleJobStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get Timings of current Job, to see if at least 1 is there, to avoid null pointer
                if(allJobs.getJobs().get(idIn).getTimings().size() > 0){
                    //get the current timing
                    Timing currentTiming = allJobs
                            .getJobs()
                            .get(idIn)
                            .getTimings()
                            .get(allJobs
                                    .getJobs()
                                    .get(idIn)
                                    .getTimings()
                                    .size() - 1);

                    //Check if a job is running (ended = 0 means not running)
                    ////IF NO
                    if (currentTiming.getEnded() == 0){
                        //Add a timing and date for later processing, has to be first
                        addTimingAndDate();
                        //Start the timer, in this case with 0 seconds
                        startTimer();
                    } else {

                        //If job is running, do nothing but notify:
                        Toast.makeText(getApplicationContext(),"Job is already running",Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //Add a first timing and date for later processing, has to be first
                    addTimingAndDate();
                    //Start the timer, in this case with 0 seconds
                    startTimer();
                }
                save();
            }
        });
        //Click listener to end the timing of a job, without going back to homescreen before
        singleJobStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //Stop the Timer
                    myTimer.cancel();
                    myTimer = null;

                    //get the current timing
                    Timing currentTiming = allJobs
                        .getJobs()
                        .get(idIn)
                        .getTimings()
                        .get(allJobs
                                .getJobs()
                                .get(idIn)
                                .getTimings()
                                .size() - 1);

                    //set current Timing to have ended
                    currentTiming.setEnded(0);

                    //set seconds to current Timing, will later be converted to minutes and hours
                    currentTiming.setSeconds(secondsCounted);

                    //Save data to Internal Storage
                    save();
                    //And go back to JobsOverview to avoid crashes and dataloss TO DO:
                    ////CREATE SUM UP FOR CURRENT JOB TIMING TO SHOW AFTER FINISHING
                    Intent intent = new Intent(SingleJob.this, JobsOverview.class);
                    startActivity(intent);

            }
        });

        //On entering Activity, check if user is running a timer/counting seconds at the moment (in other words: is returning from home screen)
        ////IF NO:
        if (allJobs.getJobs().get(idIn).getTimings().size() > 0){
            if(allJobs.getJobs().get(idIn).getTimings().get(allJobs.getJobs().get(idIn).getTimings().size() - 1).getEnded() == 0){

            } else {
                ////IF YES:
                //set Main Layout invisible
                constMain.setVisibility(View.GONE);

                //set Actual Timing Layout visible
                constTimingActual.setVisibility(View.VISIBLE);
                ////Set Actual Timing text to time elapsed on current timing
                textTimingActual.setText(allJobs.getJobs().get(idIn).getTimings().get(allJobs.getJobs().get(idIn).getTimings().size() - 1).getTimeLapsed()+ "");
            }
        }


    }

    public void addTimingAndDate(){
        //get today's date
        cal = Calendar.getInstance();
        now = cal.getTime();

        //add a timing to the job (required to check if a timer is running)
        Timing timing = new Timing();
        allJobs.getJobs().get(idIn).getTimings().add(timing);

        //getEnded() == 1 means the job is running
        allJobs.getJobs().get(idIn).getTimings().get(allJobs.getJobs().get(idIn).getTimings().size() - 1).setEnded(1);

        //save "today" for an overview of timings
        allJobs.getJobs().get(idIn).getTimings().get(allJobs.getJobs().get(idIn).getTimings().size() - 1).setDateStart(now);
    }

    public void startTimer(){
        //get today's date
        cal = Calendar.getInstance();
        now = cal.getTime();

        //check if seconds are > 0 and need to be converted to minutes and hours
        minutesHours();

        //print out the current and time
        timeStart = new SimpleDateFormat("dd.MM.YYYY - HH:mm");
        String nowString = timeStart.format(now);
        startDate.setText("Start Date: " + nowString);

        //start the timer
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 1000);
    }

    private NotificationManager mNotificationManager;


    //OnBackPressed has been overwritten to keep the timer running on returning to Home screen
    @Override
    public void onBackPressed() {
        //Check if a job is running
        ////IF NOT: return to JobsOverview Activity
        if(allJobs.getJobs().get(idIn).getTimings().get(allJobs.getJobs().get(idIn).getTimings().size() - 1).getEnded() == 0
                && allJobs.getJobs().get(idIn).getTimings().size() > 0){
            Intent intent = new Intent(SingleJob.this, JobsOverview.class);
            startActivity(intent);
        } else {
            /////IF YES: return to homescreen in order to keep timer running (notification is built every 10 seconds)
            Log.e("CDA", "onBackPressed Called");
            Intent setIntent = new Intent(Intent.ACTION_MAIN);
            setIntent.addCategory(Intent.CATEGORY_HOME);
            setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(setIntent);
        }
    }

    private void TimerMethod()
    {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);
    }

int secondsCounted = 0;
    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            secondsCounted++;
            Job job = allJobs.getJobs().get(idIn);
            System.out.println(secondsCounted + " testor");
            notificationLoad(job);
            job.setSeconds(secondsCounted);
            job.getTimings().get(job.getTimings().size() - 1).setTimeLapsed(timeLapsed.getText().toString());

        }
    };

    int minutes = 0;
    int hours = 0;
    int seconds = 0;

    public void minutesHours(){
        int counter = seconds;
        for(int i = counter; i > 0; i--){
            if (i % 60 == 0) {
                minutes++;
                seconds-=60;
            }
        }
        counter = minutes;
        for(int i = counter; i > 0; i--){
            if(i % 60 == 0){
                hours++;
                minutes-=60;
            }
        }
    }


    public void notificationLoad(Job job){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this.getApplicationContext(), "notify_001");
        Intent ii = new Intent(this.getApplicationContext(), SingleJob.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        seconds++;
        if (seconds == 60){
            seconds = 0;
            minutes++;
        }
        if (minutes == 60){
            minutes = 0;
            hours++;
        }
        try{
            allJobs.getJobs().get(idIn).getTimings().get(allJobs.getJobs().get(idIn).getTimings().size() - 1).setSeconds(seconds);
            System.out.println(allJobs.getJobs().get(idIn).getTimings().size());
            save();
        } catch (Exception e){
            System.out.println(e + " eee");
        }


        if(minutes == 0 && hours == 0){
            if(seconds < 10){
                bigText.bigText("Time: 0" + seconds + " seconds");
                timeLapsed.setText("Time: 0" + seconds + " seconds");

            } else {
                bigText.bigText("Time: " + seconds + " seconds");
                timeLapsed.setText("Time: " + seconds + " seconds");

            }
        } else if(hours == 0){
            if (minutes < 10){
                if(seconds < 10){
                    bigText.bigText("Time: 0" + minutes + ":0" + seconds);
                    timeLapsed.setText("Time: 0" + minutes + ":0" + seconds);

                } else {
                    bigText.bigText("Time: 0" + minutes + ":" + seconds);
                    timeLapsed.setText("Time: 0" + minutes + ":" + seconds);

                }
            } else {
                if(seconds < 10){
                    bigText.bigText("Time: " + minutes + ":0" + seconds);
                    timeLapsed.setText("Time: " + minutes + ":0" + seconds);


                } else {
                    bigText.bigText("Time: " + minutes + ":" + seconds);
                    timeLapsed.setText("Time: " + minutes + ":" + seconds);

                }
            }
        } else {
            if(hours < 10){
                if (minutes < 10){
                    if(seconds < 10){
                        bigText.bigText("Time: 0" + hours + ":0" + minutes + ":0" + seconds);
                        timeLapsed.setText("Time: 0" + hours + ":0" + minutes + ":0" + seconds);

                    } else {
                        bigText.bigText("Time: 0" + hours + ":0" + minutes + ":" + seconds);
                        timeLapsed.setText("Time: 0" + hours + ":0" + minutes + ":" + seconds);

                    }
                } else {
                    if(seconds < 10){
                        bigText.bigText("Time: 0" + hours + ":" + minutes + ":0" + seconds);
                        timeLapsed.setText("Time: 0" + hours + ":" + minutes + ":0" + seconds);

                    } else {
                        bigText.bigText("Time: 0" + hours + ":" + minutes + ":" + seconds);
                        timeLapsed.setText("Time: 0" + hours + ":" + minutes + ":" + seconds);

                    }

                }
            } else {
                if (minutes < 10){
                    if(seconds < 10){
                        bigText.bigText("Time: " + hours + ":0" + minutes + ":0" + seconds);
                        timeLapsed.setText("Time: " + hours + ":0" + minutes + ":0" + seconds);

                    } else {
                        bigText.bigText("Time: " + hours + ":0" + minutes + ":" + seconds);
                        timeLapsed.setText("Time: " + hours + ":0" + minutes + ":" + seconds);

                    }
                } else {
                    if(seconds < 10){
                        bigText.bigText("Time: " + hours + ":" + minutes + ":0" + seconds);
                        timeLapsed.setText("Time: " + hours + ":" + minutes + ":0" + seconds);

                    } else {
                        bigText.bigText("Time: " + hours + ":" + minutes + ":" + seconds);
                        timeLapsed.setText("Time: " + hours + ":" + minutes + ":" + seconds);

                    }
                }
            }

        }

        bigText.setBigContentTitle(job.title + "");
        bigText.setSummaryText(job.title);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Your Title");
        mBuilder.setContentText("Your text");
        mBuilder.setPriority(Notification.PRIORITY_LOW);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "Your_channel_id";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_LOW);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        if(seconds % 10 == 0 || seconds == 1){
            mNotificationManager.notify(0, mBuilder.build());

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


