package com.cinekancha.trending;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.entities.Video;
import com.cinekancha.entities.model.TrendingData;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.utils.GlobalUtils;
import com.cinekancha.view.CineFullMoviesViewModel;
import com.cinekancha.view.CineTrendingViewModel;

import java.net.MalformedURLException;
import java.util.List;

import butterknife.BindView;

public class FullMoviesActivity extends BaseNavigationActivity implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.movieRecyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.homeSwipeRefreshLayout)
    public SwipeRefreshLayout homeSwipeRefreshLayout;

    private CineFullMoviesViewModel cineFullMoviesViewModel;

    private TrendingAdapter adapter;
    private List<Video> trendingList;
    private String videoId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cineFullMoviesViewModel = ViewModelProviders.of(this).get(CineFullMoviesViewModel.class);
        init();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.movies);
    }

    private void init() {
        getSupportActionBar().setTitle("Watch Full Movies");
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        homeSwipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cineFullMoviesViewModel.getTrendingList() == null) {
            requestMovie();
        } else {
            try {
                renderMovieData();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    private void renderMovieData() throws MalformedURLException {
        this.trendingList = cineFullMoviesViewModel.getTrendingList();
        if (cineFullMoviesViewModel.getTrendingList() != null && cineFullMoviesViewModel.getTrendingList().size() > 0) {
            adapter = new TrendingAdapter(cineFullMoviesViewModel.getTrendingList(), this);
            recyclerView.setAdapter(adapter);
        } else requestMovie();
    }

    private void requestMovie() {
        compositeDisposable.add(RestAPI.getInstance().getFullMovies()
                .doOnSubscribe(disposable -> {
                    homeSwipeRefreshLayout.setRefreshing(true);
                })
                .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                .subscribe(this::handleMovieData, this::handleMovieFetchError));
    }

    private void handleMovieFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleMovieData(TrendingData data) throws MalformedURLException {
        cineFullMoviesViewModel.setTrendingList(data.getTrendingList());
        renderMovieData();
    }

    @Override
    public void onClick(int id) {
        Video movie = trendingList.get(id);
        try {
            startYoutube(movie.getLink());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void startYoutube(String url) throws MalformedURLException {
        videoId = GlobalUtils.extractYoutubeId(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + videoId));
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        requestMovie();
    }
}
