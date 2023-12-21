package com.example.a2048mult.ui.menu;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.a2048mult.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}