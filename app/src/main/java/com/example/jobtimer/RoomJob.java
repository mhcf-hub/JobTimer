package com.example.jobtimer;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "job_table")
public class RoomJob {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "color")
    int color;

    @NonNull
    @ColumnInfo(name = "seconds")
    int seconds;

    @NonNull
    @ColumnInfo(name = "timings")
    List<Timing> timings = new ArrayList<Timing>();

    public RoomJob() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @NonNull
    public List<Timing> getTimings() {
        return timings;
    }

    public void setTimings(@NonNull List<Timing> timings) {
        this.timings = timings;
    }
}