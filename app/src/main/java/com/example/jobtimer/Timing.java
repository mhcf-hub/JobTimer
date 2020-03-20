package com.example.jobtimer;

import java.io.Serializable;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "timings_table",
        foreignKeys = @ForeignKey(entity = RoomJob.class,
                parentColumns = "rid",
                childColumns = "jobId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("jobId"), @Index("seconds"), @Index("date"), @Index("ended")})
public class Timing {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;


    @NonNull
    @ColumnInfo(name = "jobId")
    private int jobId;

    @NonNull
    @ColumnInfo(name = "seconds")
    int seconds;

    @NonNull
    @ColumnInfo(name = "date")
    String date;

    @NonNull
    @ColumnInfo(name = "ended")
    int ended;

    @NonNull
    @ColumnInfo(name = "ended")
    long start;


    @NonNull
    @ColumnInfo(name = "ended")
    long end;



    @NonNull
    @ColumnInfo(name = "timeLapsed")
    String timeLapsed;

    public Timing(int jobId, int seconds, String date, int ended, String timeLapsed) {
        this.jobId = jobId;
        this.seconds = seconds;
        this.date = date;
        this.ended = ended;
        this.timeLapsed = timeLapsed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public int getEnded() {
        return ended;
    }

    public void setEnded(int ended) {
        this.ended = ended;
    }

    @NonNull
    public String getTimeLapsed() {
        return timeLapsed;
    }

    public void setTimeLapsed(@NonNull String timeLapsed) {
        this.timeLapsed = timeLapsed;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
