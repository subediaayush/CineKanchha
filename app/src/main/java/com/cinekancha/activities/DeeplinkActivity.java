package com.cinekancha.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.cinekancha.BuildConfig;
import com.cinekancha.article.ArticleDetailActivity;
import com.cinekancha.utils.GlobalUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;

public class DeeplinkActivity extends AppCompatActivity {
	private String id = null;
	private Intent intent = null;
	private Uri uri = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		handleIntent(getIntent());

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
	
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		handleIntent(intent);
	}
	
	private void handleIntent(Intent intent) {
		try {
			Uri uri = intent.getData().normalizeScheme();
			handleDeeplink(uri);
		} catch (Exception ignored) {
			// Some error, just exit
			if (BuildConfig.DEBUG) throw ignored;
		}
		
		finish();
	}
	
	private void handleDeeplink(Uri uri) {
		if (!isSupportedLink(uri)) {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(uri);
			
			startActivity(intent);
		}
		
		if (isAppScheme(uri)) {
			uri = convertToHttpScheme(uri);
		}
		
		List<String> segments = new ArrayList<>(uri.getPathSegments());
		Bundle params = parseOptions(uri);
		
		if (segments.isEmpty()) {
			startMainActivity(params);
		} else {
			handlePathLinks(segments, params);
		}
	}
	
	private void handlePathLinks(List<String> segments, Bundle params) {
		String root = segments.remove(0);
		switch (root) {
			case "news": {
				handleNewsLink(segments, params);
				return;
			}
		}
	}
	
	private void handleNewsLink(List<String> segments, Bundle params) {
		String newsId = segments.remove(0);
		
		Intent intent = new Intent(this, ArticleDetailActivity.class);
		intent.putExtras(params);
		intent.putExtra("articleId", newsId);
		startActivity(intent);
	}
	
	private void startMainActivity(Bundle params) {
		GlobalUtils.navigateActivity(this, HomeActivity.class, params);
	}
	
	private Uri convertToHttpScheme(Uri uri) {
		uri = uri.buildUpon().scheme("https").authority("www.fifu.com").build();
		return uri;
	}
	
	private boolean isSupportedLink(Uri uri) {
		return isAppScheme(uri) || isAppHttpScheme(uri);
	}
	
	private boolean isAppScheme(Uri uri) {
		String scheme = uri.getScheme();
		return "fifu".equals(scheme);
	}
	
	private boolean isAppHttpScheme(Uri uri) {
		String host = uri.getHost();
		
		return "fifu.com".equals(host) || "www.fifu.com".equals(host);
	}
	
	private Bundle parseOptions(Uri uri) {
		Bundle options = new Bundle();
		Map<String, List<String>> queryParameters = getQueryParameters(uri);
		if (queryParameters == null) {
			return options;
		}
		
		for (String key : queryParameters.keySet()) {
			List<String> values = queryParameters.get(key);
			if (values.size() == 1) {
				options.putString(key, values.get(0));
			} else if (values.size() > 1) {
				options.putStringArrayList(key, new ArrayList<String>(values));
			}
		}
		
		return options;
	}
	
	private Map<String, List<String>> getQueryParameters(Uri uri) {
		Map<String, List<String>> map = new LinkedHashMap<>();
		
		Set<String> parameters = uri.getQueryParameterNames();
		for (String paramName : parameters) {
			map.put(paramName, uri.getQueryParameters(paramName));
		}
		
		return map;
	}
}
