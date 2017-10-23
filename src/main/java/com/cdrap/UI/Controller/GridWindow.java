package com.cdrap.UI.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cdrap.UI.FXHandler;
import com.cdrap.UI.FXUtils;
import com.cdrap.transit.Session;
import com.cdrap.transit.UI.CustomGridWindowBundle;
import com.cdrap.transit.UI.ResourceList;
import com.cdrap.transit.UI.CustomGridWindowBundle.ButtonHolder;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.util.Callback;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

public class GridWindow<T> implements Initializable{
	@FXML
    private HBox HeaderText;
    @FXML
    private TableView<T> TableViewer;
    @FXML
    private HBox ButtonsContainers;
    private ArrayList<ChangingWidthColumn<T>> columns = new ArrayList();
    private ArrayList<Button> actions = new ArrayList<>();
    private OnReturn toReturn;
    //navigation buttons
    
    
    //to be streamlined in the future
    @FXML
    private void onNewDocument(ActionEvent event) {
        //try {
           // FXHandler.handle.switchStage("com/cdarp/UI/FXML/ExcelWindow.fxml",new ResourceList(Collections.emptyList()));
       // } catch (IOException ex) {
       //     Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
       // }
    }
    //to be removed in future release
    @FXML
    private void onReturnClick(ActionEvent event){
    	if(this.toReturn!=null){
    		toReturn.onReturn();
    	}
    }
    @FXML
    private void onSearchByTime(ActionEvent event){
    	try {
			FXHandler.handle.performPopup(FXHandler.FX_POPUP_DIR_SEARCH_BY_TIME_WINDOW_DEFAULT, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @FXML
    private void onSearchByClass(ActionEvent event){
    	try {
			FXHandler.handle.performPopup(FXHandler.FX_POPUP_DIR_SEARCH_BY_CLASS_WINDOW_DEFAULT, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    	CustomGridWindowBundle<T> bundle = (CustomGridWindowBundle) rb;
    	this.HeaderText.getChildren().clear();
    	this.ButtonsContainers.getChildren().clear();
        for(CustomGridWindowBundle<T>.ButtonHolder<T> b:bundle.getButtonHolders()){
        	this.addButton(b.getText(), b.getClick());
        }
        for(CustomGridWindowBundle<T>.ColumnHolder<T> b:bundle.getColumnsHolders()){
        	this.addColumn(b.getTopText(),b.getWeight(),b.getCallback());
        }
        this.setItems(bundle.getList());
        this.TableViewer.widthProperty().addListener((ob,oldVal,newVal)->{resizeEvent(newVal.doubleValue());});
        Platform.runLater(()->{
        	resizeEvent();
        });
        this.toReturn = bundle.getReturn();
        
        for(String text:bundle.getHeaderText()){
        	addHeaderText(text);
        }
    }    
    private void addHeaderText(String text){
    	TextField field = new TextField();
    	field.setPrefWidth(10000000);
    	field.setText(text);
    	field.setEditable(false);
    	this.HeaderText.getChildren().add(field);
    }
    private void addButton(String text,OnClick<T> evt){
    	Button b = new Button();
    	b.setPrefWidth(10000000);
    	b.setText(text);
    	b.setOnMouseClicked((e)->{evt.onClick(this.TableViewer.getItems());});
    	this.ButtonsContainers.getChildren().add(b);
    }
    private void addColumn(String columnName,double weight, Callback<TableColumn<T,T>,TableCell<T,T>> factory){
    	ChangingWidthColumn<T> c = new ChangingWidthColumn(weight);
    	c.setText(columnName);
    	//c.set
    	this.columns.add(c);
    	FXUtils.setupTableColumnView(c, factory);
    	resizeEvent();
    	this.TableViewer.getColumns().add(c);
        this.TableViewer.refresh();
    }
    private void resizeEvent(){
    	resizeEvent(this.TableViewer.getWidth());
    }
    private void resizeEvent(double size){
    	for(int i = 0; i < columns.size();i++){
    		columns.get(i).setMinWidth(Math.min(size,size*columns.get(i).width));
    		columns.get(i).setPrefWidth(Math.min(size,size*columns.get(i).width));
    		columns.get(i).setMaxWidth(size);
    	}
    }
    public void setItems(Collection<T> items){
        ObservableList<T> l = FXCollections.observableArrayList(items);
        this.TableViewer.setItems(l);
    }
    public static interface OnClick<T>{
    	void onClick(List<T> items);
    }
    public static interface OnReturn<T>{
    	void onReturn();
    }
    private class ChangingWidthColumn<T> extends TableColumn<T,T>{
    	private double width;
    	public ChangingWidthColumn(double width){
    		this.width = width;
    	}
    }
}
