/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdarp.Transit;

/**
 *
 * @author Navnik
 */
public class ClassSession {
	private Session parent;
    private String room;

    public ClassSession(Session parent,String room) {
        this.room = room;
        this.parent = parent;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
    public Session getParent(){
    	return parent;
    }
    
}
