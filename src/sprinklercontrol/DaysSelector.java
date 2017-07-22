/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprinklercontrol;

import java.util.ArrayList;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;

/**
 *
 * @author kyle
 */
public class DaysSelector extends VBox {

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
