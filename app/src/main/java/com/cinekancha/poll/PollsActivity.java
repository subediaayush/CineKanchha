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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Response;

public class PollsActivity extends BaseNavigationActivity implements OnPollClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.pollRecyclerView)
    public RecyclerView recyclerView;

    @BindView(R.id.homeSwipeRefreshLayout)
    public SwipeRefreshLayout homeSwipeRefreshLayout;

    private CinePollViewModel cinePollViewModel;

    private PollAdapter adapter;
    private long optionId;
    private long pollId;
    private List<PollDatabase> pollDatabaseList = new ArrayList();

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
            checkPollData();
            adapter = new PollAdapter(cinePollViewModel.getPollData().getData(), this);
            recyclerView.setAdapter(adapter);
        } else requestMovie();
    }

    private void checkPollData() {
        if (pollDatabaseList != null && pollDatabaseList.size() > 0) {
            for (PollDatabase item : pollDatabaseList) {
                for (int i = 0; i < cinePollViewModel.getPollData().getData().size(); i++) {
                    if (item.getPollId() == cinePollViewModel.getPollData().getData().get(i).getId()) {
                        cinePollViewModel.getPollData().getData().get(i).setStatus("INACTIVE");
                    }
                }
            }
        }
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
        compositeDisposable.add(GetDataRepository.getInstance().getPollDatabase()
                .doOnSubscribe(disposable -> {
                    homeSwipeRefreshLayout.setRefreshing(true);
                })
                .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                .subscribe(this::handlePollDatabase, this::handleMovieFetchError));
        if (data != null && data.getData() != null) {
            cinePollViewModel.setPollData(data);
            renderMovieData();
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
    public void onClick(int optionId, int pollId) {
        this.pollId = pollId;
        this.optionId = optionId;
        compositeDisposable.add(RestAPI.getInstance().postPoll(optionId)
                .doOnSubscribe(disposable -> {
                })
                .doFinally(() -> {
                })
                .subscribe(this::handlePostPoll, this::handleMovieFetchError));
    }

    private void handlePostPoll(Response<Void> data) throws MalformedURLException {
        Log.d("ResponseCode",String.valueOf(data));
        if (data.code() == 200) {
            PollDatabase pollDatabase = new PollDatabase();
            pollDatabase.setOptionId(optionId);
            pollDatabase.setPollId(pollId);
            compositeDisposable.add(SetDataRepository.getInstance().setPollDatabase(pollDatabase).toObservable()
                    .doOnSubscribe(disposable -> {
                    })
                    .doFinally(() -> {
                    })
                    .subscribe(this::notifyDataPoll, this::handleMovieFetchError));
        }

    }

    private void notifyDataPoll(PollDatabase pollDatabase) {
        requestMovie();

    }

    @Override
    public void onRefresh() {
        requestMovie();
    }
}
