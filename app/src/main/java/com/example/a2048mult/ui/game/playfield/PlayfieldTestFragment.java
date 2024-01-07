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
        protoPlayfield(4, 4);

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
                protoSetupMove4x4();
            }
        });

        return binding.getRoot();
    }

    private void protoPlayfield(int width, int height) {
        PlayfieldView playfield = new PlayfieldView(binding.getRoot().getContext());
////
        int[][] showcaseData = new int[height][width];
        int level = 1;
        for (int y = 0; y < height; y++) {
            int[] row = new int[width];
            for (int x = 0; x < width; x++) {
                row[x] = level;
                level++;
            }
            showcaseData[y] = row;
        }
        this.playfield = playfield;
        playfield.drawPlayfieldBackground(width, height);
        playfield.drawPlayfieldState(showcaseData);
        binding.getRoot().addView(playfield);
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

    private void protoSetupMove4x4() {
        binding.getRoot().removeView(this.playfield);
        PlayfieldView playfield = new PlayfieldView(binding.getRoot().getContext());
        int[][] showcaseData = new int[4][4];
        showcaseData[0] = new int[]{5, 2, 0, 2};
        showcaseData[1] = new int[]{0, 0, 0, 0};
        showcaseData[2] = new int[]{5, 0, 0, 0};
        showcaseData[3] = new int[]{0, 0, 0, 0};

        this.playfield = playfield;
        playfield.drawPlayfieldBackground(4, 4);
        playfield.drawPlayfieldState(showcaseData);
        binding.getRoot().addView(playfield);
    }
}