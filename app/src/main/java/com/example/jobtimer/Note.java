package com.example.jobtimer;

import android.text.Editable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table",
        foreignKeys = @ForeignKey(entity = RoomJob.class,
                parentColumns = "rid",
                childColumns = "jobId",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index("jobId"), @Index("content")})
public class Note {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;


    @NonNull
    @ColumnInfo(name = "jobId")
    private int jobId;

    @NonNull
    @ColumnInfo(name = "content")
    String content;

    public Note(@NonNull int jobId) {
        this.jobId = jobId;
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

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }
}
