package com.cinekancha.poll;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.activities.base.PaginationNestedOnScrollListener;
import com.cinekancha.entities.model.Poll;
import com.cinekancha.entities.model.PollData;
import com.cinekancha.entities.model.UserPoll;
import com.cinekancha.entities.rest.GetDataRepository;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.entities.rest.SetDataRepository;
import com.cinekancha.listener.OnPollClickListener;
import com.cinekancha.view.CinePollViewModel;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
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
    private List<UserPoll> userPollList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cinePollViewModel = ViewModelProviders.of(this).get(CinePollViewModel.class);
        init();
        if (cinePollViewModel.getPollDataList() == null) {
            requestPoll();
        } else {
            renderPollData();
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
                requestPoll();
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

    private void renderPollData() {
        if (cinePollViewModel.isToAppend()) {
            adapter.addPollDataList(cinePollViewModel.getAppendPollDataList());
            cinePollViewModel.setToAppend(false);
        } else {
            adapter.setPollDataList(cinePollViewModel.getPollDataList());
            cinePollViewModel.setToAppend(false);
        }
    }

    private List<PollData> checkPollData(List<PollData> polls) {
        if (userPollList != null && userPollList.size() > 0) {
            for (UserPoll item : userPollList) {
                for (int i = 0; i < polls.size(); i++) {
                    if (item.getPollId() == polls.get(i).getId()) {
                        polls.get(i).setStatus("INACTIVE");
                    }
                }
            }
        }
        return polls;
    }

    private void requestPoll() {
        compositeDisposable.add(RestAPI.getInstance().getPoll(cinePollViewModel.getCurrentPage())
                .doOnSubscribe(disposable -> {
                    homeSwipeRefreshLayout.setRefreshing(true);
                })
                .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                .subscribe(this::handlePollData, this::handlePollFetchError));

    }

    private void handlePollFetchError(Throwable throwable) {
        throwable.printStackTrace();
        Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handlePollData(Poll data) throws MalformedURLException {
        compositeDisposable.add(GetDataRepository.getInstance().getPollDatabase().toObservable()
                .doOnSubscribe(disposable -> {
                    homeSwipeRefreshLayout.setRefreshing(true);
                })
                .doFinally(() -> homeSwipeRefreshLayout.setRefreshing(false))
                .subscribe(this::handlePollDatabase, this::handlePollFetchError));

        if (data != null && data.getData() != null) {
            List<PollData> checkedPollData = checkPollData(data.getData());
            cinePollViewModel.setPollDataList(checkedPollData);
            cinePollViewModel.setAppendPollDataList(checkedPollData);
            cinePollViewModel.setLastPage(data.getMeta().getLastPage());
            renderPollData();
        } else Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
    }

    private void handlePollDatabase(List<UserPoll> userPolls) {
        if (userPollList == null) {
            userPollList = userPolls;
        } else {
            userPollList.clear();
            userPollList.addAll(userPolls);
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
                .subscribe(this::handlePostPoll, this::handlePollFetchError));
    }

    private void handlePostPoll(Response<Void> data) throws MalformedURLException {
        Log.d("ResponseCode", String.valueOf(data));
        if (data.code() == 200) {
            UserPoll userPoll = new UserPoll();
            userPoll.setOptionId(optionId);
            userPoll.setPollId(pollId);
            compositeDisposable.add(SetDataRepository.getInstance().setPollDatabase(userPoll).toObservable()
                    .doOnSubscribe(disposable -> {
                    })
                    .doFinally(() -> {
                    })
                    .subscribe(this::notifyDataPoll, this::handlePollFetchError));
        }

    }
    
    private void notifyDataPoll(Object o) {
        onRefresh();
    }
    
    @Override
    public void onRefresh() {
        cinePollViewModel.resetState();
        paginationNestedOnScrollListener.resetState();
        requestPoll();
    }
}
