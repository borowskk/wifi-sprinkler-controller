/*
 * Copyright 2017 Kyle Borowski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sprinklercontrol.view;

import com.sprinklercontrol.view.ScheduleTable;
import com.sprinklercontrol.view.BigButton;
import com.sprinklercontrol.controller.CommTask;
import com.sprinklercontrol.model.Day;
import com.sprinklercontrol.view.DaysSelector;
import com.sprinklercontrol.model.ScheduleEntry;
import com.sprinklercontrol.model.TxMsgAddSchedule;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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

        final BigButton addButton = new BigButton("Add");
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
                table.clearTable();
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
