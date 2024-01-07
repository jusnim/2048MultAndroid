package com.example.a2048mult.ui.menu;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a2048mult.R;
import com.example.a2048mult.databinding.FragmentSinglePlayerMenuBinding;
public class SinglePlayerMenuFragment extends Fragment {
    private FragmentSinglePlayerMenuBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSinglePlayerMenuBinding.inflate(getLayoutInflater());

        binding.buttonStartSingleplayer.setOnClickListener(
                v -> startSingleplayer()
        );

//        binding.getRoot().addView(new ChooseView(getContext()));
        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void startSingleplayer(){
        NavHostFragment.findNavController(this).navigate(R.id.action_start2_to_multiplayerMenu);
    }
}