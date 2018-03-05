package com.cinekancha.entities.rest;

import com.cinekancha.BuildConfig;
import com.cinekancha.entities.model.Movie;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by paoneking on 3/3/18.
 */

public class RestAPI {
    private static final String TAG = RestAPI.class.getSimpleName();
    private static RestAPI INSTANCE;
    private static ApiService service;
    private Call<?> call;

    private RestAPI() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .cache(CacheControlInterceptor.getCache())
                .addNetworkInterceptor(new CacheControlInterceptor())
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(client)
                .build();
        service = retrofit.create(ApiService.class);
    }

    public static RestAPI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RestAPI();
        }
        return INSTANCE;
    }

    public static ApiService getApiService() {
        return getInstance().service;
    }

    public void cancel() {
        if (call != null && call.isExecuted() && !call.isCanceled())
            call.cancel();
    }

    public Observable<Movie> getMovie() {
        return getApiService().getMovie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }
}
