package com.cdarp.Transit.UI;

import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class ResourceList<T> extends ResourceBundle{
	private LinkedList<T> collections = new LinkedList<T>();
	
	public ResourceList(Collection<T> objs){
		this.collections.addAll(objs);
	}
	
	public LinkedList<T> getList(){
		return collections;
	}
	
	//Unused by code.
	@Override
	public Enumeration<String> getKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object handleGetObject(String key) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
