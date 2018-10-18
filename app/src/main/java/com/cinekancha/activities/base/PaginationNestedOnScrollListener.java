package com.cinekancha.activities.base;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.cinekancha.view.BasePaginationViewModel;

public abstract class PaginationNestedOnScrollListener implements NestedScrollView.OnScrollChangeListener {
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;

/*    // The current offset index of data you have loaded
    private int currentPage = 0;*/

    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;

    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;

    private RecyclerView.LayoutManager mLayoutManager;

    private RecyclerView mRecyclerView;

    private BasePaginationViewModel mPaginationViewModel;

    public PaginationNestedOnScrollListener(RecyclerView recyclerView, LinearLayoutManager layoutManager, BasePaginationViewModel basePaginationViewModel) {
        this.mRecyclerView = recyclerView;
        this.mLayoutManager = layoutManager;
        this.mPaginationViewModel = basePaginationViewModel;
    }

    public PaginationNestedOnScrollListener(RecyclerView recyclerView, GridLayoutManager layoutManager, BasePaginationViewModel basePaginationViewModel) {
        this.mRecyclerView = recyclerView;
        this.mLayoutManager = layoutManager;
        this.mPaginationViewModel = basePaginationViewModel;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public PaginationNestedOnScrollListener(RecyclerView recyclerView, StaggeredGridLayoutManager layoutManager, BasePaginationViewModel basePaginationViewModel) {
        this.mRecyclerView = recyclerView;
        this.mLayoutManager = layoutManager;
        this.mPaginationViewModel = basePaginationViewModel;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        if (v.getChildAt(v.getChildCount() - 1) != null) {
            if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY) {
                int lastVisibleItemPosition = 0;
                int totalItemCount = mLayoutManager.getItemCount();

                if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                    int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
                    // get maximum element within the list
                    lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
                } else if (mLayoutManager instanceof GridLayoutManager) {
                    lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                } else if (mLayoutManager instanceof LinearLayoutManager) {
                    lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
                }

                // If it’s still loading, we check to see if the dataset count has
                // changed, if so we conclude it has finished loading and update the current page
                // number and total item count.
                if (loading && (totalItemCount > previousTotalItemCount)) {
                    loading = false;
                    previousTotalItemCount = totalItemCount;
                }

                // If it isn’t currently loading, we check to see if we have breached
                // the visibleThreshold and need to reload more data.
                // If we do need to reload some more data, we execute onLoadMore to fetch the data.
                // threshold should reflect how many total columns there are too

                if (mPaginationViewModel.getCurrentPage() > mPaginationViewModel.getLastPage()) {
                }

                if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount
                        && mRecyclerView.getAdapter().getItemCount() > visibleThreshold) {// This condition will useful when recyclerview has less than visibleThreshold items
//                    currentPage++;
                    if (mPaginationViewModel.getCurrentPage() < mPaginationViewModel.getLastPage()) {
                        mPaginationViewModel.setCurrentPage(mPaginationViewModel.getCurrentPage() + 1);
                        mPaginationViewModel.setToAppend(true);
                        onLoadMore(mPaginationViewModel.getCurrentPage(), totalItemCount);
                        loading = true;
                    }
                }
            }
        }
    }

    // Call whenever performing new searches
    public void resetState() {
        mPaginationViewModel.setCurrentPage(mPaginationViewModel.startingPageIndex);
        mPaginationViewModel.setToAppend(false);
        this.previousTotalItemCount = 0;
        this.loading = true;
    }

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount);
}
