package com.cinekancha.trolls;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.activities.base.PaginationNestedOnScrollListener;
import com.cinekancha.adapters.base.RecyclerViewClickListener;
import com.cinekancha.entities.model.Troll;
import com.cinekancha.entities.model.TrollData;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.view.CineTrollViewModel;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.net.MalformedURLException;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class TrollListActivity extends BaseNavigationActivity implements RecyclerViewClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.list_view)
    public RecyclerView mArticleList;
    @BindView(R.id.homeSwipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    private PaginationNestedOnScrollListener paginationNestedOnScrollListener;

    private CineTrollViewModel mCineTrollViewModel;
    private TrollAdapter mTrollAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCineTrollViewModel = ViewModelProviders.of(this).get(CineTrollViewModel.class);
        getSupportActionBar().setTitle(R.string.troll);
        init();

        if (mCineTrollViewModel.getTrollDataList() == null) {
            requestArticles();
        } else {
            renderData();
        }
    }

    private void init() {
        swipeRefreshLayout.setOnRefreshListener(this);
        mTrollAdapter = new TrollAdapter();
        mArticleList.setLayoutManager(new LinearLayoutManager(this));
        mArticleList.setNestedScrollingEnabled(false);
        mArticleList.setAdapter(mTrollAdapter);
        mTrollAdapter.setOnClickListener(this);
        paginationNestedOnScrollListener = new PaginationNestedOnScrollListener(mArticleList, (LinearLayoutManager) mArticleList.getLayoutManager(), mCineTrollViewModel) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                requestArticles();
            }
        };
        nestedScrollView.setOnScrollChangeListener(paginationNestedOnScrollListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_general_list;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void renderData() {
        if (mCineTrollViewModel.isToAppend()) {
            mTrollAdapter.addTrollList(mCineTrollViewModel.getAppendTrollDataList());
            mCineTrollViewModel.setToAppend(false);
        } else {
            mTrollAdapter.setTrollList(mCineTrollViewModel.getTrollDataList());
            mCineTrollViewModel.setToAppend(false);
        }
    }

    private void requestArticles() {
            compositeDisposable.add(RestAPI.getInstance().getTroll(mCineTrollViewModel.getCurrentPage())
                    .doOnSubscribe(disposable -> {
                        swipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> swipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleDatabase, this::handleMovieFetchError));

    }

    private void handleDatabase(Troll data) throws MalformedURLException {
        handleTrollData(data);
    }


    private void handleMovieFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleTrollData(Troll troll) throws MalformedURLException {
        if (troll != null && troll.getData() != null) {
            mCineTrollViewModel.setTrollDataList(troll.getData());
            mCineTrollViewModel.setAppendTrollDataList(troll.getData());
            mCineTrollViewModel.setLastPage(troll.getMeta().getLastPage());
            renderData();
        } else Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v, int position) {
        new ImageViewer.Builder<>(this, mCineTrollViewModel.getTrollDataList())
                .setFormatter(TrollData::getImageUrl)
                .setStartPosition(position).show();
    }

    @Override
    public void onRefresh() {
        mCineTrollViewModel.resetState();
        paginationNestedOnScrollListener.resetState();
        requestArticles();
    }
}
