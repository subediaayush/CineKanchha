package com.cinekancha.newsGossips;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.adapters.base.RecyclerViewClickListener;
import com.cinekancha.article.ArticleDetailActivity;
import com.cinekancha.entities.model.FeaturedContent;
import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.model.Links;
import com.cinekancha.entities.model.NewsGossip;
import com.cinekancha.entities.rest.GetDataRepository;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.entities.rest.SetDataRepository;
import com.cinekancha.home.OnSlideClickListener;
import com.cinekancha.utils.Connectivity;
import com.cinekancha.utils.GlobalUtils;
import com.cinekancha.utils.ListUtils;
import com.cinekancha.view.CineNewsGossipsViewModel;

import java.net.MalformedURLException;

import butterknife.BindView;

public class NewsGossipsActivity extends BaseNavigationActivity implements OnSlideClickListener, SwipeRefreshLayout.OnRefreshListener, RecyclerViewClickListener {
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
        init();
    }

    private void init() {
        getSupportActionBar().setTitle(R.string.newReleases);
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewNews.setNestedScrollingEnabled(false);
        recyclerViewNews.setHasFixedSize(true);
        adapter = new NewsGossipAdapter();
        adapter.setOnClickListener(this);
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
        } else if (Connectivity.isConnected(this))
            requestNewsGossipList();
        else
            Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void requestNewsGossipList() {
        if (Connectivity.isConnected(this)) {
            compositeDisposable.add(RestAPI.getInstance().getNewsGossip()
                    .doOnSubscribe(disposable -> {
                        newsSwipeToRefresh.setRefreshing(true);
                    })
                    .doFinally(() -> newsSwipeToRefresh.setRefreshing(false))
                    .subscribe(this::handleDatabase, this::handleMovieFetchError));
        } else {
            compositeDisposable.add(GetDataRepository.getInstance().getNewsGossip()
                    .doOnSubscribe(disposable -> {
                        newsSwipeToRefresh.setRefreshing(true);
                    })
                    .doFinally(() -> newsSwipeToRefresh.setRefreshing(false))
                    .subscribe(this::handleNewsGossipData, this::handleMovieFetchError));
        }
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
        if (data != null && data.getData() != null) {
            mCineNewsGossipsModel.setNewsGossip(data);
            renderNewsGossip();
        } else Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleDatabase(NewsGossip data) {
        compositeDisposable.add(SetDataRepository.getInstance().setNewsGossip(data).toObservable()
                .doOnSubscribe(disposable -> {
                })
                .doFinally(() -> {
                })
                .subscribe(this::handleNewsGossipData, this::handleMovieFetchError));
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

    @Override
    public void onClick(View v, int position) {
        if (v.getId() == R.id.holder) {
            ArticleDetailActivity.startActivity(this, mCineNewsGossipsModel.getNewsGossip().getData().get(position));
        }
    }
}
