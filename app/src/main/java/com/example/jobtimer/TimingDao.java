package com.example.jobtimer;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface TimingDao {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertTiming(Timing timing);

    @Query("DELETE FROM timings_table")
    void deleteAll();

    @Query("SELECT * from timings_table")
    LiveData<List<Timing>> getTimings();

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(Timing timing);

}