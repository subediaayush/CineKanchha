package com.cinekancha.home;

import android.view.View;
import android.widget.TextView;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class FeaturedNewsHolder extends HomeItemHolder {
	
	@BindView(R.id.news)
	public TextView title;
	
	public FeaturedNewsHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
		super(baseRecyclerAdapter, view);
		view.setOnClickListener(this);
	}
}
