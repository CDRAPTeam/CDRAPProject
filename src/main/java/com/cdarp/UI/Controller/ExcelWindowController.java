/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdarp.UI.Controller;

import com.cdarp.Fake.FakeDatabase;
import com.cdarp.Transit.ClassSession;
import com.cdarp.Transit.DateTimeRow;
import com.cdarp.Transit.UI.ExcelWindowResourceBundle;
import com.cdarp.Transit.UI.ResourceList;
import com.cdarp.UI.FXHandler;
import com.cdarp.UI.FXUtils;
import com.cdarp.UI.Controller.Wrapper.ComboBoxCell;

import java.io.IOException;
import java.net.URL;
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
public class ExcelWindowController implements Initializable {
	private ClassSession parent;
	private ExcelWindowResourceBundle row;
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
    @FXML
    private TextField YearName;

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
    	try {
			FXHandler.handle.switchStage(FXHandler.FX_PAGE_DIR_EXCEL_CLASS_WINDOW,FakeDatabase.findAndWrapClassSessions(row.getYear(), row.getSeason(),row.getSession()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //this.Monday.setEditable(true);
        FXUtils.setupTableColumnView(Time, new Callback<TableColumn<DateTimeRow,DateTimeRow>,TableCell<DateTimeRow,DateTimeRow>>() {
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
        });
        FXUtils.setupTableColumnView(Monday, new Callback<TableColumn<DateTimeRow,DateTimeRow>,TableCell<DateTimeRow,DateTimeRow>>(){

			@Override
			public TableCell<DateTimeRow, DateTimeRow> call(TableColumn<DateTimeRow, DateTimeRow> arg0) {
				// TODO Auto-generated method stub
				return new ComboBoxCell<DateTimeRow>(new ComboBoxCell.StringConverter<DateTimeRow>() {

					@Override
					public String getString(DateTimeRow t) {
						// TODO Auto-generated method stub
						return (t.getMonday() == 0 ? "Unselected": "Selected");
					}

					@Override
					public String[] getOptions(DateTimeRow t) {
						// TODO Auto-generated method stub
						return new String[]{"Unselceted","Selected"};
					}

					@Override
					public void onSelected(DateTimeRow base, String str) {
						// TODO Auto-generated method stub
						if(str.equals("Selected")){
							base.setMonday(1);
						}
						else{
							base.setMonday(0);
						}
						System.out.println(str);
						
					}
				})
				{
					@Override
					public void updateItem(DateTimeRow item, boolean empty){
						if(!empty){
							setStyle("-fx-background-color:"+(item.getMonday()==1?"#ff0000ff":"#0000ffff"));
						}
						super.updateItem(item, empty);
					}
					@Override
					public void cancelEdit(){
						super.cancelEdit();
						DateTimeRow i = getItem();
						if(i!=null){
							setStyle("-fx-background-color:"+(i.getMonday()==1?"#ff0000ff":"#0000ffff"));
						}
					}
				};
				
			}});
        /*
        FXUtils.setupTableColumnView(Monday,new Callback<TableColumn<DateTimeRow,DateTimeRow>,TableCell<DateTimeRow,DateTimeRow>>() {
            @Override/*
            public TableCell<DateTimeRow, DateTimeRow> call(TableColumn<DateTimeRow, DateTimeRow> param) {
                TableCell cell = new TableCell<DateTimeRow,DateTimeRow>(){
                	private ComboBox<DateTimeRow> comboBox;
                	@Override
                    public void startEdit() {
                        super.startEdit();

                        if (comboBox == null) {
                            createComboBox();
                        }

                        setGraphic(comboBox);
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }
                	private void createComboBox() {
                        // ClassesController.getLevelChoice() is the observable list of String
                        comboBox = new ComboBox<>();
                        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap()*2);
                        comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent t) {
                                if (t.getCode() == KeyCode.ENTER) {
                                    commitEdit((DateTimeRow) comboBox.getSelectionModel().getSelectedItem());
                                } else if (t.getCode() == KeyCode.ESCAPE) {
                                    cancelEdit();
                                }
                            }
                        });
                    }
                	private String getString() {
                        return getItem() == null ? "" : ""+getItem().getMonday();
                    }
                    @Override
                    public void cancelEdit() {
                        super.cancelEdit();

                        setText(String.valueOf(getItem()));
                        setContentDisplay(ContentDisplay.TEXT_ONLY);
                    }

                    public void updateItem(DateTimeRow item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            if (isEditing()) {
                                if (comboBox != null) {
                                    comboBox.setValue(getItem());
                                }
                                setGraphic(comboBox);
                                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                            } else {
                                setText(getString());
                                setContentDisplay(ContentDisplay.TEXT_ONLY);
                            }
                        }
                    }
                	
                	/*@Override
                    public void updateItem(DateTimeRow item, boolean empty) {
                        super.updateItem(item, empty);
                        super.getChildren().add(new ComboBox());
                        if(!empty){
                            //setText(item.getMilitaryTime());
                            
                            setStyle("-fx-background-color:"+(item.getMonday()==1?"#ff0000ff":"#0000ffff"));
                        }
                        //setText(empty ? null : getString());
                    }
                };
               // cell.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandlerSample());
                return cell;
            }*
        });*/
        
        
        TableViewer.setEditable(true);
        Monday.setEditable(true);
        //this.Monday.addEventHandler(MouseEvent.MOUSE_CLICKED,  new EventHandlerSample());
        row = (ExcelWindowResourceBundle) rb;
        handleObserverList(row.getList());
        
        //Set the top fields
        this.YearName.setText("Year: "+row.getYear());
        this.SemesterName.setText("Semester: "+row.getSeason());
        this.SessionName.setText("Session: "+row.getSession());
        this.ClassName.setText("Class: "+row.getRoom());
    }    
    private void handleObserverList(List<DateTimeRow> row){
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
