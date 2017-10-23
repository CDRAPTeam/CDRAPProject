package com.cdrap.GoogleAccessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import com.cdrap.UI.Controller.Wrapper.ButtonCell.OnClick;
import com.cdrap.UI.FXHandler;
import com.cdrap.UI.Controller.Wrapper.ButtonCell;
import com.cdrap.transit.ClassData;
import com.cdrap.transit.ClassSession;
import com.cdrap.transit.Session;
import com.cdrap.transit.TimeData;
import com.cdrap.transit.Callback.StringReturnCallback;
import com.cdrap.transit.UI.CustomGridWindowBundle;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class DriveHandler {
	private static ArrayList<SheetFileContainer> containers = new ArrayList();
	private static SheetHandler handler;
	private static DriveAccessor accessor;
	//https://drive.google.com/open?id=1l8jbc96t7QNQ3BDA9TpBmM88wN4ZChkUrdCFaS098NE
	static{
		try {
			handler = new SheetHandler();
			accessor = new DriveAccessor(handler);
			refreshSheets();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 
	 * for future file deciding, and selecting season and session
	 */
	public static void refreshSheets() throws IOException{
		containers.clear();
		containers.addAll(accessor.getFileReferences());
	}
	public static CustomGridWindowBundle createDataFormatBundle(int year, String season, int session,String day, int start, int timeFrame) throws IOException{
		ArrayList<TimeData> data = new ArrayList();
		int selectedDay;
		switch(day.toLowerCase()){
			case "monday":
				selectedDay = 0;
				break;
			case "tuesday":
				selectedDay = 1;
				break;
			case "wednesday":
				selectedDay = 2;
				break;
			case "thursday":
				selectedDay = 3;
				break;
			case "friday":
				selectedDay = 4;
				break;
			case "saturday":
				selectedDay = 5;
				break;
			case "sunday":
			default:
				selectedDay = 6;
		}
		
		for(SheetFileContainer file:DriveHandler.getFileContainers(year)){
			data.addAll(handler.getTimeData(file.getFileid(), season, session, selectedDay, start, timeFrame));
		}
		//LocationRequest lr = new LocationRequest(parent.getParent().getSeason(),CLASS_DATA_OFFSET_X,CLASS_DATA_OFFSET_Y,CLASS_DATA_OFFSET_X+DAYS_IN_WEEK-1,CLASS_DATA_OFFSET_Y+DATA_SIZE_Y);
		//lr.shiftY((parent.getYPointer())*TOTAL_SIZE_Y);
		//lr.shiftX((parent.getParent().getSession()-1)*TOTAL_SIZE_X);//offby one error, session 1 is in pointer location of the array @ 0.
		CustomGridWindowBundle<TimeData> bundle = new CustomGridWindowBundle(data,new String[]{"Year:"+year,"Season:"+season,"Session:"+session,"Day:"+day});
		bundle.addColumn("Room",.2, new Callback<TableColumn<TimeData,TimeData>,TableCell<TimeData,TimeData>>(){
			@Override
			public TableCell<TimeData,TimeData> call(TableColumn<TimeData,TimeData> arg0) {
				// TODO Auto-generated method stub
				TableCell<TimeData,TimeData> c = new TableCell<TimeData,TimeData>(){
					//Session target;
					@Override
                    public void updateItem(TimeData item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                        	setText(item.getRoom());
                            //setText(""+item.getYear());
                            //target = item;
                        }
                        else{
                        	//target = null;
                        }
                        //setText(empty ? null : getString());
                        //setStyle("-fx-background-color:"+getString());
                    }
				};
				
				return c;
			}
		});
		bundle.setOnReturn(()->{try {
			FXHandler.handle.switchStage(FXHandler.FX_PAGE_CUSTOM_SHEET_WINDOW,DriveHandler.getYearSeasonSessionSelector());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}});
		for(int i =0; i < timeFrame;i++){
			//loosly stored J for future reference in table.
			final int j = i;
			bundle.addColumn(""+ClassData.convertIntoMilitaryTime(start+i),.2, new Callback<TableColumn<TimeData,TimeData>,TableCell<TimeData,TimeData>>(){
				@Override
				public TableCell<TimeData,TimeData> call(TableColumn<TimeData,TimeData> arg0) {
					// TODO Auto-generated method stub
					TableCell<TimeData,TimeData> c = new TableCell<TimeData,TimeData>(){
						//Session target;
						@Override
	                    public void updateItem(TimeData item, boolean empty) {
	                        super.updateItem(item, empty);
	                        if(!empty){
	                        	setText(item.getElements()[j]);
	                            //setText(""+item.getYear());
	                            //target = item;
	                        }
	                        else{
	                        	//target = null;
	                        }
	                        //setText(empty ? null : getString());
	                        //setStyle("-fx-background-color:"+getString());
	                    }
					};
					
					return c;
				}
			});
			
		}
		return bundle;
	}
	public static void main(String...strings) throws IOException{
		createDataFormatBundle(2017,"Fall",1,"monday",28,5);
	}
	public static void searchSwitch(){
		
	}
	
	public static CustomGridWindowBundle getYearSeasonSessionSelector(){
		ArrayList<Session> ret = new ArrayList();
		for(SheetFileContainer c:containers){
			//fall stuff
			for(int i = 1; i <= c.getFallSessions();i++){
				ret.add(new Session(c.getFileid(),c.getYear(),SheetHandler.SEASON_FALL_TAB,i));
			}
			//Spring
			for(int i = 1; i <= c.getSpringSessions();i++){
				ret.add(new Session(c.getFileid(),c.getYear(),SheetHandler.SEASON_SPRING_TAB,i));
			}
			//summer
			for(int i = 1; i <= c.getSummerSessions();i++){
				ret.add(new Session(c.getFileid(),c.getYear(),SheetHandler.SEASON_SUMMER_TAB,i));
			}
		}
		
		CustomGridWindowBundle<Session> b = new CustomGridWindowBundle(ret,new String[]{"Year/File Selection"});
		b.addButton("Stub button", (d)->{});
		b.addColumn("Year",.295,new Callback<TableColumn<Session,Session>,TableCell<Session,Session>>(){
			@Override
			public TableCell<Session,Session> call(TableColumn<Session,Session> arg0) {
				// TODO Auto-generated method stub
				TableCell c = new TableCell<Session,Session>(){
					Session target;
					@Override
                    public void updateItem(Session item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            setText(""+item.getYear());
                            target = item;
                        }
                        else{
                        	target = null;
                        }
                        //setText(empty ? null : getString());
                        //setStyle("-fx-background-color:"+getString());
                    }
				};
				
				return c;
			}
		});
		b.addColumn("Season",.295,new Callback<TableColumn<Session,Session>,TableCell<Session,Session>>(){
			@Override
			public TableCell<Session,Session> call(TableColumn<Session,Session> arg0) {
				// TODO Auto-generated method stub
				TableCell c = new TableCell<Session,Session>(){
					Session target;
					@Override
                    public void updateItem(Session item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            setText(""+item.getSeason());
                            target = item;
                        }
                        else{
                        	target = null;
                        }
                        //setText(empty ? null : getString());
                        //setStyle("-fx-background-color:"+getString());
                    }
				};
				
				return c;
			}
		});
		b.addColumn("Session",.295,new Callback<TableColumn<Session,Session>,TableCell<Session,Session>>(){
			@Override
			public TableCell<Session,Session> call(TableColumn<Session,Session> arg0) {
				// TODO Auto-generated method stub
				TableCell c = new TableCell<Session,Session>(){
					Session target;
					@Override
                    public void updateItem(Session item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            setText(""+item.getSession());
                            target = item;
                        }
                        else{
                        	target = null;
                        }
                        //setText(empty ? null : getString());
                        //setStyle("-fx-background-color:"+getString());
                    }
				};
				
				return c;
			}
		});
		b.addColumn("GOTO",.1,new Callback<TableColumn<Session,Session>,TableCell<Session,Session>>(){
			@Override
			public TableCell<Session,Session> call(TableColumn<Session,Session> arg0) {
				// TODO Auto-generated method stub
				ButtonCell<Session> c = new ButtonCell((text)->{return "navigate to";},new OnClick<Session>(){

					@Override
					public void onClick(Session item) {
						prepareSwitch(item);
					}});
				return c;
			}
		});
		
		return b;
	}
	public static void prepareSwitch(Session s){
		try {
			FXHandler.handle.switchStage(FXHandler.FX_PAGE_CUSTOM_SHEET_WINDOW, getClassSelector(s));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static CustomGridWindowBundle getClassSelector(Session parent) throws IOException{
		//dataset
		ArrayList<ClassSession> sessions = handler.getSessions(parent.getFileID(), parent);
		CustomGridWindowBundle<ClassSession> b = new CustomGridWindowBundle(sessions, new String[]{"Year:"+parent.getYear(),"Season:"+parent.getSeason(),"Session:"+parent.getSession()});
		b.setOnReturn(()->{

	        try {
				FXHandler.handle.switchStage(FXHandler.FX_PAGE_CUSTOM_SHEET_WINDOW,DriveHandler.getYearSeasonSessionSelector());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
		b.addButton("Stub button", (d)->{});
		b.addColumn("room",.890,new Callback<TableColumn<ClassSession,ClassSession>,TableCell<ClassSession,ClassSession>>(){
			@Override
			public TableCell<ClassSession,ClassSession> call(TableColumn<ClassSession,ClassSession> arg0) {
				// TODO Auto-generated method stub
				TableCell c = new TableCell<ClassSession,ClassSession>(){
					ClassSession target;
					@Override
                    public void updateItem(ClassSession item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            setText(""+item.getRoom());
                            target = item;
                        }
                        else{
                        	target = null;
                        }
                        //setText(empty ? null : getString());
                        //setStyle("-fx-background-color:"+getString());
                    }
				};
				
				return c;
			}
		});
		b.addColumn("GOTO",.1,new Callback<TableColumn<ClassSession,ClassSession>,TableCell<ClassSession,ClassSession>>(){
			@Override
			public TableCell<ClassSession,ClassSession> call(TableColumn<ClassSession,ClassSession> arg0) {
				// TODO Auto-generated method stub
				ButtonCell<ClassSession> c = new ButtonCell((text)->{return "navigate to";},new OnClick<ClassSession>(){
					@Override
					public void onClick(ClassSession item) {
						prepareSwitch(item);
					}});
				return c;
			}
		});
		return b;
	}
	public static void prepareSwitch(ClassSession session){
		try {
			FXHandler.handle.switchStage(FXHandler.FX_PAGE_CUSTOM_SHEET_WINDOW,getClassData(session));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ArrayList<String> getClassData(String fileid) throws IOException{
		ArrayList<String> ret = new ArrayList();
		ArrayList<ClassSession> classes = handler.getClassFragments(fileid);
		for(ClassSession s:classes){
			ret.add(s.getRoom());
		}
		return ret;
	}
	private static ArrayList<SheetFileContainer> getFileContainers(int year){
		ArrayList<SheetFileContainer> container = new ArrayList();
		for(SheetFileContainer c:containers){
			if(c.getYear() == year){
				container.add(c);
			}
		}
		return container;
	}
	public static ArrayList<Session> generateSession(int year, String season, int session){
		ArrayList<Session> ret = new ArrayList();
		for(SheetFileContainer container:getFileContainers(year)){
			switch(season){
				case SheetHandler.SEASON_FALL_TAB:
					if(container.getFallSessions() <= session){
						ret.add(new Session(container.getFileid(),year,season,session));
						
					}
					break;
				case SheetHandler.SEASON_SPRING_TAB:
					if(container.getSpringSessions() <= session){
						ret.add(new Session(container.getFileid(),year,season,session));
					}
					break;
				case SheetHandler.SEASON_SUMMER_TAB:
					if(container.getSummerSessions() <= session){
						ret.add(new Session(container.getFileid(),year,season,session));
					}
					break;
			}
		}
		return ret;
		
	}
	public static void quickGuess(int year,StringReturnCallback run){
		new Thread(){
			public void run(){
				ArrayList<SheetFileContainer> files = getFileContainers(year);
				HashSet<String> ret = new HashSet();
				for(SheetFileContainer c:files){
					try {
						ret.addAll(getClassData(c.getFileid()));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				run.callback(ret);
			}
		}.start();
	}
	public static CustomGridWindowBundle getClassData(int year, String season,int session, String classBox) throws IOException{
		//todo, year and file selection
		//if(year == 2018){
		ArrayList<Session> numSession = generateSession(year,season,session);
		if(!numSession.isEmpty()){
			Session s;
			if(numSession.size() > 1){
				throw new RuntimeException("new code needed here, multiple year fragments of the same year detected");
			}
			else{
				s = numSession.get(0);
			}
			ArrayList<ClassSession> sessions = handler.getClassFragments(s.getFileID());
			for(int i = 0; i < sessions.size();i++){
				if(sessions.get(i).getRoom().equals(classBox)){
					return getClassData(new ClassSession(s,classBox,i));
				}
			}
			//ClassSession s = new ClassSession(s,classBox);
		}
		//nope
		return null;
	}
	public static CustomGridWindowBundle getClassData(ClassSession parent) throws IOException{
		ArrayList<ClassData> data = handler.getClassData(parent.getParent().getFileID(), parent);
		CustomGridWindowBundle<ClassData> b = new CustomGridWindowBundle(data,new String[]{"Year:"+parent.getParent().getYear(),"Season:"+parent.getParent().getSeason(),"Session:"+parent.getParent().getSession(),"Room:"+parent.getRoom()});
		b.setOnReturn(()->{prepareSwitch(parent.getParent());});
		b.addButton("Stub button", (d)->{});
		b.addColumn("Time",.124,new Callback<TableColumn<ClassData,ClassData>,TableCell<ClassData,ClassData>>(){
			@Override
			public TableCell<ClassData,ClassData> call(TableColumn<ClassData,ClassData> arg0) {
				// TODO Auto-generated method stub
				TableCell c = new TableCell<ClassData,ClassData>(){
					ClassData target;
					@Override
                    public void updateItem(ClassData item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            setText(""+item.getMilitaryTime());
                            target = item;
                            super.setOnMouseClicked((evt)->{
                            	if(target!=null){
									//prepareSwitch(target);
								}
                            });
                        }
                        else{
                        	target = null;
                        }
                        //setText(empty ? null : getString());
                        //setStyle("-fx-background-color:"+getString());
                    }
				};
				
				return c;
			}
		});
		b.addColumn("Monday",.124,new Callback<TableColumn<ClassData,ClassData>,TableCell<ClassData,ClassData>>(){
			@Override
			public TableCell<ClassData,ClassData> call(TableColumn<ClassData,ClassData> arg0) {
				// TODO Auto-generated method stub
				TableCell c = new TableCell<ClassData,ClassData>(){
					ClassData target;
					@Override
                    public void updateItem(ClassData item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            setText(""+item.getMonday());
                            target = item;
                            super.setOnMouseClicked((evt)->{
                            	if(target!=null){
									//prepareSwitch(target);
								}
                            });
                        }
                        else{
                        	target = null;
                        }
                        //setText(empty ? null : getString());
                        //setStyle("-fx-background-color:"+getString());
                    }
				};
				
				return c;
			}
		});
		b.addColumn("Tuesday",.124,new Callback<TableColumn<ClassData,ClassData>,TableCell<ClassData,ClassData>>(){
			@Override
			public TableCell<ClassData,ClassData> call(TableColumn<ClassData,ClassData> arg0) {
				// TODO Auto-generated method stub
				TableCell c = new TableCell<ClassData,ClassData>(){
					ClassData target;
					@Override
                    public void updateItem(ClassData item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            setText(""+item.getTuesday());
                            target = item;
                            super.setOnMouseClicked((evt)->{
                            	if(target!=null){
									//prepareSwitch(target);
								}
                            });
                        }
                        else{
                        	target = null;
                        }
                        //setText(empty ? null : getString());
                        //setStyle("-fx-background-color:"+getString());
                    }
				};
				
				return c;
			}
		});
		b.addColumn("Wednesday",.124,new Callback<TableColumn<ClassData,ClassData>,TableCell<ClassData,ClassData>>(){
			@Override
			public TableCell<ClassData,ClassData> call(TableColumn<ClassData,ClassData> arg0) {
				// TODO Auto-generated method stub
				TableCell c = new TableCell<ClassData,ClassData>(){
					ClassData target;
					@Override
                    public void updateItem(ClassData item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            setText(""+item.getWendsday());
                            target = item;
                            super.setOnMouseClicked((evt)->{
                            	if(target!=null){
									//prepareSwitch(target);
								}
                            });
                        }
                        else{
                        	target = null;
                        }
                        //setText(empty ? null : getString());
                        //setStyle("-fx-background-color:"+getString());
                    }
				};
				
				return c;
			}
		});
		b.addColumn("Thursday",.124,new Callback<TableColumn<ClassData,ClassData>,TableCell<ClassData,ClassData>>(){
			@Override
			public TableCell<ClassData,ClassData> call(TableColumn<ClassData,ClassData> arg0) {
				// TODO Auto-generated method stub
				TableCell c = new TableCell<ClassData,ClassData>(){
					ClassData target;
					@Override
                    public void updateItem(ClassData item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            setText(""+item.getThursday());
                            target = item;
                            super.setOnMouseClicked((evt)->{
                            	if(target!=null){
									//prepareSwitch(target);
								}
                            });
                        }
                        else{
                        	target = null;
                        }
                        //setText(empty ? null : getString());
                        //setStyle("-fx-background-color:"+getString());
                    }
				};
				
				return c;
			}
		});
		b.addColumn("Friday",.124,new Callback<TableColumn<ClassData,ClassData>,TableCell<ClassData,ClassData>>(){
			@Override
			public TableCell<ClassData,ClassData> call(TableColumn<ClassData,ClassData> arg0) {
				// TODO Auto-generated method stub
				TableCell c = new TableCell<ClassData,ClassData>(){
					ClassData target;
					@Override
                    public void updateItem(ClassData item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            setText(""+item.getFriday());
                            target = item;
                            super.setOnMouseClicked((evt)->{
                            	if(target!=null){
									//prepareSwitch(target);
								}
                            });
                        }
                        else{
                        	target = null;
                        }
                        //setText(empty ? null : getString());
                        //setStyle("-fx-background-color:"+getString());
                    }
				};
				
				return c;
			}
		});
		b.addColumn("Saturday",.124,new Callback<TableColumn<ClassData,ClassData>,TableCell<ClassData,ClassData>>(){
			@Override
			public TableCell<ClassData,ClassData> call(TableColumn<ClassData,ClassData> arg0) {
				// TODO Auto-generated method stub
				TableCell c = new TableCell<ClassData,ClassData>(){
					ClassData target;
					@Override
                    public void updateItem(ClassData item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            setText(""+item.getSaturday());
                            target = item;
                            super.setOnMouseClicked((evt)->{
                            	if(target!=null){
									//prepareSwitch(target);
								}
                            });
                        }
                        else{
                        	target = null;
                        }
                        //setText(empty ? null : getString());
                        //setStyle("-fx-background-color:"+getString());
                    }
				};
				
				return c;
			}
		});
		b.addColumn("Sunday",.124,new Callback<TableColumn<ClassData,ClassData>,TableCell<ClassData,ClassData>>(){
			@Override
			public TableCell<ClassData,ClassData> call(TableColumn<ClassData,ClassData> arg0) {
				// TODO Auto-generated method stub
				TableCell c = new TableCell<ClassData,ClassData>(){
					ClassData target;
					@Override
                    public void updateItem(ClassData item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            setText(""+item.getSunday());
                            target = item;
                            super.setOnMouseClicked((evt)->{
                            	if(target!=null){
									//prepareSwitch(target);
								}
                            });
                        }
                        else{
                        	target = null;
                        }
                        //setText(empty ? null : getString());
                        //setStyle("-fx-background-color:"+getString());
                    }
				};
				
				return c;
			}
		});
		return b;
	}
}
