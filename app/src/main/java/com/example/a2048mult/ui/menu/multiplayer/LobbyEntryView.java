package com.example.a2048mult.ui.menu.multiplayer;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.a2048mult.Control.BtConnectAsClientThread;
import com.example.a2048mult.R;
import com.example.a2048mult.databinding.ViewLobbyEntryBinding;
import com.example.a2048mult.ui.menu.MainActivity;


public class LobbyEntryView extends ConstraintLayout {

    private ViewLobbyEntryBinding binding;

    public LobbyEntryView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public LobbyEntryView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LobbyEntryView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public LobbyEntryView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        binding = ViewLobbyEntryBinding.inflate(LayoutInflater.from(context), this, true);
    }

    /**
     * sets LobbyName for LobbyEntry
     */
    public void setName(String name) {
        binding.lobbyName.setText(name);
    }

    /**
     * adds the reference for the join button to the corressponding device
     */
    public void addLobbyDeviceConnection(BluetoothDevice device, Fragment fragment) {
        binding.buttonJoinLobby.setOnClickListener(v ->
                {
                    if(device != null){
                        MainActivity.getBTManagerInstance().btConnectAsClient(device);
                    }

                    // TODO check if connection estabilished
//                    NavHostFragment.findNavController(fragment)
//                            .navigate(R.id.action_multiplayerMenu_to_multiplayerMenuLobbyFragment);
                }
        );
    }
}
