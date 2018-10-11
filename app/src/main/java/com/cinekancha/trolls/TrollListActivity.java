package com.cinekancha.trolls;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.adapters.base.RecyclerViewClickListener;
import com.cinekancha.entities.model.TrendingData;
import com.cinekancha.entities.model.Troll;
import com.cinekancha.entities.model.TrollData;
import com.cinekancha.entities.rest.GetDataRepository;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.entities.rest.SetDataRepository;
import com.cinekancha.utils.Connectivity;
import com.cinekancha.view.CineTrollViewModel;
import com.stfalcon.frescoimageviewer.ImageViewer;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class TrollListActivity extends BaseNavigationActivity implements RecyclerViewClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.list_view)
    public RecyclerView mArticleList;
    @BindView(R.id.homeSwipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;

    private CineTrollViewModel mCineTrollViewModel;
    private TrollAdapter mTrollAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCineTrollViewModel = ViewModelProviders.of(this).get(CineTrollViewModel.class);

        mTrollAdapter = new TrollAdapter();

        mArticleList.setLayoutManager(new LinearLayoutManager(this));
        mArticleList.setNestedScrollingEnabled(false);
        mArticleList.setAdapter(mTrollAdapter);
        mTrollAdapter.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_general_list;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mCineTrollViewModel.getTrolls() == null) {
            requestArticles(null, 50);
        } else {
            renderData();
        }
    }

    private void renderData() {
        mTrollAdapter.setTrolls(mCineTrollViewModel.getTrolls());

    }

    private void requestArticles(String cursor, int count) {
        if (Connectivity.isConnected(this))
            compositeDisposable.add(RestAPI.getInstance().getTroll()
                    .doOnSubscribe(disposable -> {
                        swipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> swipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleDatabase, this::handleMovieFetchError));
        else
            compositeDisposable.add(GetDataRepository.getInstance().getTroll()
                    .doOnSubscribe(disposable -> {
                        swipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> swipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleTrollData, this::handleMovieFetchError));
    }

    private void handleDatabase(Troll data) {
        compositeDisposable.add(SetDataRepository.getInstance().setTroll(data).toObservable()
                .doOnSubscribe(disposable -> {
                })
                .doFinally(() -> {
                })
                .subscribe(this::handleTrollData, this::handleMovieFetchError));
    }


    private void handleMovieFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleTrollData(Troll troll) {
        if (troll != null && troll.getData() != null) {
            mCineTrollViewModel.setArticles(troll.getData());
            renderData();
        } else Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v, int position) {
        new ImageViewer.Builder<>(this, mCineTrollViewModel.getTrolls())
                .setFormatter(TrollData::getImageUrl)
                .setStartPosition(position).show();
    }

    @Override
    public void onRefresh() {
        requestArticles(null, 50);
    }
}
