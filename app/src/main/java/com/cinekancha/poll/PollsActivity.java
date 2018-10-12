package com.cinekancha.poll;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.entities.model.NewRelease;
import com.cinekancha.entities.model.Poll;
import com.cinekancha.entities.model.PollDatabase;
import com.cinekancha.entities.rest.GetDataRepository;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.entities.rest.SetDataRepository;
import com.cinekancha.listener.OnClickListener;
import com.cinekancha.listener.OnPollClickListener;
import com.cinekancha.utils.Connectivity;
import com.cinekancha.view.CinePollViewModel;

import java.net.MalformedURLException;

import butterknife.BindView;

public class PollsActivity extends BaseNavigationActivity implements OnPollClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.pollRecyclerView)
    public RecyclerView recyclerView;

    @BindView(R.id.homeSwipeRefreshLayout)
    public SwipeRefreshLayout homeSwipeRefreshLayout;

    private CinePollViewModel cinePollViewModel;

    private PollAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cinePollViewModel = ViewModelProviders.of(this).get(CinePollViewModel.class);
        init();
    }

    private void init() {
        getSupportActionBar().setTitle(R.string.poll);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        homeSwipeRefreshLayout.setOnRefreshListener(this);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_polls;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cinePollViewModel.getPollData() == null) {
            requestMovie();
        } else {
            renderMovieData();
        }
    }

    private void renderMovieData() {
        if (cinePollViewModel.getPollData().getData() != null && cinePollViewModel.getPollData().getData().size() > 0) {
            adapter = new PollAdapter(cinePollViewModel.getPollData().getData(), this);
            recyclerView.setAdapter(adapter);
        } else requestMovie();
    }

    private void requestMovie() {
        if (Connectivity.isConnected(this))
            compositeDisposable.add(RestAPI.getInstance().getPoll()
                    .doOnSubscribe(disposable -> {
                        homeSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleDatabase, this::handleMovieFetchError));
        else
            compositeDisposable.add(GetDataRepository.getInstance().getPollData()
                    .doOnSubscribe(disposable -> {
                        homeSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleMovieData, this::handleMovieFetchError));
    }

    private void handleDatabase(Poll data) {
        compositeDisposable.add(SetDataRepository.getInstance().setPoll(data).toObservable()
                .doOnSubscribe(disposable -> {
                })
                .doFinally(() -> {
                })
                .subscribe(this::handleMovieData, this::handleMovieFetchError));
    }

    private void handleMovieFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handleMovieData(Poll data) throws MalformedURLException {
        if (data != null && data.getData() != null) {
            cinePollViewModel.setPollData(data);
            renderMovieData();
        } else Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onClick(int optionId, int pollId) {
        PollDatabase pollDatabase = new PollDatabase();
        pollDatabase.setOptionId(optionId);
        pollDatabase.setPollId(optionId);
        compositeDisposable.add(SetDataRepository.getInstance().setPollDatabase(pollDatabase).toObservable()
                .doOnSubscribe(disposable -> {
                })
                .doFinally(() -> {
                })
                .subscribe(this::handle, this::handleMovieFetchError));
    }

    private void handle(PollDatabase data) throws MalformedURLException {
         Log.d("Database", "Saved data");
    }

    @Override
    public void onRefresh() {
        requestMovie();
    }
}
