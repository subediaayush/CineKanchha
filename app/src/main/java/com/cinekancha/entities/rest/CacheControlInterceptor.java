package com.cinekancha.entities.rest;

import com.cinekancha.MyApplication;
import com.cinekancha.utils.Connectivity;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by paoneking on 3/3/18.
 */

public class CacheControlInterceptor implements Interceptor {
    public static Cache getCache() {
        //setup cache
        File httpCacheDirectory = new File(MyApplication.getInstance().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        return new Cache(httpCacheDirectory, cacheSize);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (Connectivity.isConnected(MyApplication.getInstance())) {
            int maxAge = 60 * 10; // read from cache for 10 minute
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }
}
