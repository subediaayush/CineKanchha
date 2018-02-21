package com.cinekancha.utils;

import android.app.Activity;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;

public class AnalyticsUtil {

    public static void logScreenTime(FirebaseAnalytics analytics, Activity activity, String screenName) {
        analytics.setCurrentScreen(activity, screenName, null);
    }

    public static void logAppOpenEvent(FirebaseAnalytics analytics) {
        analytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null);
    }

    public static void logButtonClickedEvent(FirebaseAnalytics analytics, String title) {
        Bundle analyticsData = new Bundle();
        analytics.logEvent(title, analyticsData);
    }

    public static void logStickerClickedEvent(FirebaseAnalytics analytics, String title, int id, String category, String stickerName){
        Bundle analyticsData = new Bundle();
        analyticsData.putInt(FirebaseAnalytics.Param.ITEM_ID, id);
        analyticsData.putString(FirebaseAnalytics.Param.ITEM_NAME, stickerName);
        analyticsData.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, category);
        analyticsData.putString(FirebaseAnalytics.Param.CONTENT_TYPE, title);
        analytics.logEvent(stickerName, analyticsData);
    }
}
