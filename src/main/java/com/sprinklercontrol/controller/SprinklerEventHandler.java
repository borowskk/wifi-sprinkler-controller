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
package com.sprinklercontrol.controller;

import com.sprinklercontrol.controller.CommTaskEventHandler;
import com.sprinklercontrol.model.RxMsgScheduleResponse;
import com.sprinklercontrol.model.RxMsgStatus;
import com.sprinklercontrol.model.RxMsgZoneStatus;
import com.sprinklercontrol.model.ScheduleEntry;
import com.sprinklercontrol.view.SprinklerControlView;
import java.util.List;

/**
 *
 * @author kyle
 */
public class SprinklerEventHandler implements CommTaskEventHandler {
    
    private final SprinklerControlView view;
    
    public SprinklerEventHandler(SprinklerControlView view) {
        this.view = view;
    }
    
    @Override
    public void onStatus(RxMsgStatus msg) {
        RxMsgStatus.SimpleTime time = msg.getTime();
        view.setTime(time.hour, time.minute);
    }
    
    @Override
    public void onZoneStatus(RxMsgZoneStatus status) {
        List<Integer> onZones = status.getOnZones();
                
        for(Integer zone : view.zones) {
            view.setButtonState(zone, onZones.contains(zone));
        }
    }
    
    @Override
    public void onScheduleQueryResponse(RxMsgScheduleResponse scheduleQueryResponse) {
        List<ScheduleEntry> entries = scheduleQueryResponse.toScheduleEntry();
        for (ScheduleEntry entry : entries) {
            if (!entry.daysOfWeek.isEmpty()) {
                view.addToTable(entry);
            }
        }
    }
 
    
}
