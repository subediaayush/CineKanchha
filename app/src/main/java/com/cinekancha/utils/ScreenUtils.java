package com.cinekancha.utils;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import android.util.DisplayMetrics;

public class ScreenUtils {

    private static float DP_PX_RATIO = -1;
    private static int densityDpi = 0;

    public static int getDensityDpi(@NonNull Activity activity) {
        if (densityDpi == 0) {
            DisplayMetrics dMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
            densityDpi = dMetrics.densityDpi;
        }
        return densityDpi;
    }

    public static int dpToPx(Context context, int dp) {
        setDp_Px_Ratio(context);
        int px = Math.round(dp * DP_PX_RATIO);
        return px;
    }

    private static void setDp_Px_Ratio(Context context) {
        if (DP_PX_RATIO == -1) {
            DisplayMetrics displayMetrics = context.getResources()
                    .getDisplayMetrics();
            DP_PX_RATIO = displayMetrics.density;
        }
    }

    public static int pxTodp(Context context, float px) {
        setDp_Px_Ratio(context);
        int dp = Math.round(px / DP_PX_RATIO);
        return dp;
    }

    public static int getScreenWidthInDp(Activity activity) {
        DisplayMetrics dMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
        float density = dMetrics.density;
        int w = Math.round(dMetrics.widthPixels / density);
        return w;
    }

    public static int getScreenHeightInDp(Activity activity) {
        DisplayMetrics dMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dMetrics);
        float density = dMetrics.density;
        int w = Math.round(dMetrics.heightPixels / density);
        return w;
    }

}
