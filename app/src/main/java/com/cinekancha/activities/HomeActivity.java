package com.cinekancha.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.entities.model.FeaturedItem;
import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.home.HomeDataAdapter;
import com.cinekancha.home.OnSlideClickListener;
import com.cinekancha.home.SlideShowAdapter;
import com.cinekancha.view.CineHomeViewModel;
import com.google.gson.Gson;

import butterknife.BindView;

public class HomeActivity extends BaseNavigationActivity implements OnSlideClickListener {
    private static final String TAG = "HomeActivity";
    
    @BindView(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.home_list_view)
    protected RecyclerView mHomeListView;
    @BindView(R.id.featured_pager)
    protected ViewPager mFeaturedPager;
    
    private HomeDataAdapter mHomeDataAdapter;
    
    private CineHomeViewModel mCineHomeViewModel;
    private SlideShowAdapter mSlideAdapter;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mCineHomeViewModel = ViewModelProviders.of(this).get(CineHomeViewModel.class);
        
        mHomeDataAdapter = new HomeDataAdapter();
        mSlideAdapter = new SlideShowAdapter(getSupportFragmentManager(), mFeaturedPager);
        
        mFeaturedPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mSlideAdapter.stopSlideshow();
            }
    
            @Override
            public void onPageSelected(int position) {
                mSlideAdapter.startSlideshow();
            }
    
            @Override
            public void onPageScrollStateChanged(int state) {
        
            }
        });
        mSlideAdapter.setOnSlideClickListener(this);
        
        mSwipeRefreshLayout.setOnRefreshListener(this::requestHomeData);
        HomeData data = mCineHomeViewModel.getHomeData();
        if (data == null) requestHomeData();
        else mCineHomeViewModel.setHomeData(data);
        setHomeData();
        
        mHomeListView.setLayoutManager(new LinearLayoutManager(this));
        mHomeListView.setAdapter(mHomeDataAdapter);
    }
    
    private void setHomeData() {
        HomeData data = mCineHomeViewModel.getHomeData();
        mHomeDataAdapter.setHomeData(data);
        mSlideAdapter.setFeaturedItems(data.getFeaturedItems());
        mSlideAdapter.startSlideshow();
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
        setHomeData();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        mSlideAdapter.stopSlideshow();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        setHomeData();
    }
    
    @Override
    public void onSlideClicked(FeaturedItem item) {
        Log.d(TAG, "clicked on item " + new Gson().toJson(item));
    }
}
