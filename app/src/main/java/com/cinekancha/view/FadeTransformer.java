package com.cinekancha.view;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class FadeTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.8f;
    private static final float MIN_ALPHA = 0.3f;

    public void transformPage(View view, float position) {
        view.setAlpha(1 - Math.abs(position));
        if (position < 0) {
            view.setScrollX((int) ((float) (view.getWidth()) * position));
        } else if (position > 0) {
            view.setScrollX(-(int) ((float) (view.getWidth()) * -position));
        } else {
            view.setScrollX(0);
        }
        if (position < -1) {  // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 1) { // [-1,1]

            view.setScaleX(Math.max(MIN_SCALE, 1 - Math.abs(position)));
            view.setScaleY(Math.max(MIN_SCALE, 1 - Math.abs(position)));
            view.setAlpha(Math.max(MIN_ALPHA, 1 - Math.abs(position)));

        } else {  // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);

        }

    }
}
