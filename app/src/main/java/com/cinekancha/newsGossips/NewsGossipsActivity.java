package com.cinekancha.newsGossips;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.entities.model.FeaturedContent;
import com.cinekancha.entities.model.Links;
import com.cinekancha.entities.model.NewsGossip;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.home.OnSlideClickListener;
import com.cinekancha.utils.GlobalUtils;
import com.cinekancha.utils.ListUtils;
import com.cinekancha.view.CineNewsGossipsViewModel;

import java.net.MalformedURLException;

import butterknife.BindView;

public class NewsGossipsActivity extends BaseNavigationActivity implements OnSlideClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.newsSwipeToRefresh)
    public SwipeRefreshLayout newsSwipeToRefresh;

    @BindView(R.id.recyclerViewNews)
    public RecyclerView recyclerViewNews;
    private CineNewsGossipsViewModel mCineNewsGossipsModel;
    private NewsGossipAdapter adapter;
    private String videoId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCineNewsGossipsModel = ViewModelProviders.of(this).get(CineNewsGossipsViewModel.class);
        toolbar.setTitle(getString(R.string.app_name));
        init();
    }

    private void init() {
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewNews.setNestedScrollingEnabled(false);
        recyclerViewNews.setHasFixedSize(true);
        adapter = new NewsGossipAdapter();
        recyclerViewNews.setAdapter(adapter);
        newsSwipeToRefresh.setOnRefreshListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_gossips;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mCineNewsGossipsModel.getNewsGossip() == null || ListUtils.isEmpty(mCineNewsGossipsModel.getNewsGossip().getData())) {
            requestNewsGossipList();
        } else {
            try {
                renderNewsGossip();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    private void renderNewsGossip() throws MalformedURLException {
        newsSwipeToRefresh.setRefreshing(false);
        if (adapter != null && mCineNewsGossipsModel.getNewsGossip() != null) {
            adapter.setArticles(mCineNewsGossipsModel.getNewsGossip().getData());
        }
    }

    private void requestNewsGossipList() {
        compositeDisposable.add(RestAPI.getInstance().getNewsGossip()
                .doOnSubscribe(disposable -> {
                    newsSwipeToRefresh.setRefreshing(true);
                })
                .doFinally(() -> newsSwipeToRefresh.setRefreshing(false))
                .subscribe(this::handleNewsGossipData, this::handleMovieFetchError));
    }

    private void startYoutube(String url) throws MalformedURLException {
        videoId = GlobalUtils.extractYoutubeId(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + videoId));
        startActivity(intent);
    }


    private void handleMovieFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleNewsGossipData(NewsGossip data) throws MalformedURLException {
        mCineNewsGossipsModel.setNewsGossip(data);
        renderNewsGossip();
    }


    @Override
    public void onSlideClicked(FeaturedContent item) {

    }

    @Override
    public void onSlideClicked(Links item) {
        try {
            startYoutube(item.getUrl());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRefresh() {
        requestNewsGossipList();
    }
}
