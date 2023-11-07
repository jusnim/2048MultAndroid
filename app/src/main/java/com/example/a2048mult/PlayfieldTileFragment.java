package com.example.a2048mult;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.a2048mult.databinding.FragmentPlayfieldTileBinding;

public class PlayfieldTileFragment extends Fragment {
    private FragmentPlayfieldTileBinding binding;

    private static final String TILE_LEVEL = "tileLevel";
    private int tileLevel = -1;

    public PlayfieldTileFragment() {
        // Required empty public constructor
    }

    /**
     *  creates a new instance of this fragment using the provided parameters.
     *
     * @param tileLevel
     * @return A new instance of fragment Tile.
     */
    public static PlayfieldTileFragment newInstance(int tileLevel) {
        PlayfieldTileFragment fragment = new PlayfieldTileFragment();
        Bundle args = new Bundle();
        args.putInt(TILE_LEVEL, tileLevel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tileLevel = getArguments().getInt(TILE_LEVEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPlayfieldTileBinding.inflate(getLayoutInflater());

        if(tileLevel >= 0){
            setTileTextAndColor();
        } else{
            Log.w("! PlayfieldTile","no level value passed --> test tile");
        }

        return binding.getRoot();
    }

    private void setTileTextAndColor(){
        // get value for Text
        SpannableStringBuilder valueForText;

        // if value is >= 20 --> simplify text to 2^tileLevel
        if(this.tileLevel<20){
            long value = (long)Math.pow(2,this.tileLevel);
            valueForText= SpannableStringBuilder.valueOf(Long.toString(value));
        } else{
            valueForText = new SpannableStringBuilder("2"+this.tileLevel);
            valueForText.setSpan(new SuperscriptSpan(), 1, valueForText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            valueForText.setSpan(new RelativeSizeSpan(0.30f), 1, valueForText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        binding.textView.setText(valueForText);

        // ---------- use value to determine BGcolor and TextColor ----------

        // get color schemes
        int[] colorLevelSteps = getContext().getResources().getIntArray(R.array.colorLevelSteps);
        int[] colorLevelSteps_Text = getContext().getResources().getIntArray(R.array.colorLevelSteps_Text);

        int colorIndex =this.tileLevel;
        if (this.tileLevel > colorLevelSteps.length){
            colorIndex = colorLevelSteps.length;
        }
        colorIndex -= 1;

        // set BGcolor
        binding.getRoot().getBackground().setTint(colorLevelSteps[colorIndex]);

        // set Textcolor
        binding.textView.setTextColor(colorLevelSteps_Text[colorIndex]);
    }
}