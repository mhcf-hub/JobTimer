package com.example.jobtimer;

import android.graphics.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class Job implements Serializable {
    String title;
    int color;
    int id;
    int seconds;
    List<Timing> timings = new ArrayList<Timing>();

    public Job(String title, int color, int id) {
        this.title = title;
        this.color = color;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public List<Timing> getTimings() {
        return timings;
    }

    public void setTimings(List<Timing> timings) {
        this.timings = timings;
    }

    public void addTiming(Timing timing){
        this.timings.add(timing);
    }
}
