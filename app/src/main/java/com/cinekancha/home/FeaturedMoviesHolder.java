package com.cinekancha.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class FeaturedMoviesHolder extends HomeItemHolder {
	
	@BindView(R.id.image)
	public ImageView image;
	
	@BindView(R.id.title)
	public TextView title;
	
	public FeaturedMoviesHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
		super(baseRecyclerAdapter, view);
		view.setOnClickListener(this);
	}
}