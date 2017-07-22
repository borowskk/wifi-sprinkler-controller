/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
