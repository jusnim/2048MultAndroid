package com.example.a2048mult;


import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class GameInputListener implements View.OnTouchListener {
//    , View.OnKeyListene
    private final GestureDetector gestureDetector;
    private float velocityThreshold = 100;
    private float threshold = 100;

    public GameInputListener(Context context) {
        Log.e("INPUT","right");
        gestureDetector = new GestureDetector(context, new GestureListener());
    }
    public GameInputListener(Context context, float threshold, float velocityThreshold) {
        gestureDetector = new GestureDetector(context, new GestureListener());
        this.threshold=threshold;
        this.velocityThreshold=velocityThreshold;
    }

    public abstract void onLeft();

    public abstract void onDown();

    public abstract void onUp();
    public abstract void onRight();

//    @Override
//    public final boolean onKey(View v, int keyCode, KeyEvent event) {
//        boolean result = false;
//        switch (keyCode){
//            case KeyEvent.KEYCODE_DPAD_DOWN:
//            case KeyEvent.KEYCODE_S:
//                onDown();
//                result = true;
//                break;
//            case KeyEvent.KEYCODE_DPAD_UP:
//            case KeyEvent.KEYCODE_W:
//                onUp();
//                result = true;
//                break;
//            case KeyEvent.KEYCODE_DPAD_LEFT:
//            case KeyEvent.KEYCODE_A:
//                onLeft();
//                result = true;
//                break;
//            case KeyEvent.KEYCODE_DPAD_RIGHT:
//            case KeyEvent.KEYCODE_D:
//                onRight();
//                result = true;
//                break;
//        }
//        return result;
//    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
//        @Override
//        public boolean onDown(@NonNull MotionEvent e) {
//            return true;
//        }
        @Override
        public boolean onFling(@Nullable MotionEvent e1, @NonNull MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            Log.i("INPUT","right");
            float diffX = e2.getX() - e1.getX();
            float diffY = e2.getY() - e1.getY();

            // horizontal
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > GameInputListener.this.threshold && Math.abs(velocityX) > GameInputListener.this.velocityThreshold) {
                    if (diffX > 0) {

                        GameInputListener.this.onRight();
                    } else {
                        GameInputListener.this.onLeft();
                    }
                    result = true;
                }
            }
            // vertical
            else if (Math.abs(diffY) > GameInputListener.this.threshold && Math.abs(velocityY) > GameInputListener.this.velocityThreshold) {
                if (diffY > 0) {
                    GameInputListener.this.onDown();
                } else {
                    GameInputListener.this.onUp();
                }
                result = true;
            }

            return result;
        }
    }



}
