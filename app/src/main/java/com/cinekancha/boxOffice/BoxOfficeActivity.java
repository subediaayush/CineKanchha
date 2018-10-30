package com.cinekancha.boxOffice;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.entities.model.BoxOfficeItem;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.movieDetail.MoviePostDetailActivity;
import com.cinekancha.utils.Connectivity;
import com.cinekancha.view.CineBoxOfficeViewModel;

import java.util.List;

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
        getSupportActionBar().setTitle(R.string.boxOffice);
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
        if (cineBoxOfficeViewModel.getBoxOffice() != null && cineBoxOfficeViewModel.getBoxOffice().size() > 0) {
            adapter = new BoxOfficeAdapter(cineBoxOfficeViewModel.getBoxOffice(), this);
            recyclerView.setAdapter(adapter);
        } else requestBoxOffice();
    }

    private void requestBoxOffice() {
        if (Connectivity.isConnected(this))
            compositeDisposable.add(RestAPI.getInstance().getBOxOffice()
                    .doOnSubscribe(disposable -> {
                        swipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> swipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleDatabase, this::handleMovieFetchError));

    }

    private void handleDatabase(List<BoxOfficeItem> boxOffice) {
        handleBoxOfficeData(boxOffice);
    }

    private void handleMovieFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleBoxOfficeData(List<BoxOfficeItem> boxOffice) {
        if (boxOffice.size() > 0 && boxOffice != null) {
            cineBoxOfficeViewModel.setBoxOffice(boxOffice);
            renderBoxOfficeData();
        } else
            Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(int id) {
        Intent detail = new Intent(this, MoviePostDetailActivity.class);
        detail.putExtra("movie", String.valueOf(cineBoxOfficeViewModel.getBoxOffice().get(id).getMovieId()));
        startActivity(detail);
    }

    @Override
    public void onRefresh() {
        requestBoxOffice();
    }
}
