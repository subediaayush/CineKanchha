package com.cinekancha.boxOffice;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.entities.model.BoxOffice;
import com.cinekancha.entities.model.BoxOfficeItem;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.movies.MoviesAdapter;
import com.cinekancha.view.CineBoxOfficeViewModel;
import com.cinekancha.view.CineMovieViewModel;

import java.net.MalformedURLException;

import butterknife.BindView;

public class BoxOfficeActivity extends BaseNavigationActivity implements OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.boxOfficeRecyclerview)
    public RecyclerView recyclerView;

    @BindView(R.id.swipeRefreshLayout)
    public SwipeRefreshLayout swipeRefreshLayout;

    private CineBoxOfficeViewModel cineBoxOfficeViewModel;

    private BoxOfficeAdapter adapter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_box_office;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cineBoxOfficeViewModel = ViewModelProviders.of(this).get(CineBoxOfficeViewModel.class);
        init();
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cineBoxOfficeViewModel.getBoxOffice() == null) {
            requestBoxOffice();
        } else {
            renderBoxOfficeData();
        }
    }

    private void renderBoxOfficeData() {
        if (cineBoxOfficeViewModel.getBoxOffice().getData() != null && cineBoxOfficeViewModel.getBoxOffice().getData().size() > 0) {
            adapter = new BoxOfficeAdapter(cineBoxOfficeViewModel.getBoxOffice().getData(), this);
            recyclerView.setAdapter(adapter);
        } else requestBoxOffice();
    }

    private void requestBoxOffice() {
        compositeDisposable.add(RestAPI.getInstance().getBOxOffice()
                .doOnSubscribe(disposable -> {
                    swipeRefreshLayout.setRefreshing(true);
                })
                .doFinally(() -> swipeRefreshLayout.setRefreshing(false))
                .subscribe(this::handleBoxOfficeData, this::handleMovieFetchError));
    }

    private void handleMovieFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleBoxOfficeData(BoxOffice boxOffice) {
        cineBoxOfficeViewModel.setBoxOffice(boxOffice);
        renderBoxOfficeData();
    }

    @Override
    public void onClick(int id) {

    }

    @Override
    public void onRefresh() {
        requestBoxOffice();
    }
}
