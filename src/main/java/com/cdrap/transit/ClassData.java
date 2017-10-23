package com.cdrap.transit;

public class ClassData {
	private String monday, tuesday,wendsday,thursday,friday,saturday,sunday;
	private int time;
	
	
	public ClassData(int time,String monday, String tuesday, String wendsday, String thursday, String friday, String saturday,
			String sunday) {
		this.monday = monday;
		this.tuesday = tuesday;
		this.wendsday = wendsday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
		this.time = time;
	}
	public String getMonday() {
		return monday;
	}
	public void setMonday(String monday) {
		this.monday = monday;
	}
	public String getTuesday() {
		return tuesday;
	}
	public void setTuesday(String tuesday) {
		this.tuesday = tuesday;
	}
	public String getMilitaryTime(){
        return convertIntoMilitaryTime(this.time);
    }
	public static String convertIntoMilitaryTime(int time){
        int hour = time/4;
        return ""+hour+":"+(time%4==1?"15":time%4==2?"30":time%4==3?"45":"00");
	}
	public static int convertMilitaryToInt(String time){
		String[] frags = time.split(":");
		return (Integer.parseInt(frags[0])*4)+(Integer.parseInt(frags[1])/15);
	}
	public String getWendsday() {
		return wendsday;
	}
	public void setWendsday(String wendsday) {
		this.wendsday = wendsday;
	}
	public String getThursday() {
		return thursday;
	}
	public void setThursday(String thursday) {
		this.thursday = thursday;
	}
	public String getFriday() {
		return friday;
	}
	public void setFriday(String friday) {
		this.friday = friday;
	}
	public String getSaturday() {
		return saturday;
	}
	public void setSaturday(String saturday) {
		this.saturday = saturday;
	}
	public String getSunday() {
		return sunday;
	}
	public void setSunday(String sunday) {
		this.sunday = sunday;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
}
