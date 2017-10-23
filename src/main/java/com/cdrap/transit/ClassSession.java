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
public class ClassSession {
	private Session parent;
    private String room;
    private int YPointer = -1;
    public ClassSession(Session parent,String room,int yPointer) {
        this.room = room;
        this.parent = parent;
        this.YPointer = yPointer;
    }
    public String getRoom() {
        return room;
    }
    public int getYPointer(){
    	return YPointer;
    }
    public void setRoom(String room) {
        this.room = room;
    }
    public Session getParent(){
    	return parent;
    }
    
}
