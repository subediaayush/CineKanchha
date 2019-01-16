package com.cinekancha.boxOffice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.entities.model.BoxOfficeData;
import com.cinekancha.entities.model.MovieBoxOffice;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.movieDetail.MoviePostDetailActivity;
import com.cinekancha.view.CineBoxOfficeViewModel;

import java.util.List;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
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

    private void handleBoxOfficeData(BoxOfficeData data) {
        List<MovieBoxOffice> boxOffice = data.getData();
        if (boxOffice.size() > 0) {
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
