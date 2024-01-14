package com.example.a2048mult.Control;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class BtAcceptAsServerThread extends Thread {

    private BluetoothManager btManager;
    public BluetoothServerSocket btServerSocket;

    private BluetoothSocket btSocket;

    private ArrayList<UUID> candidates;


    private int connectionCount = 0;


    public BtAcceptAsServerThread(BluetoothManager btManager) {
        candidates = new ArrayList<UUID>();
        candidates.add(btManager.getSERVICE_UUID1());
        candidates.add(btManager.getSERVICE_UUID2());
        candidates.add(btManager.getSERVICE_UUID3());
        this.btManager = btManager;
        BluetoothServerSocket tmpSocket = null;

    }


    @Override
    public void run() {

        BluetoothServerSocket tmpSocket = null;

        try {
            Log.d(btManager.getLOG_TAG(), "[BtAcceptAsServerThread] Get a BluetoothServerSocket");

            if (ActivityCompat.checkSelfPermission(btManager.getApp(), Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(btManager.app, new String[]{"Manifest.permission.BLUETOOTH_CONNECT"}, 1);
            }
            tmpSocket = btManager.getBluetoothAdapter().listenUsingRfcommWithServiceRecord(btManager.getSERVICE_NAME(), btManager.getSERVICE_UUID1());
            Log.d(btManager.getLOG_TAG(), "ServerSocket created");
        } catch (IOException e) {
            Log.d(btManager.getLOG_TAG(), "[BtAcceptAsServerThread] IOException on BluetoothServerSocket creation");
        }

        this.btServerSocket = tmpSocket;



        while (true) {
            try {
                Log.d(btManager.getLOG_TAG(), "[BtAcceptAsServerThread] Listening and waiting for socket...");
                btSocket = btServerSocket.accept();
            } catch (IOException e) {
                Log.d(btManager.getLOG_TAG(), "[BtAcceptAsServerThread] IOException");
                break;
            }

            if (btSocket != null) {
                Log.d(btManager.getLOG_TAG(), "[BtAcceptAsServerThread] Connection established!");
                // Manage connection
                //btManager.btManageConnection(btSocket);
                try {
                    // Close the useless BluetoothServerSocket
                    btServerSocket.close();
                } catch (IOException e) {
                    Log.d(btManager.getLOG_TAG(), "[BtAcceptAsServerThread] IOException when .close()");
                }

                }
            }
        }




    // Cancel the listening socket and cause the thread to finish
    public void cancel() {
        try {
            btServerSocket.close();
        } catch (IOException e) {
            Log.d(btManager.getLOG_TAG()    , "[BtAcceptThread] IOException when .close()");
        }
    }

    public int getConnectionCount(){
        return connectionCount;
    }

    public BluetoothSocket getBtSocket(){
        return this.btSocket;
    }



}
