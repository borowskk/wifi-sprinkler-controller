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
public class TxMsgOnOff extends TxMsg {

    private final byte OPEN = (byte)0x1;
    private final byte CLOSE = (byte)0x2;
    
    private final byte zone;
    private final byte onOff;
    
    public TxMsgOnOff(boolean on, byte zone) {
        this.onOff = on ? OPEN : CLOSE;
        this.zone = zone;
    }
    
    @Override
    public byte[] getBytes() {
        byte[] retval = { TXSTART, OPONOFF, zone, onOff, FILL, FILL, FILL, FILL, 
                                  FILL, FILL, FILL, FILL, FILL, FILL, FILL, FILL, 
                                  FILL, FILL, FILL, TXEND};
        return retval;    
    }
    
}
