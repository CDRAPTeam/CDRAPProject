/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdarp.UI.Controller;

import com.cdarp.Fake.FakeDatabase;
import com.cdarp.Transit.DateTimeRow;
import com.cdarp.Transit.Session;
import com.cdarp.Transit.UI.ResourceList;
import com.cdarp.UI.Controller.Wrapper.SelfCallBack;
import com.cdarp.UI.FXHandler;
import com.cdarp.UI.FXUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
    @FXML
    private void onNewDocument(ActionEvent event) {
        try {
            FXHandler.handle.switchStage("com/cdarp/UI/FXML/ExcelWindow.fxml",new ResourceList(Collections.emptyList()));
        } catch (IOException ex) {
            Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void onReturnClick(ActionEvent event){
    	
    }
    @FXML
    private void onSearch(ActionEvent event){
    	try {
			FXHandler.handle.performPopup(FXHandler.FX_POPUP_DIR_SEARCH_WINDOW_DEFAULT, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        FXUtils.setupTableColumnView(Year,new Callback<TableColumn<Session,Session>,TableCell<Session,Session>>() {
            @Override
            public TableCell<Session,Session> call(TableColumn<Session,Session> param) {
                TableCell cell = new TableCell<Session,Session>(){
                   
                    @Override
                    public void updateItem(Session item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            setText(""+item.getYear());
                            
                            //setStyle("-fx-background-color:"+(item.getMonday()==1?"#ff0000ff":"#0000ffff"));
                        }
                        //setText(empty ? null : getString());
                    }
                };
                cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandlerSample());
                return cell;
            }
        });
        FXUtils.setupTableColumnView(Season,new Callback<TableColumn<Session,Session>,TableCell<Session,Session>>() {
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
        FXUtils.setupTableColumnView(Session,new Callback<TableColumn<Session,Session>,TableCell<Session,Session>>() {
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
        setup(((ResourceList<Session>)rb).getList());
    }    
    void setup(List<Session> row){
        ObservableList<Session> l = FXCollections.observableArrayList(row);
        System.out.println(l);
        this.TableViewer.setItems(l);
    }
    
    private class EventHandlerSample implements EventHandler<MouseEvent> {
 
        @Override
        public void handle(MouseEvent t) {
            try {
                TableCell c = (TableCell) t.getSource();
                int index = c.getIndex();
                Session row = TableViewer.getItems().get(index);
                FXHandler.handle.switchStage(FXHandler.FX_PAGE_DIR_EXCEL_CLASS_WINDOW,FakeDatabase.getClassSession(row));
            } catch (IOException ex) {
                Logger.getLogger(StartWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
