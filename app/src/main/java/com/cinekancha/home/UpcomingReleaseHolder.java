package com.cinekancha.home;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;
import com.cinekancha.entities.model.Movie;

import java.util.List;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class UpcomingReleaseHolder extends HomeItemHolder {
	
	@BindView(R.id.movies)
	public RecyclerView upcomingMoviesList;
	private final MovieAdapter adapter;
	
	public UpcomingReleaseHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
		super(baseRecyclerAdapter, view);
		setIsRecyclable(false);
		adapter = new MovieAdapter();
		upcomingMoviesList.setAdapter(adapter);
		upcomingMoviesList.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
	}
	
	public void setMovies(List<Movie> movies) {
		adapter.setMovies(movies);
	}
	
}
