package com.cinekancha.trolls;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cinekancha.R;
import com.cinekancha.activities.base.BaseNavigationActivity;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.entities.model.Troll;
import com.cinekancha.view.CineTrollViewModel;
import com.stfalcon.frescoimageviewer.ImageViewer;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/19/18.
 */

public class TrollListActivity extends BaseNavigationActivity implements BaseRecyclerAdapter.RecyclerViewClickListener {
	@BindView(R.id.list_view)
	public RecyclerView mArticleList;
	
	private CineTrollViewModel mCineTrollViewModel;
	private TrollAdapter mTrollAdapter;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mCineTrollViewModel = ViewModelProviders.of(this).get(CineTrollViewModel.class);
		
		mTrollAdapter = new TrollAdapter();
		
		mArticleList.setLayoutManager(new LinearLayoutManager(this));
		mArticleList.setAdapter(mTrollAdapter);
		mTrollAdapter.setOnClickListener(this);
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
			mTrollAdapter.setTrolls(mCineTrollViewModel.getTrolls());
		}
	}
	
	private void requestArticles(String cursor, int count) {
		// Request article here
	}
	
	@Override
	public void onClick(View v, int position) {
		new ImageViewer.Builder<>(this, mCineTrollViewModel.getTrolls())
				.setFormatter(Troll::getImage)
				.setStartPosition(position).show();
	}
}
