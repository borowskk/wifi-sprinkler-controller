/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprinklercontrol;

import java.util.List;

/**
 *
 * @author kyle
 */
public class SprinklerEventHandler implements CommTaskEventHandler {
    
    private SprinklerControl view;
    
    public SprinklerEventHandler(SprinklerControl view) {
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
