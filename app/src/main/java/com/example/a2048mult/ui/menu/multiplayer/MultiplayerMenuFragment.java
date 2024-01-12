package com.example.a2048mult.ui.menu.multiplayer;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.example.a2048mult.ui.menu.MainActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2048mult.R;
import com.example.a2048mult.databinding.FragmentMultiplayerMenuBinding;
import com.example.a2048mult.game.logic.GameLogic;

public class MultiplayerMenuFragment extends Fragment {

    private FragmentMultiplayerMenuBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.getInstance().BTinit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMultiplayerMenuBinding.inflate(getLayoutInflater());
        binding.buttonChangeUsername.setOnClickListener(
                v -> changeUsername()
        );
        binding.buttonBluetooth.setOnClickListener(
                // TODO remove test
                v -> test()
        );
        binding.buttonCreateLobby.setOnClickListener(
                v -> createLobby()
        );

        return binding.getRoot();
    }

    private void createLobby() {
        Log.e("!","test");
        NavHostFragment.findNavController(this).navigate(R.id.action_multiplayerMenu_to_multiplayerMenuLobbyFragment);
    }



    private void test() {
        binding.connectList.removeView(binding.noLobbyInfo);
        binding.connectList.addView(new LobbyEntryView(getContext()));
    }

    private void changeUsername() {
        String username;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("new username:");
        final EditText input = new EditText(binding.getRoot().getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Change", (dialog, which) ->
                binding.usernameInput.setText(input.getText().toString()));

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
        MainActivity.getInstance().ChangeDeviceName(binding.usernameInput.getText().toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}