package com.cinekancha.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.entities.model.FeaturedContent;
import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.model.Links;
import com.cinekancha.entities.rest.GetDataRepository;
import com.cinekancha.entities.rest.SetDataRepository;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.home.HomeDataAdapter;
import com.cinekancha.home.OnSlideClickListener;
import com.cinekancha.home.SlideShowAdapter;
import com.cinekancha.utils.Connectivity;
import com.cinekancha.view.CineHomeViewModel;
import com.google.gson.Gson;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;

public class HomeActivity extends BaseNavigationActivity implements OnSlideClickListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "HomeActivity";

    @BindView(R.id.homeSwipeRefreshLayout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.home_list_view)
    protected RecyclerView mHomeListView;

    @BindView(R.id.featured_pager)
    protected ViewPager mFeaturedPager;

    @BindView(R.id.appbar_layout)
    protected AppBarLayout mAppbarLayout;

    @BindView(R.id.indicator)
    protected CircleIndicator mIndicator;

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

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
        getSupportActionBar().setTitle("Home");

        mCineHomeViewModel = ViewModelProviders.of(this).get(CineHomeViewModel.class);

        mHomeDataAdapter = new HomeDataAdapter();
        mSlideAdapter = new SlideShowAdapter(getSupportFragmentManager(), mFeaturedPager);
        mFeaturedPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    mSlideAdapter.startSlideshow();
                } else {
                    mSlideAdapter.stopSlideshow();
                }
            }
        });
        mSlideAdapter.setOnSlideClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mHomeListView.setLayoutManager(new LinearLayoutManager(this));
        mHomeListView.setAdapter(mHomeDataAdapter);

        mHomeListView.setNestedScrollingEnabled(false);

        mFeaturedPager.setAdapter(mSlideAdapter);

        mIndicator.setViewPager(mFeaturedPager);
        mSlideAdapter.registerDataSetObserver(mIndicator.getDataSetObserver());
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.home);
    }

    private void renderHomeData() {
        HomeData data = mCineHomeViewModel.getHomeData();
        mHomeDataAdapter.setHomeData(data);
        mSlideAdapter.setFeaturedItems(data.getFeaturedContents());
        mSlideAdapter.startSlideshow();
    }

    private void requestHomeData() {
        if (Connectivity.isConnected(this)) {
            compositeDisposable.add(RestAPI.getInstance().getHomeData()
                    .doOnSubscribe(disposable -> {
                        mSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> mSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleDatabase, this::handleHomeFetchError));
        } else {
            compositeDisposable.add(GetDataRepository.getInstance().getHomeData()
                    .doOnSubscribe(disposable -> {
                        mSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> mSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleHomeData, this::handleHomeFetchError));
        }
    }

    private void handleHomeFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleDatabase(HomeData data) {
        compositeDisposable.add(SetDataRepository.getInstance().setHomeData(data)
                .doOnSubscribe(disposable -> {
                })
                .doFinally(() -> {
                })
                .subscribe(this::handleHomeData, this::handleHomeFetchError));
    }

    private void handleHomeData(HomeData data) {
        mCineHomeViewModel.setHomeData(data);
        renderHomeData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSlideAdapter.stopSlideshow();
    }

    @Override
    protected void onResume() {
        super.onResume();
        HomeData data = mCineHomeViewModel.getHomeData();
        if (data != null) renderHomeData();
        else requestHomeData();
    }

    @Override
    public void onSlideClicked(FeaturedContent item) {
        Log.d(TAG, "clicked on item " + new Gson().toJson(item));
    }

    @Override
    public void onSlideClicked(Links item) {

    }

    @Override
    public void onRefresh() {
        requestHomeData();
    }
}
