package com.example.a2048mult.Control.wifi;

import static android.os.Looper.getMainLooper;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;

import androidx.core.app.ActivityCompat;

import com.example.a2048mult.ui.menu.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WifiManager {
    private final IntentFilter intentFilter = new IntentFilter();
    WifiP2pManager manager;
    WifiP2pManager.Channel channel;
    BroadcastReceiver receiver;

    Button wifiButton;

    List<WifiP2pDevice> peers = new ArrayList<WifiP2pDevice>();
    String[] deviceNameArray;
    WifiP2pDevice[] deviceArray;
    Socket socket;


    boolean isHost;
    MainActivity activity;
    Context context;

    public WifiManager(MainActivity activity, WifiP2pManager manager){
        this.activity = activity;
        context = activity;
        this.manager = manager;
        channel = manager.initialize(context, getMainLooper(), null);
        receiver = new WifiBroadcastReceiver(manager, channel,this,  activity, context);

        // Indicates a change in the Wi-Fi Direct status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi Direct connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);

        //wifiButton = MultiplayerMenuFragment.getWifiButton();TODO

        // TODO start
//        exqListener();
    }

    public void startDiscovery(){
        if (ActivityCompat.checkSelfPermission(context,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, android.Manifest.permission.NEARBY_WIFI_DEVICES)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e("!", "whattf");
            return;
        }
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess() {
                Log.i("!","Discovery started");
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(int reason) {
               Log.i("!", "Discovery failed");
            }
        });
    }

    public WifiP2pDevice[] getWifiDevices(){
        return this.deviceArray;
    };

    public void connectToDevice(WifiP2pDevice device){
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context, android.Manifest.permission.NEARBY_WIFI_DEVICES) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;

        manager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess() {
                Log.i("!", "Connected" + device.deviceName);
//                textView.setText("Connected" + device.deviceName);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(int reason) {
                Log.e("!","not connected");
//                textView.setText("Not Connected");
            }
        });
    }

    WifiP2pManager.PeerListListener peerListListener = new WifiP2pManager.PeerListListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onPeersAvailable(WifiP2pDeviceList peersA) {
            if(!peersA.getDeviceList().equals(peers)){
                peers.clear();
                peers.addAll(peersA.getDeviceList());

                deviceNameArray = new String[peersA.getDeviceList().size()];
                deviceArray = new WifiP2pDevice[peersA.getDeviceList().size()];

                int index = 0;
                for(WifiP2pDevice device: peersA.getDeviceList()){
                    deviceNameArray[index] = device.deviceName;
                    deviceArray[index] = device;
                    index++;
                }

                //ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, deviceNameArray);
                //listView.setAdapter(adapter);

                if(peers.size()==0){
                    Log.d("Wiiifiii","no device found");
                }
            }
        }
    };

    WifiP2pManager.ConnectionInfoListener connectionInfoListener = new WifiP2pManager.ConnectionInfoListener() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onConnectionInfoAvailable(WifiP2pInfo info) {
            final InetAddress groupOwnerAddress = info.groupOwnerAddress;
            if(info.groupFormed && info.isGroupOwner){
                Log.d("Wiiifiii", "Host");
                isHost = true;
                //serverClass = new ServerClass();
                //serverClass.start();
            }else if(info.groupFormed){
                Log.d("Wiiifiii","Client");
                isHost = false;
                //clientClass = new ClientClass(groupOwnerAddress);
                //clientClass.start();
            }
        }
    };


    public class ServerClass extends Thread {
        ServerSocket serverSocket;
        private InputStream inputStream;
        private OutputStream outputStream;

        public void write(byte[] bytes){
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(8888);
                socket = serverSocket.accept();
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    byte[] buffer = new byte[1024];
                    int bytes;

                    while(socket!= null ){
                        try {
                            bytes = inputStream.read(buffer);
                            if(bytes > 0 ){
                                int finalBytes = bytes;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String tempMSG = new String(buffer, 0, finalBytes);
                                        //textView.setText(tempMSG);
                                    }
                                });
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }
    }

    public class ClientClass extends Thread{
        String hostAdd;
        private InputStream inputStream;
        private OutputStream outputStream;

        public ClientClass(InetAddress hostAddress){
            hostAdd = hostAddress.getHostAddress();
            socket = new Socket();

        }

        public void write(byte[] bytes){
            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            try {
                socket.connect(new InetSocketAddress(hostAdd, 8888), 500);
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(new Runnable() {
                @Override
                public void run() {
                    byte[] buffer = new byte[1024];
                    int bytes;

                    while (socket!= null){
                        try {
                            bytes = inputStream.read(buffer);
                            if(bytes > 0){
                                int finalBytes = bytes;
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String tmpMSG = new String(buffer, 0, finalBytes);
                                        //textView.setText(tmpMSG);
                                    }
                                });
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });

        }
    }
}
