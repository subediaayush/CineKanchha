package com.cinekancha.poll;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.entities.model.MovieData;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.movieDetail.MoviePostDetailActivity;
import com.cinekancha.movies.MoviesAdapter;
import com.cinekancha.view.CineMovieViewModel;
import com.cinekancha.view.CinePollViewModel;

import java.net.MalformedURLException;
import java.util.List;

import butterknife.BindView;

public class PollsActivity extends BaseNavigationActivity {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.pollRecyclerView)
    public RecyclerView recyclerView;

    private CinePollViewModel cinePollViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cinePollViewModel = ViewModelProviders.of(this).get(CinePollViewModel.class);
        initToolbar();
        init();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.poll);
    }


    private void init() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_polls;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cinePollViewModel.getPollData() == null) {
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
//        this.movieList = cinePollViewModel.getMovieList();
//        if (movieList != null && movieList.size() > 0) {
//            adapter = new MoviesAdapter(movieList, this);
//            recyclerView.setAdapter(adapter);
//        } else requestMovie();
    }

    private void requestMovie() {
        compositeDisposable.add(RestAPI.getInstance().getMovie()
                .doOnSubscribe(disposable -> {
//                    mSwipeRefreshLayout.setRefreshing(true);
                })
//                .doFinally(() -> mSwipeRefreshLayout.setRefreshing(false))
                .subscribe(this::handleMovieData, this::handleMovieFetchError));
    }

    private void handleMovieFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleMovieData(MovieData data) throws MalformedURLException {
//        cinePollViewModel.setMovieList(data.getData());
//        renderMovieData();
    }


}
