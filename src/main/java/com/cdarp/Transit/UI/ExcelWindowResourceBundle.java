package com.cdarp.Transit.UI;

import java.util.Collection;

import com.cdarp.Transit.DateTimeRow;

public class ExcelWindowResourceBundle extends ResourceList<DateTimeRow>{
	private int year;
	private String season;
	private int session;
	private String room;
	public ExcelWindowResourceBundle(Collection<DateTimeRow> objs,int year, String season, int session,String room) {
		super(objs);
		// TODO Auto-generated constructor stub
		this.year = year;
		this.season = season;
		this.session = session;
		this.room = room;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	public int getSession() {
		return session;
	}
	public void setSession(int session) {
		this.session = session;
	}
	public void setRoom(String room){
		this.room = room;
	}
	public String getRoom(){
		return room;
	}
}
