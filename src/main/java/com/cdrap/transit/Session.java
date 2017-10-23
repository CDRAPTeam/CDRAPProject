/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdrap.transit;

/**
 *
 * @author Navnik
 */
public class Session {
    
	private String fileID;
    private int year;
    private String season;
    private int session;

    public Session(String fileID,int year, String season, int session) {
        this.year = year;
        this.season = season;
        this.session = session;
        this.fileID = fileID;
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
    public String getFileID(){
    	return fileID;
    }
    
}
