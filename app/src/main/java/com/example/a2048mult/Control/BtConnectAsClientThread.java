//package com.example.a2048mult.Control;
//
//import android.annotation.SuppressLint;
//import android.bluetooth.BluetoothDevice;
//import android.bluetooth.BluetoothSocket;
//import android.util.Log;
//
//import java.io.IOException;
//
//public class BtConnectAsClientThread extends Thread{
//
//
//    private BluetoothManager btManager;
//
//    public final BluetoothSocket btSocket;
//    public final BluetoothDevice btDevice;
//
//    @SuppressLint("MissingPermission")
//    public BtConnectAsClientThread(BluetoothDevice device, String UUID) {
//        this.btManager = btManager;
//        BluetoothSocket tmpSocket = null;
//        btDevice = device;
//
//        // Get a BluetoothSocket for connection with the given device
//        try {
//        Log.d(btManager.getLOG_TAG(), "[BtConnectAsClientThread] Get a BluetoothSocket for connection with the given device");
//        tmpSocket = btDevice.createRfcommSocketToServiceRecord(java.util.UUID.fromString(UUID));
//        } catch (IOException e) {
//        Log.d(btManager.getLOG_TAG(), "[BtConnectAsClientThread] IOException occurred on socket creation");
//        }
//
//        btSocket = tmpSocket;
//        }
//
//@SuppressLint("MissingPermission")
//@Override
//public void run() {
//        // Cancel discovery because it will slow down the connection
//        Log.d(btManager.getLOG_TAG(), "[BtConnectAsClientThread] Cancel discovery for efficiency concern");
//        btManager.getBluetoothAdapter().cancelDiscovery();
//
//        try {
//        // Connect the remote device through the socket
//        Log.d(btManager.getLOG_TAG(), "[BtConnectAsClientThread] Try to connect with the device...");
//
//        // Send message to the UI thread
//        //updateUI("Trying to connect to " + btDevice.getName());
//
//        // Connect
//        btSocket.connect();
//        } catch (IOException connectExp) {
//        // Unable to connect. Get out immediately
//        try {
//        //updateUI("Failed to connecto to " + btDevice.getName());
//        String conExpMsg = connectExp.getMessage();
//        Log.d(btManager.getLOG_TAG(), "[BtConnectAsClientThread] IOException connectExp, Close the socket");
//        Log.d(btManager.getLOG_TAG(), "Message: " + conExpMsg);
//        btSocket.close();
//        } catch (IOException closeExp) {
//        Log.d(btManager.getLOG_TAG(), "[BtConnectAsClientThread] IOException closeExp, Close exception");
//        }
//        return;
//        }
//
//        Log.d(btManager.getLOG_TAG(), "[BtConnectAsClientThread] Connection is established!");
//        // Send message to the UI thread
//        // updateUI("Connection is established with " + btDevice.getName());
//        //updateUI(RESUlT_CONN_OK);
//
//        // Manage the connection
//        btManager.btManageConnection(btSocket);
//        }
//
//// Cancel the blocked connect() and close the socket
//public void cancel() {
//        try {
//        Log.d(btManager.getLOG_TAG(), "[BtConnectAsClientThread] Close the socket");
//        btSocket.close();
//        } catch (IOException e) {
//        Log.d(btManager.getLOG_TAG(), "[BtConnectAsClientThread] Close exception");
//        }
//        }
//
///*public void updateUI(String msgString) {
//        Bundle msgBundle = new Bundle();
//        msgBundle.putString(CLIENTSOCK_MSG_KEY, msgString);
//        Message msg = new Message();
//        msg.what = CLIENTSOCK_THREAD_WHAT;
//        msg.setData(msgBundle);
//        msgHandler.sendMessage(msg);
//        }*/
//}
