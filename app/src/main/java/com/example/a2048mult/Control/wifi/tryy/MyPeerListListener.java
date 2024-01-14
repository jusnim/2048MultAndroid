package com.example.a2048mult.Control.wifi.tryy;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;

import com.example.a2048mult.ui.menu.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class MyPeerListListener implements WifiP2pManager.PeerListListener {

    private final MainActivity mainActivity;
    private List<WifiP2pDevice> peers = new ArrayList<>();
    private String[] deviceNameArray;
    private WifiP2pDevice[] deviceArray;

    public MyPeerListListener(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
        if (!wifiP2pDeviceList.getDeviceList().equals(peers)) {
            peers.clear();
            peers.addAll(wifiP2pDeviceList.getDeviceList());

            deviceNameArray = new String[wifiP2pDeviceList.getDeviceList().size()];
            deviceArray = new WifiP2pDevice[wifiP2pDeviceList.getDeviceList().size()];

            int index = 0;
            for (WifiP2pDevice device : wifiP2pDeviceList.getDeviceList()) {
                deviceNameArray[index] = device.deviceName;
                deviceArray[index] = device;
                index++;
            }
        }
    }

    public WifiP2pDevice[] getDeviceList() {
        return this.deviceArray;
    }
}
