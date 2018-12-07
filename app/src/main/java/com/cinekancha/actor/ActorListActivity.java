package com.cinekancha.actor;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.activities.base.PaginationNestedOnScrollListener;
import com.cinekancha.entities.model.Actor;
import com.cinekancha.entities.model.ActorGallery;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.utils.CharacterItemDecoration;
import com.cinekancha.utils.ScreenUtils;
import com.cinekancha.view.CineActorViewModel;

import java.net.MalformedURLException;

import butterknife.BindView;

public class ActorListActivity extends BaseNavigationActivity implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.movieRecyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.homeSwipeRefreshLayout)
    public SwipeRefreshLayout homeSwipeRefreshLayout;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    private CineActorViewModel cineActorViewModel;

    private ActorAdapter adapter;

    private PaginationNestedOnScrollListener paginationNestedOnScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cineActorViewModel = ViewModelProviders.of(this).get(CineActorViewModel.class);
        init();
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

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.movies);
    }

    private void init() {
        getSupportActionBar().setTitle(R.string.photoGallery);
        homeSwipeRefreshLayout.setOnRefreshListener(this);
        adapter = new ActorAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        int spanCount = 2; // 3 columns
        int spacing = ScreenUtils.dpToPx(this, 16); // 50px
        boolean includeEdge = true;
        recyclerView.addItemDecoration(new CharacterItemDecoration(spanCount, spacing, includeEdge));
        paginationNestedOnScrollListener = new PaginationNestedOnScrollListener(recyclerView, (GridLayoutManager) recyclerView.getLayoutManager(), cineActorViewModel) {
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
        if (cineActorViewModel.isToAppend()) {
            adapter.addMovieList(cineActorViewModel.getAppendActorList());
            cineActorViewModel.setToAppend(false);
        } else {
            adapter.setMovieList(cineActorViewModel.getActorList());
            cineActorViewModel.setToAppend(false);
        }
       /* if (cineActorViewModel.getActorList() != null && cineActorViewModel.getActorList().size() > 0) {
            adapter = new ActorAdapter(cineActorViewModel.getActorList(), this);
            recyclerView.setAdapter(adapter);
        } else requestMovie();*/
    }

    private void requestMovie() {
            compositeDisposable.add(RestAPI.getInstance().getActorList(cineActorViewModel.getCurrentPage())
                    .doOnSubscribe(disposable -> {
                        homeSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleDatabase, this::handleMovieFetchError));
    }

    private void handleDatabase(ActorGallery data) throws MalformedURLException {
        handleMovieData(data);
    }

    private void handleMovieFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleMovieData(ActorGallery data) throws MalformedURLException {
        if (data != null && data.getData() != null) {
            cineActorViewModel.setActorList(data.getData());
            cineActorViewModel.setAppendActorList(data.getData());
            cineActorViewModel.setLastPage(data.getMeta().getLastPage());
            renderMovieData();
        } else Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
       /* if (data != null && data.getData() != null) {
            cineActorViewModel.setActorList(data.getData());
            renderMovieData();
        } else
            Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();*/
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
        cineActorViewModel.resetState();
        paginationNestedOnScrollListener.resetState();
        requestMovie();
    }
}
