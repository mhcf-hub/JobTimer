package com.example.jobtimer;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RoomJobViewModel extends AndroidViewModel {

    private RoomJobRepo roomJobRepo;

    private LiveData<List<RoomJob>> rAllJobs;

    public RoomJobViewModel (Application application) {
        super(application);
        roomJobRepo = new RoomJobRepo(application);
        rAllJobs = roomJobRepo.getAllJobs();
    }

    LiveData<List<RoomJob>> getAllJobs() { return rAllJobs; }

    public void insert(RoomJob roomJob) { roomJobRepo.insert(roomJob); }
}