package com.example.a2048mult.Control.wifi.tryy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.a2048mult.ui.menu.MainActivity;
import com.example.simplewifi.MyBroadcastReceiver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WifiManagerTry {

    private final MyPeerListListener peerListListener;
    private final MyConnectionInfoListener connectionInfoListener;
    private Context context;
    private MainActivity mainActivity;
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private com.example.simplewifi.MyBroadcastReceiver receiver;
    private IntentFilter intentFilter = new IntentFilter();
    ;


    // constructor
    public WifiManagerTry(Context context) {
        this.context = context;
        this.mainActivity = (MainActivity) context;
        this.peerListListener = new MyPeerListListener(mainActivity);
        this.connectionInfoListener = new MyConnectionInfoListener();

        init();
    }

    private void init() {
        manager = (WifiP2pManager) mainActivity.getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(context, mainActivity.getMainLooper(), null);
        receiver = new MyBroadcastReceiver(manager, channel, mainActivity, context);

        // Indicates a change in the Wi-Fi Direct status.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);

        // Indicates a change in the list of available peers.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);

        // Indicates the state of Wi-Fi Direct connectivity has changed.
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
    }

    public void openWifiSettings() {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        mainActivity.startActivityForResult(intent, 1);
    }

    public void discoverPeers() {
        if (ActivityCompat.checkSelfPermission(
                context, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context,
                android.Manifest.permission.NEARBY_WIFI_DEVICES)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        manager.discoverPeers(channel, new WifiP2pManager.ActionListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess() {
                Log.e("!", "Discovery started");
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(int reason) {
                Log.e("!", "Discovery failed");
            }
        });
    }

    public void connectToDevice(WifiP2pDevice device) {
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context, android.Manifest.permission.NEARBY_WIFI_DEVICES) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess() {
                Log.e("!", "connected" + device.deviceName);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(int reason) {
                Log.e("!", "not connected");
            }
        });
    }

    public void sendStringToDevice(String string) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            if (string != null && connectionInfoListener.getIsHost()) {
                connectionInfoListener.getConnectionTypeClass().write(string.getBytes());
            } else if (string != null) {
                connectionInfoListener.getConnectionTypeClass().write(string.getBytes());
            }
        });
    }

    /**
     * receiver is called from activity
     * @return
     */
    public MyBroadcastReceiver getReceiver(){
        return this.receiver;
    }

    /**
     * intentFilter is called from activity
     * @return
     */
    public IntentFilter getIndentFilter(){
        return this.intentFilter;
    }

    public WifiP2pDevice[] getDeviceList(){
        return peerListListener.getDeviceList();
    }
    public MyPeerListListener getPeerListListener(){
        return this.peerListListener;
    }

    public MyConnectionInfoListener getConnectionInfoListener(){
        return this.connectionInfoListener;
    }
    // re
//        @Override
//        protected void onResume() {
//            super.onResume();
//           c.registerReceiver(receiver, intentFilter);
//        }
//
//        @Override
//        protected void onPause() {
//            super.onPause();
//            unregisterReceiver(receiver);
//        }
}

