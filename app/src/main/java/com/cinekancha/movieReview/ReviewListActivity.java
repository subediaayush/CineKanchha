package com.cinekancha.movieReview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.activities.base.PaginationNestedOnScrollListener;
import com.cinekancha.entities.model.ReviewData;
import com.cinekancha.entities.model.Reviews;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.utils.CharacterItemDecoration;
import com.cinekancha.utils.ScreenUtils;
import com.cinekancha.view.CineReviewViewModel;

import java.net.MalformedURLException;

import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class ReviewListActivity extends BaseNavigationActivity implements SwipeRefreshLayout.OnRefreshListener, OnClickListener {
    @BindView(R.id.list_view)
    public RecyclerView mArticleList;
    @BindView(R.id.homeSwipeRefreshLayout)
    protected SwipeRefreshLayout homeSwipeRefreshLayout;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    private CineReviewViewModel cineReviewViewModel;
    private ReviewAdapter mArticleAdapter;
    private PaginationNestedOnScrollListener paginationNestedOnScrollListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.moviesReviews);
        cineReviewViewModel = ViewModelProviders.of(this).get(CineReviewViewModel.class);
        init();

        if (cineReviewViewModel.getReviewDataList() == null) {
            requestTrivia();
        } else {
            try {
                renderTrivia();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        homeSwipeRefreshLayout.setOnRefreshListener(this);
        mArticleAdapter = new ReviewAdapter(this);
        mArticleList.setLayoutManager(new LinearLayoutManager(this));
        mArticleList.setAdapter(mArticleAdapter);
        mArticleList.setNestedScrollingEnabled(false);
        int spanCount = 1; // 3 columns
        int spacing = ScreenUtils.dpToPx(this, 16); // 50px
        boolean includeEdge = true;
        mArticleList.addItemDecoration(new CharacterItemDecoration(spanCount, spacing, includeEdge));
        paginationNestedOnScrollListener = new PaginationNestedOnScrollListener(mArticleList, (LinearLayoutManager) mArticleList.getLayoutManager(), cineReviewViewModel) {
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
            compositeDisposable.add(RestAPI.getInstance().getReviews(cineReviewViewModel.getCurrentPage())
                    .doOnSubscribe(disposable -> {
                        homeSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleDatabase, this::handleFetchError));

    }

    private void handleTriviaData(Reviews reviews) throws MalformedURLException {
        if (reviews != null && reviews.getData() != null) {
            cineReviewViewModel.setReviewDataList(reviews.getData());
            cineReviewViewModel.setAppendReviewDataList(reviews.getData());
            cineReviewViewModel.setLastPage(reviews.getMeta().getLastPage());
            renderTrivia();
        } else Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
        /*if (reviews != null && reviews.getData() != null) {
            cineReviewViewModel.setReviews(reviews);
            renderTrivia();
        } else Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();*/
    }

    private void handleDatabase(Reviews reviews) throws MalformedURLException {
        handleTriviaData(reviews);
    }

    private void handleFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void renderTrivia() throws MalformedURLException {
        if (cineReviewViewModel.isToAppend()) {
            mArticleAdapter.addReviewList(cineReviewViewModel.getAppendReviewDataList());
            cineReviewViewModel.setToAppend(false);
        } else {
            mArticleAdapter.setReviewList(cineReviewViewModel.getReviewDataList());
            cineReviewViewModel.setToAppend(false);
        }
        /*if (cineReviewViewModel.getReviews() != null && cineReviewViewModel.getReviews().getData().size() > 0) {
            mArticleAdapter.setTrivias(cineReviewViewModel.getReviews().getData());
        } else {
            requestTrivia(null, 50);
        }*/
    }


    @Override
    public void onRefresh() {
        cineReviewViewModel.resetState();
        paginationNestedOnScrollListener.resetState();
        requestTrivia();
    }

    @Override
    public void onClick(int position) {
        ReviewData reviewData = cineReviewViewModel.getReviewDataList().get(position);
        Intent intent = new Intent(this, ReviewDetailActivity.class);
        intent.putExtra("review", (Parcelable) reviewData);
        startActivity(intent);
    }
}
