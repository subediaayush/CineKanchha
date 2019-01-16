package com.cinekancha.view;

import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class ParallaxPagerTransformer implements ViewPager.PageTransformer {
    private int id;
    private int border = 0;
    private float speed = 0.2f;
    private float minAlpha = 0.3f;

    public ParallaxPagerTransformer(int id) {
        this.id = id;
    }

    @Override
    public void transformPage(View view, float position) {

        View parallaxView = view.findViewById(id);

        if (view == null) {
            Log.w("ParallaxPager", "There is no view");
        }

        if (parallaxView != null && Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            if (position > -1 && position < 1) {
                float width = parallaxView.getWidth();
                float viewWidth = view.getWidth();
                parallaxView.setTranslationX(-(position * width * speed));
                float sc = width != 0 ? ((float) viewWidth - border) / view.getWidth() : 1f;
                if (position == 0) {
                    view.setScaleX(1);
                    view.setScaleY(1);
                } else {
                    view.setScaleX(sc);
                    view.setScaleY(sc);
                }
            }
        }

        if (position < -1) {  // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);

        } else if (position <= 1) { // [-1,1]
            view.setAlpha(Math.max(minAlpha, 1 - Math.abs(position)));
        } else {  // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
        }
    }

    public void setBorder(int px) {
        border = px;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setAlpha(float alpha) {
        minAlpha = alpha;
    }
}
