package com.example.jobtimer;

import java.io.Serializable;
import java.util.Date;

public class Timing implements Serializable {


    int seconds;
    Date dateStart;
    Date dateEnd;
    int ended;
    String timeLapsed;

    public Timing() {
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getEnded() {
        return ended;
    }

    public void setEnded(int ended) {
        this.ended = ended;
    }

    public String getTimeLapsed() {
        return timeLapsed;
    }

    public void setTimeLapsed(String timeLapsed) {
        this.timeLapsed = timeLapsed;
    }
}
