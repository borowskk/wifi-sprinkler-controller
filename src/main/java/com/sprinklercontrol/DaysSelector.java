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
package com.sprinklercontrol;

import java.util.ArrayList;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 *
 * @author kyle
 */
public class DaysSelector extends HBox {

    private final RadioButton sundayButton;
    private final RadioButton mondayButton;
    private final RadioButton tuesdayButton;
    private final RadioButton wednesdayButton;
    private final RadioButton thursdayButton;
    private final RadioButton fridayButton;
    private final RadioButton saturdayButton;

    public DaysSelector() {
        super();

        sundayButton = new RadioButton("U");
        mondayButton = new RadioButton("M");
        tuesdayButton = new RadioButton("T");
        wednesdayButton = new RadioButton("W");
        thursdayButton = new RadioButton("R");
        fridayButton = new RadioButton("F");
        saturdayButton = new RadioButton("S");
        
        Font font = new Font("Arial", 10);
        
        sundayButton.setFont(font);
        mondayButton.setFont(font);
        tuesdayButton.setFont(font);
        wednesdayButton.setFont(font);
        thursdayButton.setFont(font);
        fridayButton.setFont(font);
        saturdayButton.setFont(font);
        
        this.getChildren().addAll(sundayButton, mondayButton, tuesdayButton, wednesdayButton, thursdayButton, fridayButton, saturdayButton);
    }

    public ArrayList<Day> getDays() {
        ArrayList<Day> days = new ArrayList<>();
        if (sundayButton.isSelected()) {
            days.add(Day.U);
        }
        if (mondayButton.isSelected()) {
            days.add(Day.M);
        }
        if (tuesdayButton.isSelected()) {
            days.add(Day.T);
        }
        if (wednesdayButton.isSelected()) {
            days.add(Day.W);
        }
        if (thursdayButton.isSelected()) {
            days.add(Day.R);
        }
        if (fridayButton.isSelected()) {
            days.add(Day.F);
        }
        if (saturdayButton.isSelected()) {
            days.add(Day.S);
        }
        return days;
    }
}
