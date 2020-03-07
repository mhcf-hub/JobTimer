package com.example.jobtimer;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RoomJobViewModel extends AndroidViewModel {

    private JTRepo JTRepo;
//    private TimingRepo timingRepo;

    private LiveData<List<RoomJob>> rAllJobs;
    private LiveData<List<Timing>> allTimings;

    public RoomJobViewModel (Application application) {
        super(application);
        JTRepo = new JTRepo(application);
        //timingRepo = new TimingRepo(application);
        rAllJobs = JTRepo.getAllJobs();
  //      allTimings = JTRepo.getAllTimings();
    }

    LiveData<List<RoomJob>> getAllJobs() { return rAllJobs; }
//    LiveData<List<Timing>> getAllTimings() { return allTimings; }

    public void insert(RoomJob roomJob) { JTRepo.insert(roomJob); }
    public void update(RoomJob roomJob) { JTRepo.update(roomJob); }
//   public void insertTiming(Timing timing) { JTRepo.insertTiming(timing); }






}