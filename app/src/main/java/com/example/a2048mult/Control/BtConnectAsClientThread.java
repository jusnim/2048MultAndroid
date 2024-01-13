package com.example.a2048mult.Control;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class BtConnectAsClientThread extends Thread {


    private BluetoothManager btManager;

    private final BluetoothSocket btSocket;
    private final BluetoothDevice btDevice;

    private ArrayList<UUID> candidates = new ArrayList<>();

    private int connectionCount = 0;


    public BtConnectAsClientThread(BluetoothManager btManager, BluetoothDevice device) {
        candidates.add(btManager.getSERVICE_UUID1());
        candidates.add(btManager.getSERVICE_UUID2());
        candidates.add(btManager.getSERVICE_UUID3());
        this.btManager = btManager;
        BluetoothSocket tmpSocket = null;
        btDevice = device;
        if (ActivityCompat.checkSelfPermission(btManager.getApp(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            btSocket = null;
            return;
        }
        // Get a BluetoothSocket for connection with the given device
        try {
            Log.d(btManager.getLOG_TAG(), "[BtConnectAsClientThread] Get a BluetoothSocket for connection with the given device");


            tmpSocket = btDevice.createRfcommSocketToServiceRecord(candidates.get(connectionCount));
            connectionCount++;
        } catch (IOException e) {
            Log.d(btManager.getLOG_TAG(), "[BtConnectAsClientThread] IOException occurred on socket creation");
            throw new RuntimeException();

        }

        btSocket = tmpSocket;
    }


    @Override
    public void run() {
        // Cancel discovery because it will slow down the connection
        Log.d(btManager.getLOG_TAG(), "[BtConnectAsClientThread] Cancel discovery for efficiency concern");
        if (ActivityCompat.checkSelfPermission(btManager.getApp(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        btManager.getBluetoothAdapter().cancelDiscovery();


        try {
            // Connect the remote device through the socket
            Log.d(btManager.getLOG_TAG(), "[BtConnectAsClientThread] Try to connect with the device...");


            //updateUI("Trying to connect to " + btDevice.getName());

            // Connect
            btSocket.connect();
        } catch (IOException connectExp) {
            // Unable to connect. Get out immediately
            try {
                Log.d(btManager.getLOG_TAG(), "[BtConnectAsClientThread] IOException connectExp, Close the socket");
                btSocket.close();
                connectionCount--;
            } catch (IOException closeExp) {
                Log.d(btManager.getLOG_TAG(), "[BtConnectAsClientThread] IOException closeExp, Close exception");
            }
            return;
        }

        Log.d(btManager.getLOG_TAG(), "[BtConnectAsClientThread] Connection is established!");
        // Send message to the UI thread
        // updateUI("Connection is established with " + btDevice.getName());
        //updateUI(RESUlT_CONN_OK);

        // Manage the connection
        //btManager.btManageConnection(btSocket);
    }

    // Cancel the blocked connect() and close the socket
    public void cancel() {
        try {
            Log.d(btManager.getLOG_TAG(), "[BtConnectAsClientThread] Close the socket");
            btSocket.close();
        } catch (IOException e) {
            Log.d(btManager.getLOG_TAG(), "[BtConnectAsClientThread] Close exception");
        }
    }

    public BluetoothSocket getBtSocket(){
        return this.btSocket;
    }



}
