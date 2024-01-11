package com.example.a2048mult.Control;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class BtAcceptAsServerThread extends Thread {

    private BluetoothManager btManager;
    public final BluetoothServerSocket btServerSocket;

    private ArrayList<UUID> candidates;



    private int connectionCount = 0;


    @SuppressLint("MissingPermission")
    public BtAcceptAsServerThread(BluetoothManager btManager) {
        candidates = new ArrayList<UUID>();
        candidates.add(btManager.getSERVICE_UUID1());
        candidates.add(btManager.getSERVICE_UUID2());
        candidates.add(btManager.getSERVICE_UUID3());
        this.btManager = btManager;
        BluetoothServerSocket tmpSocket = null;

        try {
            Log.d(btManager.getLOG_TAG(), "[BtAcceptAsServerThread] Get a BluetoothServerSocket");
            tmpSocket = btManager.getBluetoothAdapter().listenUsingRfcommWithServiceRecord(btManager.getSERVICE_NAME(),candidates.get(connectionCount));
        } catch (IOException e) {
            Log.d(btManager.getLOG_TAG(), "[BtAcceptAsServerThread] IOException on BluetoothServerSocket creation");
        }

        btServerSocket = tmpSocket;
    }

    @Override
    public void run() {
        BluetoothSocket btSocket = null;

        while (true) {
            try {
                Log.d(btManager.getLOG_TAG(), "[BtAcceptAsServerThread] Listening and waiting for socket...");
                btSocket = btServerSocket.accept();
                connectionCount += 1;
            } catch (IOException e) {
                Log.d(btManager.getLOG_TAG(), "[BtAcceptAsServerThread] IOException");
                break;
            }

            if (btSocket != null) {
                Log.d(btManager.getLOG_TAG(), "[BtAcceptAsServerThread] Connection established!");
                // updateUI("Connection established with: " + btSocket.getRemoteDevice().getName());
                //updateUI(RESUlT_CONN_OK);

                // Manage connection
                btManager.btManageConnection(btSocket);

                if(connectionCount == 3) {
                    try {
                        // Close the useless BluetoothServerSocket
                        btServerSocket.close();
                    } catch (IOException e) {
                        Log.d(btManager.getLOG_TAG(), "[BtAcceptAsServerThread] IOException when .close()");
                    }
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

   /* public void updateUI(String msgString) {
        Bundle msgBundle = new Bundle();
        msgBundle.putString(SERVERSOCK_MSG_KEY, msgString);
        Message msg = new Message();
        msg.what = SERVERSOCK_THREAD_WHAT;
        msg.setData(msgBundle);

        msgHandler.sendMessage(msg);
    }*/
}
