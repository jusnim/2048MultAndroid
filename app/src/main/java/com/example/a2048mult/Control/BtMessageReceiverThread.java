package com.example.a2048mult.Control;

import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;


    public class BtMessageReceiverThread extends Thread {

        public BluetoothManager btManager;
        public final BluetoothSocket btSocket;
        public final InputStream btIStream;
        private final String CHAR_SET = "UTF-8";


        public BtMessageReceiverThread(BluetoothManager btManager, BluetoothSocket otherSocket) {
            this.btManager = btManager;
            btSocket = otherSocket;
            InputStream tmpIStream = null;

            try {
                Log.d(btManager.getLOG_TAG(), "[BtMessageReceiverThread] Trying to get I/O Stream from BT socket");
                tmpIStream = btSocket.getInputStream();
            } catch (IOException e) {
                Log.d(btManager.getLOG_TAG(), "[BtMessageReceiverThread] IOException while getting I/O Streams");
            }

            btIStream = tmpIStream;
        }

        /**
         * Receive data with Thread function
         */
        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int numBytes;

            // Keep listening to the InputStream until an exception occurs
            Log.d(btManager.getLOG_TAG(), "[ConnectionManager] Listening InputStream fore data");
            while (true) {
                try {
                    numBytes = btIStream.read(buffer);
                    // Log.d(LOG_TAG, "[BtMessageReceiverThread] Read bytes: " + numBytes);
                    // Send the received data to the UI thread
                    String receivedMsg = new String(buffer, 0, numBytes, CHAR_SET);
                    receivedMsg = receivedMsg.trim();
                    //updateUI(receivedMsg);
                } catch (IOException e) {
                    Log.d(btManager.getLOG_TAG(), "[ConnectionManager] IOException while receiving data from the InputStream");
                    break;
                }
            }
        }

        public void cancel() {
            try {
                Log.d(btManager.getLOG_TAG(), "[ConnectionManager] Trying to shutdown the connection");
                btSocket.close();
            } catch (IOException e) {
                Log.d(btManager.getLOG_TAG(), "[ConnectionManager] IOException while canceling");
            }
        }

        /*public void updateUI(String msgString) {
            Bundle msgBundle = new Bundle();
            msgBundle.putString(RECEIVER_MSG_KEY, msgString);
            Message msg = new Message();
            msg.what = RECEIVER_THREAD_WHAT;
            msg.setData(msgBundle);
            msgHandler.sendMessage(msg);
        }*/
    }

