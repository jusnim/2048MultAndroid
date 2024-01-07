package com.example.a2048mult.ui.menu.multiplayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2048mult.ui.menu.MenuLobbyChangeListener;
import com.example.a2048mult.R;
import com.example.a2048mult.databinding.FragmentMultiplayerMenuBinding;
import com.example.a2048mult.game.logic.GameLogic;

public class MultiplayerMenuFragment extends Fragment  {

    private FragmentMultiplayerMenuBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMultiplayerMenuBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}