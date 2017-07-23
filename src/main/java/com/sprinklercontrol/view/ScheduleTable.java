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

import com.sprinklercontrol.controller.CommTask;
import com.sprinklercontrol.model.ScheduleEntry;
import com.sprinklercontrol.model.TxMsgDeleteSchedule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author kyle
 */
public class ScheduleTable extends VBox {
    
    private class TableLabel extends Label {

        public TableLabel(String label) {
            this(label, S_WIDTH);
        }
        
        public TableLabel(String label, double minWidth) {
            super(label);
            setMinWidth(minWidth);
            setFont(new Font("Arial", 16));
            setTextFill(Color.WHITE);
        }
    }

    private final Map<HBox, ScheduleEntry> rows = new HashMap<>();

    private CommTask commTask = null;

    private final HBox headerRow = new HBox();

    public static final double S_WIDTH = 65;
    
    public static final double L_WIDTH = 150;

    public ScheduleTable(CommTask commTask) {
        this.commTask = commTask;

        headerRow.getChildren().addAll(new TableLabel("Zone"), new TableLabel("Time"), new TableLabel("Days", L_WIDTH));
        headerRow.setSpacing(5);
        headerRow.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY.EMPTY, Insets.EMPTY.EMPTY)));
        getChildren().add(headerRow);
    }

    public void addScheduleEntry(ScheduleEntry entry) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                List<String> sort = new ArrayList<>();
                for (ScheduleEntry existing : rows.values()) {
                    if (entry.equals(existing)) {
                        return;
                    }
                    sort.add(String.format("%02d", existing.zone) + String.format("%02d", existing.hour) + String.format("%02d", existing.minute));
                }
                String toAdd = String.format("%02d", entry.zone) + String.format("%02d", entry.hour) + String.format("%02d", entry.minute);
                sort.add(toAdd);               
                
                java.util.Collections.sort(sort);                               
                
                HBox row = createRow(entry);
                rows.put(row, entry);
                               
                getChildren().add(sort.indexOf(toAdd) + 1, row);
            }
        });

    }

    public void removeScheduleEntry(ScheduleEntry entry) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                for (HBox key : rows.keySet()) {
                    if (rows.get(key) == entry) {
                        getChildren().remove(key);
                        rows.remove(key);
                    }
                }
            }
        });

    }

    public void clearTable() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                getChildren().clear();
                rows.clear();
                getChildren().add(headerRow);
            }
        });

    }

    private HBox createRow(ScheduleEntry entry) {
        HBox row = new HBox();
        TableLabel zone = new TableLabel(String.format("%02d", entry.zone));
        if (entry.open) {
            row.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY.EMPTY, Insets.EMPTY.EMPTY)));
        } else {
            row.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        
        TableLabel time = new TableLabel(String.format("%02d", entry.hour) + ":" + String.format("%02d", entry.minute));
        TableLabel days = new TableLabel(entry.daysOfWeek.toString(), L_WIDTH);
        days.setMinWidth(100);
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                deleteAction(row);
            }
        });
        row.setAlignment(Pos.CENTER);
        row.setSpacing(10);
        row.getChildren().addAll(zone, time, days, deleteButton);
        return row;
    }

    private void deleteAction(HBox row) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Confirm Delete: ");
        alert.setContentText("Are you sure you want to delete this schedule entry?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            ScheduleEntry entry = rows.get(row);
            System.out.println("Deleting row: " + entry.toString());
            commTask.sendMessage(new TxMsgDeleteSchedule(entry));
            clearTable();
            commTask.triggerQuery();
        }
    }
}
