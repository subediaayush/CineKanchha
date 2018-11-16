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
import com.cinekancha.entities.model.TrendingData;
import com.cinekancha.entities.model.Video;
import com.cinekancha.entities.rest.GetDataRepository;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.entities.rest.SetDataRepository;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.utils.Connectivity;
import com.cinekancha.utils.GlobalUtils;
import com.cinekancha.view.CineTrendingViewModel;

import java.net.MalformedURLException;

import butterknife.BindView;

public class TrendingActivity extends BaseNavigationActivity implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.movieRecyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.homeSwipeRefreshLayout)
    public SwipeRefreshLayout homeSwipeRefreshLayout;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    private PaginationNestedOnScrollListener paginationNestedOnScrollListener;

    private CineTrendingViewModel cineTrendingViewModel;

    private TrendingAdapter adapter;
    private String videoId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cineTrendingViewModel = ViewModelProviders.of(this).get(CineTrendingViewModel.class);
        init();
        if (cineTrendingViewModel.getTrendingList() == null) {
            requestMovie();
        } else {
            try {
                renderMovieData();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        getSupportActionBar().setTitle("Trending Videos");

        homeSwipeRefreshLayout.setOnRefreshListener(this);
        adapter = new TrendingAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        paginationNestedOnScrollListener = new PaginationNestedOnScrollListener(recyclerView, (LinearLayoutManager) recyclerView.getLayoutManager(), cineTrendingViewModel) {
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
        if (cineTrendingViewModel.isToAppend()) {
            adapter.addTrendingList(cineTrendingViewModel.getAppendTrendingList());
            cineTrendingViewModel.setToAppend(false);
        } else {
            adapter.setTrendingList(cineTrendingViewModel.getTrendingList());
            cineTrendingViewModel.setToAppend(false);
        }
    }

    private void requestMovie() {
        if (Connectivity.isConnected(this))
            compositeDisposable.add((RestAPI.getInstance().getTrending(cineTrendingViewModel.getCurrentPage()))
                    .doOnSubscribe(disposable -> {
                        homeSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleDatabase, this::handleMovieFetchError));
        else
            compositeDisposable.add(GetDataRepository.getInstance().getTrendingData()
                    .doOnSubscribe(disposable -> {
                        homeSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleMovieData, this::handleMovieFetchError));
    }

    private void handleDatabase(TrendingData data) {
        compositeDisposable.add(SetDataRepository.getInstance().setTrending(data).toObservable()
                .doOnSubscribe(disposable -> {
                })
                .doFinally(() -> {
                })
                .subscribe(this::handleMovieData, this::handleMovieFetchError));
    }

    private void handleMovieFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleMovieData(TrendingData data) throws MalformedURLException {
        if (data != null && data.getTrendingList() != null) {
            cineTrendingViewModel.setTrendingList(data.getTrendingList());
            cineTrendingViewModel.setAppendTrendingList(data.getTrendingList());
            cineTrendingViewModel.setLastPage(data.getMeta().getLastPage());
            renderMovieData();
        } else Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(int id) {
        Video movie = cineTrendingViewModel.getTrendingList().get(id);
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
        cineTrendingViewModel.resetState();
        paginationNestedOnScrollListener.resetState();
        requestMovie();
    }
}
