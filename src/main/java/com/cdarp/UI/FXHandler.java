/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdarp.UI;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Navnik
 */
public class FXHandler extends Application {
    private Stage stage;
    public static FXHandler handle;
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("com/cdarp/UI/FXML/ExcelWindow.fxml"));
        //Scene scene = new Scene(root);
        //stage.setScene(scene);
        stage.show();
        handle = this;
        this.stage = stage;
        switchStage("com/cdarp/UI/FXML/StartWindow.fxml");
    }
    public void switchStage(String file) throws IOException{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(file));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
