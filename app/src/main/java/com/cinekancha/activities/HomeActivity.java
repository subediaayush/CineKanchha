package com.cinekancha.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.entities.rest.RestAPI;
import com.google.gson.GsonBuilder;

import butterknife.BindView;

public class HomeActivity extends BaseNavigationActivity {
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.txtMovieDetail)
    TextView txtMovieDetail;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() -> getMovieApi());

        getMovieApi();
    }

    private void getMovieApi() {
        compositeDisposable.add(RestAPI.getInstance().getMovie()
                .doOnSubscribe(disposable -> {
                    txtMovieDetail.setText("");
                    swipeRefreshLayout.setRefreshing(true);
                })
                .doFinally(() -> swipeRefreshLayout.setRefreshing(false))
                .subscribe(this::handleMovieResults, this::handleMovieError));
    }

    private void handleMovieError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void handleMovieResults(Movie movie) {
        if (movie != null) {
            txtMovieDetail.setText("Movie Json : " + new GsonBuilder().setPrettyPrinting().create().toJson(movie));
        }
    }
}
