package com.example.jobtimer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllJobs implements Serializable {

    public AllJobs(){

    }

    List<Job> jobs = new ArrayList<Job>();

    public List<Job> getJobs() {
        return jobs;
    }

    public void addJob(Job job) {
        this.jobs.add(job);
    }
}
