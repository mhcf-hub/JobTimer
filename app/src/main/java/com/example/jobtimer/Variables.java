package com.example.jobtimer;

import android.app.Application;

public class Variables extends Application {

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    int seconds;

}
