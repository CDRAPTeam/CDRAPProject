package com.cdrap.GoogleAccessor;

public class Utils {
	//character to do math on.
	public static final char CHARACTER_POS = 'A';
	public static final int MAX_CHARACTER = ('Z'-'A')+1;
	public static final String SHEET_SEPERATOR_TO_POSITION = "!";
	public static final String SHEET_SEPERATOR_POSITIONS = ":";
	private Utils(){
		
	}
	public static final String convertXToLetter(int n){
		//0 based calculation, converts to google based api positioning
		n++;
		//formula to get percise char array size needed
		char[] buf = new char[(int) Math.floor(Math.log(25*(n+1))/Math.log(26))];
		for(int i = buf.length-1;i >=0;i--){
			//reduce by 1 to remove off by one error
			n--;
			//get asssossiated character with slot
			buf[i] = (char) (CHARACTER_POS + n%MAX_CHARACTER);
			//divide down.
			n /= MAX_CHARACTER;
		}
		//return as string
		return new String(buf);
	}
	public static String toStringY(int y){
		//0 based calculation, converts to google sheets api positioning
		return ""+(y+1);
	}
	public static String convertToPosition(int x, int y){
		//super basic math
		return ""+convertXToLetter(x)+toStringY(y);
	}
	public static String convertToPosition(String sheet,int x, int y){
		return sheet+SHEET_SEPERATOR_TO_POSITION+convertToPosition(x,y);
	}
	public static String convertToPosition(String sheet,int x1, int y1, int x2, int y2){
		return convertToPosition(sheet,x1,y1)+SHEET_SEPERATOR_POSITIONS+convertToPosition(x2,y2);
	}
	public static String convertToPosition(LocationRequest request){
		System.out.println(convertToPosition(request.getSheet(),request.getX1(),request.getY1(),request.getX2(),request.getY2()));
		return convertToPosition(request.getSheet(),request.getX1(),request.getY1(),request.getX2(),request.getY2());
	}
}
