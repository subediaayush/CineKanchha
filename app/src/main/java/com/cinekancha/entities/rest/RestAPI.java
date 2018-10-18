package com.cinekancha.entities.rest;

import com.cinekancha.BuildConfig;
import com.cinekancha.entities.model.ActorGallery;
import com.cinekancha.entities.model.ActorPhoto;
import com.cinekancha.entities.model.BoxOfficeItem;
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

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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

    public Observable<List<BoxOfficeItem>> getBOxOffice() {
        return getApiService().getBoxOffice()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }

    public Observable<HomeData> getHomeData() {
        return getApiService().getHomeData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }

    public Observable<NewsGossip> getNewsGossip() {
        return getApiService().getNewsGossip()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }


    public Observable<Troll> getTroll() {
        return getApiService().getTroll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }

    public Observable<TrendingData> getTrending() {
        return getApiService().getTrending()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }

    public Observable<ActorGallery> getActorList() {

        return getApiService().getActorList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }

    public Observable<Reviews> getReviews() {
        return getApiService().getReviews()
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

    public Observable<FullMovies> getFullMovies() {
        return getApiService().getFullMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }

    public Observable<Poll> getPoll() {
        return getApiService().getPoll()
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

    public Observable<Trivia> getTrivia() {
        return getApiService().getTrivia()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .retry(1);
    }
}
