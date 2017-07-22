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
