package com.example.a2048mult.Control;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;

import androidx.core.app.ActivityCompat;

import com.example.a2048mult.ui.menu.MainActivity;

public class WifiBroadcastReceiver extends BroadcastReceiver {
    private WifiP2pManager manager;
    WifiManager wifiManager;
    WifiP2pManager.Channel channel;
    private MainActivity activity;
    Context context;

    public WifiBroadcastReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel,WifiManager wifiManager, MainActivity activity, Context context){
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
        this.context = context;
        this.wifiManager = wifiManager;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Determine if Wi-Fi Direct mode is enabled or not, alert
            // the Activity.

        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {

            // The peer list has changed! We should probably do something about
            // that.
            if (manager != null) {
                if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context, android.Manifest.permission.NEARBY_WIFI_DEVICES) != PackageManager.PERMISSION_GRANTED) {
                    // T ODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                manager.requestPeers(channel, wifiManager.peerListListener);
            }
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {

            // Connection state changed! We should probably do something about
            // that.
            if(manager != null){
                NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
                if(networkInfo.isConnected()){
                    manager.requestConnectionInfo(channel, wifiManager.connectionInfoListener);
                }else {
                    //activity.textView.setText("Not connected");
                }
            }

        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {

        }
    }
}
