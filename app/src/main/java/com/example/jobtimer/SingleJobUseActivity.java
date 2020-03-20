//package com.example.jobtimer;
//
//import android.animation.Animator;
//import android.animation.AnimatorListenerAdapter;
//import android.animation.ObjectAnimator;
//import android.app.Notification;
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.ContextWrapper;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.TaskStackBuilder;
//import androidx.core.content.ContextCompat;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//
//public class SingleJobUseActivity extends AppCompatActivity {
//
//    //INT for identifying the job
//    int idIn;
//
//    //TextView for Job Title
//    TextView jobTitleTextView;
//
//    //Timer for timingfunction
//    Timer myTimer;
//
//    //NotificationManager for notify about timing
//    private NotificationManager mNotificationManager;
//
//    //ints for counting time
//    int secondsCounted;
//
//    //viewModel to get saved Jobs from roomdb
//    private RoomJobViewModel roomJobViewModel;
//    private TimingViewModel timingViewModel;
//    //RoomJob to work with
//    RoomJob rJob;
//    //Timings to work with
//    List<Timing> timingsFound;
//    Timing currentTiming;
//
//    //TextView for currentTiming
//    TextView currentTimingTextView;
//
//    //Buttons for Start/stop
//    Button buttonStartTiming;
//    Button buttonStopTiming;
//
//    //Calendar for getting today:
//    Calendar cal;
//    Date now;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_single_job_use);
//
//
//        //Seconds counter
//        secondsCounted = 0;
//
//
//
//        //Identify the job, the user has selected:
//        Intent intent = getIntent();
//        final int id = intent.getIntExtra("id", -1);
//        idIn = id;
//        System.out.println(idIn + " timing start");
//
//        //TextView for Current Timing
//        currentTimingTextView = (TextView) findViewById(R.id.textViewTimeCurrentTiming);
//
//        //Declare Timer
//        myTimer = new Timer();
//
//        //Buttons for Start/Stop Timing
//        buttonStartTiming = (Button) findViewById(R.id.buttonStartTiming);
//        buttonStopTiming = (Button) findViewById(R.id.buttonEndTiming);
//
//        //Prepare Variable for current Job
//        rJob = new RoomJob("new Job");
//        //Get Current Job
//        roomJobViewModel = new ViewModelProvider(this).get(RoomJobViewModel.class);
//        roomJobViewModel.getAllJobs().observe(this, new Observer<List<RoomJob>>() {
//            @Override
//            public void onChanged(@Nullable final List<RoomJob> rJobs) {
//                //TextView for Job Title
//                jobTitleTextView = (TextView) findViewById(R.id.textViewJobTitle);
//
//                //sort out current job
//                for (RoomJob rIJob : rJobs) {
//                    if (rIJob.getRid() == idIn) {
//                        //get current Job
//                        rJob = rIJob;
//
//                        //set Job Title to display
//                        jobTitleTextView.setText(rJob.getTitle());
//                    }
//                }
//            }
//        });
//
//        //List for extracted timings for later use
//        timingsFound = new ArrayList<Timing>();
//
//        //TimeViewModel
//        timingViewModel = new ViewModelProvider(this).get(TimingViewModel.class);
//        ////Set Observer
//        timingViewModel.getAllTimings().observe(this, new Observer<List<Timing>>() {
//            @Override
//            public void onChanged(@Nullable final List<Timing> timings) {
//
//                //Iterate through timings in DB to check for timings with current jobid
//                for (Timing timing : timings) {
//                    //if ids match:
//                    if (timing.getJobId() == idIn) {
//                        //put found timings into list for later use
//                        //Add them to list
//                        timingsFound.add(timing);
//                        System.out.println(timing.getEnded() + " timing ended"
//                                + timing.getId() + " timing id"
//                                + timing.getDate() + " timing start"
//                                + timing.getSeconds() + " timing seconds"
//                                + timing.getTimeLapsed() + " timing end");
//
//                    }
//                }
//
//                //CHeck if job already has timings, avoid null pointer
//                if (timingsFound.size() > 0) {
//                    //if has timings
//
//                    //get Current Timing, can only be last in List
//                    currentTiming = timingsFound.get(timingsFound.size() - 1);
//
//                    //display current seconds
//                    currentTimingTextView.setText(minutesHours(secondsCounted) + "");
//
//                    //Check if job is still running (returning from home screen through notification or after start)
//                    if (currentTiming.getEnded() == 1) {
//                        //if is running first round:
//
//                        //avoid second timer running
//                        buttonStartTiming.setEnabled(false);
//
//                        //Animate stop button to show
//                        ObjectAnimator animation = ObjectAnimator.ofFloat(buttonStopTiming,
//                                "translationY",
//                                buttonStopTiming.getTranslationY(),
//                                buttonStopTiming.getTranslationY() + 162);
//
//                        animation.setDuration(600);
//                        animation.start();
//                        animation.addListener(new AnimatorListenerAdapter() {
//                            public void onAnimationEnd(Animator animation) {
//
//                            }
//                        });
//                        //Set timing to ended = 2, which means everything is running
//                        currentTiming.setEnded(2);
//
//                        //Save state
//                        timingViewModel.update(currentTiming);
//
//                    } else if (currentTiming.getEnded() == 2) {
//                        //if is running after first round:
//
//                        //set Start Button to Continue button
//                        buttonStartTiming.setText("Continue");
//
//                        //if Seconds Counterd = 0, means you are coming back from notification
//                        if (secondsCounted == 0) {
//                            currentTimingTextView.setText(currentTiming.getSeconds() + "");
//                        }
//
//                        //Set translation of button to where it animated to
//                        buttonStopTiming.setTranslationY(162);
//                    }
//
//                    //Set Stop timing click listener, always same;
//                    buttonStopTiming.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            stopTimer();
//                        }
//                    });
//                } else {
//
//                    //if job has no timings:
//
//                    //Create new Timing ended of 1 (important so start timer function will pick right choice)
//                    currentTiming = new Timing(idIn, 0, "newjob", 1, "");
//
//                    ///And set current Seconds to display
//                    currentTimingTextView.setText(currentTiming.getSeconds() + "");
//                }
//
//
//                //Start timer AFTER checking if timings exist and are running, start timer has choise that depends on it
//                buttonStartTiming.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startTimer();
//                        started = true;
//                    }
//                });
//            }
//
//            public void stopTimer() {
//                stopService();
//                getGloabSeconds();
//
//                currentTimingTextView.setText(minutesHours(secondsCounted) + "");
//                System.out.println(secondsCounted + " testor timing");
//                currentTiming.setSeconds(secondsCounted);
//                timingViewModel.update(currentTiming);
//
//                started = false;
//                myTimer.cancel();
//                currentTiming.setEnded(0);
//
//                cal = Calendar.getInstance();
//                now = cal.getTime();
//                SimpleDateFormat timeStart = new SimpleDateFormat("dd.MM.YYYY - HH:mm");
//                String nowString = timeStart.format(now);
//                currentTiming.setTimeLapsed(nowString);
//
//                timingViewModel.update(currentTiming);
//                //secondsCounted = 0;
//               // update();
//
//               Intent intent = new Intent(SingleJobUseActivity.this, OverviewJobsActivity.class);
//               startActivity(intent);
//            }
//
//            public void startTimer() {
//                secondsCounted = 0;
//                update();
//                //no timing was foud, this is the first timing for a job:
//                if (currentTiming.getDate().equals("newjob")) {
//                    //get today's date
//                    cal = Calendar.getInstance();
//                    now = cal.getTime();
//                    SimpleDateFormat timeStart = new SimpleDateFormat("dd.MM.YYYY - HH:mm");
//                    String nowString = timeStart.format(now);
//                    Timing nextTiming = currentTiming;
//                    nextTiming.setDate(nowString);
//                    timingViewModel.insertTiming(nextTiming);
//
//
//
//                    startService();
//
//                    myTimer.schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            TimerMethod();
//                        }
//                    }, 0, 1000);
//                    //Timings are found, and currentTiming (last in array) is still running (ended = 1)
//                } else if (currentTiming.getEnded() > 0) {
//                    secondsCounted = currentTiming.getSeconds();
//                    startService();
////                    myTimer.schedule(new TimerTask() {
////                        @Override
////                        public void run() {
////                            TimerMethod();
////                        }
////                    }, 0, 1000);
//                    buttonStartTiming.setEnabled(false);
//                } else if (currentTiming.getEnded() == 0) {
//                    Timing nextTiming = new Timing(idIn, 0, "", 1, "");
//
//                    cal = Calendar.getInstance();
//                    now = cal.getTime();
//                    SimpleDateFormat timeStart = new SimpleDateFormat("dd.MM.YYYY - HH:mm");
//                    String nowString = timeStart.format(now);
//                    nextTiming.setDate(nowString);
//
//                    timingViewModel.insertTiming(nextTiming);
//                    System.out.println(timingsFound.size() + " timing current size");
//                    startService();
//                    myTimer.schedule(new TimerTask() {
//                        @Override
//                        public void run() {
//                            TimerMethod();
//                            System.out.println("timing current is starting");
//                        }
//                    }, 0, 1000);
//                    System.out.println(currentTiming.getId() + " id  timing current\n" +
//                            currentTiming.getJobId() + " current timing current\n" +
//                            currentTiming.getEnded() + " ended current timing current\n" +
//                            idIn + " idIn timing\n" +
//                            currentTiming.getSeconds() + " secods current timing current");
//                }
//            }
//
//        });
//
//
//
//
//
//    }
//
//    public void update(){
//        ((Variables) this.getApplication()).setSeconds(secondsCounted);
//    }
//
//    public void getGloabSeconds(){
//        secondsCounted = ((Variables) this.getApplication()).getSeconds();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        secondsCounted = ((Variables) this.getApplication()).getSeconds();
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        myTimer.cancel();
//        timingViewModel.update(currentTiming);
//        System.out.println(currentTiming.getEnded() + " timing destroy");
//
//    }
//
//
//    private void TimerMethod() {
//        //This method is called directly by the timer
//        //and runs in the same thread as the timer.
//
//        //We call the method that will work with the UI
//        //through the runOnUiThread method.
//        this.runOnUiThread(Timer_Tick);
//    }
//
//
//    private Runnable Timer_Tick = new Runnable() {
//        public void run() {
//            if (currentTiming.getEnded() != 0) {
//                getGloabSeconds();
//                currentTimingTextView.setText(minutesHours(secondsCounted) + "");
////                secondsCounted++;
////                currentTimingTextView.setText(minutesHours(secondsCounted) + "");
////                System.out.println(secondsCounted + " testor timing");
////                currentTiming.setSeconds(secondsCounted);
////                timingViewModel.update(currentTiming);
//
//                //notificationLoad();
//
//
//            }
//
//        }
//    };
//
//    boolean started = false;
//
//    @Override
//    public void onBackPressed() {
//        if (secondsCounted == 0 && started == false) {
//            Intent intent = new Intent(SingleJobUseActivity.this, OverviewJobsActivity.class);
//            startActivity(intent);
//        } else {
//            /////IF YES: return to homescreen in order to keep timer running (notification is built every 10 seconds)
//            Log.e("CDA", "onBackPressed Called");
//            Intent setIntent = new Intent(Intent.ACTION_MAIN);
//            setIntent.addCategory(Intent.CATEGORY_HOME);
//            setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(setIntent);
//        }
//    }
//
//    public String minutesHours(int seconds) {
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
//        for (int i = counter; i > 0; i--) {
//            if (i % 60 == 0) {
//                minutes++;
//                seconds -= 60;
//            }
//        }
//        //counter = minutes so wen count how many hours and subtract 60 minutes each hour,
//        //until last started hour
//        counter = minutes;
//        for (int i = counter; i > 0; i--) {
//            if (i % 60 == 0) {
//                hours++;
//                minutes -= 60;
//            }
//        }
//
//        //Convert to a readable String, depending on seconds/minutes/hours > 0 and < 10
//        if (minutes == 0 && hours == 0) {
//            if (seconds < 10) {
//                timeConverted = ("Time: 0" + seconds + " seconds");
//
//            } else {
//                timeConverted = ("Time: " + seconds + " seconds");
//
//            }
//        } else if (hours == 0) {
//            if (minutes < 10) {
//                if (seconds < 10) {
//                    timeConverted = ("Time: 0" + minutes + ":0" + seconds);
//
//                } else {
//                    timeConverted = ("Time: 0" + minutes + ":" + seconds);
//
//                }
//            } else {
//                if (seconds < 10) {
//                    timeConverted = ("Time: " + minutes + ":0" + seconds);
//
//
//                } else {
//                    timeConverted = ("Time: " + minutes + ":" + seconds);
//
//                }
//            }
//        } else {
//            if (hours < 10) {
//                if (minutes < 10) {
//                    if (seconds < 10) {
//                        timeConverted = ("Time: 0" + hours + ":0" + minutes + ":0" + seconds);
//
//                    } else {
//                        timeConverted = ("Time: 0" + hours + ":0" + minutes + ":" + seconds);
//
//                    }
//                } else {
//                    if (seconds < 10) {
//                        timeConverted = ("Time: 0" + hours + ":" + minutes + ":0" + seconds);
//
//                    } else {
//                        timeConverted = ("Time: 0" + hours + ":" + minutes + ":" + seconds);
//
//                    }
//
//                }
//            } else {
//                if (minutes < 10) {
//                    if (seconds < 10) {
//                        timeConverted = ("Time: " + hours + ":0" + minutes + ":0" + seconds);
//
//                    } else {
//                        timeConverted = ("Time: " + hours + ":0" + minutes + ":" + seconds);
//
//                    }
//                } else {
//                    if (seconds < 10) {
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
//    public void startService() {
//        Intent serviceIntent = new Intent(this, ForegroundService.class);
//        serviceIntent.putExtra("inputExtra", idIn);
//        ContextCompat.startForegroundService(this, serviceIntent);
//
//    }
//    public void stopService() {
//        Intent serviceIntent = new Intent(this, ForegroundService.class);
//        stopService(serviceIntent);
//    }
//
//    public void notificationLoad() {
//        //Declare NotificationCompat mBuilder
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this.getApplicationContext(), "notify_001");
//        //Declare intent for Nofitication to return to
//        Intent ii = new Intent(this, SingleJobUseActivity.class);
//        ii.putExtra("id", idIn);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addNextIntentWithParentStack(ii);
//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
//
//        bigText.bigText(minutesHours(secondsCounted) + "");
//
//        bigText.setBigContentTitle(rJob.getTitle() + "");
//        bigText.setSummaryText(rJob.getTitle());
//
//        //Configure NotificationCompat mBuilder
//        mBuilder.setContentIntent(pendingIntent);
//        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
//        mBuilder.setContentTitle("Your Title");
//        mBuilder.setContentText("Your text");
//        mBuilder.setPriority(Notification.PRIORITY_HIGH);
//        mBuilder.setStyle(bigText);
//
//        mNotificationManager =
//                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            String channelId = "Your_channel_id";
//            NotificationChannel channel = new NotificationChannel(
//                    channelId,
//                    "Channel human readable title",
//                    NotificationManager.IMPORTANCE_HIGH);
//            mNotificationManager.createNotificationChannel(channel);
//            mBuilder.setChannelId(channelId);
//        }
//
//        mNotificationManager.notify(0, mBuilder.build());
//
//
//      //  ContextCompat.startForegroundService(this, ii);
//
//
//
//    }
//
//
//}
//
//
