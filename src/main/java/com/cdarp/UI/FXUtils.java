package com.cdarp.UI;

import com.cdarp.Transit.ClassSession;
import com.cdarp.UI.Controller.Wrapper.SelfCallBack;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class FXUtils {
	//cannot init, util class
	private FXUtils(){
		
	}
	/**
	 *
	 * @param col
	 * @param transfereNode
	 */
	public static <T> void setupTableColumnView(TableColumn<T,T> col, Callback<TableColumn<T,T>,TableCell<T,T>> transfereNode){
		col.setCellValueFactory(new SelfCallBack<T>());
		col.setCellFactory(transfereNode);
		
	}
}
