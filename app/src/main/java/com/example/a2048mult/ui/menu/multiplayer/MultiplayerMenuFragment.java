package com.example.a2048mult.ui.menu.multiplayer;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.a2048mult.databinding.FragmentMultiplayerMenuBinding;

public class MultiplayerMenuFragment extends Fragment {

    private FragmentMultiplayerMenuBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMultiplayerMenuBinding.inflate(getLayoutInflater());
        binding.buttonChangeUsername.setOnClickListener(
                v -> changeUsername()
        );
        binding.buttonBluetooth.setOnClickListener(
                v -> test()
        );

        return binding.getRoot();
    }

    private void test() {
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

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}