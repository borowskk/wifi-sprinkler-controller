/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprinklercontrol;

import java.util.ArrayList;

/**
 *
 * @author kyle
 */
public class RxMsgStatus extends RxMsg {
    
    public class SimpleTime {
        public int hour;
        public int minute;
        public int second;
    }
    
    public RxMsgStatus(ArrayList<Byte> raw) {
        super(raw);
    }
    
    public SimpleTime getTime() {
        SimpleTime retval = new SimpleTime();
        retval.hour = raw.get(4);
        retval.minute = raw.get(5);
        retval.second = raw.get(6);
        return retval;
    }
    
    
    @Override
    public String toString() {
        SimpleTime time = getTime();
        String retval = "Status Msg | Time: " + time.hour + ":" + time.minute;
        return retval;
    }
}
