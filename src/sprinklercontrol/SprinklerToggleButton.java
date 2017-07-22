/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprinklercontrol;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;

/**
 *
 * @author kyle
 */
public class SprinklerToggleButton extends ToggleButton {

    private byte code;
    private CommTask commTask;

    public SprinklerToggleButton(byte code, String text, CommTask commTask) {
        super(text);
        this.code = code;
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
                SprinklerToggleButton button = (SprinklerToggleButton) event.getSource();
                if (button.isSelected()) {
                    commTask.sendMessage(new TxMsgOnOff(true, code));
                } else {
                    commTask.sendMessage(new TxMsgOnOff(false, code));
                }
            }
        });
    }
}
