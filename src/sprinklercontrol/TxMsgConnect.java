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
public class TxMsgConnect extends TxMsg {

    public TxMsgConnect() {
        
    }
    
    @Override
    public byte[] getBytes() {
        byte[] retval = { TXSTART, OPCONNECT, FILL, FILL, FILL, FILL, FILL, FILL, 
                          FILL, FILL, FILL, FILL, FILL, FILL, FILL, FILL, FILL, 
                          FILL, FILL, TXEND};
        return retval;
    }
    
}
