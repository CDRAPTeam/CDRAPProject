package com.cdrap.GoogleAccessor;

public class SheetFileContainer {
	private String fileid;
	private int year;
	private int SummerSessions;
	private int FallSession;
	private int SpringSessions;
	
	public SheetFileContainer(String fileid,int year, int summerSessions, int fallSession, int springSessions) {
		this.fileid = fileid;
		this.year = year;
		SummerSessions = summerSessions;
		FallSession = fallSession;
		SpringSessions = springSessions;
	}
	
	public String getFileid() {
		return fileid;
	}
	public void setFileid(String fileid) {
		this.fileid = fileid;
	}
	public int getSummerSessions() {
		return SummerSessions;
	}
	public void setSummerSessions(int summerSessions) {
		SummerSessions = summerSessions;
	}
	public int getFallSessions() {
		return FallSession;
	}
	public void setFallSession(int fallSession) {
		FallSession = fallSession;
	}
	public int getSpringSessions() {
		return SpringSessions;
	}
	public void setSpringSessions(int springSessions) {
		SpringSessions = springSessions;
	}
	public int getYear(){
		return year;
	}
	public void setYear(int year){
		this.year = year;
	}
	
}
