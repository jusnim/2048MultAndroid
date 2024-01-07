package com.example.a2048mult.ui.menu.singleplayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2048mult.ui.menu.MenuLobbyChangeListener;
import com.example.a2048mult.R;
import com.example.a2048mult.databinding.FragmentSinglePlayerMenuBinding;
import com.example.a2048mult.game.logic.GameLogic;
import com.example.a2048mult.game.logic.Player;
import com.example.a2048mult.game.logic.PlayerImpl;
import com.example.a2048mult.game.logic.PlayfieldState;
import com.example.a2048mult.game.logic.PlayfieldStateImpl;

public class SinglePlayerMenuFragment extends Fragment implements MenuLobbyChangeListener {
    private FragmentSinglePlayerMenuBinding binding;
    private GameLogic gameLogic;

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
        this.gameLogic = new GameLogic();

        gameLogic.setPlayFieldSize(binding.chooseView.getSelectedPlayfieldSize());

        PlayfieldState playfieldState = new PlayfieldStateImpl();
        Player player = new PlayerImpl("username", 0, playfieldState);

        gameLogic.addPlayer(player);
        gameLogic.setLeader(player);

        gameLogic.startGame();

        NavHostFragment.findNavController(this).navigate(R.id.action_singlePlayerMenu_to_gameFragment, gameLogic.getBundle());
    }

    @Override
    public void notifyChangeInLobby() {
        // update UI based on gameLogic-object
        this.gameLogic.getAllPlayer();
        this.gameLogic.getLeader();
        this.gameLogic.getPlayFieldSize();
    }
}