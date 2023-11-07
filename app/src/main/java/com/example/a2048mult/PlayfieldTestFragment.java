package com.example.a2048mult;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a2048mult.databinding.FragmentPlayfieldTestBinding;

public class PlayfieldTestFragment extends Fragment {

    private FragmentPlayfieldTestBinding binding;

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

        FragmentManager fragmentManager = getParentFragmentManager();
        PlayfieldFragment fragment = PlayfieldFragment.newInstance(5);
        fragmentManager.beginTransaction().add(binding.fragmentContainerTest.getId(),fragment).commit();

        return binding.getRoot();
    }
}