package com.cinekancha.home;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cinekancha.adapters.base.RecyclerViewClickListener;
import com.cinekancha.entities.model.Movie;
import com.cinekancha.utils.ListUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aayushsubedi on 3/15/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MoviesHolder> implements RecyclerViewClickListener {
	
	private List<Movie> mMovies = new ArrayList<>();
	
	private final int mItemLayout;
	
	public MovieAdapter(int layout) {
		this.mItemLayout = layout;
	}
	
	
	@Override
	public MoviesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(mItemLayout, parent, false);
		return new MoviesHolder(this, view);
	}
	
	@Override
	public void onBindViewHolder(MoviesHolder holder, int position) {
		Movie movie = mMovies.get(position);
		holder.title.setText(movie.getName());
		if (holder.subTitle != null) holder.subTitle.setText(movie.getRelease_date());
		if (!TextUtils.isEmpty(movie.getFeaturedImage())) Picasso.with(holder.itemView.getContext())
				.load(movie.getFeaturedImage()).into(holder.thumbnail);
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
	
	@Override
	public void onClick(View v, int position) {
	
	}
}
