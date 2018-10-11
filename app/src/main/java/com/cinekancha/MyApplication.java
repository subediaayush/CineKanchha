package com.cinekancha;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.cinekancha.utils.AnalyticsUtil;
import com.cinekancha.utils.Logger;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.firebase.analytics.FirebaseAnalytics;

import io.realm.Realm;
import io.realm.RealmConfiguration;

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
    public void onCreate() {
        if (instance == null)
            instance = this;

        Realm.init(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
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
