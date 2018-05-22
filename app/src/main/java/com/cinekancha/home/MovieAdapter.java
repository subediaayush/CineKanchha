package com.cinekancha.home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cinekancha.R;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aayushsubedi on 3/15/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MoviesHolder> {
	
	private List<Movie> mMovies = new ArrayList<>();
	
	@Override
	public MoviesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.layout_featued_movie, parent, false);
		return new MoviesHolder(view);
	}
	
	@Override
	public void onBindViewHolder(MoviesHolder holder, int position) {
	
	}
	
	@Override
	public int getItemCount() {
		return ListUtils.getSize(mMovies);
	}
	
	public void setMovies(List<Movie> movies) {
		mMovies.clear();
		mMovies.addAll(movies);
		notifyDataSetChanged();
	}
}
