package com.cinekancha.home;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class FeaturedNewsHolder extends HomeItemHolder {
	
	@BindView(R.id.image)
	public AppCompatImageView image;
	
	@BindView(R.id.title)
	public TextView title;
	
	@BindView(R.id.summary)
	public TextView summary;
	
	public FeaturedNewsHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
		super(baseRecyclerAdapter, view);
		view.setOnClickListener(this);
	}
}
