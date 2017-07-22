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
package sprinklercontrol;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author borowskk
 */
public class SprinklerControl extends Application {

    private final TableView table = new TableView();
    
    public final List<Integer> zones = new ArrayList<>(java.util.Arrays.asList(0, 2, 4, 6, 8, 10, 12, 14));

    private final Text time = new Text();
    
    private final List<SprinklerToggleButton> buttons = new ArrayList<>();
    
    private Thread commThread = null;

    @Override
    public void start(Stage primaryStage) {
        SprinklerEventHandler commHandler = new SprinklerEventHandler(this);
        CommTask commTask = new CommTask("192.168.1.186", 8080, zones, commHandler);
        commThread = new Thread(commTask);

        VBox root = new VBox();
        
        setTime(0, 0);
        
        root.getChildren().add(time);
        
        for (int zone : zones) {
            SprinklerToggleButton btn = new SprinklerToggleButton((byte) zone, "Toggle Zone " + zone, commTask);
            btn.setMinWidth(400);
            root.getChildren().add(btn);
            buttons.add(btn);
        }

        final Label label = new Label("Schedule");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

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

        table.getColumns().addAll(zoneCol, openCol, dowCol, hourCol, minuteCol);
        table.setMaxWidth(400);
        table.setItems(FXCollections.observableArrayList());
        table.setOnMousePressed((MouseEvent event) -> {
            if (event.isSecondaryButtonDown()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Delete");
                alert.setHeaderText("Confirm Delete");
                alert.setContentText("Are you sure you want to delete this schedule entry?");
                
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    ScheduleEntry entry = (ScheduleEntry)table.getSelectionModel().getSelectedItem();
                    System.out.println("Deleting row: " + entry.toString());
                    commTask.sendMessage(new TxMsgDeleteSchedule(entry));
                    table.getItems().clear();
                    commTask.triggerQuery();
                }
            }
        });

        root.setAlignment(Pos.CENTER);
        root.setSpacing(5);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.getChildren().addAll(label, table);

        final TextField addZone = new TextField();
        addZone.setPromptText("Zone");
        addZone.setMaxWidth(zoneCol.getPrefWidth());
        final RadioButton addOpen = new RadioButton();
        addOpen.setMaxWidth(openCol.getPrefWidth());
        addOpen.setText("Open");
        final DaysSelector addDow = new DaysSelector();
        addDow.setMaxWidth(dowCol.getPrefWidth());
        final TextField addHour = new TextField();
        addHour.setMaxWidth(hourCol.getPrefWidth());
        addHour.setPromptText("Hour");
        final TextField addMinute = new TextField();
        addMinute.setMaxWidth(minuteCol.getPrefWidth());
        addMinute.setPromptText("Minute");

        final Button addButton = new Button("Add:");
        addButton.setOnAction((ActionEvent e) -> {
            try {
                byte zone = Byte.valueOf(addZone.getText());
                boolean open = addOpen.isSelected();
                ArrayList<Day> days = addDow.getDays();
                
                byte hour = Byte.valueOf(addHour.getText());
                byte minute = Byte.valueOf(addMinute.getText());
                
                TxMsgAddSchedule addMsg = new TxMsgAddSchedule(new ScheduleEntry(open, zone, hour, minute, days, 0));
                System.out.println("Sending schedule add: " + addMsg.toString());
                System.out.println();
                commTask.sendMessage(addMsg);
                table.getItems().clear();
                commTask.triggerQuery();
            } catch (NumberFormatException except) {
                System.err.println("Could not add to table: " + except);
            }
        });

        HBox hbox = new HBox();
        hbox.getChildren().addAll(addButton, addZone, addOpen, addDow, addHour, addMinute);
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(5);

        root.getChildren().add(hbox);

        Scene scene = new Scene(root, 500, 600);

        primaryStage.setTitle("Sprinkler Control");
        primaryStage.setScene(scene);
        primaryStage.show();

        commThread.start();
    }

    @Override
    public void stop() throws Exception {
        if (commThread != null) {
            commThread.stop();
        }
    }

    void clearTable() {
        table.getItems().clear();
    }

    void addToTable(ScheduleEntry entry) {
        if (!table.getItems().contains(entry)) {
            table.getItems().add(entry);
        }
    }

    void removeFromTable(int rowIndex) {
        table.getItems().remove(rowIndex);
    }

    void setTime(int hour, int minute) {
        String hourString = String.format("%02d", hour);
        String minuteString = String.format("%02d", minute);
        time.setText("Time: " + hourString + ":" + minuteString);
    }
    
    void setButtonState(int zone, boolean toggleOn) {
        int i = zones.indexOf(zone);
        if (i != -1) {
            buttons.get(i).setSelected(toggleOn);
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(SprinklerControl.class, args);
    }

}
