/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprinklercontrol;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kyle
 */
public class RxMsgZoneStatus extends RxMsg {
    
    public RxMsgZoneStatus(ArrayList<Byte> raw) {
        super(raw);
    }
    
    public List<Integer> getOnZones() {
        List<Integer> retval = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            if (raw.get(i+1) == (byte) 0x1) {
                retval.add(i);
            }
        }
        return retval;
    }
    
    @Override
    public String toString() {
        String retval =  "Zone Status Msg | On: ";
        List<Integer> zones = getOnZones();
        for (Integer zone : zones) {
            retval += zone;
            retval += " ";
        }
        return retval;
    }
}
