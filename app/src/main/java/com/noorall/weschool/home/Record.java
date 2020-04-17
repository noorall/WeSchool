package com.noorall.weschool.home;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Record {
    private String location;
    private Date date;
    private String startTime;
    private String endTime;
    private String minutes;
    private String km;
    public Record(Date date,String location, String minutes,String km){
        int continueTime=Double.valueOf(minutes).intValue()*60000;
        this.date=date;
        this.minutes=minutes+"min";
        this.km=km+"km";
        this.location=location;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        this.startTime=formatter.format(date);
        this.endTime = formatter.format(new Date(date.getTime()+continueTime));
    }
    public void updateData(Date date,String location, String minutes,String km){
        int continueTime=Double.valueOf(minutes).intValue()*60000;
        this.date=date;
        this.minutes=minutes+"min";
        this.km=km+"km";
        this.location=location;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        this.startTime=formatter.format(date);
        this.endTime = formatter.format(new Date(date.getTime()+continueTime));
    }
    public String getDate()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String dateString = formatter.format(date);
        return dateString;
    }
    public String getLocation()
    {
        return location;
    }

    public String getKm() {
        return km;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getMinutes() {
        return minutes;
    }

    public String getStartTime() {
        return startTime;
    }
}
