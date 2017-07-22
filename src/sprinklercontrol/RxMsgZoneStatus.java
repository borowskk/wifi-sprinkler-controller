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
