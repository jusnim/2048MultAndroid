package com.example.a2048mult.ui.menu;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2048mult.Control.BTListAdapter;
import com.example.a2048mult.Control.BluetoothManager;
import com.example.a2048mult.Control.wifi.tryy.WifiManagerTry;
import com.example.a2048mult.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static BluetoothManager btManager;
    // TODO meilenstein4 uml gamestate

    private static WifiManagerTry wifiManager;
    private ActivityMainBinding binding;

    public static BluetoothManager getBTManagerInstance() {
        return btManager;
    }

    public static WifiManagerTry getWifiManagerInstance() {
        return wifiManager;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        android.os.Process.setThreadPriority(0);
        btManager = new BluetoothManager(this, new BTListAdapter(this));
        wifiManager = new WifiManagerTry(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(wifiManager.getReceiver(), wifiManager.getIndentFilter());
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(wifiManager.getReceiver());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(btManager.getBtBroadcastReceiver());
    }

}