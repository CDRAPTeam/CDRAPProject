/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdarp.UI.Controller;

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
public class StartWindowController implements Initializable {

    @FXML
    private TableView<Session> TableViewer;
    @FXML
    private TableColumn<Session, Session> Year;
    @FXML
    private TableColumn<Session, Session> Season;
    @FXML
    private TableColumn<Session, Session> Session;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Year.setCellValueFactory(new SelfCallBack());
        Season.setCellValueFactory(new SelfCallBack());
        Session.setCellValueFactory(new SelfCallBack());
        
        Year.setCellFactory(new Callback<TableColumn<Session,Session>,TableCell<Session,Session>>() {
            @Override
            public TableCell<Session,Session> call(TableColumn<Session,Session> param) {
                TableCell cell = new TableCell<Session,Session>(){
                   
                    @Override
                    public void updateItem(Session item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            setText(item.getYear());
                            
                            //setStyle("-fx-background-color:"+(item.getMonday()==1?"#ff0000ff":"#0000ffff"));
                        }
                        //setText(empty ? null : getString());
                    }
                };
                cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandlerSample());
                return cell;
            }
        });
        Season.setCellFactory(new Callback<TableColumn<Session,Session>,TableCell<Session,Session>>() {
            @Override
            public TableCell<Session,Session> call(TableColumn<Session,Session> param) {
                TableCell cell = new TableCell<Session,Session>(){
                   
                    @Override
                    public void updateItem(Session item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            setText(item.getSeason());
                            
                            //setStyle("-fx-background-color:"+(item.getMonday()==1?"#ff0000ff":"#0000ffff"));
                        }
                        //setText(empty ? null : getString());
                    }
                };
                cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandlerSample());
                return cell;
            }
        });
        Session.setCellFactory(new Callback<TableColumn<Session,Session>,TableCell<Session,Session>>() {
            @Override
            public TableCell<Session,Session> call(TableColumn<Session,Session> param) {
                TableCell cell = new TableCell<Session,Session>(){
                   
                    @Override
                    public void updateItem(Session item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            setText(String.valueOf(item.getSession()));
                            
                            //setStyle("-fx-background-color:"+(item.getMonday()==1?"#ff0000ff":"#0000ffff"));
                        }
                        //setText(empty ? null : getString());
                    }
                };
                cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandlerSample());
                return cell;
            }
        });
        ArrayList<Session> sess = new ArrayList();
        for(int i = 2012; i < 2018;i++){
            //Fall
            for(int j = 0; j < 3;j++){
                sess.add(new Session(""+i,"Fall",j));
            }
            //Spring
            for(int j = 0; j < 3;j++){
                sess.add(new Session(""+i,"Spring",j));
            }
            //Summer
            for(int j = 0; j < 2;j++){
                sess.add(new Session(""+i,"Summer",j));
            }
        }
        setup(sess);
    }    
    void setup(List<Session> row){
        ObservableList<Session> l = FXCollections.observableArrayList(row);
        System.out.println(l);
        this.TableViewer.setItems(l);
    }
    @FXML
    private void onNewDocument(ActionEvent event) {
        try {
            FXHandler.handle.switchStage("com/cdarp/UI/FXML/ExcelWindow.fxml");
        } catch (IOException ex) {
            Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private class EventHandlerSample implements EventHandler<MouseEvent> {
 
        @Override
        public void handle(MouseEvent t) {
            try {
                TableCell c = (TableCell) t.getSource();
                int index = c.getIndex();
                Session row = TableViewer.getItems().get(index);
                FXHandler.handle.switchStage("com/cdarp/UI/FXML/ClassWindow.fxml");
            } catch (IOException ex) {
                Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
