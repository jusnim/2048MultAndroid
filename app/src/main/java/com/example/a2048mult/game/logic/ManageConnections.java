package com.example.a2048mult.game.logic;


import android.bluetooth.BluetoothDevice;

import java.util.ArrayList;

public interface ManageConnections {

    /**
     * hands over the List of over Bluetooth found Devices
     * @return BluetoothDevice List for MenuView to display
     */
    ArrayList<BluetoothDevice> getDevices();

    /**
     * set the clickedDevice inside the BluetoothConnectionManager to the actually clicked on Devics in MenuView
     * @param bluetoothDevice the the clicked on Device with wich the connection is supposed to be established
     */
    void setclickedDevice(BluetoothDevice bluetoothDevice);

}
