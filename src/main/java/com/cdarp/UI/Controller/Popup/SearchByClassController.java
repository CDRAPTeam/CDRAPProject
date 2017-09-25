/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdarp.UI.Controller.Popup;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.cdarp.Fake.FakeDatabase;
import com.cdarp.UI.FXHandler;

import javafx.scene.control.ComboBox;
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
    }    
    @FXML
    private void onCancel(ActionEvent event){
    	FXHandler.handle.closePopup();
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
    		FXHandler.handle.switchStage(FXHandler.FX_PAGE_DIR_EXCEL_DOC_PAGE,FakeDatabase.findAndWrapDateTimeRow(year, season, session, room));
    	}
    	catch(Exception ex){
    		//failed to parse strings to integers.
    		
    	}
    	FXHandler.handle.closePopup();
    }
    
}
