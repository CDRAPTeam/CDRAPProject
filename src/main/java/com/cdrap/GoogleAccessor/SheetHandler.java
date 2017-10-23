package com.cdrap.GoogleAccessor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.cdrap.transit.ClassData;
import com.cdrap.transit.ClassSession;
import com.cdrap.transit.Session;
import com.cdrap.transit.TimeData;

public class SheetHandler {
	public static final String SEASON_FALL_TAB = "Fall";
	public static final String SEASON_SPRING_TAB = "Spring";
	public static final String SEASON_SUMMER_TAB = "Summer";
	
	public static final String METADATA_PAGE = "Metadata";
	public static final int METADATA_MIN_X = 1;
	public static final int METADATA_MIN_Y = 1;
	public static final int METADATA_MAX_X = 5;
	public static final int METADATA_MAX_Y = 1;
	public static final int METADATA_SIZE_X = METADATA_MAX_X-METADATA_MIN_X+1;
	
	public static final int METADATA_LOCATION_YEAR = 0;
	public static final int METADATA_ROW = 0;
	public static final int METADATA_LOCATION_TEMPLATE_CHECK = 1;
	public static final int METADATA_LOCATION_SUMMER = 2;
	public static final int METADATA_LOCATION_FALL = 3;
	public static final int METADATA_LOCATION_SPRING = 4;
	
	public static final int CLASS_DATA_OFFSET_X = 1;//toget to first cell
	public static final int SPACE_BETWEEN_SETS_X = 2; //includes times on left
	public static final int DAYS_IN_WEEK = 7;
	public static final int TOTAL_SIZE_X = DAYS_IN_WEEK+SPACE_BETWEEN_SETS_X+CLASS_DATA_OFFSET_X;
	
	public static final int CLASS_DATA_OFFSET_Y = 2;
	public static final int EMPTY_SPACE_Y = 4;
	public static final int DATA_SIZE_Y = 95;
	public static final int TOTAL_SIZE_Y = DATA_SIZE_Y+EMPTY_SPACE_Y+CLASS_DATA_OFFSET_Y;
	
	public static final int CLASS_START_TIME = 28;
	public static final int DATA_ROLL_OVER_POINT = 96-28;
	public static final int NEXT_DAY_SLOT = 96;
	
	public static final String ROOMS_NUMBER_PAGE = "Room Numbers";
	
	private SheetsAccessor accessor;
	public SheetHandler() throws IOException{
		accessor = new SheetsAccessor();
	}
	//incomplete data
	public void sendToSheet(){
		
	}
	
	public SheetFileContainer checkFile(String id) throws IOException{
		try{
			LocationRequest request = new LocationRequest(METADATA_PAGE,METADATA_MIN_X,METADATA_MIN_Y,METADATA_MAX_X,METADATA_MAX_Y);
			List<List<String>> metadata = accessor.compileQueryItems(id,Arrays.asList(request)).get(0);
			if(metadata.size()== 1 && metadata.get(METADATA_ROW).size() == METADATA_SIZE_X){
				return new SheetFileContainer(id,
					Integer.parseInt(metadata.get(METADATA_ROW).get(METADATA_LOCATION_YEAR)),
					Integer.parseInt(metadata.get(METADATA_ROW).get(METADATA_LOCATION_SUMMER)),
					Integer.parseInt(metadata.get(METADATA_ROW).get(METADATA_LOCATION_FALL)),
					Integer.parseInt(metadata.get(METADATA_ROW).get(METADATA_LOCATION_SPRING)));
			}
		}
		catch(Exception ex){
			//something went wrong
		}
		return null;
	}
	public ArrayList<ClassSession> getClassFragments(String fileid) throws IOException{
		return getSessions(fileid,null);
	}
	public ArrayList<ClassSession> getSessions(String fileid,Session parent) throws IOException{
		LocationRequest request = new LocationRequest(ROOMS_NUMBER_PAGE,0,1,0,100);
		List<List<String>> ret = accessor.queryItems(fileid, Arrays.asList(Utils.convertToPosition(request))).get(0);
		ArrayList<ClassSession> sessions = new ArrayList();
		int i = 0;
		for(List<String>r: ret){
			sessions.add(new ClassSession(parent,r.get(0),i));
			i++;
		}
		return sessions;
	}
	public ArrayList<TimeData> getTimeData(String fileid,String season,int session, int day,int startTime,int timeFrame) throws IOException{
		ArrayList<TimeData> ret = new ArrayList();
		ArrayList<ClassSession> tempRoom = getClassFragments(fileid);
		LinkedList<LocationRequest> toRequest = new LinkedList();
		
		//ArrayList<LocationRequest> headers = new ArrayList();
		
		/*if(startTime < DATA_ROLL_OVER_POINT && DATA_ROLL_OVER_POINT < startTime+timeFrame){
			throw new RuntimeException("handling of data needs to be asked about here");
		}
		else if(startTime < NEXT_DAY_SLOT && NEXT_DAY_SLOT <startTime+timeFrame){
			//ask question about time fragment
			throw new RuntimeException("handling of data needs to be asked about here");
		}
		else{*/
			LocationRequest header = new LocationRequest(season,
					(CLASS_DATA_OFFSET_X+day)+((session-1)*TOTAL_SIZE_X),
					CLASS_DATA_OFFSET_Y+(startTime-CLASS_START_TIME+NEXT_DAY_SLOT)%NEXT_DAY_SLOT,
					(CLASS_DATA_OFFSET_X+day)+((session-1)*TOTAL_SIZE_X),
					CLASS_DATA_OFFSET_Y+(startTime-CLASS_START_TIME+NEXT_DAY_SLOT)%NEXT_DAY_SLOT+timeFrame);
			//headers.add(request);
		//}
		
		
		int shift = 0;
		for(ClassSession room:tempRoom){
			LocationRequest copy = header.createCopy();
			copy.shiftY(TOTAL_SIZE_Y*shift);
			toRequest.add(copy);
			shift++;
		}
		ArrayList<List<List<String>>> items = accessor.compileQueryItems(fileid, toRequest);
		
		shift = 0;
		//Major edit may be needed her for split
		for(List<List<String>> list:items){
			ArrayList<String> fragment = new ArrayList();
			int i = 0;
			if(list !=null){
				for(; i < list.size();i++){
					if(list.get(i).size()!=0){
						fragment.add(list.get(i).get(0));
					}
					else{
						fragment.add("");
					}
				}
			}
			for(;i<timeFrame;i++){
				fragment.add("");
			}
			ret.add(new TimeData(tempRoom.get(shift).getRoom(),fragment.toArray(new String[fragment.size()])));
			shift++;
		}
		return ret;
	}
	public ArrayList<ClassData> getClassData(String id,ClassSession parent) throws IOException{
		ArrayList<ClassData> ret = new ArrayList();
		LocationRequest lr = new LocationRequest(parent.getParent().getSeason(),CLASS_DATA_OFFSET_X,CLASS_DATA_OFFSET_Y,CLASS_DATA_OFFSET_X+DAYS_IN_WEEK-1,CLASS_DATA_OFFSET_Y+DATA_SIZE_Y);
		lr.shiftY((parent.getYPointer())*TOTAL_SIZE_Y);
		lr.shiftX((parent.getParent().getSession()-1)*TOTAL_SIZE_X);//offby one error, session 1 is in pointer location of the array @ 0.
		List<List<String>> data = accessor.queryItems(id, Arrays.asList(Utils.convertToPosition(lr))).get(0);
		int i = CLASS_START_TIME;
		int j = 0;
		//if no data exists, skip this step.
		if(data != null && !data.isEmpty()){
			for(List<String> strings:data){
				String monday = "", tuesday = "", wednsday = "", thursday = "", friday = "", saturday = "", sunday = "";
				switch(strings.size()){
					case 7:
						sunday = strings.get(6);
					case 6:
						saturday = strings.get(5);
					case 5:
						friday = strings.get(4);
					case 4:
						thursday = strings.get(3);
					case 3:
						wednsday = strings.get(2);
					case 2:
						tuesday = strings.get(1);
					case 1:
						monday = strings.get(0);
						break;
				}
				ret.add(new ClassData(i,monday,tuesday,wednsday,thursday,friday,saturday,sunday));
				i++;
				j++;
				i%=NEXT_DAY_SLOT;
			}
		}
		for(;j<=DATA_SIZE_Y;j++,i++){
			i%=NEXT_DAY_SLOT;
			ret.add(new ClassData(i,"","","","","","",""));
		}
		return ret;
	}
}
