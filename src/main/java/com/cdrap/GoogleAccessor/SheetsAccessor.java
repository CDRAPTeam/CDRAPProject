package com.cdrap.GoogleAccessor;

import java.io.IOException;
import java.util.*;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.Sheets.Spreadsheets.Values.BatchGet;
import com.google.api.services.sheets.v4.model.BatchGetValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

public class SheetsAccessor {
	
	private Sheets handler;
	public SheetsAccessor() throws IOException{
		handler = DriveEssentials.getSheetsService();
	}
	public synchronized ArrayList<List<List<String>>> compileQueryItems(String id, List<LocationRequest> requests) throws IOException{
		ArrayList<String> items = new ArrayList(requests.size());
		for(LocationRequest request:requests){
			items.add(Utils.convertToPosition(request));
		}
		return queryItems(id,items);
	}
	public ArrayList<List<List<String>>> queryItems(String id, List<String> PositionRequest) throws IOException{
		BatchGet get = handler.spreadsheets().values().batchGet(id)
			.setRanges(PositionRequest);
		BatchGetValuesResponse ret = get.execute();
		
		ArrayList<List<List<String>>> returned = new ArrayList<>(PositionRequest.size());
		//trick to get around certain immediate java checking, its the same list but the pointer is playing dumb. google api should be string behind the scenes.
		@SuppressWarnings(value = { "sneaking around silly requirements when google api behind the scenes is using string" })
		List unsafe = returned;
		for(ValueRange vr:ret.getValueRanges()){
			unsafe.add(vr.getValues());
		}
		return returned;
	}
}
