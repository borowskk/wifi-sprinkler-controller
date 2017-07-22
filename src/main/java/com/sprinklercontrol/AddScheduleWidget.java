/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sprinklercontrol;

import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author kyle
 */
public class AddScheduleWidget extends VBox {

    private CommTask commTask = null;
    
    private ScheduleTable table = null;
    
    private TextField addZone = null;
    
    private RadioButton addOpen = null;
    
    private DaysSelector addDow = null;
    
    private TextField addHour = null;
    
    private TextField addMinute = null;
    
    public AddScheduleWidget(CommTask commTask, ScheduleTable table) {
        this.commTask = commTask;
        this.table = table;
        
        addZone = new TextField();
        addZone.setPromptText("Zone");
        addOpen = new RadioButton();
        addOpen.setText("Open");
        addDow = new DaysSelector();
        addHour = new TextField();
        addHour.setPromptText("Hour");
        addMinute = new TextField();
        addMinute.setPromptText("Minute");

        final Button addButton = new Button("Add");
        addButton.setOnAction((ActionEvent e) -> {
            addAction();
        });

        setSpacing(5);
        
        HBox textFields = new HBox();
        textFields.getChildren().addAll(addZone, addHour, addMinute);
        textFields.setSpacing(5);
        textFields.setMaxWidth(300);
        
        getChildren().addAll(addButton, textFields, addDow, addOpen);
    }

    private void addAction() {
        try {
            byte zone = Byte.valueOf(addZone.getText());
            boolean open = addOpen.isSelected();
            ArrayList<Day> days = addDow.getDays();

            byte hour = Byte.valueOf(addHour.getText());
            byte minute = Byte.valueOf(addMinute.getText());

            TxMsgAddSchedule addMsg = new TxMsgAddSchedule(new ScheduleEntry(open, zone, hour, minute, days, 0));            
            
            if (table != null) {
                table.getItems().clear();
            }

            if (commTask != null) {
                commTask.sendMessage(addMsg);
                commTask.triggerQuery();
            }
        } catch (NumberFormatException except) {
            System.err.println("Could not add to table: " + except);
        }
    }
}
