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

/**
 *
 * @author kyle
 */
public abstract class TxMsg {
    
    protected byte FILL = (byte)0x01;
    protected byte TXSTART = (byte)0xaa;
    protected byte TXEND = (byte)0xbb;
    protected byte OPQUERY = (byte)0x2d;
    protected byte OPCONNECT = (byte)0x1e;
    protected byte OPONOFF = (byte)0xf;
    protected byte OPADDSCHEDOPEN = (byte)0x21;
    protected byte OPADDSCHEDCLOSE = (byte)0x23;
    protected byte OPDELETESCHEDOPEN = (byte)0x28;
    protected byte OPDELETESCHEDCLOSE = (byte)0x2a;
    
    public TxMsg() {
        
    }
    
    public abstract byte[] getBytes();
    
    public String toHexString() {
        StringBuilder retval = new StringBuilder("Msg: ");
        for (Byte b: getBytes()) {
            Integer i = new Integer(b);
            retval.append(Integer.toHexString(i));
            retval.append(" ");
        }
        return retval.toString();
    }
}
