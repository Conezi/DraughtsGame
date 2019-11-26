package com.assignment.draughtsgame.anims;

import android.view.View;
import android.view.animation.ScaleAnimation;

/**
 * Created by Connel on 7/14/2018.
 */
public class Animations {
        public void Scale(float fromX, float toX, float fromY, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue, long duration, int repeatCount, int repeatMode, View view){
            ScaleAnimation scaleAnimation=new ScaleAnimation(fromX, toX, fromY, toY, pivotXType, pivotXValue, pivotYType, pivotYValue);
            scaleAnimation.setDuration(duration);
            scaleAnimation.setRepeatCount(repeatCount);
            scaleAnimation.setRepeatMode(repeatMode);
            scaleAnimation.setFillAfter(true);
            view.startAnimation(scaleAnimation);
        }
    }
