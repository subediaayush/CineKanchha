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
import com.cinekancha.article.ArticleDetailActivity;
import com.cinekancha.entities.model.Article;
import com.cinekancha.entities.model.FeaturedContent;
import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.model.Links;
import com.cinekancha.entities.model.PollDatabase;
import com.cinekancha.entities.rest.GetDataRepository;
import com.cinekancha.entities.rest.SetDataRepository;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.home.HomeDataAdapter;
import com.cinekancha.home.OnSlideClickListener;
import com.cinekancha.home.SlideShowAdapter;
import com.cinekancha.listener.OnPollClickListener;
import com.cinekancha.utils.Connectivity;
import com.cinekancha.view.CineHomeViewModel;
import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Response;

public class HomeActivity extends BaseNavigationActivity implements OnSlideClickListener, SwipeRefreshLayout.OnRefreshListener, OnPollClickListener {
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

    private long optionId;
    private long pollId;
    private List<PollDatabase> pollDatabaseList = new ArrayList();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Home");

        mCineHomeViewModel = ViewModelProviders.of(this).get(CineHomeViewModel.class);

        mHomeDataAdapter = new HomeDataAdapter(this);
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
        checkPollData();
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
        compositeDisposable.add(SetDataRepository.getInstance().setHomeData(data).toObservable()
                .doOnSubscribe(disposable -> {
                })
                .doFinally(() -> {
                })
                .subscribe(this::handleHomeData, this::handleHomeFetchError));
    }

    private void checkPollData() {
        if (pollDatabaseList != null && pollDatabaseList.size() > 0) {
            for (PollDatabase item : pollDatabaseList) {
                if (item.getPollId() == mCineHomeViewModel.getHomeData().getPoll().getId()) {
                    mCineHomeViewModel.getHomeData().getPoll().setStatus("INACTIVE");
                }
            }
        }
    }

    private void handleHomeData(HomeData data) {
        compositeDisposable.add(GetDataRepository.getInstance().getPollDatabase()
                .doOnSubscribe(disposable -> {
                    mSwipeRefreshLayout.setRefreshing(true);
                })
                .doFinally(() -> mSwipeRefreshLayout.setRefreshing(false))
                .subscribe(this::handlePollDatabase, this::handleHomeFetchError));
        if (data != null) {
            mCineHomeViewModel.setHomeData(data);
            renderHomeData();
        } else Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handlePollDatabase(List<PollDatabase> pollDatabases) {
        if (pollDatabaseList == null) {
            pollDatabaseList = pollDatabases;
        } else {
            pollDatabaseList.clear();
            pollDatabaseList.addAll(pollDatabases);
        }
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
        Article article = new Article();
        article.setId(item.getId());
        article.setTitle(item.getTitle());
        article.setContent(item.getSubtitle());
        article.setImage(item.getImageUrl());
        ArticleDetailActivity.startActivity(this, article);
    }

    @Override
    public void onSlideClicked(Links item) {

    }

    @Override
    public void onRefresh() {
        requestHomeData();
    }

    @Override
    public void onClick(int optionId, int pollId) {
        this.pollId = pollId;
        this.optionId = optionId;
        compositeDisposable.add(RestAPI.getInstance().postPoll(optionId)
                .doOnSubscribe(disposable -> {
                })
                .doFinally(() -> {
                })
                .subscribe(this::handlePostPoll, this::handleHomeFetchError));
    }

    private void handlePostPoll(Response<Void> data) throws MalformedURLException {
        Log.d("ResponseCode", String.valueOf(data));
        if (data.code() == 200) {
            PollDatabase pollDatabase = new PollDatabase();
            pollDatabase.setOptionId(optionId);
            pollDatabase.setPollId(pollId);
            compositeDisposable.add(SetDataRepository.getInstance().setPollDatabase(pollDatabase).toObservable()
                    .doOnSubscribe(disposable -> {
                    })
                    .doFinally(() -> {
                    })
                    .subscribe(this::notifyDataPoll, this::handleHomeFetchError));
        }

    }

    private void notifyDataPoll(PollDatabase pollDatabase) {
        requestHomeData();

    }

}
