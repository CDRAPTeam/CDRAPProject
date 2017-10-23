package com.cdrap.transit.UI;

import java.util.ArrayList;
import java.util.Collection;

import com.cdrap.UI.Controller.GridWindow.OnClick;
import com.cdrap.UI.Controller.GridWindow.OnReturn;
import com.cdrap.transit.UI.CustomGridWindowBundle.ButtonHolder;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class CustomGridWindowBundle<T> extends ResourceList<T>{
	private ArrayList<ColumnHolder<T>> columns = new ArrayList();
	private ArrayList<ButtonHolder<T>> buttons = new ArrayList();
	private OnReturn returnables;
	private final String[] HeaderText;
	
	public CustomGridWindowBundle(Collection<T> objs,String[] headerText) {
		super(objs);
		this.HeaderText = headerText;
		// TODO Auto-generated constructor stub
	}
	public String[] getHeaderText(){
		return HeaderText;
	}
	
	public void addColumn(String text,double weight, Callback<TableColumn<T,T>,TableCell<T,T>> call){
		this.columns.add(new ColumnHolder(text,weight,call));
	}
	public void addButton(String text, OnClick<T> click){
		this.buttons.add(new ButtonHolder(text,click));
	}
	public ArrayList<ColumnHolder<T>> getColumnsHolders(){
		return this.columns;
	}
	public ArrayList<ButtonHolder<T>> getButtonHolders(){
		return this.buttons;
	}
	public OnReturn getReturn(){
		return returnables;
	}
	public void setOnReturn(OnReturn returner){
		this.returnables = returner;
	}
	public class ColumnHolder<T>{
		private String topText;
		private double weight;
		private Callback<TableColumn<T,T>,TableCell<T,T>> callback;
		public ColumnHolder(String text,double weight, Callback<TableColumn<T,T>,TableCell<T,T>> call){
			this.callback = call;
			this.topText = text;
			this.weight = weight;
		}
		public String getTopText() {
			return topText;
		}
		public double getWeight(){
			return weight;
		}
		public void setTopText(String topText) {
			this.topText = topText;
		}
		public Callback<TableColumn<T, T>, TableCell<T, T>> getCallback() {
			return callback;
		}
		public void setCallback(Callback<TableColumn<T, T>, TableCell<T, T>> callback) {
			this.callback = callback;
		}
	}
	public class ButtonHolder<T>{
		private String text;
		private OnClick<T> click;
		public ButtonHolder(String text, OnClick<T> click){
			this.text = text;
			this.click = click;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public OnClick<T> getClick() {
			return click;
		}
		public void setClick(OnClick<T> click) {
			this.click = click;
		}
	}
}
