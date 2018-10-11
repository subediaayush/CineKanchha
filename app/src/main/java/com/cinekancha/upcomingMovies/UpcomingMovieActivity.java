package com.cinekancha.upcomingMovies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.entities.model.MovieData;
import com.cinekancha.entities.model.MovieDetail;
import com.cinekancha.entities.model.UpcomingMovie;
import com.cinekancha.entities.rest.GetDataRepository;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.entities.rest.SetDataRepository;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.movieDetail.MoviePostDetailActivity;
import com.cinekancha.movies.MoviesAdapter;
import com.cinekancha.utils.Connectivity;
import com.cinekancha.view.CineMovieViewModel;

import java.net.MalformedURLException;

import butterknife.BindView;

public class UpcomingMovieActivity extends BaseNavigationActivity implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.homeSwipeRefreshLayout)
    protected SwipeRefreshLayout homeSwipeRefreshLayout;

    @BindView(R.id.movieRecyclerView)
    public RecyclerView recyclerView;

    private CineMovieViewModel cineMovieViewModel;

    private MoviesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cineMovieViewModel = ViewModelProviders.of(this).get(CineMovieViewModel.class);
        init();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.movies);
    }

    private void init() {
        getSupportActionBar().setTitle(R.string.upcomingMovies);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
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
        if (cineMovieViewModel.getMovieList() == null) {
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
        if (cineMovieViewModel.getMovieList() != null && cineMovieViewModel.getMovieList().size() > 0) {
            adapter = new MoviesAdapter(cineMovieViewModel.getMovieList(), this);
            recyclerView.setAdapter(adapter);
        } else requestMovie();
    }

    private void requestMovie() {
        if (Connectivity.isConnected(this))
            compositeDisposable.add(RestAPI.getInstance().getUpcomingMovie()
                    .doOnSubscribe(disposable -> {
                        homeSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleDatabase, this::handleMovieFetchError));
        else
            compositeDisposable.add(GetDataRepository.getInstance().getUpcomingData()
                    .doOnSubscribe(disposable -> {
                        homeSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleMovieData, this::handleMovieFetchError));
    }

    private void handleDatabase(UpcomingMovie data) {
        compositeDisposable.add(SetDataRepository.getInstance().setUpcomingMovie(data)
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

    private void handleMovieData(UpcomingMovie data) throws MalformedURLException {
        cineMovieViewModel.setMovieList(data.getData());
        renderMovieData();
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
        requestMovie();
    }
}
