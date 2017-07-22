/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sprinklercontrol;

import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 *
 * @author kyle
 */
public class HeadingLabel extends Label {
    public HeadingLabel(String text) {
        super(text);
        setFont(new Font("Arial", 20));
    }
}
