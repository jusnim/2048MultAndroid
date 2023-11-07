package com.example.a2048mult;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a2048mult.databinding.FragmentSinglePlayerMenuBinding;
public class SinglePlayerMenuFragment extends Fragment {
    private FragmentSinglePlayerMenuBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSinglePlayerMenuBinding.inflate(getLayoutInflater());

        FragmentManager fragmentManager = getParentFragmentManager();

        PlayfieldItemFragment fragment = PlayfieldItemFragment.newInstance(16);
        fragmentManager.beginTransaction().add(binding.fragmentContainerView.getId(),fragment).commit();


        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}