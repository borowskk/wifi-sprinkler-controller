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
package com.sprinklercontrol.controller;

import com.sprinklercontrol.model.RxMsg;
import com.sprinklercontrol.model.RxMsgStatus;
import com.sprinklercontrol.model.RxMsgZoneStatus;
import com.sprinklercontrol.model.RxMsgScheduleResponseComplete;
import com.sprinklercontrol.model.RxMsgScheduleResponse;
import com.sprinklercontrol.model.TxMsg;
import com.sprinklercontrol.model.TxMsgQuerySchedule;
import com.sprinklercontrol.model.TxMsgConnect;
import com.sprinklercontrol.view.AlertDialog;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 *
 * @author kyle
 */
public class CommTask implements Runnable {

    private Socket socket;
    private DataInputStream din;
    private DataOutputStream dout;
    private boolean exitRequested;
    private CommTaskEventHandler handler;
    private boolean waitingForQueryResponse = false;

    private long lastStatus = 0;
    private final Map<Byte, Date> lastScheduleQueryTime = new HashMap<>();
    private String hostName;
    private int port;
    private final List<Integer> zones;

    public CommTask(String hostName, int port, List<Integer> zones, CommTaskEventHandler handler) {
        this.hostName = hostName;
        this.port = port;
        this.socket = new Socket();
        this.zones = zones;
        this.handler = handler;
        exitRequested = false;
    }

    @Override
    public void run() {
        while (!exitRequested) {
            try {
                if (!socket.isConnected()) {
                    System.out.println("Will connect to controller at: " + hostName + " on port: " + port);
                    InetAddress inteAddress = InetAddress.getByName(hostName);
                    SocketAddress socketAddress = new InetSocketAddress(inteAddress, port);

                    // create a socket
                    socket = new Socket();

                    // this method will block no more than timeout ms.
                    int timeoutInMs = 10 * 1000;   // 10 seconds
                    socket.connect(socketAddress, timeoutInMs);
                }

                din = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                dout = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

                WatchDogTimer queryWatchDog = new WatchDogTimer(500);

                while (!exitRequested) {
                    // Send connect msg, device will respond w/ status
                    if (System.currentTimeMillis() - lastStatus > 10000) {
                        sendMessage(new TxMsgConnect());
                    }

                    RxMsg msg = readMessage();
                    if (msg instanceof RxMsgStatus) {
                        if (handler != null) {
                            handler.onStatus((RxMsgStatus) msg);
                        }
                    } else if (msg instanceof RxMsgZoneStatus) {
                        lastStatus = System.currentTimeMillis();
                        if (handler != null) {
                            handler.onZoneStatus((RxMsgZoneStatus) msg);
                        }
                    } else if (msg instanceof RxMsgScheduleResponse) {
                        if (handler != null) {
                            handler.onScheduleQueryResponse((RxMsgScheduleResponse) msg);
                        }
                    } else if (msg instanceof RxMsgScheduleResponseComplete) {
                        waitingForQueryResponse = false;
                    }

                    // Check the watchdog timer because the HLK SW seems buggy and occasionally fails to respond to queries
                    if (waitingForQueryResponse) {
                        if (queryWatchDog.checkExpired()) {
                            System.out.println("A timeout occurred waiting for a query response. Will try again.");
                            triggerQuery();
                            waitingForQueryResponse = false;
                        }
                    }

                    if (!waitingForQueryResponse) {
                        for (int i : zones) {
                            if (queryZone((byte) i)) {
                                queryWatchDog.start();
                                waitingForQueryResponse = true;
                                break;
                            }
                        }
                    }

                }

                din.close();

            } catch (IOException e) {
                try {
                    // Consider changing a connected/disconnected icon here
                    Thread.sleep(5000);
                } catch (InterruptedException interruptedException) {
                    AlertDialog alert = new AlertDialog("Fatal Error", "Could not recover from recieve failure.");
                    alert.showAndWait();
                    System.exit(1);
                }
            }
        }
    }

    public void reset(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;

        try {
            socket.close();
            socket = new Socket();
        } catch (IOException e) {
            AlertDialog alert = new AlertDialog("Communication Eror", "Failed to close socket");
            alert.showAndWait();
        }

        exitRequested = false;
    }

    public void stop() {
        exitRequested = true;
    }

    public void setHandler(CommTaskEventHandler handler) {
        this.handler = handler;
    }

    public void triggerQuery() {
        lastScheduleQueryTime.clear();
    }

    private boolean queryZone(byte zone) {
        Date now = new Date();
        if (lastScheduleQueryTime.get(zone) == null
                || now.getTime() - lastScheduleQueryTime.get(zone).getTime() > 30000) {
            sendMessage(new TxMsgQuerySchedule(zone));
            lastScheduleQueryTime.put(zone, new Date());
            return true;
        }
        return false;
    }

    public void sendMessage(TxMsg msg) {
        try {
            byte[] raw = msg.getBytes();

            dout.write(raw);
            dout.flush();
        } catch (IOException e) {
            AlertDialog alert = new AlertDialog("Send Failed", "Failed to send command to device.");
            alert.showAndWait();
        }
    }

    private RxMsg readMessage() throws IOException {
        // Read until we get the start of message signal
        ArrayList<Byte> garbage = readUntil((byte) 0xcc);
        ArrayList<Byte> message = readUntil((byte) 0xdd);

        if (message.get(0) == (byte) 0x1f) {
            return new RxMsgStatus(message);
        }
        if (message.get(0) == (byte) 0x0c) {
            return new RxMsgZoneStatus(message);
        }
        if (message.get(0) == (byte) 0x2e || message.get(0) == (byte) 0x2f) {
            return new RxMsgScheduleResponse(message);
        }
        if (message.get(0) == (byte) 0x1b) {
            return new RxMsgScheduleResponseComplete(message);
        } else {
            // Messages we don't really care about
            return new RxMsg(message);
        }
    }

    private ArrayList<Byte> readUntil(byte match) throws IOException {
        ArrayList<Byte> bytesBeforeMatch = new ArrayList<>();
        byte cur = din.readByte();

        bytesBeforeMatch.add(cur);

        while (cur != match) {
            cur = din.readByte();
            bytesBeforeMatch.add(cur);
        }

        bytesBeforeMatch.remove(bytesBeforeMatch.size() - 1);

        return bytesBeforeMatch;
    }

}
