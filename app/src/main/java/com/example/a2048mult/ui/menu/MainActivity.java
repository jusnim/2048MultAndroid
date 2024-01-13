package com.example.a2048mult.ui.menu;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2048mult.Control.BTListAdapter;
import com.example.a2048mult.Control.BluetoothManager;
import com.example.a2048mult.Control.WifiManager;
import com.example.a2048mult.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static BluetoothManager btManager;
    // TODO meilenstein4 uml gamestate

    private WifiManager wifiManager;
    private ActivityMainBinding binding;

    public static BluetoothManager getBTManagerInstance() {
        return btManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        android.os.Process.setThreadPriority(0);
        btManager = new BluetoothManager(this, new BTListAdapter(this));
        wifiManager = new WifiManager(this, (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(btManager.getBtBroadcastReceiver());
    }
}