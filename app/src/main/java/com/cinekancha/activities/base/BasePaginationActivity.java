package com.cinekancha.activities.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;

public abstract class BasePaginationActivity extends BaseNavigationActivity {
    private int visibleItemCount;
    private int totalItemCount;
    private int pastVisiblesItems;
    private boolean isLoadData = true;
    protected int currentPage = 1;

    protected abstract NestedScrollView getNestedScrollView();

    protected abstract GridLayoutManager getLayoutManager();

    protected abstract void loadMoreData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getNestedScrollView().setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (v.getChildAt(v.getChildCount() - 1) != null) {
                    if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY) {

                        visibleItemCount = BasePaginationActivity.this.getLayoutManager().getChildCount();
                        totalItemCount = BasePaginationActivity.this.getLayoutManager().getItemCount();
                        pastVisiblesItems = BasePaginationActivity.this.getLayoutManager().findFirstVisibleItemPosition();

                        if (BasePaginationActivity.this.isLoadData()) {
                            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                currentPage++;
                                BasePaginationActivity.this.loadMoreData();
                            }
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
