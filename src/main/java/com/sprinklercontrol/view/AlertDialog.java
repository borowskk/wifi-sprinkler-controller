/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
