package com.example.a2048mult.ui.menu;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.a2048mult.Control.BTListAdapter;
import com.example.a2048mult.Control.BluetoothManager;
import com.example.a2048mult.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    //private static final BluetoothManager btManager = new BluetoothManager(MainActivity., new BTListAdapter(this));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    /*public static final BluetoothManager getInstance(){
        return btManager;
    }*/
}