package com.example.a2048mult.GameAppearance.Menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a2048mult.R;
import com.example.a2048mult.databinding.FragmentChooseBinding;

public class ChooseFragment extends Fragment {
    private FragmentChooseBinding binding;

    private Fragment size3;
    private Fragment size4;
    private Fragment size5;
    private Fragment size6;
    private Fragment size8;
    private Fragment size10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChooseBinding.inflate(getLayoutInflater());
        init();
        return binding.getRoot();
    }

    private void init() {
        createPreviews();
        NavHostFragment finalHost = NavHostFragment.create(R.navigation.choosenav);
    }

    private void createPreviews() {
        this.size3 = createPreview(3);
        this.size4 = createPreview(4);
        this.size5 = createPreview(5);
        this.size6 = createPreview(6);
        this.size8 = createPreview(8);
        this.size10 = createPreview(10);
    }

    private Fragment createPreview(int size) {
        return ChoosingPreviewFragment.newInstance(size, size);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}