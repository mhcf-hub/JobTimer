package com.example.jobtimer;

import java.io.Serializable;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "timings_table")
public class Timing implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;


    @NonNull
    @ColumnInfo(name = "jobId")
    private int jobId;

    @NonNull
    @ColumnInfo(name = "seconds")
    int seconds;

    @NonNull
    @ColumnInfo(name = "date")
    Date date;

    @NonNull
    @ColumnInfo(name = "ended")
    int ended;

    @NonNull
    @ColumnInfo(name = "timeLapsed")
    String timeLapsed;

    public Timing() {
    }
}
