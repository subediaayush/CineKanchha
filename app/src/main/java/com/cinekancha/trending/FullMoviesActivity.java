package com.cinekancha.trending;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.activities.base.PaginationNestedOnScrollListener;
import com.cinekancha.entities.model.FullMovies;
import com.cinekancha.entities.model.Video;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.utils.GlobalUtils;
import com.cinekancha.view.CineFullMoviesViewModel;

import java.net.MalformedURLException;

import butterknife.BindView;

public class FullMoviesActivity extends BaseNavigationActivity implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.movieRecyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.homeSwipeRefreshLayout)
    public SwipeRefreshLayout homeSwipeRefreshLayout;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    private PaginationNestedOnScrollListener paginationNestedOnScrollListener;
    private CineFullMoviesViewModel cineFullMoviesViewModel;

    private TrendingAdapter adapter;
    private String videoId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cineFullMoviesViewModel = ViewModelProviders.of(this).get(CineFullMoviesViewModel.class);
        init();
        if (cineFullMoviesViewModel.getVideoList() == null) {
            requestMovie();
        } else {
            try {
                renderMovieData();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.movies);
    }

    private void init() {
        getSupportActionBar().setTitle("Watch Full Movies");
        homeSwipeRefreshLayout.setOnRefreshListener(this);
        adapter = new TrendingAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        paginationNestedOnScrollListener = new PaginationNestedOnScrollListener(recyclerView, (LinearLayoutManager) recyclerView.getLayoutManager(), cineFullMoviesViewModel) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                requestMovie();
            }
        };
        nestedScrollView.setOnScrollChangeListener(paginationNestedOnScrollListener);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_movie;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void renderMovieData() throws MalformedURLException {
        if (cineFullMoviesViewModel.isToAppend()) {
            adapter.addTrendingList(cineFullMoviesViewModel.getAppendVideoList());
            cineFullMoviesViewModel.setToAppend(false);
        } else {
            adapter.setTrendingList(cineFullMoviesViewModel.getVideoList());
            cineFullMoviesViewModel.setToAppend(false);
        }
    }

    private void requestMovie() {
            compositeDisposable.add((RestAPI.getInstance().getFullMovies(cineFullMoviesViewModel.getCurrentPage()))
                    .doOnSubscribe(disposable -> {
                        homeSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleDatabase, this::handleMovieFetchError));
    }

    private void handleMovieFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleDatabase(FullMovies data) throws MalformedURLException {
        handleMovieData(data);
    }

    private void handleMovieData(FullMovies data) throws MalformedURLException {
        if (data != null && data.getTrendingList() != null) {
            cineFullMoviesViewModel.setVideoList(data.getTrendingList());
            cineFullMoviesViewModel.setAppendVideoList(data.getTrendingList());
            cineFullMoviesViewModel.setLastPage(data.getMeta().getLastPage());
            renderMovieData();
        } else Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(int id) {
        Video movie = cineFullMoviesViewModel.getVideoList().get(id);
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
        cineFullMoviesViewModel.resetState();
        paginationNestedOnScrollListener.resetState();
        requestMovie();
    }
}
