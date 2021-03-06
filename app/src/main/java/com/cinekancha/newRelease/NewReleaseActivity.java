package com.cinekancha.newRelease;

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
import com.cinekancha.entities.model.Movie;
import com.cinekancha.entities.model.NewRelease;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.movieDetail.MoviePostDetailActivity;
import com.cinekancha.movies.MoviesAdapter;
import com.cinekancha.utils.CharacterItemDecoration;
import com.cinekancha.utils.ScreenUtils;
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
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    private CineMovieViewModel cineMovieViewModel;

    private MoviesAdapter adapter;
    private List<Movie> movieList;
    private PaginationNestedOnScrollListener paginationNestedOnScrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cineMovieViewModel = ViewModelProviders.of(this).get(CineMovieViewModel.class);
        init();
        if (cineMovieViewModel.getMovieList() == null) {
            requestMovie();
        } else {
            renderMovieData();
        }
    }

    private void init() {
        getSupportActionBar().setTitle(R.string.newReleases);
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

    private void renderMovieData() {
        if (cineMovieViewModel.isToAppend()) {
            adapter.addMovieList(cineMovieViewModel.getAppendMovieList());
            cineMovieViewModel.setToAppend(false);
        } else {
            adapter.setMovieList(cineMovieViewModel.getMovieList());
            cineMovieViewModel.setToAppend(false);
        }
    }

    private void requestMovie() {
        compositeDisposable.add(RestAPI.getInstance().getNewRelease(cineMovieViewModel.getCurrentPage())
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

    private void handleMovieData(NewRelease data) throws MalformedURLException {
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
