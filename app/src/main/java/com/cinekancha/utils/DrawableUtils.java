package com.cinekancha.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

public class DrawableUtils {

    public static Drawable createTintedDrawable(Context context, @DrawableRes int drawableId, @ColorRes int colorId) {
        Drawable drawable = createDrawable(context, drawableId);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(context, colorId));
        return drawable;
    }

    public static Drawable createDrawable(Context context, @DrawableRes int drawableId) {
        Drawable wrappedDrawable = DrawableCompat.wrap(ContextCompat.getDrawable(context, drawableId).mutate());
        return wrappedDrawable;
    }

    public static Drawable createGradiantDrawable(GradientDrawable.Orientation orientation, int[] colors) {
        GradientDrawable gd = new GradientDrawable(orientation, colors);
        gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        return gd;
    }
}