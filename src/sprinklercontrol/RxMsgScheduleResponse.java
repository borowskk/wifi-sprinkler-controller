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
