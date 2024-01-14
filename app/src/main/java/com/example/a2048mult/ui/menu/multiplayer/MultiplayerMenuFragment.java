package com.example.a2048mult.ui.menu.multiplayer;

import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class MultiplayerMenuFragment extends Fragment {

    private FragmentMultiplayerMenuBinding binding;
    private String username = "coolerGamer";
    private List<LobbyEntryView> lobbyEntryViewList = new ArrayList<>();

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
        binding.buttonWiFiDirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLobbyListWiFi();

            }
        });

        binding.buttonCreateLobby.setOnClickListener(
                v -> createLobby()
        );

        binding.usernameInput.setText(this.username);

        return binding.getRoot();
    }

    private void updateLobbyListWiFi(){
        MainActivity.getWifiManagerInstance().discoverPeers();
        Runnable r = () ->{
            Log.e("!", Arrays.toString(MainActivity.getWifiManagerInstance().getDeviceList()));

        };

        HandlerThread handlerThread = new HandlerThread("thread", 3);
        handlerThread.start();
        Handler handler = new Handler( handlerThread.getLooper());
        handler.postDelayed(r, 2000);



    }
    private void updateLobbyList() {
//        MainActivity.getBTManagerInstance().findDevices();
        deleteLobbiesFromUI();
        Set<Pair<BluetoothDevice, String>> lobbies = MainActivity.getBTManagerInstance().getDevices();
        lobbies.stream().forEach(
                (lobby) -> {
                    if(lobby.first != null){
                        addNewLobbyEntryToUI(lobby.first, lobby.second);
                    }
                }
        );
    }

    private void deleteLobbiesFromUI(){
        lobbyEntryViewList.forEach(
                (lobby) ->binding.connectList.removeView(lobby)
        );
        lobbyEntryViewList = new ArrayList<>();

    }
    private void createLobby() {
        Log.e("!", "test");
        NavHostFragment.findNavController(this).navigate(R.id.action_multiplayerMenu_to_multiplayerMenuLobbyFragment);
        MainActivity.getBTManagerInstance().setConnectionType(ConnectionType.Server);
    }


    private void addNewLobbyEntryToUI(BluetoothDevice device, String lobbyname) {
        binding.connectList.removeView(binding.noLobbyInfo);

        Log.e("!", "    " + String.valueOf(device));
        if (lobbyname.length() < 1 || lobbyname == null){
            lobbyname = String.valueOf(device);
        }
        LobbyEntryView lobbyEntryView = new LobbyEntryView(getContext());
        lobbyEntryView.setName(lobbyname);
        lobbyEntryView.addLobbyDeviceConnection(device, this);

        binding.connectList.addView(lobbyEntryView);
        lobbyEntryViewList.add(lobbyEntryView);
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