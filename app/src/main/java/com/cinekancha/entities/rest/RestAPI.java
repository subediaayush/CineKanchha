package com.cinekancha.entities.rest;

import android.content.Context;

import com.cinekancha.BuildConfig;
import com.cinekancha.MyApplication;
import com.cinekancha.entities.model.ActorGallery;
import com.cinekancha.entities.model.ActorPhoto;
import com.cinekancha.entities.model.Article;
import com.cinekancha.entities.model.BoxOfficeData;
import com.cinekancha.entities.model.FullMovies;
import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.model.MovieData;
import com.cinekancha.entities.model.MovieDetail;
import com.cinekancha.entities.model.NewRelease;
import com.cinekancha.entities.model.NewsGossip;
import com.cinekancha.entities.model.Poll;
import com.cinekancha.entities.model.Reviews;
import com.cinekancha.entities.model.TrendingData;
import com.cinekancha.entities.model.Trivia;
import com.cinekancha.entities.model.Troll;
import com.cinekancha.entities.model.UpcomingMovie;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by paoneking on 3/3/18.
 */

public class RestAPI {
    
    private static final String BIG_CACHE_PATH = "fifu-http";
    private static final int MAX_DISK_CACHE_SIZE = 40 * 1024 * 1024;      // 20MB
    
    
    private static final String TAG = RestAPI.class.getSimpleName();
    private static RestAPI INSTANCE;
    private static ApiService service;
    private Call<?> call;

    private RestAPI() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        
        Context context = MyApplication.getInstance();

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .cache(createCache(context))
                .addNetworkInterceptor(new Interceptors.ResponseCacheInterceptor())
                .addInterceptor(new Interceptors.OfflineResponseCacheInterceptor(context))
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
        return service;
    }
    
    public Cache createCache(Context context) {
        return new Cache(new File(
                context.getApplicationContext().getCacheDir(),
                BIG_CACHE_PATH), MAX_DISK_CACHE_SIZE);
    }

    public void cancel() {
        if (call != null && call.isExecuted() && !call.isCanceled())
            call.cancel();
    }

    public Observable<MovieDetail> getMovieDetail(int id) {
        return getApiService().getMovie(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }

    public Observable<MovieData> getMovie(int currentPage) {
        return getApiService().getMovieList(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }

    public Observable<NewRelease> getNewRelease(int currentPage) {
        return getApiService().getNewRelease(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }

    public Observable<UpcomingMovie> getUpcomingMovie(int currentPage) {
        return getApiService().getUpcomingMovie(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }

    public Observable<BoxOfficeData> getBOxOffice() {
        return getApiService().getBoxOffice()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }

    public Observable<Response<HomeData>> getHomeData() {
        return getApiService().getHomeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }

    public Observable<NewsGossip> getNewsGossip(int currentPage) {
        return getApiService().getNewsGossip(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }


    public Observable<Troll> getTroll(int currentPage) {
        return getApiService().getTroll(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }

    public Observable<TrendingData> getTrending(int currentPage) {
        return getApiService().getTrending(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }

    public Observable<ActorGallery> getActorList(int currentPage) {

        return getApiService().getActorList(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }

    public Observable<Reviews> getReviews(int currentPage) {
        return getApiService().getReviews(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }


    public Observable<ActorPhoto> getActorPhoto(int id) {
        return getApiService().getActorPhoto(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }

    public Observable<FullMovies> getFullMovies(int currentPage) {
        return getApiService().getFullMovies(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }

    public Observable<Poll> getPoll(int currentPage) {
        return getApiService().getPoll(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }

    public Observable<Response<Void>> postPoll(long optionId) {
        return getApiService().postPoll(optionId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }

    public Observable<Trivia> getTrivia(int currentPage) {
        return getApiService().getTrivia(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }
    
    public Observable<Article> getArticle(long articleId) {
        return getApiService().getArticle(articleId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }
    
    public Observable<Article> getNews(long newsId) {
        return getApiService().getNews(newsId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }
}
