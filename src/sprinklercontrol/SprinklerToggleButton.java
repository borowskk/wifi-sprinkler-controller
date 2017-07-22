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
