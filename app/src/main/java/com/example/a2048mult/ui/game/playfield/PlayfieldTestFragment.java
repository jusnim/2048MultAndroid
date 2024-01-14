package com.example.a2048mult.ui.game.playfield;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.a2048mult.databinding.FragmentPlayfieldTestBinding;
import com.example.a2048mult.ui.game.InputListener;

public class PlayfieldTestFragment extends Fragment {

    private FragmentPlayfieldTestBinding binding;
    private LinearLayout lm;
    private PlayfieldView playfield;

    public PlayfieldTestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        binding = FragmentPlayfieldTestBinding.inflate(getLayoutInflater());
        lm = new LinearLayout(binding.getRoot().getContext());

//        Toast.makeText(binding.getRoot().getContext(), "lol", Toast.LENGTH_SHORT).show();

        return binding.getRoot();
    }



}