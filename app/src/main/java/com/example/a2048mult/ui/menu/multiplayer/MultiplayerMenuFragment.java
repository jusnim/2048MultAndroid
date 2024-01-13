package com.example.a2048mult.ui.menu.multiplayer;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2048mult.Control.ConnectionType;
import com.example.a2048mult.R;
import com.example.a2048mult.databinding.FragmentMultiplayerMenuBinding;
import com.example.a2048mult.ui.menu.MainActivity;

import java.util.Set;

public class MultiplayerMenuFragment extends Fragment {

    private FragmentMultiplayerMenuBinding binding;
    private String username = "coolerGamer";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.getBTManagerInstance().BTinit();
        this.username = MainActivity.getBTManagerInstance().getBtDeviceName();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MainActivity.getBTManagerInstance().findDevices();
        for(BluetoothDevice i : MainActivity.getBTManagerInstance().getBtListAdapter().getDevices()){
            System.out.print(i);
        }
        binding = FragmentMultiplayerMenuBinding.inflate(getLayoutInflater());

        binding.buttonChangeUsername.setOnClickListener(
                v -> changeUsername()
        );
        binding.buttonRefresh.setOnClickListener(
                // TODO remove test
                v -> updateLobbyList()
        );
        binding.buttonCreateLobby.setOnClickListener(
                v -> createLobby()
        );

        binding.usernameInput.setText(this.username);

        return binding.getRoot();
    }

    private void updateLobbyList() {
        MainActivity.getBTManagerInstance().findDevices();
        /*Set<Pair<BluetoothDevice, String>> lobbies = MainActivity.getBTManagerInstance().getDevices();
        lobbies.stream().forEach(
                (lobby) -> {
                    Log.e("!", String.valueOf(lobby.first));
                    Log.e("!", String.valueOf(lobby.second));
                    addNewLobbyEntryToUI(lobby.first, lobby.second);
                }
        );*/
    }

    private void createLobby() {
        Log.e("!", "test");
        NavHostFragment.findNavController(this).navigate(R.id.action_multiplayerMenu_to_multiplayerMenuLobbyFragment);
        MainActivity.getBTManagerInstance().setConnectionType(ConnectionType.Server);
    }


    private void addNewLobbyEntryToUI(BluetoothDevice device, String lobbyname) {
        binding.connectList.removeView(binding.noLobbyInfo);
        LobbyEntryView lobbyEntryView = new LobbyEntryView(getContext());
        lobbyEntryView.setName(lobbyname);
        lobbyEntryView.addLobbyDeviceConnection(device, this);
    }

    private void changeUsername() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("new username:");
        final EditText input = new EditText(binding.getRoot().getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Change", (dialog, which) -> {
            this.username = input.getText().toString();
            binding.usernameInput.setText(input.getText().toString());
            MainActivity.getBTManagerInstance().ChangeDeviceName(this.username);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    public Button getWifiButton(){
        return (Button) binding.getRoot().getViewById(R.id.buttonWiFiDirect);
    }


}