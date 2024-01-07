package com.example.a2048mult.ui.menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2048mult.ui.game.GameFragment;
import com.example.a2048mult.R;
import com.example.a2048mult.databinding.FragmentSinglePlayerMenuBinding;
import com.example.a2048mult.game.GameState;
import com.example.a2048mult.game.GameStateImpl;
import com.example.a2048mult.game.logic.Player;
import com.example.a2048mult.game.logic.PlayerImpl;
import com.example.a2048mult.game.logic.PlayfieldState;
import com.example.a2048mult.game.logic.PlayfieldStateImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

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

    private void startSingleplayer() {

        // init playfield
        PlayfieldState playfieldState = new PlayfieldStateImpl(binding.chooseView.getSelectedPlayfieldSize());
        Player[] players = {new PlayerImpl("test", 0, playfieldState)};
        GameState gameState = new GameStateImpl(players);

        byte[] gamestateContent;
        try {
            // serialize object to byte Array
            ByteArrayOutputStream osB = new ByteArrayOutputStream();
            ObjectOutputStream osO = new ObjectOutputStream(osB);
            osO.writeObject(gameState);
            osB.flush();
            gamestateContent =osB.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Bundle bundle = new Bundle();
        bundle.putByteArray(GameFragment.GAMESTATE, gamestateContent);

        NavHostFragment.findNavController(this).navigate(R.id.action_singlePlayerMenu_to_gameFragment, bundle);
    }
}