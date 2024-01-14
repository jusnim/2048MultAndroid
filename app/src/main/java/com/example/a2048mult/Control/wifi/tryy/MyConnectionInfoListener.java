package com.example.a2048mult.Control.wifi.tryy;

import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;

import java.net.InetAddress;

public class MyConnectionInfoListener implements WifiP2pManager.ConnectionInfoListener {
    private Boolean isHost;
    private ConnectionTypeClass connectionTypeClass;

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo wifiP2pInfo) {
        final InetAddress groupOwnerAddress = wifiP2pInfo.groupOwnerAddress;
        if(wifiP2pInfo.groupFormed && wifiP2pInfo.isGroupOwner){
//            textView.setText("Host");
            isHost = true;
            connectionTypeClass = new ServerClass();
            connectionTypeClass.start();
        }else if(wifiP2pInfo.groupFormed){
//            textView.setText("Client");
            isHost = false;
            connectionTypeClass = new ClientClass(groupOwnerAddress);
            connectionTypeClass.start();
        }
    }
    
    public ConnectionTypeClass getConnectionTypeClass(){
        return connectionTypeClass;
    }

    public boolean getIsHost(){
        return this.isHost;
    }
}
