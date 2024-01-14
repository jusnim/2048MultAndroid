package com.example.a2048mult.game.currentlyNotUsed;


import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;

public interface ManageConnections {

    /**
     * sets the connection type for future Multiplayer
     * @param type - true, represents Bluetooth, false represents Wi-Fi-Direct
     */
    void setConnectionType(Boolean type);

    /**
     * hands over the List of over found Devices
     * @return Device List for MenuView to display
     */
    ArrayList<ConnectionDevice> getDevices();

    /**
     * connect to the given ConnectionDevice
     * @param connectionDevice
     */
    void connectToDevice(ConnectionDevice connectionDevice);

}
