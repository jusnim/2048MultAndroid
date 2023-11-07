package com.example.a2048mult;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.a2048mult.databinding.FragmentPlayfieldBlockBinding;

public class PlayfieldBlockFragment extends Fragment {
    private FragmentPlayfieldBlockBinding binding;

    private static final String ITEM_LEVEL = "itemLevel";
    private int itemLevel = -1;

    public PlayfieldBlockFragment() {
        // Required empty public constructor
    }

    /**
     *  creates a new instance of this fragment using the provided parameters.
     *
     * @param itemLevel
     * @return A new instance of fragment Item.
     */
    public static PlayfieldBlockFragment newInstance(int itemLevel) {
        PlayfieldBlockFragment fragment = new PlayfieldBlockFragment();
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
        binding = FragmentPlayfieldBlockBinding.inflate(getLayoutInflater());

        if(itemLevel >= 0){
            setItemTextAndColor();
        } else{
            Log.w("! PlayfieldItem","no level value passed --> test item");
        }

        return binding.getRoot();
    }

    private void setItemTextAndColor(){
        // get value for Text
        SpannableStringBuilder valueForText;

        // if value is >= 20 --> simplify text to 2^itemlevel
        if(this.itemLevel<20){
            long value = (long)Math.pow(2,this.itemLevel);
            valueForText= SpannableStringBuilder.valueOf(Long.toString(value));
        } else{
            valueForText = new SpannableStringBuilder("2"+this.itemLevel);
            valueForText.setSpan(new SuperscriptSpan(), 1, valueForText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            valueForText.setSpan(new RelativeSizeSpan(0.30f), 1, valueForText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
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
        binding.getRoot().getBackground().setTint(colorLevelSteps[colorIndex]);

        // set Textcolor
        binding.textView.setTextColor(colorLevelSteps_Text[colorIndex]);
    }
}