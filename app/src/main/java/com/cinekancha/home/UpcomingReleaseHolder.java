package com.cinekancha.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class UpcomingReleaseHolder extends HomeItemHolder {
	
	@BindView(R.id.movies)
	public RecyclerView upcomingMoviesList;
	
	public UpcomingReleaseHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
		super(baseRecyclerAdapter, view);
		setIsRecyclable(false);
	}
}
