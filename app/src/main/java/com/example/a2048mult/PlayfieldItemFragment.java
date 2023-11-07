package com.example.a2048mult;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.a2048mult.databinding.FragmentPlayfieldItemBinding;

public class PlayfieldItemFragment extends Fragment {
    private FragmentPlayfieldItemBinding binding;

    private static final String ITEM_LEVEL = "itemLevel";
    private int itemLevel;

    public PlayfieldItemFragment() {
        // Required empty public constructor
    }

    /**
     *  creates a new instance of this fragment using the provided parameters.
     *
     * @param itemLevel
     * @return A new instance of fragment Item.
     */
    public static PlayfieldItemFragment newInstance(int itemLevel) {
        PlayfieldItemFragment fragment = new PlayfieldItemFragment();
        Bundle args = new Bundle();
        args.putInt(ITEM_LEVEL, itemLevel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            itemLevel = getArguments().getInt(ITEM_LEVEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlayfieldItemBinding.inflate(getLayoutInflater());
        setItemTextAndColor();

        return binding.getRoot();
    }

    private void setItemTextAndColor(){
        // get value for Text
        int value = (int)Math.pow(2,this.itemLevel);
        String valueForText = Integer.toString(value);
        binding.textView.setText(valueForText);

        // ---------- use value to determine BGcolor and TextColor ----------

        // get color schemes
        int[] colorLevelSteps = getContext().getResources().getIntArray(R.array.colorLevelSteps);
        int[] colorLevelSteps_Text = getContext().getResources().getIntArray(R.array.colorLevelSteps_Text);

        int colorIndex =this.itemLevel;
        if (this.itemLevel > colorLevelSteps.length){
            colorIndex = colorLevelSteps.length;
        }
        colorIndex -= 1;


        // set BGcolor
        binding.getRoot().setBackgroundColor(colorLevelSteps[colorIndex]);

        // set Textcolor
        binding.textView.setTextColor(colorLevelSteps_Text[colorIndex]);

    }
}