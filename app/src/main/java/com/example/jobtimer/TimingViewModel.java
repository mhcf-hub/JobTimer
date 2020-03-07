package com.example.jobtimer;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TimingViewModel extends AndroidViewModel {

    private RoomJobRepo roomJobRepo;
  private TimingRepo timingRepo;

   // private LiveData<List<RoomJob>> rAllJobs;
  private LiveData<List<Timing>> allTimings;

    public TimingViewModel (Application application) {
        super(application);
       roomJobRepo = new RoomJobRepo(application);
        timingRepo = new TimingRepo(application);
   //     rAllJobs = roomJobRepo.getAllJobs();
        allTimings = roomJobRepo.getAllTimings();
    }

   // LiveData<List<RoomJob>> getAllJobs() { return rAllJobs; }
   LiveData<List<Timing>> getAllTimings() { return allTimings; }

    //public void insert(RoomJob roomJob) { roomJobRepo.insert(roomJob); }
    public void insertTiming(Timing timing) {
            roomJobRepo.insertTiming(timing);
        }

        public void update(Timing timing){
        roomJobRepo.updateTiming(timing);
        }

}