/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdarp.UI.Controller;

import com.cdarp.Transit.DateTimeRow;
import com.cdarp.UI.Controller.Wrapper.SelfCallBack;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
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
public class ExcelWindowController implements Initializable {

    @FXML
    private TableView<DateTimeRow> TableViewer;
    @FXML
    private TableColumn<DateTimeRow, DateTimeRow> Time;
    @FXML
    private TableColumn<DateTimeRow, DateTimeRow> Monday;
    @FXML
    private TableColumn<?, ?> Tuesday;
    @FXML
    private TableColumn<?, ?> Wendsday;
    @FXML
    private TableColumn<?, ?> Thursday;
    @FXML
    private TableColumn<?, ?> Friday;
    @FXML
    private TableColumn<?, ?> Saturday;
    @FXML
    private TableColumn<?, ?> Sunday;
    @FXML
    private TextField SemesterName;
    @FXML
    private TextField SessionName;
    @FXML
    private TextField ClassName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //this.Monday.setEditable(true);
        Callback<TableColumn<DateTimeRow,DateTimeRow>,TableCell<DateTimeRow,DateTimeRow>> cellFactory;
        cellFactory = new Callback<TableColumn<DateTimeRow,DateTimeRow>,TableCell<DateTimeRow,DateTimeRow>>() {
            @Override
            public TableCell<DateTimeRow, DateTimeRow> call(TableColumn<DateTimeRow, DateTimeRow> param) {
                TableCell cell = new TableCell<DateTimeRow,DateTimeRow>(){
                   
                    @Override
                    public void updateItem(DateTimeRow item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            setText(item.getMilitaryTime());
                        }
                        //setText(empty ? null : getString());
                        //setStyle("-fx-background-color:"+getString());
                    }
                };
                return cell;
            }
        };
        this.Time.setCellFactory(cellFactory);
        this.Time.setCellValueFactory(new SelfCallBack());
        
        this.Monday.setCellValueFactory(new SelfCallBack());
        this.Monday.setCellFactory(new Callback<TableColumn<DateTimeRow,DateTimeRow>,TableCell<DateTimeRow,DateTimeRow>>() {
            @Override
            public TableCell<DateTimeRow, DateTimeRow> call(TableColumn<DateTimeRow, DateTimeRow> param) {
                TableCell cell = new TableCell<DateTimeRow,DateTimeRow>(){
                   
                    @Override
                    public void updateItem(DateTimeRow item, boolean empty) {
                        super.updateItem(item, empty);
                        if(!empty){
                            //setText(item.getMilitaryTime());
                            
                            setStyle("-fx-background-color:"+(item.getMonday()==1?"#ff0000ff":"#0000ffff"));
                        }
                        //setText(empty ? null : getString());
                    }
                };
                cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandlerSample());
                return cell;
            }
        });
        //this.Monday.addEventHandler(MouseEvent.MOUSE_CLICKED,  new EventHandlerSample());
        
        ArrayList<DateTimeRow> row = new ArrayList(48);
        for(int i = 0; i < 48;i++){
            row.add(new DateTimeRow(i));
            if(i%3==0){
                row.get(i).setMonday(1);
            }
        }
        handleObserverList(row);
        
        System.out.println(this.TableViewer.getItems().size());
        System.out.println(row.size());
    }    
    private void handleObserverList(Collection<DateTimeRow> row){
        ObservableList<DateTimeRow> l = FXCollections.observableArrayList(row);
        this.TableViewer.setItems(l);
    }
    //Sample class
    class EventHandlerSample implements EventHandler<MouseEvent> {
 
        @Override
        public void handle(MouseEvent t) {
            TableCell c = (TableCell) t.getSource();
            int index = c.getIndex();
            DateTimeRow row = TableViewer.getItems().get(index);
            if(row.getMonday() == 0){
                row.setMonday(1);
            }
            else{
                row.setMonday(0);
            }
            TableViewer.refresh();
        }
    }
}
