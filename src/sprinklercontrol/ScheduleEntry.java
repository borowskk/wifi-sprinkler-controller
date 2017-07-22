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
    public class ScheduleEntry {
        public boolean open;
        public int zone;
        public int hour;
        public int minute;
        public ArrayList<Day> daysOfWeek;
        public int idx; // 0 - 4
        
        public ScheduleEntry(boolean open, int zone, int hour, int minute, ArrayList<Day> days,  int idx) {
            this.open = open;
            this.zone = zone;
            this.hour = hour;
            this.minute = minute;
            this.daysOfWeek = days;
            this.idx = idx;
            
        }
        
        public int getZone() {
            return zone;
        }

        public ArrayList<Day> getDaysOfWeek() {
            return daysOfWeek;
        }

        public int getHour() {
            return hour;
        }

        public int getMinute() {
            return minute;
        }

        public boolean isOpen() {
            return open;
        }
        
        @Override 
        public boolean equals(Object other) {
            if (!(other instanceof ScheduleEntry)) {
                return false;
            }
            ScheduleEntry otherEntry = (ScheduleEntry) other;
            return this.toString().equals(otherEntry.toString());
        }
        
        @Override
        public String toString() {
            return "Switch: " + zone + 
                    " Open: " + open + 
                    " Hour: " + hour + 
                    " Minute: " + minute + 
                    " Days: " + daysOfWeek;
        }
    }
