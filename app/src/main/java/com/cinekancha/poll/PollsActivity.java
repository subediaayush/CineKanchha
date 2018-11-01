package com.cinekancha.poll;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.activities.base.PaginationNestedOnScrollListener;
import com.cinekancha.entities.model.Poll;
import com.cinekancha.entities.model.PollData;
import com.cinekancha.entities.model.PollDatabase;
import com.cinekancha.entities.rest.GetDataRepository;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.entities.rest.SetDataRepository;
import com.cinekancha.listener.OnPollClickListener;
import com.cinekancha.view.CinePollViewModel;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import retrofit2.Response;

public class PollsActivity extends BaseNavigationActivity implements OnPollClickListener, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.pollRecyclerView)
    public RecyclerView recyclerView;
    @BindView(R.id.homeSwipeRefreshLayout)
    public SwipeRefreshLayout homeSwipeRefreshLayout;
    @BindView(R.id.toolbar)
    protected Toolbar toolbar;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;

    private PaginationNestedOnScrollListener paginationNestedOnScrollListener;

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
        if (cinePollViewModel.getPollDataList() == null) {
            requestMovie();
        } else {
            try {
                renderMovieData();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() {
        getSupportActionBar().setTitle(R.string.poll);
        homeSwipeRefreshLayout.setOnRefreshListener(this);
        adapter = new PollAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        paginationNestedOnScrollListener = new PaginationNestedOnScrollListener(recyclerView, (LinearLayoutManager) recyclerView.getLayoutManager(), cinePollViewModel) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                requestMovie();
            }
        };
        nestedScrollView.setOnScrollChangeListener(paginationNestedOnScrollListener);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_polls;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void renderMovieData() throws MalformedURLException {
        if (cinePollViewModel.isToAppend()) {
            adapter.addPollDataList(cinePollViewModel.getAppendPollDataList());
            cinePollViewModel.setToAppend(false);
        } else {
            adapter.setPollDataList(cinePollViewModel.getPollDataList());
            cinePollViewModel.setToAppend(false);
        }
    }

    private List<PollData> checkPollData(List<PollData> polls) {
        if (pollDatabaseList != null && pollDatabaseList.size() > 0) {
            for (PollDatabase item : pollDatabaseList) {
                for (int i = 0; i < polls.size(); i++) {
                    if (item.getPollId() == polls.get(i).getId()) {
                        polls.get(i).setStatus("INACTIVE");
                    }
                }
            }
        }
        return polls;
    }

    private void requestMovie() {
            compositeDisposable.add(RestAPI.getInstance().getPoll(cinePollViewModel.getCurrentPage())
                    .doOnSubscribe(disposable -> {
                        homeSwipeRefreshLayout.setRefreshing(true);
                    })
                    .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                    .subscribe(this::handleDatabase, this::handleMovieFetchError));
        
//            compositeDisposable.add(GetDataRepository.getInstance().getPollData()
//                    .doOnSubscribe(disposable -> {
//                        homeSwipeRefreshLayout.setRefreshing(true);
//                    })
//                    .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
//                    .subscribe(this::handleMovieData, this::handleMovieFetchError));
    }

    private void handleDatabase(Poll data) throws MalformedURLException {
        handleMovieData(data);
//        compositeDisposable.add(SetDataRepository.getInstance().setPoll(data).toObservable()
//                .doOnSubscribe(disposable -> {
//                })
//                .doFinally(() -> {
//                })
//                .subscribe(this::handleMovieData, this::handleMovieFetchError));
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
            List<PollData> checkedPollData = checkPollData(data.getData());
            cinePollViewModel.setPollDataList(checkedPollData);
            cinePollViewModel.setAppendPollDataList(checkedPollData);
            cinePollViewModel.setLastPage(data.getMeta().getLastPage());
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
        Log.d("ResponseCode", String.valueOf(data));
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
       onRefresh();
    }

    @Override
    public void onRefresh() {
        cinePollViewModel.resetState();
        paginationNestedOnScrollListener.resetState();
        requestMovie();
    }
}
