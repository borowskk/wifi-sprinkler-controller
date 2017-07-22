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
public class RxMsgScheduleResponse extends RxMsg {

    public RxMsgScheduleResponse(ArrayList<Byte> raw) {
        super(raw);
    }

    public List<ScheduleEntry> toScheduleEntry() {
        boolean open = true;
        if (raw.get(0) == (byte) 0x2f) {
            open = false;
        }

        List<ScheduleEntry> retval = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            int idx= 2 + i*3;
            ArrayList<Day> days = new ArrayList<>();
            int mon = (int) raw.get(idx+2) & (int) 0x2;
            int tue = (int) raw.get(idx+2) & (int) 0x4;
            int wed = (int) raw.get(idx+2) & (int) 0x8;
            int thu = (int) raw.get(idx+2) & (int) 0x10;
            int fri = (int) raw.get(idx+2) & (int) 0x20;
            int sat = (int) raw.get(idx+2) & (int) 0x40;
            int sun = (int) raw.get(idx+2) & (int) 0x80;

            if (mon > 0) {
                days.add(Day.M);
            }
            if (tue > 0) {
                days.add(Day.T);
            }
            if (wed > 0) {
                days.add(Day.W);
            }
            if (thu > 0) {
                days.add(Day.R);
            }
            if (fri > 0) {
                days.add(Day.F);
            }
            if (sat > 0) {
                days.add(Day.S);
            }
            if (sun > 0) {
                days.add(Day.U);
            }

            retval.add(new ScheduleEntry(open, raw.get(1), raw.get(idx), raw.get(idx+1), days, i));
        }
        return retval;
    }

}
