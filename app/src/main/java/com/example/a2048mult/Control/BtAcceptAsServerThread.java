package com.example.a2048mult.Control;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;

public class BtAcceptAsServerThread extends Thread {

    private BluetoothManager btManager;
    public final BluetoothServerSocket btServerSocket;


    @SuppressLint("MissingPermission")
    public BtAcceptAsServerThread(BluetoothManager btManager, String UUID) {
        this.btManager = btManager;
        BluetoothServerSocket tmpSocket = null;

        try {
            Log.d(btManager.getLOG_TAG(), "[BtAcceptAsServerThread] Get a BluetoothServerSocket");
            tmpSocket = btManager.getBluetoothAdapter().listenUsingRfcommWithServiceRecord(btManager.getSERVICE_NAME(), java.util.UUID.fromString(UUID));
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

   /* public void updateUI(String msgString) {
        Bundle msgBundle = new Bundle();
        msgBundle.putString(SERVERSOCK_MSG_KEY, msgString);
        Message msg = new Message();
        msg.what = SERVERSOCK_THREAD_WHAT;
        msg.setData(msgBundle);

        msgHandler.sendMessage(msg);
    }*/
}
