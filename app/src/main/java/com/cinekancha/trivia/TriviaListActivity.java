package com.cinekancha.trivia;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.adapters.base.RecyclerViewClickListener;
import com.cinekancha.view.CineTriviaViewModel;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class TriviaListActivity extends BaseNavigationActivity implements RecyclerViewClickListener {
	@BindView(R.id.list_view)
	public RecyclerView mArticleList;
	
	private CineTriviaViewModel mCineTriviaViewModel;
	private TriviaAdapter mArticleAdapter;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mCineTriviaViewModel = ViewModelProviders.of(this).get(CineTriviaViewModel.class);
		
		mArticleAdapter = new TriviaAdapter();
		
		mArticleList.setLayoutManager(new LinearLayoutManager(this));
		mArticleList.setAdapter(mArticleAdapter);
		mArticleAdapter.setOnClickListener(this);
	}
	
	@Override
	protected int getLayoutId() {
		return R.layout.activity_general_list;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		if (mCineTriviaViewModel.getTrivias() == null) {
			requestArticles(null, 50);
		} else {
			mArticleAdapter.setTrivias(mCineTriviaViewModel.getTrivias());
		}
	}
	
	private void requestArticles(String cursor, int count) {
		// Request article here
	}
	
	@Override
	public void onClick(View v, int position) {
		// Nothing to do, copied from above
	}
}
