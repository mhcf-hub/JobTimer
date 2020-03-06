package com.example.jobtimer;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {RoomJob.class}, version = 1, exportSchema = false)
public abstract class RoomJobDB extends RoomDatabase {

    public abstract RoomJobDao rJobDao();

    private static volatile RoomJobDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static RoomJobDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RoomJobDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomJobDB.class, "word_database")

                            .build();
                }
            }
        }
        return INSTANCE;
    }

//    .addCallback(sRoomDatabaseCallback)
//
//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
//        @Override
//        public void onOpen(@NonNull SupportSQLiteDatabase db) {
//            super.onOpen(db);
//
//            // If you want to keep data through app restarts,
//            // comment out the following block
//            databaseWriteExecutor.execute(() -> {
//                // Populate the database in the background.
//                // If you want to start with more words, just add them.
//                RoomJobDao dao = INSTANCE.rJobDao();
//                dao.deleteAll();
//
//                RoomJob roomJob = new RoomJob();
//                roomJob.setTitle("Valletest");
//                dao.insert(roomJob);
//            });
//        }
//    };



}


