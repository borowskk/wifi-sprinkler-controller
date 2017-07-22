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
package com.sprinklercontrol;

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
