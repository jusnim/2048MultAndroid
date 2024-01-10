package com.example.a2048mult.ui.menu.multiplayer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.a2048mult.databinding.ViewLobbyEntryBinding;


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
    public void setName() {

    }
}
