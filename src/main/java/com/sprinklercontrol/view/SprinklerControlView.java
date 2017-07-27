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

import com.sprinklercontrol.model.TxMsgOnOff;
import com.gluonhq.charm.glisten.control.AppBar;
import com.gluonhq.charm.glisten.mvc.View;
import com.gluonhq.charm.glisten.visual.MaterialDesignIcon;
import com.sprinklercontrol.controller.CommTask;
import com.sprinklercontrol.model.ScheduleEntry;
import com.sprinklercontrol.controller.SprinklerEventHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SprinklerControlView extends View {

    private ScheduleTable table = null;

    //public final List<Integer> zones = new ArrayList<>(java.util.Arrays.asList(12, 14));
    public final List<Integer> zones = new ArrayList<>(java.util.Arrays.asList(0, 2, 4, 6, 8, 10, 12, 14));

    private final Text time = new Text();

    private final List<SprinklerToggleButton> buttons = new ArrayList<>();

    public CommTask commTask = null;

    private Thread commThread = null;
    
    private SprinklerEventHandler commHandler = null;

    private final String HOSTNAME_PREF_NAME = "hostname";
    
    public SprinklerControlView(String name) {
        super(name);
        
        Preferences prefs = Preferences.userNodeForPackage(SprinklerControlView.class);
        String hostname = prefs.get(HOSTNAME_PREF_NAME, "192.168.1.186");

        commHandler = new SprinklerEventHandler(this);
        commTask = new CommTask(hostname, 8080, zones, commHandler);
        commThread = new Thread(commTask);

        ScrollPane scrollRoot = new ScrollPane();
        scrollRoot.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        VBox root = new VBox();
        root.setSpacing(5);
        root.setPadding(new Insets(5, 5, 5, 5));
        root.setMaxWidth(350);

        root.getChildren().add(new HeadingLabel("Time"));
        root.getChildren().add(time);
        root.getChildren().add(new Separator());

        root.getChildren().add(new HeadingLabel("On/Off"));
        for (int zone : zones) {
            SprinklerToggleButton btn = new SprinklerToggleButton((byte) zone, "Toggle Zone " + String.format("%02d", zone), commTask);
            root.getChildren().add(btn);
            buttons.add(btn);
        }
        root.getChildren().add(new Separator());

        root.getChildren().add(new HeadingLabel("Routines"));
        BigButton blowout = new BigButton("BLOWOUT");
        blowout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert warn = new Alert(Alert.AlertType.CONFIRMATION);
                warn.setTitle("Confirm Blowout");
                warn.setHeaderText("Confirm Blowout");
                warn.setContentText("Click OK once air compressor is ready.");
                Optional<ButtonType> result = warn.showAndWait();
                if (result.get() == ButtonType.OK) {
                    for (int zone : zones) {
                        try {
                            commTask.sendMessage(new TxMsgOnOff(true, (byte) zone));
                            Thread.sleep(30000); // Run station for 30 seconds
                            commTask.sendMessage(new TxMsgOnOff(false, (byte) zone));
                            Thread.sleep(300000); // Wait 5 min for air compressor to recharge
                        } catch (InterruptedException e) {
                            AlertDialog alert = new AlertDialog("Routine Failed", "Routine unexpectedly failed.");
                            alert.showAndWait();
                        }
                    }
                }
            }
        });
        root.getChildren().add(blowout);

        BigButton allZones = new BigButton("RUNONCE");
        allZones.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setTitle("Confirm All Zones");
                confirm.setHeaderText("Confirm All Zones");
                confirm.setContentText("Click OK to run all zones for 10 minutes.");
                Optional<ButtonType> result = confirm.showAndWait();
                if (result.get() == ButtonType.OK) {
                    for (int zone : zones) {
                        try {

                            commTask.sendMessage(new TxMsgOnOff(true, (byte) zone));
                            Thread.sleep(600000); // Run station for 10 minutes
                            commTask.sendMessage(new TxMsgOnOff(false, (byte) zone));

                        } catch (InterruptedException e) {
                            AlertDialog alert = new AlertDialog("Routine Failed", "Routine unexpectedly failed.");
                            alert.showAndWait();
                        }
                    }
                }
            }
        });
        root.getChildren().add(allZones);
        root.getChildren().add(new Separator());

        root.getChildren().add(new HeadingLabel("Schedule"));
        table = new ScheduleTable(commTask);
        root.getChildren().add(table);
        root.getChildren().add(new Separator());

        root.getChildren().add(new HeadingLabel("Add To Schedule"));
        root.getChildren().add(new AddScheduleWidget(commTask, table));
        root.getChildren().add(new Separator());

        commThread.start();

        scrollRoot.setContent(root);

        setCenter(scrollRoot);
    }

    public void clearTable() {
        table.clearTable();
    }

    public void addToTable(ScheduleEntry entry) {
        table.addScheduleEntry(entry);
    }

    public void setTime(int hour, int minute) {
        String hourString = String.format("%02d", hour);
        String minuteString = String.format("%02d", minute);
        time.setText(hourString + ":" + minuteString);
    }

    public void setButtonState(int zone, boolean toggleOn) {
        int i = zones.indexOf(zone);
        if (i != -1) {
            buttons.get(i).setSelected(toggleOn);
        }
    }

    @Override
    protected void updateAppBar(AppBar appBar) {
        appBar.setNavIcon(MaterialDesignIcon.MENU.button(e -> showMenu()));
        appBar.setTitleText("DIY Sprinkler Control");
        //appBar.getActionItems().add(MaterialDesignIcon.SEARCH.button(e -> System.out.println("Search")));
    }

    private void showMenu() {
        // Retrieve the user preference node for the package com.mycompany
        Preferences prefs = Preferences.userNodeForPackage(SprinklerControlView.class);
        String cur = prefs.get(HOSTNAME_PREF_NAME, "localhost");

        TextInputDialog dialog = new TextInputDialog(cur);
        //TextInputDialog dialog = new TextInputDialog("192.168.1.186");
        dialog.setTitle("IP Address");
        dialog.setHeaderText("Specify Device IP Address");
        dialog.setContentText("Specify Device IP Address:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            // Retrieve the user preference node for the package com.mycompany
            prefs.put(HOSTNAME_PREF_NAME, result.get());

            commTask.stop();
            try {
                commThread.join(20000);
            } catch (InterruptedException e) {
                AlertDialog alert = new AlertDialog("Fatal Error", "Failed To Stop Communication Task");
                alert.showAndWait();
                System.exit(1);
            }
            commTask.reset(result.get(), 8080);
            commThread = new Thread(commTask);
            commThread.start();
        }
    }

}
