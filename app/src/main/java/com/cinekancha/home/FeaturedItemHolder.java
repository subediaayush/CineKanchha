package com.cinekancha.home;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
	public SlideShowAdapter adapter;
	
	public FeaturedItemHolder(AppCompatActivity activity, BaseRecyclerAdapter baseRecyclerAdapter, View view) {
		super(baseRecyclerAdapter, view);
		setIsRecyclable(false);
		
		adapter = new SlideShowAdapter(activity.getSupportFragmentManager(), featuredPager);
		featuredPager.setAdapter(adapter);
	}
}
