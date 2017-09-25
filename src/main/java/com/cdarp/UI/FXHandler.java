/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdarp.UI;

import java.io.IOException;
import java.util.ResourceBundle;

import com.cdarp.Fake.FakeDatabase;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Navnik
 */
public class FXHandler extends Application {
	public static final String FX_PAGE_DIR_EXCEL_DOC_PAGE = "com/cdarp/UI/FXML/ExcelWindow.fxml";
	public static final String FX_PAGE_DIR_EXCEL_START_WINDOW = "com/cdarp/UI/FXML/StartWindow.fxml";
	public static final String FX_PAGE_DIR_EXCEL_CLASS_WINDOW = "com/cdarp/UI/FXML/ClassWindow.fxml";
	public static final String FX_POPUP_DIR_SEARCH_WINDOW_DEFAULT = "com/cdarp/UI/FXML/Popup/SearchByClass.fxml";
    private Stage stage;
    private Stage popup;
    public static FXHandler handle;
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("com/cdarp/UI/FXML/ExcelWindow.fxml"));
        //Scene scene = new Scene(root);
        //stage.setScene(scene);\fewawfewaew
    	
        //stage.show();
        handle = this;
        this.stage = stage;
        stage.setWidth(1000);
        stage.setHeight(500);
        switchStage(FX_PAGE_DIR_EXCEL_START_WINDOW,FakeDatabase.getClassSessions());
        
        stage.show();
    }
    public void switchStage(String file,ResourceBundle bundle) throws IOException{
    	double curWidth = stage.getWidth();
    	double curHeight = stage.getHeight();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(file), bundle);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(curWidth);
        stage.setHeight(curHeight);
        //stage.show();
    }
    public void performPopup(String file, ResourceBundle bundle) throws IOException{
    	Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(file),bundle);
    	popup = new Stage();
    	Scene scene = new Scene(root);
    	popup.setScene(scene);
    	popup.show();
    	popup.setAlwaysOnTop(true);
    	popup.setResizable(false);
    	popup.setOnCloseRequest((e)->{popup = null;});
    	popup.sizeToScene();
    }
    public void closePopup(){
    	if(popup!=null){
    		popup.close();
    	}
    }
    public boolean isPopupActive(){
    	if(popup!=null){
    		return popup.isShowing();
    	}
    	return false;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
