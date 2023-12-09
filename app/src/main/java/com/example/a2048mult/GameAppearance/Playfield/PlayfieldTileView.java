package com.example.a2048mult.GameAppearance.Playfield;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.DrawableCompat;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.example.a2048mult.R;
import com.example.a2048mult.databinding.ViewPlayfieldTileBinding;

public class PlayfieldTileView extends ConstraintLayout {
    private ViewPlayfieldTileBinding binding;

    public PlayfieldTileView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public PlayfieldTileView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayfieldTileView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public PlayfieldTileView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(@NonNull Context context) {
        binding = ViewPlayfieldTileBinding.inflate(LayoutInflater.from(context), this, true);

        GradientDrawable shape = (GradientDrawable) binding.getRoot().getBackground();
        float r = PlayfieldConfig.tileRoundEdgesInDP;
        shape.setCornerRadii(new float[] { r, r, r, r, r, r, r, r });
        binding.getRoot().setBackground(shape);
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