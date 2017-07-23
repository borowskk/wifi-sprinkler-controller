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
