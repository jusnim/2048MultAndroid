package com.example.a2048mult.GameAppearance.Playfield;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.a2048mult.databinding.FragmentPlayfieldTestBinding;

public class PlayfieldTestFragment extends Fragment {

    private FragmentPlayfieldTestBinding binding;
    private LinearLayout lm;

    public PlayfieldTestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        binding = FragmentPlayfieldTestBinding.inflate(getLayoutInflater());
        lm = new LinearLayout(binding.getRoot().getContext());

        protoPlayfield(4,4);

//        lm = new LinearLayout(binding.getRoot().getContext());

//        protoPlayfield(4, 4);
//        Toast.makeText(binding.getRoot().getContext(), "lol", Toast.LENGTH_SHORT).show();


//        binding.getRoot().setOnTouchListener(new GameInputListener(binding.getRoot().getContext()) {
//            @Override
//            public void onLeft() {
//                Toast.makeText(binding.getRoot().getContext(), "left", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onDown() {
//                Toast.makeText(binding.getRoot().getContext(), "down", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onUp() {
//                Toast.makeText(binding.getRoot().getContext(), "top", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onRight() {
//                Toast.makeText(binding.getRoot().getContext(), "rigth", Toast.LENGTH_SHORT).show();
//            }
//        });
//        binding.getRoot().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(binding.getRoot().getContext(), "test", Toast.LENGTH_SHORT).show();
//            }
//        });


//        FragmentManager fragmentManager = getParentFragmentManager();
//        PlayfieldTileFragment fragment = PlayfieldTileFragment.newInstance(20);
//        fragmentManager.beginTransaction().add(binding.fragmentContainerTest.getId(),fragment).commit();
//        View view = inflater.inflate(R.layout.view_playfield_tile, container,false);
        return binding.getRoot();
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        protoPlayfield(4,4);
//    }

    private void protoPlayfield(int width, int height) {
        PlayfieldView playfield = new PlayfieldView(lm.getContext());
        playfield.setPlayfieldSizing(width, height);

        binding.getRoot().addView(playfield);
    }
}