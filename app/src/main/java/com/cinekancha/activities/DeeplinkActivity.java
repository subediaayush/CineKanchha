package com.cinekancha.activities;

import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.cinekancha.R;

public class DeeplinkActivity extends AppCompatActivity {
    private String id = null;
    private Intent intent = null;
    private Uri uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deeplink);
//        if (GlobalUtils.getFromPref(Constants.NAVIGATE_FROM, this) == "notification") {
//            GlobalUtils.savePref(Constants.NAVIGATE_FROM, "", this);
//            id = intent.getStringExtra(Constants.ID);
//            if (!TextUtils.isEmpty(intent.getStringExtra("url"))) {
//                uri = Uri.parse(intent.getStringExtra("url"));
//                checkMasterUrl();
//            } else
//                doNotification(intent.getStringExtra("type"));
//        } else {
//            Intent intent = intent;
//            if (intent != null && Intent.ACTION_VIEW == intent.getAction()) {
//                uri = intent..toString();
//                if (uri != null) {
//                    if (GlobalUtils.isNetworkAvailable(this)) {
//                        checkMasterUrl();
//                    } else {
//                        GlobalUtils.navigateActivity(this, true, HomeActivity.class);
//                    }
//                }
//            }
//        }
    }
}
