//package com.example.a2048mult.ui.menu.multiplayer;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.fragment.app.Fragment;
//import androidx.navigation.fragment.NavHostFragment;
//
//import com.example.a2048mult.R;
//import com.example.a2048mult.databinding.FragmentMultiplayerMenuLobbyBinding;
//import com.example.a2048mult.game.logic.GameLogic;
//import com.example.a2048mult.ui.menu.MenuLobbyChangeListener;
//
//public class MultiplayerMenuLobbyFragment extends Fragment implements MenuLobbyChangeListener {
//    private FragmentMultiplayerMenuLobbyBinding binding;
//    private GameLogic gameLogic;
//
//    public MultiplayerMenuLobbyFragment() {
//        // Required empty public constructor
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        binding = FragmentMultiplayerMenuLobbyBinding.inflate(getLayoutInflater());
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//
//    @Override
//    public void notifyChangeInLobby() {
////        this.gameLogic.getAllPlayer();
////        this.gameLogic.getLeader();
////        this.gameLogic.getPlayFieldSize();
//        if (this.gameLogic.getGameStarted()) {
//            NavHostFragment.findNavController(this).navigate(R.id.action_multiplayerMenuLobbyFragment_to_gameFragment, gameLogic.getBundle());
//        }
//    }
//
//}