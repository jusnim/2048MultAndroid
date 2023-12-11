package com.example.a2048mult.ui.menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a2048mult.R;
import com.example.a2048mult.databinding.FragmentStartBinding;


public class StartFragment extends Fragment {
    private FragmentStartBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentStartBinding.inflate(getLayoutInflater());

        // create click listener SinglePlayerbutton
        binding.buttonSingleplayer.setOnClickListener(
              new View.OnClickListener(){
                  @Override
                  public void onClick(View v) {
                      buttonSingleplayerClick();
                  }
              }
        );

        // create click listener SinglePlayerbutton
        binding.buttonMultiplayer.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        buttonMultiplayerClick();
                    }
                }
        );

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void buttonMultiplayerClick(){
        NavHostFragment.findNavController(this).navigate(R.id.action_start2_to_multiplayerMenu);
    }
    private void buttonSingleplayerClick(){
        NavHostFragment.findNavController(this).navigate(R.id.action_start2_to_singlePlayerMenu);
    }
}