/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sprinklercontrol;

import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author kyle
 */
public class ScheduleTable extends TableView {

    private CommTask commTask;
    
    public ScheduleTable(CommTask commTask) {
        this.commTask = commTask;
        
        TableColumn zoneCol = new TableColumn("Zone");
        TableColumn openCol = new TableColumn("Open");
        TableColumn dowCol = new TableColumn("Day of Week");
        TableColumn hourCol = new TableColumn("Hour");
        TableColumn minuteCol = new TableColumn("Minute");

        zoneCol.setCellValueFactory(
                new PropertyValueFactory<>("zone")
        );
        openCol.setCellValueFactory(
                new PropertyValueFactory<>("open")
        );
        dowCol.setCellValueFactory(
                new PropertyValueFactory<>("daysOfWeek")
        );
        hourCol.setCellValueFactory(
                new PropertyValueFactory<>("hour")
        );
        minuteCol.setCellValueFactory(
                new PropertyValueFactory<>("minute")
        );

        this.getColumns().addAll(zoneCol, openCol, dowCol, hourCol, minuteCol);
        
        this.setItems(FXCollections.observableArrayList());
        
        // Desktop uses right click to indicate delete
        this.setOnMousePressed((MouseEvent event) -> {
            if (event.isSecondaryButtonDown()) {
                deleteAction();
            }
        });
        
        // Mobile uses swipe right to indicate delete
        this.setOnSwipeRight((event) -> {
            deleteAction();
        });

        setEditable(true);
        
        setMaxHeight(200);
     
    }

    private void deleteAction() {
        String isSomethingSelected = this.getSelectionModel().getSelectedItem() != null ? "OK" : "BAD";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Confirm Delete: " + isSomethingSelected);
        alert.setContentText("Are you sure you want to delete this schedule entry?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ScheduleEntry entry = (ScheduleEntry) this.getSelectionModel().getSelectedItem();
            System.out.println("Deleting row: " + entry.toString());
            commTask.sendMessage(new TxMsgDeleteSchedule(entry));
            this.getItems().clear();
            commTask.triggerQuery();
        }
    }
}
