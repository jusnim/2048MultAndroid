package com.example.a2048mult.ui.game.playfield;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.a2048mult.databinding.FragmentPlayfieldTestBinding;
import com.example.a2048mult.ui.game.InputListener;

public class PlayfieldTestFragment extends Fragment {

    private FragmentPlayfieldTestBinding binding;
    private LinearLayout lm;
    private PlayfieldView playfield;

    public PlayfieldTestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        binding = FragmentPlayfieldTestBinding.inflate(getLayoutInflater());
        lm = new LinearLayout(binding.getRoot().getContext());

//        Toast.makeText(binding.getRoot().getContext(), "lol", Toast.LENGTH_SHORT).show();


        // TODO change no anonym inputlistener
        binding.getRoot().setOnTouchListener(new InputListener(binding.getRoot().getContext()) {
            @Override
            public void onLeft() {
                playfield.mergeTile(3, 0, 1, 0, 3);
            }

            @Override
            public void onDown() {
                playfield.mergeTile(0, 0, 0, 2, 6);
            }

            @Override
            public void onUp() {
                protoSpawnTilesPlayfield(4, 4);
            }

            @Override
            public void onRight() {
                protoRemovePlayfield(4, 4);
            }
        });
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return binding.getRoot();
    }



    private void protoRemovePlayfield(int width, int height) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.playfield.removeTile(x, y);
            }
        }
    }

    private void protoSpawnTilesPlayfield(int width, int height) {
        int level = 1;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.playfield.spawnTileAt(x, y, level);
                level++;
            }
        }
    }


}