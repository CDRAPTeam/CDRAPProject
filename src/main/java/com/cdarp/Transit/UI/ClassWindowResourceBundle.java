package com.cdarp.Transit.UI;

import java.util.Collection;

import com.cdarp.Transit.ClassSession;
import com.cdarp.Transit.DateTimeRow;

public class ClassWindowResourceBundle extends ResourceList<ClassSession>{
	private int year;
	private String season;
	private int session;
	public ClassWindowResourceBundle(Collection<ClassSession> objs,int year, String season, int session) {
		super(objs);
		// TODO Auto-generated constructor stub
		this.year = year;
		this.season = season;
		this.session = session;
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
	

}
