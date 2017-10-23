/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdrap.UI.Controller.Popup;

import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import com.cdrap.GoogleAccessor.DriveHandler;
import com.cdrap.GoogleAccessor.SheetHandler;
import com.cdrap.UI.FXHandler;
import com.cdrap.transit.UI.CustomGridWindowBundle;

import javafx.scene.control.ComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;

/**
 * FXML Controller class
 *
 * @author Navnik
 */
public class SearchByClassController implements Initializable {

	@FXML
	private ComboBox<String> yearBox;
	@FXML
	private ComboBox<String> seasonBox;
	@FXML
	private ComboBox<String> sessionBox;
	@FXML
	private ComboBox<String> classBox;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    	yearBox.getItems(); //setting items
    	yearBox.setEditable(true);
    	seasonBox.setEditable(true);
    	sessionBox.setEditable(true);
    	classBox.setEditable(true);
        ObservableList<String> l;
        
        //setup shown items
		//try {
			//l = FXCollections.observableArrayList(StubTester.getClassDataDefault());
			//classBox.setItems(l);
		//} catch (IOException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
    	l = FXCollections.observableArrayList(Arrays.asList(SheetHandler.SEASON_FALL_TAB,SheetHandler.SEASON_SUMMER_TAB,SheetHandler.SEASON_SPRING_TAB));
    	seasonBox.setItems(l);
    	yearBox.setItems(FXCollections.observableArrayList(Arrays.asList("2018")));
    	sessionBox.setItems(FXCollections.observableArrayList(Arrays.asList(""+1,""+2,""+3)));
    	yearBox.valueProperty().addListener((o,s1,s2)->{
    		if(s1==null || (s2!=null &&!s1.equals(s2))){
    			try{
	    			int parsed = Integer.parseInt(s2);
	    			DriveHandler.quickGuess(parsed, (list)->{
	    				ObservableList<String> toSee = FXCollections.observableArrayList(list);
	    				classBox.setItems(toSee);
	    			});
	    			
    			}
    			catch(Exception ex){
    				
    			}
    		}
    	});
    }    
    @FXML
    private void onCancel(ActionEvent event){
    	FXHandler.handle.closePopup();
    }
    public void onYearBoxUpdated(){
    	
    }
    @FXML
    private void onSearch(ActionEvent event){
    	
    	int year, session;
    	String season, room;
    	String strYear,strSession;
    	strYear = yearBox.getSelectionModel().getSelectedItem();
    	strSession = sessionBox.getSelectionModel().getSelectedItem();
    	season = seasonBox.getSelectionModel().getSelectedItem();
    	room = classBox.getSelectionModel().getSelectedItem();
    	try{
    		
    		
    		year = Integer.parseInt(strYear);
    		session = Integer.parseInt(strSession);
    		CustomGridWindowBundle b = DriveHandler.getClassData(year,season,session,room);
    		if(b!=null){
    			FXHandler.handle.switchStage(FXHandler.FX_PAGE_CUSTOM_SHEET_WINDOW, b);
    			FXHandler.handle.closePopup();
    		}
    		else{
    			throw new RuntimeException("Invalid date selection exception");
    		}
    		//FXHandler.handle.switchStage(FXHandler.FX_PAGE_DIR_EXCEL_DOC_PAGE,FakeDatabase.findAndWrapDateTimeRow(year, season, session, room));
    	}
    	catch(Exception ex){
    		//failed to parse strings to integers.
    		ex.printStackTrace();
    		Toolkit.getDefaultToolkit().beep();
    	}
    	
    }
    
}
