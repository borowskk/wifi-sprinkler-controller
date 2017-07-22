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
public class TxMsgDeleteSchedule extends TxMsg {
    private ScheduleEntry entry;
    
    public TxMsgDeleteSchedule(ScheduleEntry entry) {
        this.entry = entry;
    }
    
    @Override
    public byte[] getBytes() {
        int days = 0;
        if (entry.daysOfWeek.contains(Day.M)) {
            days |= 0x2;
        } else if (entry.daysOfWeek.contains(Day.T)) {
            days |= 0x4;
        } else if (entry.daysOfWeek.contains(Day.W)) {
            days |= 0x8;
        } else if (entry.daysOfWeek.contains(Day.R)) {
            days |= 0x10;
        } else if (entry.daysOfWeek.contains(Day.F)) {
            days |= 0x20;
        } else if (entry.daysOfWeek.contains(Day.S)) {
            days |= 0x40;
        } else if (entry.daysOfWeek.contains(Day.U)) {
            days |= 0x80;
        }
        
        byte op = entry.open ? OPDELETESCHEDOPEN : OPDELETESCHEDCLOSE;
        byte[] retval = { TXSTART, op, (byte)entry.zone, (byte)entry.hour, (byte)entry.minute, (byte)days, (byte)entry.idx, FILL, 
                                  FILL, FILL, FILL, FILL, FILL, FILL, FILL, FILL, 
                                  FILL, FILL, FILL, TXEND};
        
        return retval;  
    }
}
