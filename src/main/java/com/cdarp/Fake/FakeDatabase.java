package com.cdarp.Fake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.cdarp.Transit.ClassSession;
import com.cdarp.Transit.Session;
import com.cdarp.Transit.UI.ClassWindowResourceBundle;
import com.cdarp.Transit.UI.ExcelWindowResourceBundle;
import com.cdarp.Transit.UI.ResourceList;
import com.cdarp.Transit.DateTimeRow;

public class FakeDatabase {
	private static ArrayList<Session> collections = new ArrayList();
	private static HashMap<Session,ArrayList<ClassSession>> roomsAccess = new HashMap();
	private static HashMap<ClassSession,ArrayList<DateTimeRow>> dateTimeRow = new HashMap();
	
	static{
		for(int i = 2010;i < 2015;i++){
			for(int j = 1; j <=3;j++){
				appendSession(i,"Fall",j);
			}
			for(int j = 1; j <=3;j++){
				appendSession(i,"Spring",j);
			}
			for(int j = 1; j <=2;j++){
				appendSession(i,"Summer",j);
			}
		}
	}
	public static void appendSession(int year, String season, int session){
		Session s = new Session(year,season,session);
		ArrayList<ClassSession> sessions = new ArrayList();
		for(int i = 300; i < 400;i++){
			sessions.add(appendClassSession(s,"Test ROOM: "+i));
		}
		roomsAccess.put(s,sessions);
		collections.add(s);
	}
	public static ClassSession appendClassSession(Session se,String clss){
		ClassSession s = new ClassSession(se,clss);
		ArrayList<DateTimeRow> dtr = new ArrayList();
		for(int i = 16;i < 40;i++){
			dtr.add(new DateTimeRow(s,i));
		}
		dateTimeRow.put(s, dtr);
		return s;
	}
	
	/*
	 * Sample direct click
	 */
	public static ResourceList<Session> getClassSessions(){
		return new ResourceList(collections);
	}
	public static ClassWindowResourceBundle getClassSession(Session seasion){
		return new ClassWindowResourceBundle(roomsAccess.get(seasion),seasion.getYear(),seasion.getSeason(),seasion.getSession());
	}
	public static ExcelWindowResourceBundle getDateTimeRow(ClassSession clss){
		return new ExcelWindowResourceBundle(dateTimeRow.get(clss),clss.getParent().getYear(),clss.getParent().getSeason(),clss.getParent().getSession(),clss.getRoom());
	}
	/*
	 * Sample searching
	 */
	public static Session findSession(int year, String season, int session){
		for(Session s:collections){
			if(s.getYear()==year && s.getSeason().equals(season) && s.getSession() == session){
				return s;
			}
		}
		//nothing found
		return null;
	}
	public static ClassSession findRoom(int year, String season, int session, String roomName){
		Session s = findSession(year,season,session);
		if(s!=null){
			for(ClassSession room:roomsAccess.get(s)){
				if(room.getRoom().equals(roomName)){
					return room;
				}
			}
		}
		//nothing found
		return null;
	}
	public static ClassWindowResourceBundle findAndWrapClassSessions(int year,String season, int session){
		Session s = findSession(year,season,session);
		if(s!=null){
			return getClassSession(s);
		}
		return null;
	}
	public static ExcelWindowResourceBundle findAndWrapDateTimeRow(int year,String season, int session, String roomName){
		ClassSession s = findRoom(year,season,session,roomName);
		if(s!=null){
			return getDateTimeRow(s);
		}
		return null;
	}
	
}
