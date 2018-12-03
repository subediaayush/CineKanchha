package com.cinekancha.trivia;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.activities.base.PaginationNestedOnScrollListener;
import com.cinekancha.adapters.base.RecyclerViewClickListener;
import com.cinekancha.entities.model.Trivia;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.view.CineTriviaViewModel;

import java.net.MalformedURLException;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class TriviaListActivity extends BaseNavigationActivity implements RecyclerViewClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.list_view)
    public RecyclerView mArticleList;
    @BindView(R.id.homeSwipeRefreshLayout)
    protected SwipeRefreshLayout homeSwipeRefreshLayout;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    private CineTriviaViewModel mCineTriviaViewModel;
    private TriviaAdapter mArticleAdapter;
    private PaginationNestedOnScrollListener paginationNestedOnScrollListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCineTriviaViewModel = ViewModelProviders.of(this).get(CineTriviaViewModel.class);
        init();

        if (mCineTriviaViewModel.getTriviaDataList() == null) {
            requestTrivia();
        } else {
            renderTrivia();
        }
    }

    private void init() {
        getSupportActionBar().setTitle(R.string.trivia);
        homeSwipeRefreshLayout.setOnRefreshListener(this);
        mArticleAdapter = new TriviaAdapter();
        mArticleList.setLayoutManager(new LinearLayoutManager(this));
        mArticleList.setNestedScrollingEnabled(false);
        mArticleList.setAdapter(mArticleAdapter);
        mArticleAdapter.setOnClickListener(this);
        paginationNestedOnScrollListener = new PaginationNestedOnScrollListener(mArticleList, (LinearLayoutManager) mArticleList.getLayoutManager(), mCineTriviaViewModel) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                requestTrivia();
            }
        };
        nestedScrollView.setOnScrollChangeListener(paginationNestedOnScrollListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_general_list;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void requestTrivia() {
        compositeDisposable.add(RestAPI.getInstance().getTrivia(mCineTriviaViewModel.getCurrentPage())
                .doOnSubscribe(disposable -> {
                    homeSwipeRefreshLayout.setRefreshing(true);
                })
                .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                .subscribe(this::handleTriviaData, this::handleFetchError));
    }

    private void handleTriviaData(Trivia trivia) {
        if (trivia != null && trivia.getData() != null) {
            mCineTriviaViewModel.setTriviaDataList(trivia.getData());
            mCineTriviaViewModel.setAppendTriviaDataList(trivia.getData());
            mCineTriviaViewModel.setLastPage(trivia.getMeta().getLastPage());
            renderTrivia();
        } else Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void renderTrivia() {
        if (mCineTriviaViewModel.isToAppend()) {
            mArticleAdapter.addTriviaDataList(mCineTriviaViewModel.getAppendTriviaDataList());
            mCineTriviaViewModel.setToAppend(false);
        } else {
            mArticleAdapter.setTriviaDataList(mCineTriviaViewModel.getTriviaDataList());
            mCineTriviaViewModel.setToAppend(false);
        }
    }

    @Override
    public void onClick(View v, int position) {
    }

    @Override
    public void onRefresh() {
        mCineTriviaViewModel.resetState();
        paginationNestedOnScrollListener.resetState();
        requestTrivia();
    }
}
