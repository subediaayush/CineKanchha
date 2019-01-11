package com.cinekancha.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.article.ArticleDetailActivity;
import com.cinekancha.entities.model.Article;
import com.cinekancha.entities.model.FeaturedContent;
import com.cinekancha.entities.model.HomeData;
import com.cinekancha.entities.model.Links;
import com.cinekancha.entities.model.PollData;
import com.cinekancha.entities.model.UserPoll;
import com.cinekancha.entities.rest.GetDataRepository;
import com.cinekancha.entities.rest.RestAPI;
import com.cinekancha.entities.rest.SetDataRepository;
import com.cinekancha.entities.service.TestService;
import com.cinekancha.home.HomeDataAdapter;
import com.cinekancha.home.OnSlideClickListener;
import com.cinekancha.home.SlideShowAdapter;
import com.cinekancha.listener.OnPollClickListener;
import com.cinekancha.utils.GlobalUtils;
import com.cinekancha.view.CineHomeViewModel;
import com.cinekancha.view.CircleIndicator;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.google.android.material.appbar.AppBarLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import retrofit2.Response;

public class HomeActivity extends BaseNavigationActivity implements OnSlideClickListener, SwipeRefreshLayout.OnRefreshListener, OnPollClickListener {
	private static final String TAG = "HomeActivity";
	
	@BindView(R.id.homeSwipeRefreshLayout)
	protected SwipeRefreshLayout mSwipeRefreshLayout;
	@BindView(R.id.home_list_view)
	protected RecyclerView mHomeListView;
	
	@BindView(R.id.featured_pager)
	protected ViewPager mFeaturedPager;
	
	@BindView(R.id.appbar_layout)
	protected AppBarLayout mAppbarLayout;
	
	@BindView(R.id.indicator)
	protected CircleIndicator mIndicator;
	
	@BindView(R.id.toolbar)
	protected Toolbar toolbar;
	
	private HomeDataAdapter mHomeDataAdapter;
	private CineHomeViewModel mCineHomeViewModel;
	private SlideShowAdapter mSlideAdapter;
	
	private long optionId;
	private long pollId;
	private List<UserPoll> userPollList = new ArrayList();
	
	public static void scheduleJob(Context context) {
		FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
		Job job = createJob(dispatcher);
		dispatcher.mustSchedule(job);
	}
	
	public static Job createJob(FirebaseJobDispatcher dispatcher) {
		final int periodicity = (int) TimeUnit.HOURS.toSeconds(1); // Every 1 hour periodicity expressed as seconds
		final int toleranceInterval = (int) TimeUnit.MINUTES.toSeconds(15); // a small(ish) window of time when triggering is OK
		Job job = dispatcher.newJobBuilder()
				.setLifetime(Lifetime.FOREVER)
				.setService(TestService.class)
				.setTag("UniqueTagForYourJob")
				.setReplaceCurrent(true)
				.setRecurring(true)
				.setTrigger(Trigger.executionWindow(30, 60))
				.setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
				.setConstraints(Constraint.ON_ANY_NETWORK, Constraint.DEVICE_CHARGING)
				.build();
		return job;
	}
	
	public static Job updateJob(FirebaseJobDispatcher dispatcher) {
		Job newJob = dispatcher.newJobBuilder()
				.setReplaceCurrent(true)
				.setService(TestService.class)
				.setTag("UniqueTagForYourJob")
				.setTrigger(Trigger.executionWindow(30, 60))
				.build();
		return newJob;
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.activity_home;
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getSupportActionBar().setTitle("Home");
//        cancelJob(this);
//        scheduleJob(this);
		
		mCineHomeViewModel = ViewModelProviders.of(this).get(CineHomeViewModel.class);
		
		mHomeDataAdapter = new HomeDataAdapter(this);
		mSlideAdapter = new SlideShowAdapter(getSupportFragmentManager(), mFeaturedPager);
		mFeaturedPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageScrollStateChanged(int state) {
				if (state == ViewPager.SCROLL_STATE_IDLE) {
					mSlideAdapter.startSlideshow();
				} else {
					mSlideAdapter.stopSlideshow();
				}
			}
		});
		mSlideAdapter.setOnSlideClickListener(this);
		mSwipeRefreshLayout.setOnRefreshListener(this);
		
		mHomeListView.setLayoutManager(new LinearLayoutManager(this));
		mHomeListView.setAdapter(mHomeDataAdapter);
		
		mHomeListView.setNestedScrollingEnabled(false);
		
		mFeaturedPager.setAdapter(mSlideAdapter);
		
		mIndicator.setViewPager(mFeaturedPager);
		mSlideAdapter.registerDataSetObserver(mIndicator.getDataSetObserver());
	}
	
	public void cancelJob(Context context) {
		
		FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
		dispatcher.cancelAll();
		dispatcher.cancel("UniqueTagForYourJob");
		
	}
	
	private void renderHomeData() {
		checkPollData();
		HomeData data = mCineHomeViewModel.getHomeData();
		if (data == null) return;
		
		mHomeDataAdapter.setHomeData(data);
		mSlideAdapter.setFeaturedItems(data.getFeaturedContents());
		mSlideAdapter.startSlideshow();
	}
	
	private void requestHomeData() {
		compositeDisposable.add(RestAPI.getInstance().getHomeData()
				.doOnSubscribe(disposable -> {
					mSwipeRefreshLayout.setRefreshing(true);
				})
				.doFinally(() -> mSwipeRefreshLayout.setRefreshing(false))
				.subscribe(this::handleHomeData, this::handleHomeFetchError));
	}
	
	private void handleHomeFetchError(Throwable throwable) {
		throwable.printStackTrace();
		Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
	}
	
	private void checkPollData() {
		HomeData data = mCineHomeViewModel.getHomeData();
		if (data == null) return;
		PollData poll = data.getPoll();
		if (poll == null) return;
		
		if (userPollList != null && userPollList.size() > 0) {
			for (UserPoll item : userPollList) {
				if (item.getPollId() == poll.getId()) {
					mCineHomeViewModel.getHomeData().getPoll().setStatus("INACTIVE");
				}
			}
		}
	}
	
	private void handleHomeData(Response<HomeData> data) {
		compositeDisposable.add(GetDataRepository.getInstance().getPollDatabase().toObservable()
				.doOnSubscribe(disposable -> {
					mSwipeRefreshLayout.setRefreshing(true);
				})
				.doFinally(() -> mSwipeRefreshLayout.setRefreshing(false))
				.subscribe(this::handlePollDatabase, this::handleHomeFetchError));
		if (data != null) {
			mCineHomeViewModel.setHomeData(data.body());
			renderHomeData();
		} else Toast.makeText(this, "Could not load data", Toast.LENGTH_SHORT).show();
	}
	
	private void handlePollDatabase(List<UserPoll> userPolls) {
		if (userPollList == null) {
			userPollList = userPolls;
		} else {
			userPollList.clear();
			userPollList.addAll(userPolls);
		}
		renderHomeData();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mSlideAdapter.stopSlideshow();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		HomeData data = mCineHomeViewModel.getHomeData();
		if (data != null) renderHomeData();
		else requestHomeData();
	}
	
	@Override
	public void onSlideClicked(FeaturedContent item) {
		Log.d(TAG, "clicked on item " + new Gson().toJson(item));
		String deeplink = item.getDeeplink();
		if (TextUtils.isEmpty(deeplink)) {
			Article article = new Article();
			article.setId(item.getId());
			article.setTitle(item.getTitle());
			article.setContent(item.getSubtitle());
			article.setImage(item.getImageUrl());
			ArticleDetailActivity.startActivity(this, article);
		} else {
			GlobalUtils.navigate(this, Uri.parse(deeplink));
		}
	}
	
	@Override
	public void onSlideClicked(Links item) {
	
	}
	
	@Override
	public void onRefresh() {
		requestHomeData();
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
				.subscribe(this::handlePostPoll, this::handleHomeFetchError));
	}
	
	private void handlePostPoll(Response<Void> data) {
		Log.d("ResponseCode", String.valueOf(data));
		if (data.code() == 200) {
			UserPoll pollDatabase = new UserPoll();
			pollDatabase.setOptionId(optionId);
			pollDatabase.setPollId(pollId);
			compositeDisposable.add(SetDataRepository.getInstance().setPollDatabase(pollDatabase).toObservable()
					.doOnSubscribe(disposable -> {
					})
					.doFinally(() -> {
					})
					.subscribe(this::notifyDataPoll, this::handleHomeFetchError));
		}
		
	}
	
	private void notifyDataPoll(List<Long> longs) {
		requestHomeData();
	}
	
}
