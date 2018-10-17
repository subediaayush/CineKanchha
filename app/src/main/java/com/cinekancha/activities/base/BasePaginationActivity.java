package com.cinekancha.activities.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;

public abstract class BasePaginationActivity extends BaseNavigationActivity {
    private int visibleItemCount;
    private int totalItemCount;
    private int pastVisiblesItems;
    private boolean isLoadData = true;
    protected int currentPage = 1;

    protected abstract NestedScrollView getNestedScrollView();

    protected abstract LinearLayoutManager getLayoutManager();

    protected void loadMoreData() {
        setLoadData(true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getNestedScrollView().setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (v.getChildAt(v.getChildCount() - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY) {

                    visibleItemCount = getLayoutManager().getChildCount();
                    totalItemCount = getLayoutManager().getItemCount();
                    pastVisiblesItems = getLayoutManager().findFirstVisibleItemPosition();

                    if (isLoadData()) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            currentPage++;
                            loadMoreData();
                        }
                    }
                }
            }
        });
    }

    public boolean isLoadData() {
        return isLoadData;
    }

    public void setLoadData(boolean loadData) {
        isLoadData = loadData;
    }
}
