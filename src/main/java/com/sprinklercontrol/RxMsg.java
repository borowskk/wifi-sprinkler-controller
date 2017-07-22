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
public class RxMsg {
    
    ArrayList<Byte> raw;
    public RxMsg(ArrayList<Byte> bytes) {
        this.raw = bytes;
    }
    
    public String toHexString() {
        StringBuilder retval = new StringBuilder("Msg: ");
        for (Byte b: raw) {
            Integer i = new Integer(b);
            retval.append(Integer.toHexString(i));
            retval.append(" ");
        }
        return retval.toString();
    }
    
    @Override
    public String toString() {
        return "TypeCode: " + Integer.toHexString(raw.get(0)) + "Msg: " + toHexString();
    }
}
