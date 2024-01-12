package com.example.a2048mult.Control;

import static androidx.core.app.ActivityCompat.startActivityForResult;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Pair;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.example.a2048mult.ui.menu.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import kotlin.TuplesKt;

public class BluetoothManager {

    private String ConnectionType;
    public static final String RESUlT_CONN_OK = "connection_OK";
    public static final int SYS_MSG_WHAT = 3;
    public static final String SYS_MSG_KEY = "system_msg";
    public static final String SERVERSOCK_MSG_KEY = "serversocket_thread_msg";
    public static final String RECEIVER_MSG_KEY = "clientsocket_thread_msg";
    public static final int RECEIVER_THREAD_WHAT = 2;
    public static final int SERVERSOCK_THREAD_WHAT = 0;
    public static final String CLIENTSOCK_MSG_KEY = "clientsocket_thread_msg";
    public static final int CLIENTSOCK_THREAD_WHAT = 1;
    private static final int REQUEST_ENABLE_BT = 1;

    public Activity app;

    private final String SERVICE_NAME = "BT_2048";
    private final UUID SERVICE_UUID1 = UUID.fromString("f193cb8f-9353-48d9-b074-da3db1c21f64");
    private final UUID SERVICE_UUID2 = UUID.fromString("26be7207-966e-4512-bb1f-5f53a32d6a81");
    private final UUID SERVICE_UUID3 = UUID.fromString("f0d7cf88-18c2-486c-81a0-0b5d5d91f000");
    private final String LOG_TAG = "BluetoothConnection";
    private final String CHAR_SET = "UTF-8";
    Set<BluetoothDevice> bluetoothPairedDevices;
    private BluetoothAdapter bluetoothAdapter;
    private BTListAdapter btListAdapter;
    private final BroadcastReceiver btBroadcastReceiver = new BroadcastReceiver() {

        @SuppressLint("MissingPermission")
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get Bluetooth devices from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and the address to an array adapter and update it
                Log.d(LOG_TAG, device.getName() + ": " + device.getAddress());
                btListAdapter.add(device);
                btListAdapter.updateAdapter();
            }

        }
    };
    private BtAcceptAsServerThread serverSocketThread;
    private BtMessageReceiverThread messageReceiverThread;
    private BtMessageSender messageSender;
    private BtConnectAsClientThread connectClientSocketThread;
    private ArrayAdapter<String> testMsgAdapter;
    private Handler msgHandler;

    public BluetoothManager(Activity app, /*HandlerMessageCallback hmc,*/ BTListAdapter btListAdapter) {
        this.app = app;
        this.btListAdapter = btListAdapter;

        //Set Handler
        //msgHandler = new BtThreadHandler(hmc);

        //Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        app.registerReceiver(btBroadcastReceiver, filter);

        //Init Bluetooth and start server socket
        //BluetoothDevice btdevice = null;
        //BTinit();
        //btConnectAsServer(UUID);
        //btdevice.getAddress();
    }


    public void ChangeDeviceName(String name) {

        if (ActivityCompat.checkSelfPermission(app, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        bluetoothAdapter.setName(name);

    }

    public void BTinit() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            //device doesnt support Bluetooth
        } else {
            if (!bluetoothAdapter.isEnabled()) {

                Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(app, enableIntent, REQUEST_ENABLE_BT, null);
            } else {
                btOKCallback();
            }
        }
    }

    public Set<Pair<BluetoothDevice, String>> getDevices() {
        findDevices();
        Set tmp = new HashSet<Pair<BluetoothDevice, String>>();

        for (BluetoothDevice i : this.btListAdapter.getDevices()) {
            if (ActivityCompat.checkSelfPermission(app, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                return null;
            }
            Pair<BluetoothDevice, String> temp = new Pair<>(i, i.getName());
            tmp.add(temp);

        }
        return tmp;

    }
    public void findDevices() {
        btListAdapter.clear();
        btListAdapter.updateAdapter();

        Log.d(LOG_TAG, "Async devices discovery...");
        if (ActivityCompat.checkSelfPermission(app, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        bluetoothAdapter.cancelDiscovery();
        if (ActivityCompat.checkSelfPermission(app, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        bluetoothAdapter.startDiscovery();
        btGetKnownDevices();

    }


    public void btGetKnownDevices() {
        // Clear views
        btListAdapter.clear();
        btListAdapter.updateAdapter();

        // Querying paired devices
        if (ActivityCompat.checkSelfPermission(app, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        bluetoothPairedDevices = bluetoothAdapter.getBondedDevices();
        // If there are paired devices
        if (bluetoothPairedDevices.size() > 0) {
            for (BluetoothDevice device : bluetoothPairedDevices) {
                btListAdapter.add(device);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();
            }
        } else {
            Log.d(LOG_TAG, "No paired devices");
        }
    }

    //Make the device discoverable
    public void btMakeDiscoverable() {
        int requestCode = 1;
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        if (ActivityCompat.checkSelfPermission(app, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivityForResult(app, discoverableIntent, requestCode, null);
    }

    public void btManageConnection(BluetoothSocket btSocket) {
        // Start receiver thread
        messageReceiverThread = new BtMessageReceiverThread(this,btSocket);
        messageReceiverThread.start();
        Log.d(LOG_TAG, "Message Receiver starts receiving");
        // Init sender object
        messageSender = new BtMessageSender(this,btSocket);
        Log.d(LOG_TAG, "Sender OK");
    }

    public void btConnectAsServer(String UUID) {
        if (serverSocketThread != null && !serverSocketThread.isInterrupted()) {
            // ServerThread has already been accepting others
            serverSocketThread.cancel();
            serverSocketThread.interrupt();
        }
        serverSocketThread = new BtAcceptAsServerThread(this);
        serverSocketThread.start();
    }

    public void btConnectAsClient(BluetoothDevice btDevice, String UUID) {
        if (btDevice == null) {
            Log.d(LOG_TAG, "[btConnectAsClient] btDevice is null");
        } else {
            if (connectClientSocketThread != null && !connectClientSocketThread.isInterrupted()) {
                // Client socket has been trying to connect
                connectClientSocketThread.cancel();
                connectClientSocketThread.interrupt();
            }
            connectClientSocketThread = new BtConnectAsClientThread(btDevice);
            connectClientSocketThread.start();
        }
    }

    @SuppressLint("MissingPermission")
    public String getBtDeviceName() {
        return bluetoothAdapter.getName();
    }

    public String btOKCallback() {
        String message = "Bluetooth is OK";
        Log.d(LOG_TAG, message);
        // Start server thread
       // btConnectAsServer(UUID);
        return message;
    }

    public void cancelBtActivities() {
        // Interrupt the waiting server socket
        if (serverSocketThread != null) {
            serverSocketThread.cancel();
            if (!serverSocketThread.isInterrupted()) {
                Log.d(LOG_TAG, "Interrupt the waiting server socket thread");
                serverSocketThread.interrupt();
            }
        }
        if (connectClientSocketThread != null) {
            connectClientSocketThread.cancel();
            if (!connectClientSocketThread.isInterrupted()) {
                Log.d(LOG_TAG, "Interrupt the connecting client socket");
                connectClientSocketThread.interrupt();
            }
        }
        if (messageReceiverThread != null) {
            messageReceiverThread.cancel();
            if (!messageReceiverThread.isInterrupted()) {
                Log.d(LOG_TAG, "Interrupt the receiver thread");
                messageReceiverThread.interrupt();
            }
        }
        // Cancel the sender
        if (messageSender != null) {
            messageSender.cancel();
        }
    }

    public ArrayAdapter<String> bindMsgAdapter(ListView listView, int listViewItemLayoutId) {
        testMsgAdapter = new ArrayAdapter<String>(app.getApplicationContext(), listViewItemLayoutId);
        listView.setAdapter(testMsgAdapter);
        return testMsgAdapter;
    }

    public BluetoothManager getInstance(){
        return this;
    }
    public BluetoothAdapter getBluetoothAdapter() {
        return bluetoothAdapter;
    }

    public String getLOG_TAG (){
        return this.LOG_TAG;
    }
    public String getSERVICE_NAME (){
        return this.SERVICE_NAME;
    }

    public UUID getSERVICE_UUID1() {
        return SERVICE_UUID1;
    }

    public UUID getSERVICE_UUID2() {
        return SERVICE_UUID2;
    }

    public UUID getSERVICE_UUID3() {
        return SERVICE_UUID3;
    }

    public void setConnectionType(String connectionType) {
        ConnectionType = connectionType;
    }

    public String getConnectionType() {
        return ConnectionType;
    }

    public Activity getApp() {
        return app;
    }

    public BTListAdapter getBtListAdapter() {
        return btListAdapter;
    }






}
