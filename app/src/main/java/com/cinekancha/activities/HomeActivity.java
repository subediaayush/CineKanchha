package com.cinekancha.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.home.HomeDataAdapter;
import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.view.CineHomeViewModel;

import butterknife.BindView;

public class HomeActivity extends BaseNavigationActivity {
    @BindView(R.id.swipeRefreshLayout)
    private SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.home_list_view)
    private RecyclerView mHomeListView;
    
    private HomeDataAdapter mAdapter;
    
    private CineHomeViewModel mCineHomeViewModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mCineHomeViewModel = ViewModelProviders.of(this).get(CineHomeViewModel.class);
        
        mAdapter = new HomeDataAdapter();
        
        mSwipeRefreshLayout.setOnRefreshListener(this::requestHomeData);
        HomeData data = mCineHomeViewModel.getHomeData();
        if (data == null) requestHomeData();
        else mAdapter.setHomeData(data);
        
        mHomeListView.setLayoutManager(new LinearLayoutManager(this));
        mHomeListView.setAdapter(mAdapter);
    }
    
    private void requestHomeData() {
        compositeDisposable.add(RestAPI.getInstance().getHomeData()
                .doOnSubscribe(disposable -> {
                    mSwipeRefreshLayout.setRefreshing(true);
                })
                .doFinally(() -> mSwipeRefreshLayout.setRefreshing(false))
                .subscribe(this::handleHomeData, this::handleHomeFetchError));
    }
    
    private void handleHomeFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleHomeData(HomeData data) {
        mCineHomeViewModel.setHomeData(data);
        mAdapter.setHomeData(data);
    }
}
