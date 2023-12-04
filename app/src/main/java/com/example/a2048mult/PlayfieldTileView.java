package com.example.a2048mult;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a2048mult.databinding.ViewPlayfieldTileBinding;

public class PlayfieldTileView extends ConstraintLayout {
    private ViewPlayfieldTileBinding binding;


    public PlayfieldTileView(@NonNull Context context) {
        super(context);
        binding = ViewPlayfieldTileBinding.inflate(LayoutInflater.from(context));
    }

    public PlayfieldTileView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        binding = ViewPlayfieldTileBinding.inflate(LayoutInflater.from(context));
    }

    public PlayfieldTileView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        binding = ViewPlayfieldTileBinding.inflate(LayoutInflater.from(context));
    }

    public PlayfieldTileView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        binding = ViewPlayfieldTileBinding.inflate(LayoutInflater.from(context));
    }


    /**
     * changing the tiles appearance
     * new color and text are representing the corresponding level
     */
    void setLevel(int level){
        // get value for Text
        SpannableStringBuilder valueForText;

        // if value is >= 20 --> simplify text to 2^tileLevel
        if(level<20) {
            long value = (long)Math.pow(2,level);
            valueForText= SpannableStringBuilder.valueOf(Long.toString(value));
        } else {
            valueForText = new SpannableStringBuilder("2"+level);
            valueForText.setSpan(new SuperscriptSpan(), 1, valueForText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            valueForText.setSpan(new RelativeSizeSpan(0.30f), 1, valueForText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        binding.textView.setText(valueForText);

        // ---------- use value to determine BGcolor and TextColor ----------

        // get color schemes
        int[] colorLevelSteps = getContext().getResources().getIntArray(R.array.colorLevelSteps);
        int[] colorLevelSteps_Text = getContext().getResources().getIntArray(R.array.colorLevelSteps_Text);

        int colorIndex =level;
        if (level > colorLevelSteps.length){
            colorIndex = colorLevelSteps.length;
        }
        colorIndex -= 1;

        // set BGcolor
        binding.getRoot().getBackground().setTint(colorLevelSteps[colorIndex]);

        // set Textcolor
        binding.textView.setTextColor(colorLevelSteps_Text[colorIndex]);
    }
}