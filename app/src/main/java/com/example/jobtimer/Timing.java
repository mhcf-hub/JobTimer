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
        indices = {@Index("jobId"), @Index("ended"), @Index("start"), @Index("end")})
public class Timing {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @NonNull
    @ColumnInfo(name = "jobId")
    private int jobId;

    @NonNull
    @ColumnInfo(name = "ended")
    int ended;

    @NonNull
    @ColumnInfo(name = "start")
    long start;


    @NonNull
    @ColumnInfo(name = "end")
    long end;

    public Timing(int jobId, int ended) {
        this.jobId = jobId;
        this.ended = ended;
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

    public int getEnded() {
        return ended;
    }

    public void setEnded(int ended) {
        this.ended = ended;
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
