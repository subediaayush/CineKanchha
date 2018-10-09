package com.cinekancha.utils;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import java.util.Random;

public class GradientGenartor {
    private static final int min = 0;
    private static int[][] gradientColors = new int[][]{
            {Color.parseColor("#526cfd"), Color.parseColor("#7fdde9")},
            {Color.parseColor("#e81d62"), Color.parseColor("#ffaa00")},
            {Color.parseColor("#870d4e"), Color.parseColor("#c52727")},
            {Color.parseColor("#227788"), Color.parseColor("#998866")}
    };
    private static final int max = gradientColors.length - 1;

    public static Drawable generate(int randomNumber) {
        return DrawableUtils.createGradiantDrawable(GradientDrawable.Orientation.TL_BR, gradientColors[randomNumber]);
    }

    public static int getRandom() {
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }
}