package com.example.jobtimer;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;

class RoomJobRepo {

    private RoomJobDao rJobDao;
    private LiveData<List<RoomJob>> rAllJobs;

    // Note that in order to unit test the WordRepository, you have to remove the Application
    // dependency. This adds complexity and much more code, and this sample is not about testing.
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    RoomJobRepo(Application application) {
        RoomJobDB db = RoomJobDB.getDatabase(application);
        rJobDao = db.rJobDao();
        rAllJobs = rJobDao.getAlphabetizedWords();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<RoomJob>> getAllJobs() {
        return rAllJobs;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(RoomJob rJob) {
        RoomJobDB.databaseWriteExecutor.execute(() -> {
            rJobDao.insert(rJob);
        });
    }
}