package com.cinekancha.movieReview;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import com.cinekancha.article.ArticleDetailActivity;
import com.cinekancha.entities.model.ReviewData;
import com.cinekancha.entities.model.Reviews;
import com.cinekancha.entities.model.Trivia;
import com.cinekancha.entities.rest.GetDataRepository;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.entities.rest.SetDataRepository;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.trivia.TriviaAdapter;
import com.cinekancha.utils.Connectivity;
import com.cinekancha.utils.GlobalUtils;
import com.cinekancha.view.CineReviewViewModel;
import com.cinekancha.view.CineTriviaViewModel;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class ReviewListActivity extends BaseNavigationActivity implements SwipeRefreshLayout.OnRefreshListener, OnClickListener {
    @BindView(R.id.list_view)
    public RecyclerView mArticleList;

    private CineReviewViewModel cineReviewViewModel;
    private ReviewAdapter mArticleAdapter;

    @BindView(R.id.homeSwipeRefreshLayout)
    protected SwipeRefreshLayout homeSwipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.moviesReviews);
        cineReviewViewModel = ViewModelProviders.of(this).get(CineReviewViewModel.class);

        mArticleAdapter = new ReviewAdapter(this);

        mArticleList.setLayoutManager(new LinearLayoutManager(this));
        mArticleList.setAdapter(mArticleAdapter);
        homeSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_general_list;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (cineReviewViewModel.getReviews() == null) {
            requestTrivia(null, 50);
        } else {
            mArticleAdapter.setTrivias(cineReviewViewModel.getReviews().getData());
        }
    }

    private void requestTrivia(String cursor, int count) {
        if (Connectivity.isConnected(this))
            compositeDisposable.add(RestAPI.getInstance().getReviews()
                    .doOnSubscribe(disposable -> {
                        homeSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleDatabase, this::handleFetchError));
        else
            compositeDisposable.add(GetDataRepository.getInstance().getReviews()
                    .doOnSubscribe(disposable -> {
                        homeSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleTriviaData, this::handleFetchError));
    }

    private void handleTriviaData(Reviews reviews) {
        if (reviews != null && reviews.getData() != null) {
            cineReviewViewModel.setReviews(reviews);
            renderTrivia();
        } else Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();

    }

    private void handleDatabase(Reviews reviews) {
        compositeDisposable.add(SetDataRepository.getInstance().setReviewData(reviews).toObservable()
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
        if (cineReviewViewModel.getReviews() != null && cineReviewViewModel.getReviews().getData().size() > 0) {
            mArticleAdapter.setTrivias(cineReviewViewModel.getReviews().getData());
        } else {
            requestTrivia(null, 50);
        }
    }


    @Override
    public void onRefresh() {
        requestTrivia(null, 50);
    }

    @Override
    public void onClick(int position) {
        ReviewData reviewData = cineReviewViewModel.getReviews().getData().get(position);
        Intent intent = new Intent(this, ReviewDetailActivity.class);
        intent.putExtra("review", reviewData);
        startActivity(intent);
    }
}
