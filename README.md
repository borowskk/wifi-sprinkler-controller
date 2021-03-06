# wifi-sprinkler-controller

The DIY wifi sprinkler controller project intends to be a low-cost solution for more advanced sprinkler control. While the use-case here is targetted at sprinkler system control, the hardware/software can easily be used or modified for use on other automation projects.

Hardware:
  - The hardware used is the HLK-SW16 16 Relay Board w/ WIFI
  - The board costs around $50 USD
  - Wiring 
    - You must power the HLK-SW16 with its included power adapter
    - Use your original controller's power adapter (if the voltage is correct) to drive the sprinkler valves through the relays
    - Tie the common wire from the sprinkler valves to 1 of the wires from the original controller's power adapter
    - Tie each wire from a sprinkler valve each to 1 side of each relay
    - Tie the other wire from the controller's power adapter to the other side of each relay

Software:
  - The control software is custom Java software I wrote using JavaFX
    - The software available on the web for the HLK-SW16 is a bit buggy, difficult to use, and lacks some features I wanted
    - JavaFX and the gluon plugin for Netbeans/Eclipse makes my implementation compatible with Windows, Linux, and Android
    - While I haven't tested building for iPhone, gluon supports it, so the port would likely be easy
  - You will have to modify the IP address in the menu to point to your HLK-SW16

Background:
  - Goal: Build a low-cost DIY sprinkler controller, accessible via WIFI, with advanced features such as running custom routines
  - My original technical intentions were to reload the firmware on the HLK device w/ my own Linux and custom SW
    - However, I found the feature-set of the current firmware sufficient
    - It exposes all its interfaces over a simple TCP protocol (which I reverse-engineered in order to write this software)
    - It has a sort of CRON job scheduler
    - It can be attached to your WIFI as a client

Building:
  - Netbeans: My environment had Netbeans 8.2, the Android SDK, and Oracle's 1.8 JDK. I also installed the gluon plugin for Netbeans through the Netbeans Tools-Plugins menu
  - Command Line: Install Oracle's 1.8 JDK. Run ./gradlew tasks to see available build tasks. For example: to build a jar for Desktop Java use ./gradlew shadowJar

Picture of HLK-SW16 wired-up to replace my original sprinker controller.

![hlk-sw16](https://user-images.githubusercontent.com/1266984/28492743-02ac2c20-6ec6-11e7-892e-768215ed45e5.JPG)

Picture of the software control application in this repository.

![app](https://user-images.githubusercontent.com/1266984/28503526-24abddec-6fc6-11e7-8079-c50531422342.png)

