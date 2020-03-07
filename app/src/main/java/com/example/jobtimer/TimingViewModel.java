package com.example.jobtimer;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TimingViewModel extends AndroidViewModel {

    private JTRepo JTRepo;

    private LiveData<List<Timing>> allTimings;

    public TimingViewModel(Application application) {
        super(application);
        JTRepo = new JTRepo(application);
        allTimings = JTRepo.getAllTimings();
    }

    LiveData<List<Timing>> getAllTimings() {
        return allTimings;
    }

    public void insertTiming(Timing timing) {
        JTRepo.insertTiming(timing);
    }

    public void update(Timing timing) {
        JTRepo.updateTiming(timing);
    }

}