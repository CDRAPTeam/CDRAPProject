package com.cdrap.transit;

public class TimeData {
	private String[] elements;
	private String room;
	
	public TimeData(String room, String... elements){
		this.room = room;
		this.elements = elements;
	}
	
	public String[] getElements() {
		return elements;
	}
	public void setElements(String[] elements) {
		this.elements = elements;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}
	
}
