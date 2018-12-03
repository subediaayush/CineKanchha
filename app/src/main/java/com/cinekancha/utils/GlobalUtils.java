package com.cinekancha.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.cinekancha.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Wongel on 5/9/17.
 */
public class GlobalUtils {
    private static int radioId = 0;

    public static void navigateActivity(Context mContext, Boolean finish, Class className) {
        mContext.startActivity(new Intent(mContext, className));
        if (finish)
            ((AppCompatActivity) mContext).finish();
    }

    public static void navigateActivity(Context mContext, Boolean finish, Class className, int enterAnim, int exitAnim) {
        mContext.startActivity(new Intent(mContext, className));
        ((AppCompatActivity) mContext).overridePendingTransition(enterAnim, exitAnim);
        if (finish)
            ((AppCompatActivity) mContext).finish();
    }

    public static void navigateActivitywithData(Context mContext, Boolean finish, Class className, String data, int enterAnim, int exitAnim) {
        Intent intent = new Intent(mContext, className);
        intent.putExtra("DATA", data);
        mContext.startActivity(intent);
        ((AppCompatActivity) mContext).overridePendingTransition(enterAnim, exitAnim);
        if (finish)
            ((AppCompatActivity) mContext).finish();
    }

    public static void navigateActivitywithData(Context mContext, Boolean finish, Class className, Map<String, Object> data, int enterAnim, int exitAnim) {
        Intent intent = new Intent(mContext, className);
        for (String key : data.keySet()) {
            Object obj = data.get(key);
            if (obj instanceof String)
                intent.putExtra(key, (String) data.get(key));
            else if (obj instanceof Boolean)
                intent.putExtra(key, (boolean) data.get(key));
            else if (obj instanceof Integer)
                intent.putExtra(key, (int) data.get(key));
            else if (obj instanceof Double)
                intent.putExtra(key, (double) data.get(key));
            else if (obj instanceof Float)
                intent.putExtra(key, (float) data.get(key));
            else if (obj instanceof Long)
                intent.putExtra(key, (long) data.get(key));
        }
        mContext.startActivity(intent);
        ((AppCompatActivity) mContext).overridePendingTransition(enterAnim, exitAnim);
        if (finish)
            ((AppCompatActivity) mContext).finish();
    }

    public static void addFragment(Context context, Fragment fragment, int id, boolean addToBackstack) {
        FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager()
                .beginTransaction()
                .replace(id, fragment);

        if (addToBackstack)
            transaction.addToBackStack(null);

        transaction.commit();
    }

    public static void savePref(String name, String value, Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.PREF_TOKEN), Context.MODE_PRIVATE).edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static String getFromPref(String name, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.PREF_TOKEN), Context.MODE_PRIVATE);
        String value;
        value = prefs.getString(name, "");
        return value;
    }

    public static boolean isNetworkAvailable(Context mContext) {
        boolean result = false;
        try {
            ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        result = true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    //pattern eg "###,###,###,###.##"
    public static float formatNumber(float format, String pattern) {
        Double longval = Double.valueOf(format);
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        formatter.applyPattern(pattern);
        float formattedString = Float.parseFloat(formatter.format(longval));
        return formattedString;
    }

    public static void setLanguage(Context context, String languageToLoad) {
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(locale);
        } else {
            config.locale = locale;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            context = context.createConfigurationContext(config);
        } else {
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }
    }

    public static void restartActivity(Context context) {
        Intent intent = ((AppCompatActivity) context).getIntent();
        ((AppCompatActivity) context).finish();
        context.startActivity(intent);
    }

//    public static void savePref(String name, String value, Context context) {
//        SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.PREF_TOKEN), Context.MODE_PRIVATE).edit();
//        editor.putString(name, value);
//        editor.apply();
//    }
//
//    public static String getFromPref(String name, Context context) {
//        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.PREF_TOKEN), Context.MODE_PRIVATE);
//        String value;
//        value = prefs.getString(name, "");
//        return value;
//    }
//
//    public static void clearNumber(Context context) {
//        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.PREF_TOKEN), Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.clear();
//        editor.apply();
//    }
//
//

    public static Date getDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        try {
            Date date1 = simpleDateFormat.parse(date);
            return date1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String extractYoutubeId(String url) throws MalformedURLException {
        String query = new URL(url).getQuery();
        String[] param = query.split("&");
        String id = null;
        for (String row : param) {
            String[] param1 = row.split("=");
            if (param1[0].equals("v")) {
                id = param1[1];
            }
        }
        return id;
    }

    public static String extractYoutubeUrl(String videoUrl) {
        String imgUrl = "https://img.youtube.com/vi/" + getYoutubeVideoIdFromUrl(videoUrl) + "/0.jpg";
        return imgUrl;
    }

    public static String getYoutubeVideoIdFromUrl(String inUrl) {
        if (inUrl.toLowerCase().contains("youtu.be")) {
            return inUrl.substring(inUrl.lastIndexOf("/") + 1);
        }
        String pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(inUrl);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
