package com.cinekancha;

import android.app.Application;
import android.content.Context;

import com.cinekancha.entities.model.PollDatabase;
import com.cinekancha.utils.AnalyticsUtil;
import com.cinekancha.utils.Logger;
import com.cinekancha.utils.Prefs;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;



/**
 * Created by paoneking on 2/20/18.
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    private static FirebaseAnalytics mFirebaseAnalytics;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public static FirebaseAnalytics getAnalytics() {
        return mFirebaseAnalytics;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        if (instance == null)
            instance = this;
        FirebaseMessaging.getInstance().subscribeToTopic("cinekhancha");

        Prefs.initPrefs(this);
    
        PollDatabase.init(this);

        // Obtain the FirebaseAnalytics instance.
        if (mFirebaseAnalytics == null)
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mFirebaseAnalytics.setAnalyticsCollectionEnabled(true);
        AnalyticsUtil.logAppOpenEvent(getAnalytics());
        Logger.setWriteLog(BuildConfig.DEBUG);
        super.onCreate();

        Fresco.initialize(this);
    }
}
