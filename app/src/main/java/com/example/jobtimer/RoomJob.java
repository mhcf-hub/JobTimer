package com.example.jobtimer;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "job_table",
        indices = {@Index("title")})
public class RoomJob {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rid")
    private int rid;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "color")
    int color;

    @NonNull
    @ColumnInfo(name = "seconds")
    int seconds;


    public RoomJob(String title) {
        this.title = title;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
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

}