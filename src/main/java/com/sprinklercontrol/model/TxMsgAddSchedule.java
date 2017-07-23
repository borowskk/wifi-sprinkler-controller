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
package com.sprinklercontrol.model;

/**
 *
 * @author kyle
 */
public class TxMsgAddSchedule extends TxMsg {

    private ScheduleEntry entry;
    
    public TxMsgAddSchedule(ScheduleEntry entry) {
        this.entry = entry;
    }
    
    @Override
    public byte[] getBytes() {
        int days = 0;
        if (entry.daysOfWeek.contains(Day.M)) {
            days |=  0x2;
        }
        if (entry.daysOfWeek.contains(Day.T)) {
            days |= 0x4;
        }
        if (entry.daysOfWeek.contains(Day.W)) {
            days |= 0x8;
        }
        if (entry.daysOfWeek.contains(Day.R)) {
            days |= 0x10;
        }
        if (entry.daysOfWeek.contains(Day.F)) {
            days |= 0x20;
        }
        if (entry.daysOfWeek.contains(Day.S)) {
            days |= 0x40;
        }
        if (entry.daysOfWeek.contains(Day.U)) {
            days |= 0x80;
        }
        // I am always seeing close ops added
        byte op = entry.open ? OPADDSCHEDOPEN : OPADDSCHEDCLOSE;
        byte[] retval = { TXSTART, op, (byte)entry.zone, (byte)entry.hour, (byte)entry.minute, (byte)days, FILL, FILL, 
                                  FILL, FILL, FILL, FILL, FILL, FILL, FILL, FILL, 
                                 FILL, FILL, FILL, TXEND};
        return retval;  
    }
    
    public String toString() {
        return "Add Schedule: " + entry.toString();
    }
    
}
