package com.cdarp.UI.Controller.Wrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.*;

public class ComboBoxCell<T> extends TableCell<T,T>{
	//internal box
	private ComboBox<String> box;
	//reader
	private StringConverter<T> stringConvert;
	
	public ComboBoxCell(StringConverter<T> c){
		this.stringConvert = c;
	}
	@Override
	public void startEdit(){
		super.startEdit();
		if(box == null){
			createComboBox();
		}

        setGraphic(box);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
	}
	@Override
    public void cancelEdit() {
		super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
        handleSelection();
        setText(this.stringConvert.getString(getItem()));
        //super.cancelEdit();
    }
	public void handleSelection(){
		String selected = box.getSelectionModel().getSelectedItem();
		if(selected !=null){
			this.stringConvert.onSelected(getItem(), selected);
		}
	}
	private void createComboBox() {
        // ClassesController.getLevelChoice() is the observable list of String
        box = new ComboBox<>(FXCollections.observableArrayList(this.stringConvert.getOptions(getItem())));
        box.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
        box.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.ESCAPE || t.getCode() == KeyCode.ENTER) {
                    //cancelEdit();
                	handleSelection();
                	commitEdit(getItem());
                }
            }
        });
    }
	public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (box != null) {
                	ObservableList<String> observableStrings = FXCollections.observableArrayList(
                			this.stringConvert.getOptions(
                					getItem()));
                	box.setItems(observableStrings);
                }
                setGraphic(box);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            } else {
                setText(this.stringConvert.getString(item));
                setContentDisplay(ContentDisplay.TEXT_ONLY);
            }
        }
    }
	public static interface StringConverter<T>{
		String getString(T t);
		String[] getOptions(T t);
		void onSelected(T base,String str);
	}
}
