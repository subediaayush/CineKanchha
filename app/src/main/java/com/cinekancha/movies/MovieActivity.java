package com.cinekancha.movies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.activities.base.PaginationNestedOnScrollListener;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.entities.model.MovieData;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.movieDetail.MoviePostDetailActivity;
import com.cinekancha.utils.CharacterItemDecoration;
import com.cinekancha.utils.ScreenUtils;
import com.cinekancha.view.CineMovieViewModel;

import java.net.MalformedURLException;

import butterknife.BindView;

public class MovieActivity extends BaseNavigationActivity implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.movieRecyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.homeSwipeRefreshLayout)
    protected SwipeRefreshLayout homeSwipeRefreshLayout;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    private CineMovieViewModel cineMovieViewModel;

    private MoviesAdapter adapter;
    private PaginationNestedOnScrollListener paginationNestedOnScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cineMovieViewModel = ViewModelProviders.of(this).get(CineMovieViewModel.class);
        init();
        if (cineMovieViewModel.getMovieList() == null || cineMovieViewModel.getLastPage() == 0) {
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
        getSupportActionBar().setTitle(R.string.movies);
        homeSwipeRefreshLayout.setOnRefreshListener(this);
        adapter = new MoviesAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        int spanCount = 2; // 3 columns
        int spacing = ScreenUtils.dpToPx(this, 16); // 50px
        boolean includeEdge = true;
        recyclerView.addItemDecoration(new CharacterItemDecoration(spanCount, spacing, includeEdge));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        paginationNestedOnScrollListener = new PaginationNestedOnScrollListener(recyclerView, (GridLayoutManager) recyclerView.getLayoutManager(), cineMovieViewModel) {
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
        if (cineMovieViewModel.isToAppend()) {
            adapter.addMovieList(cineMovieViewModel.getAppendMovieList());
            cineMovieViewModel.setToAppend(false);
        } else {
            adapter.setMovieList(cineMovieViewModel.getMovieList());
            cineMovieViewModel.setToAppend(false);
        }

        /*if (cineMovieViewModel.getMovieList() != null && cineMovieViewModel.getMovieList().size() > 0) {
            adapter.setMovieList(cineMovieViewModel.getMovieList());
        } else requestMovie();*/
    }

    private void requestMovie() {
            compositeDisposable.add(RestAPI.getInstance().getMovie(cineMovieViewModel.getCurrentPage())
                    .doOnSubscribe(disposable -> {
                        homeSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> {
                        homeSwipeRefreshLayout.setRefreshing(false);
                    })
                    .subscribe(this::handleDatabase, this::handleMovieFetchError));
//        else
//            compositeDisposable.add(GetDataRepository.getInstance().getMovieData()
//                    .doOnSubscribe(disposable -> {
//                        homeSwipeRefreshLayout.setRefreshing(true);
//                    })
//                    .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
//                    .subscribe(this::handleMovieData, this::handleMovieFetchError));
    }

    private void handleDatabase(MovieData data) throws MalformedURLException {
        handleMovieData(data);
//        compositeDisposable.add(SetDataRepository.getInstance().setMovie(data).toObservable()
//                .doOnSubscribe(disposable -> {
//                })
//                .doFinally(() -> {
//                })
//                .subscribe(this::handleMovieData, this::handleMovieFetchError));
    }

    private void handleMovieFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleMovieData(MovieData data) throws MalformedURLException {
        if (data != null && data.getData() != null) {
            cineMovieViewModel.setMovieList(data.getData());
            cineMovieViewModel.setAppendMovieList(data.getData());
            cineMovieViewModel.setLastPage(data.getMeta().getLastPage());
            renderMovieData();
        } else Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(int id) {
        Movie movie = cineMovieViewModel.getMovieList().get(id);
        Intent detail = new Intent(this, MoviePostDetailActivity.class);
        detail.putExtra("movie", String.valueOf(movie.getId()));
        startActivity(detail);
    }

    @Override
    public void onRefresh() {
        cineMovieViewModel.resetState();
        paginationNestedOnScrollListener.resetState();
        requestMovie();
    }
}
