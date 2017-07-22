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

/**
 *
 * @author kyle
 */
public class TxMsgQuerySchedule extends TxMsg {

    byte zone;
    
    public TxMsgQuerySchedule(byte zone) {
        this.zone = zone;
    }
    
    @Override
    public byte[] getBytes() {
        byte[] retval = { TXSTART, OPQUERY, zone, FILL, FILL, FILL, FILL, FILL, 
                          FILL, FILL, FILL, FILL, FILL, FILL, FILL, FILL, FILL, 
                          FILL, FILL, TXEND};
        return retval;
    }
    
}
