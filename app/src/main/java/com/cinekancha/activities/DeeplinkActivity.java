package com.cinekancha.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import com.cinekancha.BuildConfig;
import com.cinekancha.actor.ActorDetailActivity;
import com.cinekancha.actor.ActorListActivity;
import com.cinekancha.article.ArticleDetailActivity;
import com.cinekancha.boxOffice.BoxOfficeActivity;
import com.cinekancha.movieDetail.MoviePostDetailActivity;
import com.cinekancha.movieReview.ReviewListActivity;
import com.cinekancha.movies.MovieActivity;
import com.cinekancha.newRelease.NewReleaseActivity;
import com.cinekancha.newsGossips.NewsGossipsActivity;
import com.cinekancha.poll.PollsActivity;
import com.cinekancha.trending.FullMoviesActivity;
import com.cinekancha.trending.TrendingActivity;
import com.cinekancha.trivia.TriviaListActivity;
import com.cinekancha.trolls.TrollListActivity;
import com.cinekancha.upcomingMovies.UpcomingMovieActivity;
import com.cinekancha.utils.GlobalUtils;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;

/*

 fifu://app/
              articles            [/{article_id}]
              news                [/{news_id}]
              movies              [&new=true] [&upcoming=true] [/{movie_id}]
              trolls
              reviews
              polls
              trivia
              gallery             [/{actor_id}]
              trending_videos     [&video={utf8(videoUrl)}]
              watch_movies        [&video={utf8(videoUrl)}]
              box_office

*/

public class DeeplinkActivity extends AppCompatActivity {
	
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
			if (!handlePathLinks(segments, params)) startMainActivity(params);
		}
	}
	
	private boolean handlePathLinks(List<String> segments, Bundle params) {
		String root = segments.remove(0);
		switch (root) {
			case "articles": {
				return handleArticleLink(segments, params);
			}
			case "news": {
				return handleNewsLink(segments, params);
			}
			case "movies": {
				return handleMovieLink(segments, params);
			}
			case "trolls": {
				return handleTrollLink(segments, params);
			}
			case "reviews": {
				return handleReviewLink(segments, params);
			}
			case "polls": {
				return handlePollLink(segments, params);
			}
			case "trivia": {
				return handleTriviaLink(segments, params);
			}
			case "gallery": {
				return handleGalleryLink(segments, params);
			}
			case "trending_videos": {
				return handleTrendingVideos(segments, params);
			}
			case "watch_movies": {
				return handleFullMovies(segments, params);
			}
			case "box_office": {
				return handleBoxOffice(segments, params);
			}
		}
		return false;
	}
	
	private boolean handleArticleLink(List<String> segments, Bundle params) {
		Intent intent = createParamIntent(params);
		if (segments.isEmpty()) {
			intent.setClass(this, NewsGossipsActivity.class);
		} else {
			intent.setClass(this, ArticleDetailActivity.class);
			String newsId = segments.remove(0);
			intent.putExtra("articleId", newsId);
		}
		startActivity(intent);
		return true;
		
	}
	
	private boolean handleBoxOffice(List<String> segments, Bundle params) {
		Intent intent = createParamIntent(params);
		intent.setClass(this, BoxOfficeActivity.class);
		startActivity(intent);
		return true;
	}
	
	private boolean handleFullMovies(List<String> segments, Bundle params) {
		Intent intent = createParamIntent(params);
		String videoUrl = params.getString("video");
		if (TextUtils.isEmpty(videoUrl)) {
			intent.setClass(this, FullMoviesActivity.class);
			startActivity(intent);
			return true;
		} else {
			try {
				String videoId = GlobalUtils.extractYoutubeId(URLDecoder.decode(videoUrl, "utf-8"));
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("vnd.youtube://" + videoId));
				startActivity(intent);
			} catch (MalformedURLException|UnsupportedEncodingException e) {
				e.printStackTrace();
				return false;
			}
			
			return true;
		}
	}
	
	private boolean handleTrendingVideos(List<String> segments, Bundle params) {
		Intent intent = createParamIntent(params);
		String videoUrl = params.getString("video");
		if (TextUtils.isEmpty(videoUrl)) {
			intent.setClass(this, TrendingActivity.class);
			startActivity(intent);
			return true;
		} else {
			try {
				String videoId = GlobalUtils.extractYoutubeId(URLDecoder.decode(videoUrl, "utf-8"));
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("vnd.youtube://" + videoId));
				startActivity(intent);
			} catch (MalformedURLException|UnsupportedEncodingException e) {
				e.printStackTrace();
				return false;
			}
			
			return true;
		}
	}
	
	private boolean handleGalleryLink(List<String> segments, Bundle params) {
		Intent intent = createParamIntent(params);
		if (segments.isEmpty()) {
			intent.setClass(this, ActorListActivity.class);
		} else {
			intent.setClass(this, ActorDetailActivity.class);
			String actorId = segments.remove(0);
			intent.putExtra("actor", actorId);
		}
		startActivity(intent);
		return true;
	}
	
	
	private boolean handleTriviaLink(List<String> segments, Bundle params) {
		Intent intent = createParamIntent(params);
		intent.setClass(this, TriviaListActivity.class);
		startActivity(intent);
		return true;
	}
	
	private boolean handlePollLink(List<String> segments, Bundle params) {
		Intent intent = createParamIntent(params);
		intent.setClass(this, PollsActivity.class);
		startActivity(intent);
		return true;
	}
	
	private boolean handleReviewLink(List<String> segments, Bundle params) {
		Intent intent = createParamIntent(params);
		intent.setClass(this, ReviewListActivity.class);
		startActivity(intent);
		return true;
	}
	
	private boolean handleTrollLink(List<String> segments, Bundle params) {
		Intent intent = createParamIntent(params);
		intent.setClass(this, TrollListActivity.class);
		
		startActivity(intent);
		return true;
	}
	
	private boolean handleMovieLink(List<String> segments, Bundle params) {
		Intent intent = createParamIntent(params);
		if (segments.isEmpty()) {
			boolean isUpcoming = Boolean.parseBoolean(params.getString("upcoming"));
			boolean isNewReleases = Boolean.parseBoolean(params.getString("new"));
			if (isUpcoming) {
				intent.setClass(this, UpcomingMovieActivity.class);
			} else if (isNewReleases) {
				intent.setClass(this, NewReleaseActivity.class);
			} else {
				intent.setClass(this, MovieActivity.class);
			}
		} else {
			intent.setClass(this, MoviePostDetailActivity.class);
			String movieId = segments.remove(0);
			intent.putExtra("movie", movieId);
		}
		
		startActivity(intent);
		return true;
	}
	
	private Intent createParamIntent(Bundle params) {
		Intent intent = new Intent();
		intent.putExtras(params);
		return intent;
	}
	
	private boolean handleNewsLink(List<String> segments, Bundle params) {
		Intent intent = createParamIntent(params);
		if (segments.isEmpty()) {
			intent.setClass(this, NewsGossipsActivity.class);
		} else {
			intent.setClass(this, ArticleDetailActivity.class);
			String newsId = segments.remove(0);
			intent.putExtra("newsId", newsId);
		}
		startActivity(intent);
		return true;
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
