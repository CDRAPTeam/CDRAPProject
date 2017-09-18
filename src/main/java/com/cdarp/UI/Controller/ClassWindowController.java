/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdarp.UI.Controller;

import com.cdarp.Transit.ClassSession;
import com.cdarp.Transit.DateTimeRow;
import com.cdarp.Transit.Session;
import com.cdarp.UI.Controller.Wrapper.SelfCallBack;
import com.cdarp.UI.FXHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ClassRoomName.setCellValueFactory(new SelfCallBack());
        ClassRoomName.setCellFactory(new Callback<TableColumn<ClassSession,ClassSession>,TableCell<ClassSession,ClassSession>>() {
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
        ArrayList<ClassSession> iz = new ArrayList(32);
        for(int i = 300; i < 330;i++){
            iz.add(new ClassSession("Test Room"+i));
        }
        addItems(iz);
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
                //Session row = TableViewer.getItems().get(index);
                FXHandler.handle.switchStage("com/cdarp/UI/FXML/ExcelWindow.fxml");
            } catch (IOException ex) {
                Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
