/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cdarp.UI.Controller.Wrapper;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 *
 * @author Navnik
 */
public class SelfCallBack<T> implements Callback<TableColumn.CellDataFeatures<T,T>,ObservableValue<T>>{

    @Override
    public ObservableValue<T> call(TableColumn.CellDataFeatures<T,T> param) {
        return new javafx.beans.property.ReadOnlyObjectWrapper<>(param.getValue());
    }
}
