package com.example.assignment4;

import java.util.Timer;
import java.util.TimerTask;

public class Trial {

    long startTime;
    long endTime;
    Blob blob;

    double indexDiff;
    double distance;

    public Trial() {
    }

    public void setBlob(Blob blob) {
        this.blob = blob;
    }

    public Blob getBlob() {
        return blob;
    }

    public void startTimer(){
        startTime = System.currentTimeMillis();
    }

    public void endTimer(){
        endTime = System.currentTimeMillis() - startTime;
    }

    public void calcID(){
        double log2 = Math.log(2);
        indexDiff = Math.log(distance/ blob.r) / log2;
    }
    
    public void calcDistance(Blob secondary){
        distance = Math.sqrt(Math.pow(secondary.x - blob.x,2) + Math.pow(secondary.y - blob.y,2));
    }

    public double getIndexDiff() {
        return indexDiff;
    }

    public long getEndTime() {
        return endTime;
    }
}
