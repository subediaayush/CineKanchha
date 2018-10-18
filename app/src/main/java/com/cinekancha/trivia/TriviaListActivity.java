package com.cinekancha.trivia;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.adapters.base.RecyclerViewClickListener;
import com.cinekancha.entities.model.Trivia;
import com.cinekancha.entities.rest.GetDataRepository;
import com.cinekancha.entities.rest.SetDataRepository;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.utils.Connectivity;
import com.cinekancha.view.CineTriviaViewModel;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class TriviaListActivity extends BaseNavigationActivity implements RecyclerViewClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.list_view)
    public RecyclerView mArticleList;

    private CineTriviaViewModel mCineTriviaViewModel;
    private TriviaAdapter mArticleAdapter;

    @BindView(R.id.homeSwipeRefreshLayout)
    protected SwipeRefreshLayout homeSwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.trivia);
        mCineTriviaViewModel = ViewModelProviders.of(this).get(CineTriviaViewModel.class);

        mArticleAdapter = new TriviaAdapter();

        mArticleList.setLayoutManager(new LinearLayoutManager(this));
        mArticleList.setNestedScrollingEnabled(false);
        mArticleList.setAdapter(mArticleAdapter);
        mArticleAdapter.setOnClickListener(this);
        homeSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_general_list;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mCineTriviaViewModel.getTrivias() == null) {
            requestTrivia(null, 50);
        } else {
            mArticleAdapter.setTrivias(mCineTriviaViewModel.getTrivias());
        }
    }

    private void requestTrivia(String cursor, int count) {
        if (Connectivity.isConnected(this))
            compositeDisposable.add(RestAPI.getInstance().getTrivia()
                    .doOnSubscribe(disposable -> {
                        homeSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleDatabase, this::handleFetchError));
        else
            compositeDisposable.add(GetDataRepository.getInstance().getTrivia()
                    .doOnSubscribe(disposable -> {
                        homeSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleTriviaData, this::handleFetchError));
    }

    private void handleTriviaData(Trivia trivia) {
        if (trivia != null && trivia.getData() != null) {
            mCineTriviaViewModel.setArticles(trivia.getData());
            renderTrivia();
        } else Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();

    }

    private void handleDatabase(Trivia trivia) {
        compositeDisposable.add(SetDataRepository.getInstance().setTriviaData(trivia).toObservable()
                .doOnSubscribe(disposable -> {
                })
                .doFinally(() -> {
                })
                .subscribe(this::handleTriviaData, this::handleFetchError));
    }

    private void handleFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void renderTrivia() {
        if (mCineTriviaViewModel.getTrivias() != null && mCineTriviaViewModel.getTrivias().size() > 0) {
            mArticleAdapter.setTrivias(mCineTriviaViewModel.getTrivias());
        } else {
            requestTrivia(null, 50);
        }
    }

    @Override
    public void onClick(View v, int position) {
    }

    @Override
    public void onRefresh() {
        requestTrivia(null, 50);
    }
}
