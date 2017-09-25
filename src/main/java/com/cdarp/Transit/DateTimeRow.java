/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdarp.Transit;

/**
 *
 * @author Navnik
 */
public class DateTimeRow {
    public static final int SELECTED = 1;
    public static final int UNSELECTED = 0;
    public static final int TIME_FRMAE_MIN = 0;
    public static final int TIME_FRAME_MAX = 24*2;//30 minute slots
    private int timeFrame;
    private int monday,tuesday,wendsday,thursday,friday,saturday,sunday;
    private ClassSession parent;

    public DateTimeRow(ClassSession parent,int timeFrame, int monday, int tuesday, int wendsday, int thursday, int friday, int saturday, int sunday) {
        this.timeFrame = timeFrame;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wendsday = wendsday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        this.parent = parent;
    }
    public DateTimeRow(ClassSession parent,int timeFrame){
        this(parent,timeFrame,0,0,0,0,0,0,0);
    }
    public int getTimeFrame() {
        return timeFrame;
    }
    public String getMilitaryTime(){
        int hour = timeFrame/2;
        return ""+hour+":"+(timeFrame%2==1?"30":"00");
    }

    public void setTimeFrame(int timeFrame) {
        this.timeFrame = timeFrame;
    }

    public int getMonday() {
        return monday;
    }

    public void setMonday(int monday) {
        this.monday = monday;
    }

    public int getTuesday() {
        return tuesday;
    }

    public void setTuesday(int tuesday) {
        this.tuesday = tuesday;
    }

    public int getWendsday() {
        return wendsday;
    }

    public void setWendsday(int wendsday) {
        this.wendsday = wendsday;
    }

    public int getThursday() {
        return thursday;
    }

    public void setThursday(int thursday) {
        this.thursday = thursday;
    }

    public int getFriday() {
        return friday;
    }

    public void setFriday(int friday) {
        this.friday = friday;
    }

    public int getSaturday() {
        return saturday;
    }

    public void setSaturday(int saturday) {
        this.saturday = saturday;
    }

    public int getSunday() {
        return sunday;
    }

    public void setSunday(int sunday) {
        this.sunday = sunday;
    }
    public ClassSession getParent(){
    	return parent;
    }
    
}
