package com.example.a2048mult.Control;


import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContextCompat.startActivity;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.UUID;

public class BluetoothConnection {

        private final Activity app;
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
        public static final String RECEIVER_MSG_KEY = "clientsocket_thread_msg";
        public static final int RECEIVER_THREAD_WHAT = 2;
        public static final int SERVERSOCK_THREAD_WHAT = 0;
        public static final String CLIENTSOCK_MSG_KEY = "clientsocket_thread_msg";
        public static final int CLIENTSOCK_THREAD_WHAT = 1;
        private BtAcceptAsServerThread serverSocketThread;
        private BtMessageReceiverThread messageReceiverThread;
        private BtMessageSender messageSender;
        private BtConnectAsClientThread connectClientSocketThread;
        private ArrayAdapter<String> testMsgAdapter;
        private Handler msgHandler;
        private final String CHAR_SET = "UTF-8";

        public BluetoothConnection(Activity app, /*HandlerMessageCallback hmc,*/ BTListAdapter btListAdapter) {
            this.app = app;
            this.btListAdapter = btListAdapter;

            // Set Handler
            //msgHandler = new BtThreadHandler(hmc);

            // Register the BroadcastReceiver
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            // app.registerReceiver(btBroadcastReceiver, filter);

            // Init Bluetooth and start server socket
            BTinit(app);
            // btConnectAsServer();
        }

        public void BTinit(Activity activity) {
            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter == null) {
                //device doesnt support Bluetooth
            } else {
                if (!bluetoothAdapter.isEnabled()) {
                    //TODO
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(activity, enableIntent, REQUEST_ENABLE_BT,null);
                } else {
                    btOKCallback();
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

       private void btManageConnection(BluetoothSocket btSocket) {
            // Start receiver thread
            messageReceiverThread = new BtMessageReceiverThread(btSocket);
            messageReceiverThread.start();
            Log.d(LOG_TAG, "Message Receiver starts receiving");
            // Init sender object
            messageSender = new BtMessageSender(btSocket);
            Log.d(LOG_TAG, "Sender OK");
        }

        public void btConnectAsServer() {
            if (serverSocketThread != null && !serverSocketThread.isInterrupted()) {
                // ServerThread has already been accepting others
                serverSocketThread.cancel();
                serverSocketThread.interrupt();
            }
            serverSocketThread = new BtAcceptAsServerThread();
            serverSocketThread.start();
        }

        public void btConnectAsClient(BluetoothDevice btDevice) {
            if (btDevice == null) {
                Log.d(LOG_TAG, "[btConnectAsClient] btDevice is null");
            }
            else {
                if (connectClientSocketThread != null && !connectClientSocketThread.isInterrupted()) {
                    // Client socket has been trying to connect
                    connectClientSocketThread.cancel();
                    connectClientSocketThread.interrupt();
                }
                connectClientSocketThread = new BtConnectAsClientThread(btDevice);
                connectClientSocketThread.start();
            }
        }

        public String getBtDeviceName() {
            return bluetoothAdapter.getName();
        }

        public String btOKCallback() {
            String message = "Bluetooth is OK";
            Log.d(LOG_TAG, message);
            // Start server thread
            btConnectAsServer();
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

           @Override
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
            }

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
            @Override
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
                }
            }
            public void cancel() {
                try {
                    Log.d(LOG_TAG, "[ConnectionManager] Trying to shutdown the connection");
                    btSocket.close();
                }
                catch (IOException e) {
                    Log.d(LOG_TAG, "[ConnectionManager] IOException while canceling");
                }
            }
            public void updateUI(String msgString) {
                Bundle msgBundle = new Bundle();
                msgBundle.putString(RECEIVER_MSG_KEY, msgString);
                Message msg = new Message();
                msg.what = RECEIVER_THREAD_WHAT;
                msg.setData(msgBundle);
                msgHandler.sendMessage(msg);
            }
        }

        public class BtMessageSender {
            public final BluetoothSocket btSocket;
            public final OutputStream btOStream;

            public BtMessageSender(BluetoothSocket otherSocket) {
                btSocket = otherSocket;
                OutputStream tmpOStream = null;

                try {
                    Log.d(LOG_TAG, "[BtMessageSender] Trying to get I/O Stream from BT socket");
                    tmpOStream = btSocket.getOutputStream();
                }
                catch (IOException e) {
                    Log.d(LOG_TAG, "[BtMessageSender] IOException while getting I/O Streams");
                }

                btOStream = tmpOStream;
            }

            public void send(String msg) {
                try {
                    byte[] bytes = msg.getBytes(CHAR_SET);
                    // Log.d(LOG_TAG, "[BtMessageSender] Trying to write bytes...");
                    btOStream.write(bytes);
                    btOStream.flush();
                }
                catch (UnsupportedEncodingException e) {
                    Log.e(LOG_TAG, "[BtMessageSender] UnsupportedEncodingException");
                }
                catch (IOException e) {
                    Log.d(LOG_TAG, "[BtMessageSender] IOException while writing OutputStreams");
                }
            }

            public void cancel() {
                try {
                    Log.d(LOG_TAG, "[BtMessageSender] Trying to shutdown the connection");
                    btSocket.close();
                }
                catch (IOException e) {
                    Log.d(LOG_TAG, "[BtMessageSender] IOException while canceling");
                }
            }
        }

        public class BtConnectAsClientThread extends Thread {
            public final BluetoothSocket btSocket;
            public final BluetoothDevice btDevice;

            public BtConnectAsClientThread(BluetoothDevice device) {
                BluetoothSocket tmpSocket = null;
                btDevice = device;

                // Get a BluetoothSocket for connection with the given device
                try {
                    Log.d(LOG_TAG, "[BtConnectAsClientThread] Get a BluetoothSocket for connection with the given device");
                    tmpSocket = btDevice.createRfcommSocketToServiceRecord(SERVICE_UUID);
                }
                catch (IOException e) {
                    Log.d(LOG_TAG, "[BtConnectAsClientThread] IOException occurred on socket creation");
                }

                btSocket = tmpSocket;
            }

            @Override
            public void run() {
                // Cancel discovery because it will slow down the connection
                Log.d(LOG_TAG, "[BtConnectAsClientThread] Cancel discovery for efficiency concern");
                bluetoothAdapter.cancelDiscovery();

                try {
                    // Connect the remote device through the socket
                    Log.d(LOG_TAG, "[BtConnectAsClientThread] Try to connect with the device...");

                    // Send message to the UI thread
                    updateUI("Trying to connect to " + btDevice.getName());

                    // Connect
                    btSocket.connect();
                }
                catch (IOException connectExp) {
                    // Unable to connect. Get out immediately
                    try {
                        updateUI("Failed to connecto to "+btDevice.getName());
                        String conExpMsg = connectExp.getMessage();
                        Log.d(LOG_TAG, "[BtConnectAsClientThread] IOException connectExp, Close the socket");
                        Log.d(LOG_TAG, "Message: " + conExpMsg);
                        btSocket.close();
                    }
                    catch (IOException closeExp) {
                        Log.d(LOG_TAG, "[BtConnectAsClientThread] IOException closeExp, Close exception");
                    }
                    return;
                }

                Log.d(LOG_TAG, "[BtConnectAsClientThread] Connection is established!");
                // Send message to the UI thread
                // updateUI("Connection is established with " + btDevice.getName());
                updateUI(RESUlT_CONN_OK);

                // Manage the connection
                btManageConnection(btSocket);
            }

            // Cancel the blocked connect() and close the socket
            public void cancel() {
                try {
                    Log.d(LOG_TAG, "[BtConnectAsClientThread] Close the socket");
                    btSocket.close();
                }
                catch (IOException e) {
                    Log.d(LOG_TAG, "[BtConnectAsClientThread] Close exception");
                }
            }

            public void updateUI(String msgString) {
                Bundle msgBundle = new Bundle();
                msgBundle.putString(CLIENTSOCK_MSG_KEY, msgString);
                Message msg = new Message();
                msg.what = CLIENTSOCK_THREAD_WHAT;
                msg.setData(msgBundle);
                msgHandler.sendMessage(msg);
            }
        }
}
