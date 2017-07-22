# wifi-sprinkler-controller

The wifi-sprinkler-controller project intends to be a low-cost solution for more advanced sprinkler control. While the use-case here is targetted at sprinkler system control, the hardware/software can easily be used or modified for use on other automation projects.

Hardware:
  - The hardware used is the HLK-SW16 16 Relay Board w/ WIFI
  - The board costs around $50 USD

Software:
  - The software is custom Java software I wrote using JavaFX
  - My original intention was to reload the firmware on the HLK device w/ my own Linux and custom SW. However, I found the feature-set of the current firmware sufficient.
    - It exposes all its interfaces over a simple TCP protocol (which I had to reverse-engineer)
    - It has a sort of CRON job scheduler
    - It can be attached to your WIFI as a client
  - It is intended to be ported to Android soon

Building:
  - My environment had Netbeans 8.2 and Oracle's 1.8 JDK

Picture:
![hlk-sw16](https://user-images.githubusercontent.com/1266984/28492743-02ac2c20-6ec6-11e7-892e-768215ed45e5.JPG)


