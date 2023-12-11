package com.example.a2048mult.Control;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.service.controls.Control;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;




import java.util.ArrayList;

public class BTListAdapter extends BaseAdapter {

    private final Activity app;
    public ArrayList<BluetoothDevice> btDevicesList;
    public BluetoothDevice clickedDevice;

    public BTListAdapter(Activity app) {
        this.app = app;
        btDevicesList = new ArrayList<BluetoothDevice>();
        clickedDevice = null;

    }

    public void add(BluetoothDevice device) {
        btDevicesList.add(device);
    }

    public void clear() {
        btDevicesList.clear();
    }

    public void updateAdapter() {
        notifyDataSetChanged();
    }

    public void setClickedDevice(BluetoothDevice btDevice) {
        clickedDevice = btDevice;
    }
    public BluetoothDevice getClickedBtDevice() {
        return clickedDevice;
    }

    @Override
    public int getCount() {
        return btDevicesList.size();
    }

    @Override
    public Object getItem(int position) {
        return btDevicesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BluetoothDevice btDevice = btDevicesList.get(position);
        TextView textView = new TextView(app.getApplicationContext());
        textView.setText(btDevice.getName() + ": " + btDevice.getAddress());
        return null;
    }
}
