package com.example.jobtimer;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Entity;

@Entity(tableName = "jobs_table")
public class RoomAllJobs {



    List<Job> jobs = new ArrayList<Job>();

    public RoomAllJobs(){

    }



    public List<Job> getJobs() {
        return jobs;
    }

    public void addJob(Job job) {
        this.jobs.add(job);
    }

}
