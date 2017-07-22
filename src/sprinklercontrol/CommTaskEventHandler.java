/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprinklercontrol;

/**
 *
 * @author kyle
 */
public interface CommTaskEventHandler {
    public void onStatus(RxMsgStatus msg);
    
    public void onZoneStatus(RxMsgZoneStatus msg);
    
    public void onScheduleQueryResponse(RxMsgScheduleResponse msg);
}
