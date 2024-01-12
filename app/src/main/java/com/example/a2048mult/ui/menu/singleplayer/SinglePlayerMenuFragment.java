package com.example.a2048mult.ui.menu.singleplayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.a2048mult.R;
import com.example.a2048mult.databinding.FragmentSinglePlayerMenuBinding;
import com.example.a2048mult.game.logic.GameLogic;
import com.example.a2048mult.game.logic.GameMenuControl;
import com.example.a2048mult.game.states.LobbySettings;
import com.example.a2048mult.game.states.Player;
import com.example.a2048mult.game.states.PlayerImpl;
import com.example.a2048mult.game.states.PlayfieldStateImpl;
import com.example.a2048mult.ui.menu.MenuLobbyChangeListener;

public class SinglePlayerMenuFragment extends Fragment implements MenuLobbyChangeListener {
    private FragmentSinglePlayerMenuBinding binding;
    private GameMenuControl gameControlMenu;
    private LobbySettings lobbySettings;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSinglePlayerMenuBinding.inflate(getLayoutInflater());

        binding.buttonStartSingleplayer.setOnClickListener(
                v -> startSingleplayer()
        );
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void startSingleplayer() {
        this.lobbySettings = new LobbySettings();

        PlayfieldStateImpl playfieldState = new PlayfieldStateImpl();
        playfieldState.setField(new int[][]
                {
                        {1, 0, 1, 1},
                        {0, 1, 1, 1},
                        {1, 1, 1, 1},
                        {0, 0, 0, 0},
                }
        );
        Player player = new PlayerImpl("cICH HABE KEInsad Ahnug was ich HIER mache", 0, playfieldState);
//        this.lobbySettings.setPlayFieldSize(binding.chooseView.getSelectedPlayfieldSize());

        this.lobbySettings.addPlayer(player);
        this.lobbySettings.setLeader(player);

        this.gameControlMenu = GameLogic.getInstance();
        this.gameControlMenu.setLobbySettings(this.lobbySettings);
        this.gameControlMenu.startGame(this, R.id.action_singlePlayerMenu_to_gameFragment);
    }

    @Override
    public void notifyChangeInLobby() {
        // no need to update Lobby in Singleplayer
        // TODO update UI based on gameLogic-object
        this.lobbySettings.getAllPlayer();
        this.lobbySettings.getLeader();
        this.lobbySettings.getPlayFieldSize();
    }
}