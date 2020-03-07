package com.example.jobtimer;


import android.app.Application;
import android.util.Log;

import java.util.List;

import androidx.lifecycle.LiveData;

class TimingRepo {
    private TimingDao timingDao;

    private LiveData<List<Timing>> rTimings;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    TimingRepo(Application application) {
        RoomJobDB db = RoomJobDB.getDatabase(application);
        timingDao = db.timingDao();
        rTimings = timingDao.getTimings();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Timing>> getAllTimings() { return rTimings;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI

    void insertTiming(Timing timing) {
        RoomJobDB.databaseWriteExecutor.execute(() -> {
            timingDao.insertTiming(timing);
            System.out.println("job tiiiii");
        });
    }

}