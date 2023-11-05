package com.example.a2048mult;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a2048mult.databinding.FragmentPlayfieldBinding;
public class Playfield extends Fragment {
    private FragmentPlayfieldBinding binding;
    // keys for accessing values
    private static final String X_Count = "xCountKey";
    private static final String Y_Count = "yCountKey";

    // here are the values stored
    private int xCount;
    private int yCount;

    public Playfield() {
        // Required empty public constructor
    }

    /**
     *  creates a new instance of this fragment using the provided parameters.
     *
     * @param xCountIn
     * @param yCountIn
     * @return A new instance of fragment Playfield.
     */
    public static Playfield newInstance(int xCountIn, int yCountIn) {
        Playfield fragment = new Playfield();
        Bundle args = new Bundle();
        args.putInt(X_Count,xCountIn);
        args.putInt(Y_Count,yCountIn);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            xCount = getArguments().getInt(X_Count);
            yCount = getArguments().getInt(Y_Count);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlayfieldBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}