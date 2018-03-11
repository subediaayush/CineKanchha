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

public class FeaturedNewsListHolder extends HomeItemHolder {
	
	@BindView(R.id.image)
	public ImageView image;
	
	@BindView(R.id.title)
	public TextView title;
	
	@BindView(R.id.summary)
	public TextView summary;
	
	public FeaturedNewsListHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
		super(baseRecyclerAdapter, view);
		view.setOnClickListener(this);
	}
}
