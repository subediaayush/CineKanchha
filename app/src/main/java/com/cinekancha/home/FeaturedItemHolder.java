package com.cinekancha.home;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.cinekancha.R;
import com.cinekancha.adapters.base.BaseRecyclerAdapter;

import butterknife.BindView;

/**
 * Created by aayushsubedi on 3/8/18.
 */

public class FeaturedItemHolder extends HomeItemHolder {
	@BindView(R.id.pager)
	public ViewPager featuredPager;
	
	public FeaturedItemHolder(BaseRecyclerAdapter baseRecyclerAdapter, View view) {
		super(baseRecyclerAdapter, view);
		setIsRecyclable(false);
	}
}
