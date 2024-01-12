package com.example.a2048mult.Control;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class BtMessageSender {

    public BluetoothManager btManager;
    private final String CHAR_SET = "UTF-8";
    public final BluetoothSocket btSocket;
    public final OutputStream btOStream;

    public BtMessageSender(BluetoothManager btManager ,BluetoothSocket otherSocket) {
        this.btManager = btManager;
        btSocket = otherSocket;
        OutputStream tmpOStream = null;

        try {
            Log.d(btManager.getLOG_TAG(), "[BtMessageSender] Trying to get I/O Stream from BT socket");
            tmpOStream = btSocket.getOutputStream();
        } catch (IOException e) {
            Log.d(btManager.getLOG_TAG(), "[BtMessageSender] IOException while getting I/O Streams");
        }

        btOStream = tmpOStream;
    }

    public void send(String msg) {
        try {
            byte[] bytes = msg.getBytes(CHAR_SET);
            // Log.d(LOG_TAG, "[BtMessageSender] Trying to write bytes...");
            btOStream.write(bytes);
            btOStream.flush();
        } catch (UnsupportedEncodingException e) {
            Log.e(btManager.getLOG_TAG(), "[BtMessageSender] UnsupportedEncodingException");
        } catch (IOException e) {
            Log.d(btManager.getLOG_TAG(), "[BtMessageSender] IOException while writing OutputStreams");
        }
    }

    public void cancel() {
        try {
            Log.d(btManager.getLOG_TAG(), "[BtMessageSender] Trying to shutdown the connection");
            btSocket.close();
        } catch (IOException e) {
            Log.d(btManager.getLOG_TAG(), "[BtMessageSender] IOException while canceling");
        }
    }
}