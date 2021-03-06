package com.cinekancha.newsGossips;

import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.activities.base.PaginationNestedOnScrollListener;
import com.cinekancha.article.ArticleDetailActivity;
import com.cinekancha.entities.model.FeaturedContent;
import com.cinekancha.entities.model.Links;
import com.cinekancha.entities.model.NewsGossip;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.home.OnSlideClickListener;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.utils.GlobalUtils;
import com.cinekancha.view.CineNewsGossipsViewModel;

import java.net.MalformedURLException;

import butterknife.BindView;

public class NewsGossipsActivity extends BaseNavigationActivity implements OnSlideClickListener, SwipeRefreshLayout.OnRefreshListener, OnClickListener {
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.newsSwipeToRefresh)
    public SwipeRefreshLayout newsSwipeToRefresh;

    @BindView(R.id.recyclerViewNews)
    public RecyclerView recyclerViewNews;

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    private CineNewsGossipsViewModel mCineNewsGossipsModel;
    private NewsGossipAdapter adapter;
    private String videoId = "";
    private PaginationNestedOnScrollListener paginationNestedOnScrollListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCineNewsGossipsModel = ViewModelProviders.of(this).get(CineNewsGossipsViewModel.class);
        init();
        if (mCineNewsGossipsModel.getNewsGossipList() == null) {
            requestNewsGossipList();
        } else {
            renderNewsGossip();
        }
    }

    private void init() {
        getSupportActionBar().setTitle(R.string.newsGossips);
        newsSwipeToRefresh.setOnRefreshListener(this);
        adapter = new NewsGossipAdapter(this);
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewNews.setNestedScrollingEnabled(false);
        recyclerViewNews.setHasFixedSize(true);
        recyclerViewNews.setAdapter(adapter);
        paginationNestedOnScrollListener = new PaginationNestedOnScrollListener(recyclerViewNews, (LinearLayoutManager) recyclerViewNews.getLayoutManager(), mCineNewsGossipsModel) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                requestNewsGossipList();
            }
        };
        nestedScrollView.setOnScrollChangeListener(paginationNestedOnScrollListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_gossips;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void renderNewsGossip() {
        if (mCineNewsGossipsModel.isToAppend()) {
            adapter.addNewsGossipList(mCineNewsGossipsModel.getAppendNewsGossipList());
            mCineNewsGossipsModel.setToAppend(false);
        } else {
            adapter.setNewsGossipList(mCineNewsGossipsModel.getNewsGossipList());
            mCineNewsGossipsModel.setToAppend(false);
        }
    }

    private void requestNewsGossipList() {
        compositeDisposable.add(RestAPI.getInstance().getNewsGossip(mCineNewsGossipsModel.getCurrentPage())
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

    private void handleNewsGossipData(NewsGossip data) {
        if (data != null && data.getData() != null) {
            mCineNewsGossipsModel.setNewsGossipList(data.getData());
            mCineNewsGossipsModel.setAppendNewsGossipList(data.getData());
            mCineNewsGossipsModel.setLastPage(data.getMeta().getLastPage());
            renderNewsGossip();
        } else Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
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
        mCineNewsGossipsModel.resetState();
        paginationNestedOnScrollListener.resetState();
        requestNewsGossipList();
    }


    @Override
    public void onClick(int id) {
        ArticleDetailActivity.startActivity(this, mCineNewsGossipsModel.getNewsGossipList().get(id));
    }
}
