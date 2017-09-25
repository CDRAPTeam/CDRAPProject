/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdarp.UI.Controller;

import com.cdarp.Fake.FakeDatabase;
import com.cdarp.Transit.ClassSession;
import com.cdarp.Transit.DateTimeRow;
import com.cdarp.Transit.Session;
import com.cdarp.Transit.UI.ClassWindowResourceBundle;
import com.cdarp.Transit.UI.ResourceList;
import com.cdarp.UI.Controller.Wrapper.SelfCallBack;
import com.cdarp.UI.FXHandler;
import com.cdarp.UI.FXUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Navnik
 */
public class ClassWindowController implements Initializable {

    @FXML
    private TableView<ClassSession> TableViewer;
    @FXML
    private TableColumn<ClassSession,ClassSession> ClassRoomName;
    @FXML
    private TableColumn<ClassSession,ClassSession> ClassRoomNull;

    @FXML
    private TextField SemesterName;
    @FXML
    private TextField SessionName;
    @FXML
    private TextField YearName;
    /**
     * Initializes the controller class.
     */
    @FXML
    private void onNewDocument(ActionEvent event) {
    }
    @FXML
    private void onReturnClick(ActionEvent event){
    	
    	try {
			FXHandler.handle.switchStage(FXHandler.FX_PAGE_DIR_EXCEL_START_WINDOW,FakeDatabase.getClassSessions());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        FXUtils.setupTableColumnView(ClassRoomName,new Callback<TableColumn<ClassSession,ClassSession>,TableCell<ClassSession,ClassSession>>() {
            @Override
            public TableCell<ClassSession, ClassSession> call(TableColumn<ClassSession, ClassSession> param) {
                TableCell cell = new TableCell<ClassSession,ClassSession>(){
                   
                    @Override
                    public void updateItem(ClassSession item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            //setText(item.getMilitaryTime());
                            setText(item.getRoom());
                           // setStyle("-fx-background-color:"+(item.getMonday()==1?"#ff0000ff":"#0000ffff"));
                        }
                        //setText(empty ? null : getString());
                    }
                };
                cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandlerSample());
                return cell;
            }
        });
        
        ClassWindowResourceBundle sessions = (ClassWindowResourceBundle) rb;
        
        //set the top fields
        this.YearName.setText("Year: "+sessions.getYear());
        this.SemesterName.setText("Semester: "+sessions.getSeason());
        this.SessionName.setText("Session: "+sessions.getSession());
        
        //fill the table
        addItems(sessions.getList());
    }
    private void addItems(List<ClassSession> row){
        
        ObservableList<ClassSession> l = FXCollections.observableArrayList(row);
        this.TableViewer.setItems(l);
    }
    private class EventHandlerSample implements EventHandler<MouseEvent> {
 
        @Override
        public void handle(MouseEvent t) {
            try {
                TableCell c = (TableCell) t.getSource();
                int index = c.getIndex();
                ClassSession row = TableViewer.getItems().get(index);
                FXHandler.handle.switchStage(FXHandler.FX_PAGE_DIR_EXCEL_DOC_PAGE,FakeDatabase.getDateTimeRow(row));
            } catch (IOException ex) {
                Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
