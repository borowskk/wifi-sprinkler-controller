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
public class WatchDogTimer {
    private int milliseconds;
    private long startTime;
    
    public WatchDogTimer(int milliseconds) {
        this.milliseconds = milliseconds;
        startTime = 0;
    }
    
    public void start() {
        startTime = System.currentTimeMillis();
    }
    
    public boolean checkExpired() {
        if (System.currentTimeMillis() - startTime >= milliseconds) {
            return true;
        }
        return false;
    }
    
}
