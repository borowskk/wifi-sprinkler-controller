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

import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 *
 * @author kyle
 */
public class AlertDialog {

    private final String title;
    private final String text;

    public AlertDialog(String title, String text) {
        this.text = text;
        this.title = title;
    }

    public void showAndWait() {
        System.err.println(text);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(title);
                alert.setHeaderText(title);
                alert.setContentText(text);
                alert.showAndWait();
            }
        });
    }
}
