package com.cinekancha.newRelease;

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
import com.cinekancha.entities.model.NewRelease;
import com.cinekancha.entities.rest.GetDataRepository;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.entities.rest.SetDataRepository;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.movieDetail.MoviePostDetailActivity;
import com.cinekancha.movies.MoviesAdapter;
import com.cinekancha.utils.Connectivity;
import com.cinekancha.view.CineMovieViewModel;

import java.net.MalformedURLException;
import java.util.List;

import butterknife.BindView;

public class NewReleaseActivity extends BaseNavigationActivity implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.movieRecyclerView)
    public RecyclerView recyclerView;

    @BindView(R.id.homeSwipeRefreshLayout)
    protected SwipeRefreshLayout homeSwipeRefreshLayout;

    private CineMovieViewModel cineMovieViewModel;

    private MoviesAdapter adapter;
    private List<Movie> movieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cineMovieViewModel = ViewModelProviders.of(this).get(CineMovieViewModel.class);
        init();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.newReleases);
    }

    private void init() {
        getSupportActionBar().setTitle(R.string.newReleases);

        homeSwipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);

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
        this.movieList = cineMovieViewModel.getMovieList();
        if (movieList != null && movieList.size() > 0) {
            adapter = new MoviesAdapter(movieList, this);
            recyclerView.setAdapter(adapter);
        } else if (Connectivity.isConnected(this))
            requestMovie();
        else
            Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void requestMovie() {
        if (Connectivity.isConnected(this))
            compositeDisposable.add(RestAPI.getInstance().getNewRelease()
                    .doOnSubscribe(disposable -> {
                        homeSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleDatabase, this::handleMovieFetchError));
        else
            compositeDisposable.add(GetDataRepository.getInstance().getNewReleaseData()
                    .doOnSubscribe(disposable -> {
                        homeSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleMovieData, this::handleMovieFetchError));

    }

    private void handleDatabase(NewRelease data) {
        compositeDisposable.add(SetDataRepository.getInstance().setNewRelease(data).toObservable()
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

    private void handleMovieData(NewRelease data) throws MalformedURLException {
        if (data != null && data.getData() != null) {
            cineMovieViewModel.setMovieList(data.getData());
            renderMovieData();
        } else Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(int id) {
        Movie movie = movieList.get(id);
        Intent detail = new Intent(this, MoviePostDetailActivity.class);
        detail.putExtra("movie", String.valueOf(movie.getId()));
        startActivity(detail);
    }

    @Override
    public void onRefresh() {
        requestMovie();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
