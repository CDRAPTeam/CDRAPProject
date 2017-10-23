package com.cdrap.GoogleAccessor;

public class LocationRequest {
	private int x1,x2,y1,y2;
	private String sheet;
	public LocationRequest(String sheet, int x1,int y1, int x2, int y2){
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.sheet = sheet;
	}
	
	public void shiftX(int x){
		x1+=x;
		x2+=x;
	}
	public void shiftY(int y){
		y1+=y;
		y2+=y;
	}
	public int getX1() {
		return x1;
	}
	public void setX1(int x1) {
		this.x1 = x1;
	}
	public int getX2() {
		return x2;
	}
	public void setX2(int x2) {
		this.x2 = x2;
	}
	public int getY1() {
		return y1;
	}
	public void setY1(int y1) {
		this.y1 = y1;
	}
	public int getY2() {
		return y2;
	}
	public void setY2(int y2) {
		this.y2 = y2;
	}
	public String getSheet() {
		return sheet;
	}
	public void setSheet(String sheet) {
		this.sheet = sheet;
	}
	public LocationRequest createCopy(){
		return new LocationRequest(sheet,x1,y1,x2,y2);
	}
	
}
