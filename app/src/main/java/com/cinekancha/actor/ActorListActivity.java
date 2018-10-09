package com.cinekancha.actor;

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
import com.cinekancha.entities.model.Actor;
import com.cinekancha.entities.model.ActorGallery;
import com.cinekancha.entities.model.TrendingData;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.movieDetail.MoviePostDetailActivity;
import com.cinekancha.trending.TrendingAdapter;
import com.cinekancha.utils.GlobalUtils;
import com.cinekancha.view.CineActorViewModel;

import java.net.MalformedURLException;
import java.util.List;

import butterknife.BindView;

public class ActorListActivity extends BaseNavigationActivity implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.movieRecyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.homeSwipeRefreshLayout)
    public SwipeRefreshLayout homeSwipeRefreshLayout;

    private CineActorViewModel cineActorViewModel;

    private ActorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cineActorViewModel = ViewModelProviders.of(this).get(CineActorViewModel.class);
        init();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.movies);
    }

    private void init() {
        getSupportActionBar().setTitle(R.string.photoGallery);
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
        if (cineActorViewModel.getActorList() == null) {
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
        if (cineActorViewModel.getActorList() != null && cineActorViewModel.getActorList().size() > 0) {
            adapter = new ActorAdapter(cineActorViewModel.getActorList(), this);
            recyclerView.setAdapter(adapter);
        } else requestMovie();
    }

    private void requestMovie() {
        compositeDisposable.add(RestAPI.getInstance().getActorList()
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

    private void handleMovieData(ActorGallery data) throws MalformedURLException {
        cineActorViewModel.setActorList(data.getData());
        renderMovieData();
    }

    @Override
    public void onClick(int id) {
        Actor actor = cineActorViewModel.getActorList().get(id);
        Intent detail = new Intent(this, ActorDetailActivity.class);
        detail.putExtra("actor", String.valueOf(actor.getId()));
        startActivity(detail);

    }

    @Override
    public void onRefresh() {
        requestMovie();
    }
}
