package com.example.a2048mult.Control;


import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.a2048mult.GameAppearance.Menu.MultiplayerMenuFragment;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class BluetoothConnection {

    private static final int REQUEST_ENABLE_BT = 1;
    private final String SERVICE_NAME = "BT_PiKaChu";
    private final UUID SERVICE_UUID = UUID.fromString("821897b0-0ae0-11e5-b939-0800200c9a66");
    private final String LOG_TAG = "BluetoothConnection";
    private BluetoothAdapter bluetoothAdapter;
    private BTListAdapter btListAdapter;
    Set<BluetoothDevice> bluetoothPairedDevices;
    public static final String RESUlT_CONN_OK = "connection_OK";
    public static final int SYS_MSG_WHAT = 3;
    public static final String SYS_MSG_KEY = "system_msg";
    public static final String SERVERSOCK_MSG_KEY = "serversocket_thread_msg";
    public static final int SERVERSOCK_THREAD_WHAT = 0;
    private BtAcceptAsServerThread serverSocketThread;
    private Handler msgHandler;
    private final String CHAR_SET = "UTF-8";


    public void BTinit(Activity activity) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            //device doesnt support Bluetooth
        } else {
            if (!bluetoothAdapter.isEnabled()) {
                //TODO
                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(activity, enableIntent, REQUEST_ENABLE_BT,null);
            }
        }
    }

    public void BTfindDevices() {
        btListAdapter.clear();
        btListAdapter.updateAdapter();

        Log.d(LOG_TAG, "Async devices discovery...");
        Bundle msgBundle = new Bundle();
        msgBundle.putString(SYS_MSG_KEY, "Async devices discovery...");
        Message msg = new Message();
        msg.what = SYS_MSG_WHAT;
        msg.setData(msgBundle);
        msgHandler.sendMessage(msg);

        bluetoothAdapter.cancelDiscovery();
        bluetoothAdapter.startDiscovery();
    }

    public void btGetKnownDevices() {
        // Clear views
        btListAdapter.clear();
        btListAdapter.updateAdapter();

        // Querying paired devices
        bluetoothPairedDevices = bluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (bluetoothPairedDevices.size() > 0) {
            for (BluetoothDevice device : bluetoothPairedDevices) {
                btListAdapter.add(device);
            }
        }
        else {
            Log.d(LOG_TAG, "No paired devices");
        }
    }

    //Make the device discoverable
    public void btMakeDiscoverable(Activity activity) {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        startActivity(activity,discoverableIntent,null);
    }

   /* private void btManageConnection(BluetoothSocket btSocket) {
        // Start receiver thread
        messageReceiverThread = new BtMessageReceiverThread(btSocket);
        messageReceiverThread.start();
        Log.d(LOG_TAG, "Message Receiver starts receiving");
        // Init sender object
        messageSender = new BtMessageSender(btSocket);
        Log.d(LOG_TAG, "Sender OK");
    }*/

    public void btConnectAsServer() {
        if (serverSocketThread != null && !serverSocketThread.isInterrupted()) {
            // ServerThread has already been accepting others
            serverSocketThread.cancel();
            serverSocketThread.interrupt();
        }
        serverSocketThread = new BtAcceptAsServerThread();
        serverSocketThread.start();
    }



    //
    public class BtAcceptAsServerThread extends Thread {
        public final BluetoothServerSocket btServerSocket;

        public BtAcceptAsServerThread() {
            BluetoothServerSocket tmpSocket = null;

            try {
                Log.d(LOG_TAG, "[BtAcceptAsServerThread] Get a BluetoothServerSocket");
                tmpSocket = bluetoothAdapter.listenUsingRfcommWithServiceRecord(SERVICE_NAME, SERVICE_UUID);
            }
            catch (IOException e) {
                Log.d(LOG_TAG, "[BtAcceptAsServerThread] IOException on BluetoothServerSocket creation");
            }

            btServerSocket = tmpSocket;
        }

       /* @Override
        public void run() {
            BluetoothSocket btSocket = null;

            while (true) {
                try {
                    Log.d(LOG_TAG, "[BtAcceptAsServerThread] Listening and waiting for socket...");
                    btSocket = btServerSocket.accept();
                }
                catch (IOException e) {
                    Log.d(LOG_TAG, "[BtAcceptAsServerThread] IOException");
                    break;
                }

                if (btSocket != null) {
                    Log.d(LOG_TAG, "[BtAcceptAsServerThread] Connection established!");
                    // updateUI("Connection established with: " + btSocket.getRemoteDevice().getName());
                    updateUI(RESUlT_CONN_OK);

                    // Manage connection
                    btManageConnection(btSocket);

                    try {
                        // Close the useless BluetoothServerSocket
                        btServerSocket.close();
                    }
                    catch (IOException e) {
                        Log.d(LOG_TAG, "[BtAcceptAsServerThread] IOException when .close()");
                    }
                }
            }
        }*/

        // Cancel the listening socket and cause the thread to finish
        public void cancel() {
            try {
                btServerSocket.close();
            }
            catch (IOException e) {
                Log.d(LOG_TAG, "[BtAcceptThread] IOException when .close()");
            }
        }

        public void updateUI(String msgString) {
            Bundle msgBundle = new Bundle();
            msgBundle.putString(SERVERSOCK_MSG_KEY, msgString);
            Message msg = new Message();
            msg.what = SERVERSOCK_THREAD_WHAT;
            msg.setData(msgBundle);

            msgHandler.sendMessage(msg);
        }
    }


    public class BtMessageReceiverThread extends Thread {
        public final BluetoothSocket btSocket;
        public final InputStream btIStream;;

        public BtMessageReceiverThread(BluetoothSocket otherSocket) {
            btSocket = otherSocket;
            InputStream tmpIStream = null;

            try {
                Log.d(LOG_TAG, "[BtMessageReceiverThread] Trying to get I/O Stream from BT socket");
                tmpIStream = btSocket.getInputStream();
            }
            catch (IOException e) {
                Log.d(LOG_TAG, "[BtMessageReceiverThread] IOException while getting I/O Streams");
            }

            btIStream = tmpIStream;
        }

        /**
         * Receive data with Thread function
         */
        /*@Override
        public void run() {
            byte[] buffer = new byte[1024];
            int numBytes;

            // Keep listening to the InputStream until an exception occurs
            Log.d(LOG_TAG, "[ConnectionManager] Listening InputStream fore data");
            while (true) {
                try {
                    numBytes = btIStream.read(buffer);
                    // Log.d(LOG_TAG, "[BtMessageReceiverThread] Read bytes: " + numBytes);
                    // Send the received data to the UI thread
                    String receivedMsg = new String(buffer, 0, numBytes, CHAR_SET);
                    receivedMsg = receivedMsg.trim();
                    updateUI(receivedMsg);
                }
                catch (IOException e) {
                    Log.d(LOG_TAG, "[ConnectionManager] IOException while receiving data from the InputStream");
                    break;
                }
            }*/
        }
}
