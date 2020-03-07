package com.example.jobtimer;

import android.content.Context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {RoomJob.class, Timing.class, Note.class}, version = 1, exportSchema = false)
public abstract class RoomJobDB extends RoomDatabase {

    public abstract RoomJobDao rJobDao();
    public abstract TimingDao timingDao();
    public abstract NoteDao noteDao();

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

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

}


