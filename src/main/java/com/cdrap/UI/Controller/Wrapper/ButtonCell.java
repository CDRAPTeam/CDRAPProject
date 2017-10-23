package com.cdrap.UI.Controller.Wrapper;

import com.cdrap.UI.Controller.Wrapper.ComboBoxCell.StringConverter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.*;

public class ButtonCell <T> extends TableCell<T,T>{
	//internal boxf
	
	private final Button btn = new Button();
	private final ButtonText bt;
	T item;
	public ButtonCell(ButtonText<T> reader, EventHandler<? super MouseEvent> handler){
		bt = reader;
		btn.setOnMouseClicked(handler);
		
		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setPrefWidth(Double.MAX_VALUE);
	}
	public ButtonCell(ButtonText<T> reader, OnClick<T> click){
		bt = reader;
		btn.setOnMouseClicked((e)->{
			click.onClick(item);
		});
		
		btn.setMaxWidth(Double.MAX_VALUE);
		btn.setPrefWidth(Double.MAX_VALUE);
	}
	
	@Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setGraphic(btn);
            setText(null);
            btn.setText(bt.getText(item));
            this.item = item;
        } else {
            setGraphic(null);
            setText(null);
            this.item = null;
        }
        
    }
	public static interface ButtonText<T>{
		String getText(T item);
	}
	public interface OnClick<T>{
		void onClick(T item);
	}
}
