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
