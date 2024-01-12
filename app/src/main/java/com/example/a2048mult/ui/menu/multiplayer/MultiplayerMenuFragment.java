package com.example.a2048mult.ui.menu.multiplayer;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.a2048mult.Control.ConnectionType;
import com.example.a2048mult.ui.menu.MainActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2048mult.R;
import com.example.a2048mult.databinding.FragmentMultiplayerMenuBinding;

import java.util.Set;

public class MultiplayerMenuFragment extends Fragment {

    private FragmentMultiplayerMenuBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.getBTManagerInstance().BTinit();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMultiplayerMenuBinding.inflate(getLayoutInflater());

        binding.buttonChangeUsername.setOnClickListener(
                v -> changeUsername()
        );
        binding.buttonBluetooth.setOnClickListener(
                // TODO remove test
                v -> addNewLobby(null,"test")
        );
        binding.buttonBluetooth.setOnClickListener(
                // TODO remove test
                v -> test2()
        );
        binding.buttonCreateLobby.setOnClickListener(
                v -> createLobby()
        );

        return binding.getRoot();
    }

    private void test2() {
        Set<Pair<BluetoothDevice,String>> lobbies = MainActivity.getBTManagerInstance().getDevices();
        lobbies.stream().forEach(
                (lobby) -> addNewLobby(lobby.first, lobby.second)
        );
    }

    private void createLobby() {
        Log.e("!","test");
        NavHostFragment.findNavController(this).navigate(R.id.action_multiplayerMenu_to_multiplayerMenuLobbyFragment);
        MainActivity.getBTManagerInstance().setConnectionType(ConnectionType.Server);
    }


    private void addNewLobby(BluetoothDevice device, String lobbyname) {
        binding.connectList.removeView(binding.noLobbyInfo);
        LobbyEntryView lobbyEntryView = new LobbyEntryView(getContext());
        lobbyEntryView.setName(lobbyname);
        lobbyEntryView.addLobbyDeviceConnection(device);
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

        MainActivity.getBTManagerInstance().ChangeDeviceName(binding.usernameInput.getText().toString());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}